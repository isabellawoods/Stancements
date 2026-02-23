package melonystudios.stancements.event;

import melonystudios.reutilities.event.custom.AddComponentTooltipsEvent;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.blockentity.custom.DyedWaterCauldronBlockEntity;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.custom.DyedWaterBucketItem;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import static melonystudios.stancements.item.custom.DyedWaterBucketItem.DEFAULT_WATER_COLOR;

@EventBusSubscriber(modid = Stancements.MOD_ID, value = Dist.CLIENT)
public class STClientEvents {
    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            if (level == null || pos == null) return DEFAULT_WATER_COLOR;
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof DyedWaterCauldronBlockEntity cauldron) {
                return cauldron.getWaterColor();
            } else {
                return 0x5DB7EF; // Cherry grove water color (for testing)
            }
        }, STBlocks.DYED_WATER_CAULDRON.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> tintIndex == 0 ? -1 : DyedItemColor.getOrDefault(stack, RecordedDiscItem.DEFAULT_DISC_COLOR), STItems.RECORDED_DISC.get());
        event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : DyedWaterBucketItem.getColor(stack), STItems.DYED_WATER_BUCKET.get());
    }

    @SubscribeEvent
    public static void addComponentTooltips(AddComponentTooltipsEvent event) {
        event.addComponent(0.1, STDataComponents.MINECART_TAG_COLOR.get());
    }
}
