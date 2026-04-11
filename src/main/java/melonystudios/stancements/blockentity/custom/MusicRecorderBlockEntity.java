package melonystudios.stancements.blockentity.custom;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.block.custom.MusicRecorderBlock;
import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.misc.STStatistics;
import melonystudios.stancements.misc.advancement.STCriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.ContainerSingleItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MusicRecorderBlockEntity extends BlockEntity implements Clearable, ContainerSingleItem.BlockContainerSingleItem {
    public static final int DEFAULT_RECORDING_DURATION = 600;
    public static final int DEFAULT_TICKS_UNTIL_FINISHED = -1;
    public static final double RECORDER_MESSAGE_MAX_RANGE = 16.0;
    private ItemStack discStack = ItemStack.EMPTY;
    private ResourceLocation musicID;
    private int ticksUntilFinishedRecording = DEFAULT_TICKS_UNTIL_FINISHED;
    private UUID recorderPlayer;
    private boolean copyingSong;

    public MusicRecorderBlockEntity(BlockPos pos, BlockState state) {
        super(STBlockEntities.MUSIC_RECORDER.get(), pos, state);
    }

    public void insertDisc(ItemStack stack) {
        this.discStack = stack;
    }

    public void startRecording(@Nullable ResourceLocation musicID, boolean copyingSong, @Nullable Player recorderPlayer) {
        this.startRecording(musicID, copyingSong, musicID == null ? DEFAULT_TICKS_UNTIL_FINISHED : DEFAULT_RECORDING_DURATION, recorderPlayer);
    }

    public void startRecording(@Nullable ResourceLocation musicID, boolean copyingSong, int recordingDuration, @Nullable Player recorderPlayer) {
        this.musicID = musicID;
        this.recorderPlayer = recorderPlayer == null ? null : recorderPlayer.getUUID();
        this.ticksUntilFinishedRecording = recordingDuration;
        this.copyingSong = copyingSong && musicID != null;
    }

    public void finishRecording(ItemStack stack, boolean canceled) {
        if (this.getLevel() != null && !canceled && this.recorderPlayer() != null) {
            Player player = this.getLevel().getPlayerByUUID(this.recorderPlayer());
            BlockPos pos = this.getBlockPos();

            if (player instanceof ServerPlayer serverPlayer) {
                // send "finished recording!" message if the recordee is within 16 blocks of the recorder ~isa 17-03-26
                if (serverPlayer.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= RECORDER_MESSAGE_MAX_RANGE) {
                    this.sendMessage(Component.translatable("tooltip.stancements.finished_recording").withColor(Stancements.ACCENT_COLOR), serverPlayer);
                }

                // award statistic
                serverPlayer.awardStat(this.copyingSong() ? STStatistics.MUSIC_DISCS_COPIED.get() : STStatistics.SONGS_RECORDED.get());
                STCriteriaTriggers.RECORD_SONG.trigger(RecordedDiscItem.sanitizeMusicIDLocation(this.musicID()), this.copyingSong(), serverPlayer);
            }
        }

        this.insertDisc(stack);
        this.musicID = null;
        if (canceled) this.recorderPlayer = null;
        this.ticksUntilFinishedRecording = DEFAULT_TICKS_UNTIL_FINISHED;
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
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.getTheItem().isEmpty()) tag.put("item", this.getTheItem().save(registries, new CompoundTag()));
        if (this.musicID() != null) tag.putString("music_id", this.musicID().toString());
        tag.putInt("ticks_until_finished_recording", this.ticksUntilFinishedRecording());
        if (this.recorderPlayer() != null) tag.putUUID("recorder_player", this.recorderPlayer());
        if (this.copyingSong()) tag.putBoolean("copying_song", true);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        ItemStack discStack = ItemStack.parse(registries, tag.getCompound("item")).orElse(ItemStack.EMPTY);
        if (!discStack.isEmpty()) this.insertDisc(discStack);
        if (tag.contains("music_id", Tag.TAG_STRING)) this.musicID = ResourceLocation.tryParse(tag.getString("music_id"));
        this.ticksUntilFinishedRecording = tag.getInt("ticks_until_finished_recording");
        if (tag.hasUUID("recorder_player")) this.recorderPlayer = tag.getUUID("recorder_player");
        if (tag.contains("copying_song", Tag.TAG_ANY_NUMERIC)) this.copyingSong = tag.getBoolean("copying_song");
    }

    @Override
    @NotNull
    public ItemStack getTheItem() {
        return this.discStack;
    }

    public ResourceLocation musicID() {
        return this.musicID;
    }

    public UUID recorderPlayer() {
        return this.recorderPlayer;
    }

    public int ticksUntilFinishedRecording() {
        return this.ticksUntilFinishedRecording;
    }

    public boolean copyingSong() {
        return this.copyingSong;
    }

    @Override
    @NotNull
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
            recorder.tryRecordingFromAdjacentJukebox(level, this.getBlockState(), this.getBlockPos(), null, this.getTheItem());
        }
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    @NotNull
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
