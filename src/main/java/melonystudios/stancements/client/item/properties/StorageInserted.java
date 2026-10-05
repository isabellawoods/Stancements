package melonystudios.stancements.client.item.properties;

import com.mojang.serialization.MapCodec;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.InventoryRecorder;
import melonystudios.stancements.tag.STItemTags;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public record StorageInserted() implements ConditionalItemModelProperty {
    public static final MapCodec<StorageInserted> CODEC = MapCodec.unit(new StorageInserted());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
        InventoryRecorder recorder = stack.get(STDataComponents.INVENTORY_RECORDER);
        return recorder != null && recorder.item().isPresent() && recorder.item().get().is(STItemTags.CASSETTE_TAPES);
    }

    @Override
    @NonNull
    public MapCodec<? extends ConditionalItemModelProperty> type() {
        return CODEC;
    }
}
