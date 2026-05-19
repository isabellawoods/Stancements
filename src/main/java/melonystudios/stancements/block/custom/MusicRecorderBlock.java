package melonystudios.stancements.block.custom;

import com.mojang.serialization.MapCodec;
import melonystudios.reutilities.api.ReAPI;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.MusicData;
import melonystudios.stancements.event.custom.StartRecordingAttemptEvent;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.network.s2c.RequestRecordingAttempt;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MusicRecorderBlock extends BaseEntityBlock {
    public static final BooleanProperty RECORDING = STBlockStateProperties.RECORDING;
    public static final Component NO_MUSIC_PLAYING_TEXT = Component.translatable("tooltip.stancements.no_music_playing").withStyle(ChatFormatting.GRAY);
    public static final Component CANNOT_COPY_TEXT = Component.translatable("tooltip.stancements.cannot_copy").withStyle(ChatFormatting.GRAY);
    private static final Direction[] DIRECTIONS = Direction.values();

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
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        CustomData data = stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
        CompoundTag tag = data.copyTag();
        if (tag.contains("ticks_until_finished_recording", Tag.TAG_ANY_NUMERIC) && tag.getInt("ticks_until_finished_recording") >= 0) {
            level.setBlock(pos, state.setValue(RECORDING, true), 3);
        }
    }

    @Override
    @NotNull
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder && !recorder.isEmpty()) {
            this.stopRecording(level, pos, true);
            level.setBlock(pos, state.setValue(RECORDING, false), 3);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            return InteractionResult.sidedSuccess(!level.isClientSide());
        }
        return InteractionResult.PASS;
    }

    @Override
    @NotNull
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!state.getValue(RECORDING) && stack.has(STDataComponents.RECORDING_TURNS_INTO) && level.getBlockEntity(pos) instanceof MusicRecorderBlockEntity recorder && recorder.isEmpty()) {
            ItemStack handStack = player.getItemInHand(hand);
            ItemStack splitStack = handStack.consumeAndReturn(1, player);

            if (player instanceof ServerPlayer serverPlayer) {
                // tell the client to start the recording process, as it requires the current song in MusicManager ~isa 17-03-26
                PacketDistributor.sendToPlayer(serverPlayer, new RequestRecordingAttempt(pos, splitStack));
            }

            // known issue: taking discs out of the recorder still triggers the hand action (place blocks, use items, etc.) ~isa 18-05-26
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public void tryRecordingFromPlayer(Level level, BlockState state, BlockPos recorderPosition, Player player, ItemStack recordableDisc, @Nullable ResourceLocation musicID, int recordingDuration) {
        BlockEntity blockEntity = level.getBlockEntity(recorderPosition);
        if (!(blockEntity instanceof MusicRecorderBlockEntity recorder)) return;

        // fire recording event ~isa 11-04-26
        StartRecordingAttemptEvent event = StartRecordingAttemptEvent.recordClientMusic(player, recorderPosition, recordableDisc, Optional.ofNullable(musicID));
        if (event.isCanceled()) return;
        recorder.insertDisc(recordableDisc.copy());

        if (musicID == null) {
            this.sendMessage(NO_MUSIC_PLAYING_TEXT, player);
        } else {
            recorder.startRecording(musicID, false, recordingDuration, player);
            this.sendMessage(this.getRecordingMessage(this.getSongName(musicID)), player);
            level.setBlock(recorderPosition, state.setValue(RECORDING, true), 3);
            level.gameEvent(GameEvent.BLOCK_CHANGE, recorderPosition, GameEvent.Context.of(player, state));
        }
    }

    public void tryRecordingFromAdjacentBlock(Level level, BlockState state, BlockPos pos, @Nullable Player player, ItemStack recordableDisc) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof MusicRecorderBlockEntity recorder)) return;
        Component errorMessage = NO_MUSIC_PLAYING_TEXT;

        // fire recording event ~isa 11-04-26
        StartRecordingAttemptEvent event = StartRecordingAttemptEvent.recordFromAdjacentBlock(player, pos, recordableDisc);
        if (event.isCanceled()) return;

        recorder.insertDisc(recordableDisc.copy());
        for (Direction direction : DIRECTIONS) {
            BlockPos adjacentPos = pos.relative(direction);
            BlockState adjacentState = level.getBlockState(adjacentPos);
            BlockEntity adjacentEntity = level.getBlockEntity(adjacentPos);

            if (adjacentEntity instanceof BlockBasedMusicPlayer musicPlayer && adjacentEntity.isValidBlockState(adjacentState)) {
                JukeboxSong song = musicPlayer.song();
                var jukeboxSongs = level.registryAccess().registry(Registries.JUKEBOX_SONG);

                // block recording if the disc is a copy
                if (jukeboxSongs.isEmpty() || MusicData.isCopied(musicPlayer.musicDisc())) {
                    errorMessage = CANNOT_COPY_TEXT;
                    break;
                }
                ResourceLocation songLocation = song == null ? null : jukeboxSongs.get().getKey(song);

                if (song != null) {
                    recorder.startRecording(songLocation, true, musicPlayer.recordingDuration(), player);
                    this.sendMessage(this.getRecordingMessage(song.description().getString()), player);
                    level.setBlock(pos, state.setValue(RECORDING, true), 3);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
                    return;
                }
            }
        }

        if (errorMessage != null && !level.isClientSide()) this.sendMessage(errorMessage, player);
    }

    public void stopRecording(Level level, BlockPos pos, boolean fromTop) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder) {
            ItemStack discStack = recorder.getTheItem();
            recorder.finishRecording(ItemStack.EMPTY, true);
            if (discStack.isEmpty()) return;

            if (fromTop) {
                double xOffset = (double) (level.getRandom().nextFloat() * 0.7F) + (double) 0.15F;
                double yOffset = (double) (level.getRandom().nextFloat() * 0.7F) + (double) 0.660000002F;
                double zOffset = (double) (level.getRandom().nextFloat() * 0.7F) + (double) 0.15F;
                ItemEntity discEntity = new ItemEntity(level, (double) pos.getX() + xOffset, (double) pos.getY() + yOffset, (double) pos.getZ() + zOffset, discStack.copy());
                discEntity.setDefaultPickUpDelay();
                level.addFreshEntity(discEntity);
            } else {
                ItemEntity discEntity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 1, pos.getZ() + 0.5D, discStack);
                discEntity.setDefaultPickUpDelay();
                level.addFreshEntity(discEntity);
            }
        }
    }

    public void sendMessage(Component component, @Nullable Player player) {
        if (player != null) player.displayClientMessage(component, true);
    }

    public Component getRecordingMessage(String songName) {
        String[] authorAndName = songName.split(I18n.get("tooltip.stancements.author_song_separator"));

        if (authorAndName.length >= 2) {
            return Component.translatable("tooltip.stancements.recording_music.split", authorAndName[1].trim(), authorAndName[0].trim()).withColor(Stancements.ACCENT_COLOR);
        } else {
            return Component.translatable("tooltip.stancements.recording_music.unified", songName).withColor(Stancements.ACCENT_COLOR);
        }
    }

    public String getSongName(ResourceLocation musicID) {
        ResourceLocation sanitized = RecordedDiscItem.sanitizeMusicIDLocation(musicID);
        String namespacePrefix = sanitized.getNamespace().equals("minecraft") ? "" : sanitized.getNamespace() + ".";

        return I18n.get(namespacePrefix + "music." + sanitized.getPath().replace("/", "."));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (ReAPI.shouldDisplay(stack, Stancements.stancements("music_recorder/tooltip"))) {
            tooltip.add(Component.translatable("tooltip.stancements.music_recorder").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            this.stopRecording(level, pos, false);
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MusicRecorderBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type, STBlockEntities.MUSIC_RECORDER.get(), MusicRecorderBlockEntity::tick);
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
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder) {
            if (!state.getValue(RECORDING)) return 0;
            return ((recorder.ticksUntilFinishedRecording() * 14) / BlockBasedMusicPlayer.DEFAULT_RECORDING_DURATION) + 1;
        }
        return 0;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
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
