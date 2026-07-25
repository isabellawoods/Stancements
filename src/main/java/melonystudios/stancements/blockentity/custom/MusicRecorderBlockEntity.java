package melonystudios.stancements.blockentity.custom;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.block.custom.MusicRecorderBlock;
import melonystudios.stancements.blockentity.BlockBasedMusicPlayer;
import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.misc.STStatistics;
import melonystudios.stancements.misc.advancement.STCriteriaTriggers;
import melonystudios.stancements.option.STOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
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
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class MusicRecorderBlockEntity extends BlockEntity implements Clearable, ContainerSingleItem.BlockContainerSingleItem {
    public static final double RECORDER_MESSAGE_MAX_RANGE = 256.0; // checked distanc is squared, so this is ^2
    private ItemStack discStack = ItemStack.EMPTY;
    private Identifier musicID;
    private int ticksUntilFinishedRecording = BlockBasedMusicPlayer.DEFAULT_TICKS_UNTIL_FINISHED;
    private EntityReference<Player> recorderPlayer;
    private boolean copyingSong;

    public MusicRecorderBlockEntity(BlockPos pos, BlockState state) {
        super(STBlockEntities.MUSIC_RECORDER.get(), pos, state);
    }

    public void insertDisc(ItemStack stack) {
        this.discStack = stack;
    }

    public void startRecording(@Nullable Identifier musicID, boolean copyingSong, @Nullable Player recorderPlayer) {
        this.startRecording(musicID, copyingSong, musicID == null ? BlockBasedMusicPlayer.DEFAULT_TICKS_UNTIL_FINISHED : STOptions.DEFAULT_RECORDING_DURATION.get(), recorderPlayer);
    }

    public void startRecording(@Nullable Identifier musicID, boolean copyingSong, int recordingDuration, @Nullable Player recorderPlayer) {
        this.musicID = musicID;
        this.recorderPlayer = recorderPlayer == null ? null : EntityReference.of(recorderPlayer);
        this.ticksUntilFinishedRecording = recordingDuration;
        this.copyingSong = copyingSong && musicID != null;
    }

    public void finishRecording(ItemStack stack, boolean canceled) {
        if (this.getLevel() != null && !canceled && this.recorderPlayer() != null) {
            Player player = this.getLevel().getPlayerByUUID(this.recorderPlayer().getUUID());
            BlockPos pos = this.getBlockPos();

            if (player instanceof ServerPlayer serverPlayer) {
                // send "finished recording!" message if the recordee is within 16 blocks of the recorder ~isa 17-03-26
                if (serverPlayer.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= RECORDER_MESSAGE_MAX_RANGE) {
                    this.sendMessage(Component.translatable("tooltip.stancements.finished_recording").withColor(Stancements.ACCENT_COLOR), serverPlayer);
                }

                // award statistic
                serverPlayer.awardStat(this.copyingSong() ? STStatistics.MUSIC_DISCS_COPIED.get() : STStatistics.SONGS_RECORDED.get());
                Identifier sanitizedMusicID = RecordedDiscItem.getJukeboxSongLocation(this.musicID());
                STCriteriaTriggers.RECORD_SONG.trigger(sanitizedMusicID, this.copyingSong(), List.of(sanitizedMusicID), serverPlayer);
            }
        }

        this.insertDisc(stack);
        this.musicID = null;
        if (canceled) this.recorderPlayer = null;
        this.ticksUntilFinishedRecording = BlockBasedMusicPlayer.DEFAULT_TICKS_UNTIL_FINISHED;
        this.copyingSong = false;
        this.setChanged();
    }

    public void sendMessage(Component component, ServerPlayer recorderPlayer) {
        recorderPlayer.sendSystemMessage(component, true);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MusicRecorderBlockEntity recorder) {
        if (recorder.ticksUntilFinishedRecording() >= 0 && state.getValue(STBlockStateProperties.RECORDING)) {
            --recorder.ticksUntilFinishedRecording;
            recorder.setChanged();
            if (recorder.ticksUntilFinishedRecording() == 0 && recorder.musicID() != null) {
                recorder.finishRecording(RecordedDiscItem.getRecordedDisc(level, recorder.musicID(), recorder.copyingSong(), recorder.getTheItem()), false);
                level.setBlock(pos, state.setValue(STBlockStateProperties.RECORDING, false), 3);
            }
        }

        if (level instanceof ServerLevel serverLevel && state.getValue(STBlockStateProperties.RECORDING) && recorder.ticksUntilFinishedRecording() % 20 == 0) {
            Vec3 notePos = Vec3.atBottomCenterOf(pos).add(0, 1.2F, 0);
            float rand = (float) serverLevel.getRandom().nextInt(4) / 24;
            serverLevel.sendParticles(ParticleTypes.NOTE, notePos.x(), notePos.y(), notePos.z(), 0, rand, 0, 0, 1);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!this.getTheItem().isEmpty()) output.store("item", ItemStack.OPTIONAL_CODEC, this.getTheItem());
        if (this.musicID() != null) output.putString("music_id", this.musicID().toString());
        output.putInt("ticks_until_finished_recording", this.ticksUntilFinishedRecording());
        EntityReference.store(this.recorderPlayer(), output, "recorder_player");
        if (this.copyingSong()) output.putBoolean("copying_song", true);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        ItemStack discStack = input.read("item", ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
        if (!discStack.isEmpty()) this.insertDisc(discStack);

        input.getString("music_id").ifPresent(musicID -> this.musicID = Identifier.tryParse(musicID));
        this.ticksUntilFinishedRecording = input.getIntOr("ticks_until_finished_recording", BlockBasedMusicPlayer.DEFAULT_TICKS_UNTIL_FINISHED);
        this.copyingSong = input.getBooleanOr("copying_song", false);

        EntityReference<Player> recordee;
        if (this.hasLevel()) {
            recordee = EntityReference.readWithOldOwnerConversion(input, "recorder_player", this.getLevel());
        } else {
            recordee = EntityReference.read(input, "recorder_player");
        }
        this.recorderPlayer = recordee;
    }

    @Override
    @NonNull
    public ItemStack getTheItem() {
        return this.discStack;
    }

    public Identifier musicID() {
        return this.musicID;
    }

    public EntityReference<Player> recorderPlayer() {
        return this.recorderPlayer;
    }

    public int ticksUntilFinishedRecording() {
        return this.ticksUntilFinishedRecording;
    }

    public boolean copyingSong() {
        return this.copyingSong;
    }

    @Override
    @NonNull
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
            this.finishRecording(this.getTheItem(), true);
        } else if (this.getBlockState().getBlock() instanceof MusicRecorderBlock recorder && level != null) {
            recorder.tryRecordingFromAdjacentBlock(level, this.getBlockState(), this.getBlockPos(), null, this.getTheItem());
        }
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    @NonNull
    public BlockEntity getContainerBlockEntity() {
        return this;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return stack.has(STDataComponents.RECORDING_TURNS_INTO) && this.getItem(slot).isEmpty();
    }

    @Override
    public boolean canTakeItem(Container target, int slot, ItemStack stack) {
        return target.hasAnyMatching(ItemStack::isEmpty);
    }
}
