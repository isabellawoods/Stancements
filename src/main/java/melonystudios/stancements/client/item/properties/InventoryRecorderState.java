package melonystudios.stancements.client.item.properties;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.InventoryRecorder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public record InventoryRecorderState() implements SelectItemModelProperty<InventoryRecorder.State> {
    public static final SelectItemModelProperty.Type<InventoryRecorderState, InventoryRecorder.State> TYPE = SelectItemModelProperty.Type.create(
            MapCodec.unit(new InventoryRecorderState()), InventoryRecorder.State.CODEC
    );

    @Override
    public InventoryRecorder.State get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
        InventoryRecorder recorder = stack.get(STDataComponents.INVENTORY_RECORDER);
        return recorder == null ? InventoryRecorder.State.IDLE : InventoryRecorder.State.get(recorder.active(), recorder.track().isPresent());
    }

    @Override
    @NonNull
    public Type<? extends SelectItemModelProperty<InventoryRecorder.State>, InventoryRecorder.State> type() {
        return TYPE;
    }

    @Override
    @NonNull
    public Codec<InventoryRecorder.State> valueCodec() {
        return InventoryRecorder.State.CODEC;
    }
}
