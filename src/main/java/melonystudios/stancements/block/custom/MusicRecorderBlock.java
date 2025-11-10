package melonystudios.stancements.block.custom;

import com.mojang.serialization.MapCodec;
import melonystudios.reutilities.api.ReAPI;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import melonystudios.stancements.component.custom.MusicData;
import melonystudios.stancements.mixin.CurrentMusicAccessor;
import melonystudios.stancements.util.tag.STItemTags;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MusicRecorderBlock extends BaseEntityBlock {
    public static final BooleanProperty RECORDING = STBlockStateProperties.RECORDING;
    public static final Component NO_MUSIC_PLAYING_TEXT = Component.translatable("tooltip.stancements.no_music_playing").withStyle(ChatFormatting.GRAY);
    public static final Component CANNOT_COPY_TEXT = Component.translatable("tooltip.stancements.cannot_copy").withStyle(ChatFormatting.GRAY);

    public MusicRecorderBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(RECORDING, false));
    }

    @Override
    @NotNull
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(MusicRecorderBlock::new);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(world, pos, state, placer, stack);

        CustomData data = stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
        CompoundTag tag = data.copyTag();
        if (tag.contains("ticks_until_finished_recording", Tag.TAG_ANY_NUMERIC) && tag.getInt("ticks_until_finished_recording") >= 0) {
            world.setBlock(pos, state.setValue(RECORDING, true), 3);
        }
    }

    @Override
    @NotNull
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder) {
//            if (recorder.isEmpty()) world.setBlock(pos, state.setValue(RECORDING, false), 3);
            if (!recorder.isEmpty()) {
                this.stopRecording(world, pos, true);
                world.setBlock(pos, state.setValue(RECORDING, false), 3);
                world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
                return InteractionResult.sidedSuccess(!world.isClientSide());
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    @NotNull
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        // todo: this will likely crash if run on a dedicated server, but how will the server know about the song? ~isa 8-11-25
        if (!state.getValue(RECORDING) && stack.is(STItemTags.RECORDABLE_DISCS) && world.getBlockEntity(pos) instanceof MusicRecorderBlockEntity recorder && recorder.isEmpty()) {
            SoundInstance currentMusic = ((CurrentMusicAccessor) Minecraft.getInstance().getMusicManager()).stancements$getCurrentMusic();
            ItemStack handStack = player.getItemInHand(hand);
            ItemStack splitStack = handStack.consumeAndReturn(1, player);

            if (currentMusic != null) {
                // always record current song first
                this.startRecording(world, state, pos, player, splitStack, currentMusic);
            } else {
                // if none is playing, try recording from an adjacent jukebox
                this.tryRecordingFromAdjacentJukebox(world, state, pos, player, splitStack);
            }
            return ItemInteractionResult.sidedSuccess(!world.isClientSide());
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public void startRecording(Level world, BlockState state, BlockPos pos, @Nullable Player player, ItemStack discStack, @Nullable SoundInstance currentMusic) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder) {
            recorder.insertDisc(discStack.copy());
            if (currentMusic != null && currentMusic.getSound() != null) {
                recorder.startRecording(currentMusic.getSound().getPath(), false, player);
                this.sendMessage(Component.translatable("tooltip.stancements.recording_music").withColor(Stancements.ACCENT_COLOR), player);
                world.setBlock(pos, state.setValue(RECORDING, true), 3);
                world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            } else {
                this.sendMessage(NO_MUSIC_PLAYING_TEXT, player);
            }
        }
    }

    public void tryRecordingFromAdjacentJukebox(Level world, BlockState state, BlockPos pos, @Nullable Player player, ItemStack discStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof MusicRecorderBlockEntity recorder)) return;
        Component errorMessage = NO_MUSIC_PLAYING_TEXT;

        recorder.insertDisc(discStack.copy());
        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = pos.relative(direction);
            BlockState adjacentState = world.getBlockState(adjacentPos);

            if (adjacentState.is(Blocks.JUKEBOX) && world.getBlockEntity(adjacentPos) instanceof JukeboxBlockEntity jukebox) {
                JukeboxSong song = jukebox.getSongPlayer().getSong();
                var jukeboxSongs = world.registryAccess().registry(Registries.JUKEBOX_SONG);

                // block recording if the disc is a copy
                if (jukeboxSongs.isEmpty() || MusicData.isCopied(jukebox.getTheItem())) {
                    errorMessage = CANNOT_COPY_TEXT;
                    break;
                }
                ResourceLocation songLocation = song == null ? null : jukeboxSongs.get().getKey(song);

                if (song != null) {
                    // exact duration of the song, so it always finishes when the song ends
                    int recordingDuration = (int) (song.lengthInTicks() - jukebox.getSongPlayer().getTicksSinceSongStarted()) + 20; // 20 ticks for padding
                    recorder.startRecording(songLocation, true, recordingDuration, player);
                    this.sendMessage(Component.translatable("tooltip.stancements.recording_music_disc").withColor(Stancements.ACCENT_COLOR), player);
                    world.setBlock(pos, state.setValue(RECORDING, true), 3);
                    world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
                    return;
                }
            }
        }

        if (errorMessage != null && !world.isClientSide()) this.sendMessage(errorMessage, player);
    }

    public void stopRecording(Level world, BlockPos pos, boolean fromTop) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder) {
            ItemStack discStack = recorder.getTheItem();
            recorder.finishRecording(ItemStack.EMPTY, true);
            if (discStack.isEmpty()) return;

            if (fromTop) {
                double xOffset = (double) (world.random.nextFloat() * 0.7F) + (double) 0.15F;
                double yOffset = (double) (world.random.nextFloat() * 0.7F) + (double) 0.660000002F;
                double zOffset = (double) (world.random.nextFloat() * 0.7F) + (double) 0.15F;
                ItemEntity discEntity = new ItemEntity(world, (double) pos.getX() + xOffset, (double) pos.getY() + yOffset, (double) pos.getZ() + zOffset, discStack.copy());
                discEntity.setDefaultPickUpDelay();
                world.addFreshEntity(discEntity);
            } else {
                ItemEntity discEntity = new ItemEntity(world, pos.getX() + 0.5D, pos.getY() + 1, pos.getZ() + 0.5D, discStack);
                discEntity.setDefaultPickUpDelay();
                world.addFreshEntity(discEntity);
            }
        }
    }

    public void sendMessage(Component component, Player player) {
        if (player instanceof ServerPlayer serverPlayer) serverPlayer.sendSystemMessage(component, true);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (ReAPI.shouldDisplay(stack, Stancements.stancements("music_recorder/tooltip"))) {
            tooltip.add(Component.translatable("tooltip.stancements.music_recorder").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            this.stopRecording(world, pos, false);
            super.onRemove(state, world, pos, newState, movedByPiston);
        }
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MusicRecorderBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return world.isClientSide() ? null : createTickerHelper(type, STBlockEntities.MUSIC_RECORDER.get(), MusicRecorderBlockEntity::tick);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder) {
            if (!state.getValue(RECORDING)) return 0;
            return ((recorder.ticksUntilFinishedRecording() * 14) / MusicRecorderBlockEntity.DEFAULT_RECORDING_DURATION) + 1;
        }
        return 0;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return state.getValue(RECORDING) ? 15 : 0;
    }

    @Override
    @NotNull
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RECORDING);
    }
}
