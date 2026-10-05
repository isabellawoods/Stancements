package melonystudios.stancements.item.custom;

import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.InventoryRecorder;
import melonystudios.stancements.sound.STSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class PocketRecorderItem extends Item {
    public PocketRecorderItem(Properties properties) {
        super(properties);
    }

    @Nullable
    public InventoryRecorder inventoryRecorder(ItemStack stack) {
        return stack.get(STDataComponents.INVENTORY_RECORDER);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        if (!handStack.has(STDataComponents.INVENTORY_RECORDER)) return super.use(level, player, hand);

        InventoryRecorder.toggle(player, handStack);
        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (stack.getCount() != 1 || action != ClickAction.SECONDARY) return false;

        InventoryRecorder recorder = this.inventoryRecorder(stack);
        if (recorder == null) {
            return false;
        } else {
            ItemStack other = slot.getItem();
            InventoryRecorder.Mutable mutable = new InventoryRecorder.Mutable(recorder);
            if (other.isEmpty() && mutable.item() != null) {
                player.playSound(STSounds.INVENTORY_RECORDER_INSERT_STORAGE.get());
                slot.safeInsert(mutable.item().create());
                mutable.insertTrackStorage(null);
            } else if (other.has(STDataComponents.TRACK_STORAGE) && recorder.item().isEmpty()) {
                mutable.insertTrackStorage(ItemStackTemplate.fromNonEmptyStack(other.copyWithCount(1)));
                other.shrink(1);
                player.playSound(STSounds.INVENTORY_RECORDER_REMOVE_STORAGE.get());
            }

            stack.set(STDataComponents.INVENTORY_RECORDER, mutable.toImmutable());
            return true;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (stack.getCount() != 1) return false;

        if (action == ClickAction.SECONDARY && slot.allowModification(player)) {
            InventoryRecorder recorder = this.inventoryRecorder(stack);
            if (recorder == null) {
                return false;
            } else {
                InventoryRecorder.Mutable mutable = new InventoryRecorder.Mutable(recorder);
                if (other.isEmpty() && mutable.item() != null) {
                    player.playSound(STSounds.INVENTORY_RECORDER_INSERT_STORAGE.get());
                    access.set(mutable.item().create());
                    mutable.insertTrackStorage(null);
                } else if (other.has(STDataComponents.TRACK_STORAGE) && recorder.item().isEmpty()) {
                    mutable.insertTrackStorage(ItemStackTemplate.fromNonEmptyStack(other.copyWithCount(1)));
                    other.shrink(1);
                    player.playSound(STSounds.INVENTORY_RECORDER_REMOVE_STORAGE.get());
                }

                stack.set(STDataComponents.INVENTORY_RECORDER, mutable.toImmutable());
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, level, owner, slot);
        if (level.isClientSide()) return;

        InventoryRecorder recorder = this.inventoryRecorder(stack);
        if (recorder != null && recorder.active()) InventoryRecorder.tick(level, owner, stack);
    }
}
