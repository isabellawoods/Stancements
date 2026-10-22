package melonystudios.stancements.blockentity.custom;

import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.block.custom.MusicRecorderBlock;
import melonystudios.stancements.blockentity.BlockBasedMusicPlayer;
import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.misc.STStatistics;
import melonystudios.stancements.misc.advancement.STCriteriaTriggers;
import melonystudios.stancements.misc.modifier.ModificationStrategy;
import melonystudios.stancements.misc.modifier.VinylModifier;
import melonystudios.stancements.misc.recording.RecordingSource;
import melonystudios.stancements.misc.recording.Track;
import melonystudios.stancements.option.STCommonOptions;
import melonystudios.stancements.util.TagUpdateManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.ContainerSingleItem;
import org.jspecify.annotations.Nullable;

import java.util.List;

import static melonystudios.stancements.block.custom.MusicRecorderBlock.*;

public class MusicRecorderBlockEntity extends BlockEntity implements Clearable, ContainerSingleItem.BlockContainerSingleItem {
    public static final double RECORDER_MESSAGE_MAX_RANGE = 256.0; // checked distanc is squared, so this is ^2
    public static final int DEFAULT_TICKS_UNTIL_EJECTION = -1;
    private ItemStack discStack = ItemStack.EMPTY;
    @Nullable
    private Track track;
    private int ticksUntilFinishedRecording = BlockBasedMusicPlayer.DEFAULT_TICKS_UNTIL_FINISHED;
    private int ticksUntilEjection = DEFAULT_TICKS_UNTIL_EJECTION;
    private EntityReference<Player> recorderPlayer;
    private boolean copying;

    public MusicRecorderBlockEntity(BlockPos pos, BlockState state) {
        super(STBlockEntities.MUSIC_RECORDER.get(), pos, state);
    }

    public void insertDisc(ItemStack stack) {
        this.discStack = stack;
    }

    public void startRecording(@Nullable Track track, boolean copying, @Nullable Player recorderPlayer) {
        this.startRecording(track, copying, track == null ? BlockBasedMusicPlayer.DEFAULT_TICKS_UNTIL_FINISHED : STCommonOptions.DEFAULT_RECORDING_DURATION.get(), recorderPlayer);
    }

    public void startRecording(@Nullable Track track, boolean copying, int recordingDuration, @Nullable Player recorderPlayer) {
        this.track = track;
        this.recorderPlayer = recorderPlayer == null ? null : EntityReference.of(recorderPlayer);
        this.ticksUntilFinishedRecording = recordingDuration;
        this.copying = copying && track != null;

        if (track != null && recorderPlayer instanceof ServerPlayer serverPlayer) {
            var result = VinylModifier.recordingPipeline(this, ModificationStrategy.START);
            this.insertDisc(result.stack());

            if (!result.recordingText().getString().isBlank()) {
                this.sendMessage(result.recordingText(), serverPlayer);
            } else if (this.getLevel() != null) {
                var jukeboxSongs = this.getLevel().registryAccess().lookupOrThrow(Registries.JUKEBOX_SONG);

                if (copying) {
                    var jukeboxSong = track.unwrap(jukeboxSongs);
                    jukeboxSong.ifPresent(song -> this.sendMessage(getRecordingMessage(song.description().getString()), serverPlayer));
                } else {
                    this.sendMessage(getRecordingMessage(getSongName(jukeboxSongs, track.jukeboxSongID()).getString()), serverPlayer);
                }
            }
        }
    }

    public void finishRecording(ItemStack stack, Component recordingText, boolean canceled) {
        if (this.getLevel() != null && !canceled && this.recorderPlayer() != null) {
            Player player = this.getPlayerFromRecorderUUID();
            BlockPos pos = this.getBlockPos();

            if (player instanceof ServerPlayer serverPlayer) {
                // send "finished recording!" message if the recordee is within 16 blocks of the recorder ~isa 17-03-26
                if (serverPlayer.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= RECORDER_MESSAGE_MAX_RANGE && !recordingText.getString().isBlank()) {
                    this.sendMessage(recordingText, serverPlayer);
                }

                // award statistic
                serverPlayer.awardStat(this.copying() ? STStatistics.MUSIC_DISCS_COPIED.get() : STStatistics.SONGS_RECORDED.get());
                STCriteriaTriggers.RECORD_SONG.trigger(this.track(), RecordingSource.MUSIC_RECORDER, this.copying(), List.of(this.track()), serverPlayer);
            }
        }

        this.insertDisc(stack);
        this.track = null;
        if (canceled) this.recorderPlayer = null;
        this.ticksUntilFinishedRecording = BlockBasedMusicPlayer.DEFAULT_TICKS_UNTIL_FINISHED;
        if (!STCommonOptions.RECORDER_FREE_WILL.get()) this.ticksUntilEjection = DEFAULT_TICKS_UNTIL_EJECTION;
        this.copying = false;
        this.setChanged();
    }

