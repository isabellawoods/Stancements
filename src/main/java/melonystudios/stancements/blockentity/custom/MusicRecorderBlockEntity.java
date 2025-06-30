package melonystudios.stancements.blockentity.custom;

import melonystudios.stancements.block.custom.MusicRecorderBlock;
import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.util.STUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class MusicRecorderBlockEntity extends TileEntity implements ITickableTileEntity, IInventory {
    private ItemStack discStack = ItemStack.EMPTY;
    private ResourceLocation musicID;
    private int ticksUntilFinishedRecording = -1;
    private UUID recorderPlayer;
    private boolean isRecording = false;

    public MusicRecorderBlockEntity() {
        super(STBlockEntities.MUSIC_RECORDER.get());
    }

    public void insertDisc(ItemStack discStack) {
        this.discStack = discStack;
    }

    public boolean startRecording(@Nullable ResourceLocation musicID, @Nullable PlayerEntity recorderPlayer) {
        this.musicID = musicID;
        this.recorderPlayer = recorderPlayer == null ? null : recorderPlayer.getUUID();
        this.ticksUntilFinishedRecording = musicID == null ? -1 : 600;
        this.setRecording(musicID != null);
        return this.musicID != null;
    }

    public void finishRecording(ItemStack discStack, boolean canceled) {
        if (this.getLevel() != null && !canceled && this.recorderPlayer != null) {
            PlayerEntity player = this.getLevel().getPlayerByUUID(this.recorderPlayer);
            this.sendMessage(new TranslationTextComponent("tooltip.stancements.finished_recording").withStyle(TextFormatting.GOLD), player);
        }

        this.discStack = discStack;
        this.musicID = null;
        if (canceled) this.recorderPlayer = null;
        this.ticksUntilFinishedRecording = -1;
        this.setRecording(false);
        this.setChanged();
    }

    public void sendMessage(ITextComponent component, @Nullable PlayerEntity recorderPlayer) {
        if (recorderPlayer instanceof ServerPlayerEntity) ((ServerPlayerEntity) recorderPlayer).sendMessage(component, ChatType.GAME_INFO, Util.NIL_UUID);
    }

    @Override
    public void tick() {
        World world = this.getLevel();
        if (this.ticksUntilFinishedRecording >= 0 && this.recording()) {
            --this.ticksUntilFinishedRecording;
            this.setChanged();
            if (this.ticksUntilFinishedRecording == 0 && this.musicID != null) {
                this.finishRecording(RecordedDiscItem.getRecordedDisc(this.musicID, this.discStack), false);
                if (world != null) world.setBlock(this.getBlockPos(), this.getBlockState().setValue(MusicRecorderBlock.RECORDING, false), 3);
            }
        }

        if (world != null && world.isClientSide && this.getBlockState().getValue(MusicRecorderBlock.RECORDING)) {
            if (world.getGameTime() % 20 == 0) {
                float rand1 = world.getRandom().nextInt(4) / 24F;
                world.addParticle(ParticleTypes.NOTE, this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 1.2, this.getBlockPos().getZ() + 0.5, 0, rand1, 0);
            }
        }
    }

    @Override
    @Nonnull
    public CompoundNBT save(CompoundNBT tag) {
        super.save(tag);
        if (!this.discStack.isEmpty()) {
            CompoundNBT discTag = STUtils.saveStack(this.discStack, new CompoundNBT());
            tag.put("item", discTag);
        }
        if (this.musicID != null) tag.putString("music_id", this.musicID.toString());
        tag.putInt("ticks_until_finished_recording", this.ticksUntilFinishedRecording);
        if (this.recorderPlayer != null) tag.putUUID("recorder_player", this.recorderPlayer);
        tag.putBoolean("recording", this.isRecording);
        return tag;
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);

        if (tag.contains("item", Constants.NBT.TAG_COMPOUND)) {
            this.discStack = STUtils.loadStack(tag.getCompound("item"));
        }
        if (tag.contains("music_id", Constants.NBT.TAG_STRING)) {
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

    public int ticksUntilFinishedRecording() {
        return this.ticksUntilFinishedRecording;
    }

    public boolean recording() {
        return this.isRecording;
    }

    public void setRecording(boolean recording) {
        this.isRecording = recording;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.discStack.isEmpty();
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == STItems.VINYL_DISC.get();
    }

    @Override
    @Nonnull
    public ItemStack getItem(int slot) {
        return slot == 0 ? this.discStack : ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot == 0 && !stack.isEmpty() && stack.getItem() == STItems.VINYL_DISC.get()) {
            this.insertDisc(stack.copy());
            if (this.musicID != null) {
                if (this.getLevel() != null) this.getLevel().setBlock(this.getBlockPos(), this.getBlockState().setValue(MusicRecorderBlock.RECORDING, true), 3);
                this.startRecording(this.musicID, null);
            }
            this.setChanged();
        }
    }

    @Override
    @Nonnull
    public ItemStack removeItem(int slot, int count) {
        if (slot == 0 && !this.discStack.isEmpty()) {
            ItemStack splitStack = this.discStack.split(count);
            if (this.discStack.isEmpty()) this.finishRecording(ItemStack.EMPTY, true);
            return splitStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack removeItemNoUpdate(int slot) {
        if (slot == 0 && !this.discStack.isEmpty()) {
            ItemStack stack = this.discStack.copy();
            this.discStack = ItemStack.EMPTY;
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.finishRecording(ItemStack.EMPTY, true);
    }
}
