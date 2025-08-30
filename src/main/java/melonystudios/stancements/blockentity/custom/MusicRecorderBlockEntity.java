package melonystudios.stancements.blockentity.custom;

import melonystudios.stancements.block.custom.MusicRecorderBlock;
import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MusicRecorderBlockEntity extends BlockEntity {
    private ItemStack discStack = ItemStack.EMPTY;
    private ResourceLocation musicID;
    private int ticksUntilFinishedRecording = -1;
    private UUID recorderPlayer;
    private boolean isRecording = false;

    public MusicRecorderBlockEntity(BlockPos pos, BlockState state) {
        super(STBlockEntities.MUSIC_RECORDER.get(), pos, state);
    }

    public void insertDisc(ItemStack discStack) {
        this.discStack = discStack;
    }

    public boolean startRecording(@Nullable ResourceLocation musicID, @Nullable Player recorderPlayer) {
        this.musicID = musicID;
        this.recorderPlayer = recorderPlayer == null ? null : recorderPlayer.getUUID();
        this.ticksUntilFinishedRecording = musicID == null ? -1 : 600;
        this.setRecording(musicID != null);
        return this.musicID != null;
    }

    public void finishRecording(ItemStack discStack, boolean canceled) {
        if (this.getLevel() != null && !canceled && this.recorderPlayer != null) {
            Player player = this.getLevel().getPlayerByUUID(this.recorderPlayer);
            this.sendMessage(Component.translatable("tooltip.stancements.finished_recording").withStyle(ChatFormatting.GOLD), player);
        }

        this.discStack = discStack;
        this.musicID = null;
        if (canceled) this.recorderPlayer = null;
        this.ticksUntilFinishedRecording = -1;
        this.setRecording(false);
        this.setChanged();
    }

    public void sendMessage(Component component, @Nullable Player recorderPlayer) {
        if (recorderPlayer instanceof ServerPlayer serverPlayer) serverPlayer.sendSystemMessage(component, true);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, MusicRecorderBlockEntity recorder) {
        if (recorder.ticksUntilFinishedRecording() >= 0 && recorder.recording()) {
            --recorder.ticksUntilFinishedRecording;
            recorder.setChanged();
            if (recorder.ticksUntilFinishedRecording() == 0 && recorder.musicID() != null) {
                recorder.finishRecording(RecordedDiscItem.getRecordedDisc(world, recorder.musicID(), recorder.getDiscStack()), false);
                world.setBlock(pos, state.setValue(MusicRecorderBlock.RECORDING, false), 3);
            }
        }

        if (world instanceof ServerLevel serverWorld && state.getValue(MusicRecorderBlock.RECORDING) && recorder.ticksUntilFinishedRecording() % 20 == 0) {
            Vec3 notePos = Vec3.atBottomCenterOf(pos).add(0, 1.2F, 0);
            float rand = (float) serverWorld.getRandom().nextInt(4) / 24;
            serverWorld.sendParticles(ParticleTypes.NOTE, notePos.x(), notePos.y(), notePos.z(), 0, rand, 0, 0, 1);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.discStack.isEmpty()) tag.put("item", this.discStack.save(registries, new CompoundTag()));
        if (this.musicID != null) tag.putString("music_id", this.musicID.toString());
        tag.putInt("ticks_until_finished_recording", this.ticksUntilFinishedRecording);
        if (this.recorderPlayer != null) tag.putUUID("recorder_player", this.recorderPlayer);
        tag.putBoolean("recording", this.isRecording);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        ItemStack discStack = ItemStack.parse(registries, tag.getCompound("item")).orElse(ItemStack.EMPTY);
        if (!discStack.isEmpty()) this.discStack = discStack;
        if (tag.contains("music_id", Tag.TAG_STRING)) {
            this.musicID = ResourceLocation.tryParse(tag.getString("music_id"));
        }
        this.ticksUntilFinishedRecording = tag.getInt("ticks_until_finished_recording");

        if (tag.hasUUID("recorder_player")) {
            this.recorderPlayer = tag.getUUID("recorder_player");
        }
        this.isRecording = tag.getBoolean("recording");
    }

    public ItemStack getDiscStack() {
        return this.discStack;
    }

    public ResourceLocation musicID() {
        return this.musicID;
    }

    public int ticksUntilFinishedRecording() {
        return this.ticksUntilFinishedRecording;
    }

    public boolean recording() {
        return this.isRecording;
    }

    public void setRecording(boolean recording) {
        this.isRecording = recording;
    }
}