    public void sendMessage(Component component, ServerPlayer recorderPlayer) {
        recorderPlayer.sendSystemMessage(component, true);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MusicRecorderBlockEntity recorder) {
        boolean recording = state.getValue(STBlockStateProperties.RECORDING);

        if (recorder.ticksUntilFinishedRecording() >= 0 && recording) {
            --recorder.ticksUntilFinishedRecording;
            recorder.setChanged();
            if (recorder.ticksUntilFinishedRecording() == 0 && recorder.track() != null) {
                var result = VinylModifier.recordingPipeline(recorder, ModificationStrategy.FINISH);
                ItemStack stack = result.stack();
                RecordedDiscItem.setJukeboxSong(level, stack, recorder.track(), recorder.copying(), false);

                recorder.finishRecording(stack, result.recordingText(), false);
                level.setBlock(pos, state.setValue(STBlockStateProperties.RECORDING, false), 3);
            }
        }

        // tick down the ejection timer
        if (recorder.ticksUntilEjection() >= 0 && recording && STCommonOptions.RECORDER_FREE_WILL.get()) {
            --recorder.ticksUntilEjection;
            if (recorder.ticksUntilEjection() == 0) {
                var result = VinylModifier.recordingPipeline(recorder, ModificationStrategy.EJECT);
                recorder.insertDisc(result.stack());

                interruptAndEject(level, pos, result.recordingText(), true);
                level.setBlock(pos, state.setValue(STBlockStateProperties.RECORDING, false), 3);
            }
        }

        if (level instanceof ServerLevel serverLevel && recording && recorder.ticksUntilFinishedRecording() % 20 == 0) {
            Vec3 notePos = Vec3.atBottomCenterOf(pos).add(0, 1.2F, 0);
            float rand = (float) serverLevel.getRandom().nextInt(4) / 24;
            serverLevel.sendParticles(ParticleTypes.NOTE, notePos.x(), notePos.y(), notePos.z(), 0, rand, 0, 0, 1);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!this.getTheItem().isEmpty()) output.store("item", ItemStack.OPTIONAL_CODEC, this.getTheItem());
        TagUpdateManager.saveRecorderTrack(output, this.track(), this.getBlockPos());
        output.putInt("ticks_until_finished_recording", this.ticksUntilFinishedRecording());
        if (this.ticksUntilEjection() > DEFAULT_TICKS_UNTIL_EJECTION) output.putInt("ticks_until_ejection", this.ticksUntilEjection());
        EntityReference.store(this.recorderPlayer(), output, "recorder_player");
        if (this.copying()) output.putBoolean("copying", true);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        ItemStack discStack = input.read("item", ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
        if (!discStack.isEmpty()) this.insertDisc(discStack);

        TagUpdateManager.readRecorderTrack(input, this.getBlockPos(), track -> this.track = track);
        this.ticksUntilFinishedRecording = input.getIntOr("ticks_until_finished_recording", BlockBasedMusicPlayer.DEFAULT_TICKS_UNTIL_FINISHED);
        this.ticksUntilEjection = input.getIntOr("ticks_until_ejection", DEFAULT_TICKS_UNTIL_EJECTION);

        String copyingTag = "copying";
        if (input.child("coping_song").isPresent() && input.child("copying").isEmpty()) {
            copyingTag = "copying_song";
        }
        this.copying = input.getBooleanOr(copyingTag, false);

        EntityReference<Player> recordee;
        if (this.hasLevel()) {
            recordee = EntityReference.readWithOldOwnerConversion(input, "recorder_player", this.getLevel());
        } else {
            recordee = EntityReference.read(input, "recorder_player");
        }
        this.recorderPlayer = recordee;
    }

    @Override
    public ItemStack getTheItem() {
        return this.discStack;
    }

    @Nullable
    public Track track() {
        return this.track;
    }

    @Nullable
    public EntityReference<Player> recorderPlayer() {
        return this.recorderPlayer;
    }

    public int ticksUntilFinishedRecording() {
        return this.ticksUntilFinishedRecording;
    }

    public int ticksUntilEjection() {
        return this.ticksUntilEjection;
    }

    public void setEjectionTicks(int ticks) {
        this.ticksUntilEjection = ticks;
    }

    public boolean copying() {
        return this.copying;
    }

    @Nullable
    public Player getPlayerFromRecorderUUID() {
        if (this.getLevel() != null && this.recorderPlayer() != null) return this.recorderPlayer().getEntity(this.getLevel(), Player.class);
        return null;
    }

    @Override
    public ItemStack splitTheItem(int amount) {
        ItemStack stack = this.getTheItem();
        this.setTheItem(ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setTheItem(ItemStack stack) {
        Level level = this.getLevel();
        this.insertDisc(stack);
        if (this.getTheItem().isEmpty()) {
            this.finishRecording(this.getTheItem(), Component.empty(), true);
        } else if (this.getBlockState().getBlock() instanceof MusicRecorderBlock recorder && level != null) {
            recorder.tryRecordingFromAdjacentBlock(level, this.getBlockState(), this.getBlockPos(), null, this.getTheItem());
        }
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public BlockEntity getContainerBlockEntity() {
        return this;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return stack.has(STDataComponents.RECORDABLE_TRANSFORM) && this.getItem(slot).isEmpty();
    }

    @Override
    public boolean canTakeItem(Container target, int slot, ItemStack stack) {
        return target.hasAnyMatching(ItemStack::isEmpty);
    }
}
