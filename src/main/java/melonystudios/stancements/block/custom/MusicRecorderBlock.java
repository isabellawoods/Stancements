package melonystudios.stancements.block.custom;

import com.mojang.serialization.MapCodec;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.blockentity.BlockBasedMusicPlayer;
import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.MusicData;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.client.network.RequestRecordingAttempt;
import melonystudios.stancements.option.STCommonOptions;
import melonystudios.stancements.sound.STSounds;
import melonystudios.stancements.tag.STJukeboxSongTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jspecify.annotations.Nullable;

public class MusicRecorderBlock extends BaseEntityBlock {
    public static final BooleanProperty RECORDING = STBlockStateProperties.RECORDING;
    public static final Component NO_MUSIC_PLAYING_TEXT = Component.translatable("tooltip.stancements.no_music_playing").withStyle(ChatFormatting.GRAY);
    public static final Component CANNOT_COPY_TEXT = Component.translatable("tooltip.stancements.cannot_copy").withStyle(ChatFormatting.GRAY);
    public static final Component COPYING_PROHIBITED_TEXT = Component.translatable("tooltip.stancements.copying_prohibited").withStyle(ChatFormatting.RED);
    private static final Direction[] DIRECTIONS = Direction.values();

    public MusicRecorderBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(RECORDING, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(MusicRecorderBlock::new);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        TypedEntityData<BlockEntityType<?>> data = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (data == null) return;
        CompoundTag tag = data.copyTagWithoutId();

        if (tag.contains("ticks_until_finished_recording") && tag.getIntOr("ticks_until_finished_recording", BlockBasedMusicPlayer.DEFAULT_TICKS_UNTIL_FINISHED) >= 0) {
            level.setBlock(pos, state.setValue(RECORDING, true), 3);
        }
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder && !recorder.isEmpty()) {
            interruptAndEject(level, pos, true);
            level.setBlock(pos, state.setValue(RECORDING, false), 3);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            return InteractionResult.SUCCESS_SERVER;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!state.getValue(RECORDING) && stack.has(STDataComponents.RECORDING_TURNS_INTO) && level.getBlockEntity(pos) instanceof MusicRecorderBlockEntity recorder && recorder.isEmpty()) {
            ItemStack handStack = player.getItemInHand(hand);
            ItemStack splitStack = handStack.consumeAndReturn(1, player);

            if (player instanceof ServerPlayer serverPlayer) {
                // tell the client to start the recording process, as it requires the current song in MusicManager ~isa 17-03-26
                PacketDistributor.sendToPlayer(serverPlayer, new RequestRecordingAttempt(pos, splitStack));
            }

            // known issue: taking discs out of the recorder still triggers the hand action (place blocks, use items, etc.) ~isa 18-05-26
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    public void tryRecordingFromPlayer(Level level, BlockState state, BlockPos recorderPosition, Player player, ItemStack recordableDisc, @Nullable Identifier musicID, int recordingDuration) {
        BlockEntity blockEntity = level.getBlockEntity(recorderPosition);
        if (!(blockEntity instanceof MusicRecorderBlockEntity recorder)) return;

        recorder.insertDisc(recordableDisc.copy());

        if (musicID == null) {
            this.sendMessage(NO_MUSIC_PLAYING_TEXT, player);
        } else {
            recorder.startRecording(musicID, false, recordingDuration, player);
            level.setBlock(recorderPosition, state.setValue(RECORDING, true), 3);
            level.gameEvent(GameEvent.BLOCK_CHANGE, recorderPosition, GameEvent.Context.of(player, state));
        }
    }

    public void tryRecordingFromAdjacentBlock(Level level, BlockState state, BlockPos pos, @Nullable Player player, ItemStack recordableDisc) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof MusicRecorderBlockEntity recorder)) return;
        Component errorMessage = NO_MUSIC_PLAYING_TEXT;

        recorder.insertDisc(recordableDisc.copy());
        for (Direction direction : DIRECTIONS) {
            BlockPos adjacentPos = pos.relative(direction);
            BlockState adjacentState = level.getBlockState(adjacentPos);
            BlockEntity adjacentEntity = level.getBlockEntity(adjacentPos);

            if (adjacentEntity instanceof BlockBasedMusicPlayer musicPlayer && adjacentEntity.isValidBlockState(adjacentState)) {
                JukeboxSong song = musicPlayer.song();
                var jukeboxSongs = level.registryAccess().lookup(Registries.JUKEBOX_SONG);
                if (song == null) continue;

                // block recording if the disc is a copy
                if (jukeboxSongs.isEmpty() || MusicData.isCopied(musicPlayer.musicDisc())) {
                    errorMessage = CANNOT_COPY_TEXT;
                    continue;
                }
                Identifier songIdentifier = jukeboxSongs.get().getKey(song);
                if (songIdentifier == null) continue;

                // block recording if the jukebox song disallows copies (in #copying_prohibited tag)
                var songHolder = jukeboxSongs.get().get(songIdentifier);
                if (songHolder.isPresent() && songHolder.get().is(STJukeboxSongTags.COPYING_PROHIBITED)) {
                    errorMessage = COPYING_PROHIBITED_TEXT;
                    continue;
                }

                // finally record the disc
                recorder.startRecording(songIdentifier, true, musicPlayer.recordingDuration(), player);
                level.setBlock(pos, state.setValue(RECORDING, true), 3);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
                return;
            }
        }

        if (errorMessage != null && !level.isClientSide()) this.sendMessage(errorMessage, player);
    }

    public static void interruptAndEject(Level level, BlockPos pos, boolean fromTop) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder) {
            ItemStack discStack = recorder.getTheItem();
            recorder.finishRecording(ItemStack.EMPTY, Component.empty(), true);
            if (discStack.isEmpty()) return;

            if (fromTop) {
                ItemEntity discEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.3, pos.getZ() + 0.5, discStack.copy(), 0, 0.2, 0);
                discEntity.setDefaultPickUpDelay();
                level.addFreshEntity(discEntity);

                level.playSound(null, pos, STSounds.MUSIC_RECORDER_EJECT.get(), SoundSource.BLOCKS, 1, 1);
                level.levelEvent(2010, pos, Direction.UP.get3DDataValue());
            } else {
                ItemEntity discEntity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 1, pos.getZ() + 0.5D, discStack);
                discEntity.setDefaultPickUpDelay();
                level.addFreshEntity(discEntity);
            }
        }
    }

    public void sendMessage(Component component, @Nullable Player player) {
        if (player != null) player.sendOverlayMessage(component);
    }

    public static Component getRecordingMessage(String songName) {
        String[] authorAndName = songName.split(Component.translatable("tooltip.stancements.author_song_separator_regex").getString());

        if (authorAndName.length >= 2) {
            return Component.translatable("tooltip.stancements.recording_music.split", authorAndName[1].trim(), prettyPrintAuthorNames(authorAndName[0].trim())).withColor(Stancements.ACCENT_COLOR);
        } else {
            return Component.translatable("tooltip.stancements.recording_music.unified", songName).withColor(Stancements.ACCENT_COLOR);
        }
    }

    private static MutableComponent prettyPrintAuthorNames(String authorsSingle) {
        String[] authors = authorsSingle.split(Component.translatable("tooltip.stancements.authors_separator_regex").getString());
        MutableComponent component = Component.empty();

        for (int i = 0; i < authors.length; ++i) {
            String author = authors[i].trim();
            component.append(Component.literal(author));

            if (i == authors.length - 2) {
                component.append(Component.translatable("tooltip.stancements.delimiter.all"));
            } else if (i != authors.length - 1) {
                component.append(Component.translatable("tooltip.stancements.delimiter"));
            }
        }
        return component;
    }

    public static String getSongName(RegistryAccess registries, Identifier musicID) {
        var song = BlockBasedMusicPlayer.findJukeboxSongFromID(registries, musicID, true);
        if (song.isPresent()) return song.get().value().description().getString();

        Identifier sanitized = RecordedDiscItem.getJukeboxSongLocation(musicID);
        String namespacePrefix = sanitized.getNamespace().equals("minecraft") ? "" : sanitized.getNamespace() + ".";

        return Component.translatable(namespacePrefix + "music." + sanitized.getPath().replace("/", ".")).getString();
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
        interruptAndEject(level, pos, false);
        super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);
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
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder) {
            if (!state.getValue(RECORDING)) return 0;
            return ((recorder.ticksUntilFinishedRecording() * 14) / STCommonOptions.DEFAULT_RECORDING_DURATION.get()) + 1;
        }
        return 0;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.getValue(RECORDING) ? 15 : 0;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RECORDING);
    }
}
