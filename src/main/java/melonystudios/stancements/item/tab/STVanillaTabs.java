package melonystudios.stancements.item.tab;

import melonystudios.stancements.option.STOptions;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.STItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.List;

import static melonystudios.stancements.item.STItems.DYED_WATER_BUCKET;
import static net.minecraft.world.item.CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;

@EventBusSubscriber(modid = Stancements.MOD_ID)
public class STVanillaTabs {
    @SubscribeEvent
    public static void addToVanillaTabs(BuildCreativeModeTabContentsEvent event) {
        if (!STOptions.ADD_ITEMS_TO_VANILLA_TABS.get()) return;

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.insertAfter(new ItemStack(Items.OAK_BUTTON), new ItemStack(STItems.OAK_SHELF.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.SPRUCE_BUTTON), new ItemStack(STItems.SPRUCE_SHELF.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.BIRCH_BUTTON), new ItemStack(STItems.BIRCH_SHELF.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.JUNGLE_BUTTON), new ItemStack(STItems.JUNGLE_SHELF.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.ACACIA_BUTTON), new ItemStack(STItems.ACACIA_SHELF.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.DARK_OAK_BUTTON), new ItemStack(STItems.DARK_OAK_SHELF.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.MANGROVE_BUTTON), new ItemStack(STItems.MANGROVE_SHELF.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.CHERRY_BUTTON), new ItemStack(STItems.CHERRY_SHELF.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.BAMBOO_BUTTON), new ItemStack(STItems.BAMBOO_SHELF.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.CRIMSON_BUTTON), new ItemStack(STItems.CRIMSON_SHELF.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.WARPED_BUTTON), new ItemStack(STItems.WARPED_SHELF.get()), PARENT_AND_SEARCH_TABS);

            ResourceKey<Item> paleOakButton = ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("pale_oak_button"));
            event.getParameters().holders().lookupOrThrow(Registries.ITEM).get(paleOakButton).ifPresent(
                    item -> event.insertAfter(new ItemStack(item), new ItemStack(STItems.PALE_OAK_SHELF.get()), PARENT_AND_SEARCH_TABS)
            );
        }

        if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            event.insertAfter(new ItemStack(Items.PINK_STAINED_GLASS_PANE), new ItemStack(STItems.WHITE_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.WHITE_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.GRAY_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.GRAY_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.BLACK_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.BLACK_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.BROWN_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.BROWN_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.RED_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.RED_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.ORANGE_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.ORANGE_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.YELLOW_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.YELLOW_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.LIME_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.LIME_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.GREEN_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.GREEN_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.CYAN_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.CYAN_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.BLUE_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.BLUE_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.PURPLE_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.PURPLE_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.MAGENTA_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.MAGENTA_CRAFTING_TABLE_CLOTH.get()), new ItemStack(STItems.PINK_CRAFTING_TABLE_CLOTH.get()), PARENT_AND_SEARCH_TABS);
        }

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS || event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.insertAfter(new ItemStack(Items.JUKEBOX), new ItemStack(STItems.MUSIC_RECORDER.get()), PARENT_AND_SEARCH_TABS);
        }

        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS || event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.insertAfter(new ItemStack(Items.POWERED_RAIL), new ItemStack(STItems.GILDED_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.DETECTOR_RAIL), new ItemStack(STItems.WHITE_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.WHITE_TAGGING_RAIL.get()), new ItemStack(STItems.LIGHT_GRAY_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.LIGHT_GRAY_TAGGING_RAIL.get()), new ItemStack(STItems.GRAY_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.GRAY_TAGGING_RAIL.get()), new ItemStack(STItems.BLACK_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.BLACK_TAGGING_RAIL.get()), new ItemStack(STItems.BROWN_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.BROWN_TAGGING_RAIL.get()), new ItemStack(STItems.RED_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.RED_TAGGING_RAIL.get()), new ItemStack(STItems.ORANGE_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.ORANGE_TAGGING_RAIL.get()), new ItemStack(STItems.YELLOW_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.YELLOW_TAGGING_RAIL.get()), new ItemStack(STItems.LIME_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.LIME_TAGGING_RAIL.get()), new ItemStack(STItems.GREEN_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.GREEN_TAGGING_RAIL.get()), new ItemStack(STItems.CYAN_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.CYAN_TAGGING_RAIL.get()), new ItemStack(STItems.LIGHT_BLUE_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.LIGHT_BLUE_TAGGING_RAIL.get()), new ItemStack(STItems.BLUE_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.BLUE_TAGGING_RAIL.get()), new ItemStack(STItems.PURPLE_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.PURPLE_TAGGING_RAIL.get()), new ItemStack(STItems.MAGENTA_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.MAGENTA_TAGGING_RAIL.get()), new ItemStack(STItems.PINK_TAGGING_RAIL.get()), PARENT_AND_SEARCH_TABS);
        }

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.insertAfter(new ItemStack(Items.FLOWER_POT), STItems.cropPot(1, false), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.cropPot(1, false), STItems.cropPot(1, true), PARENT_AND_SEARCH_TABS);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.insertBefore(new ItemStack(Items.MUSIC_DISC_13), new ItemStack(STItems.VINYL_DISC.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.VINYL_DISC.get()), new ItemStack(STItems.RECORDED_DISC.get()), PARENT_AND_SEARCH_TABS);
            addDyedWaterBuckets(event);

            event.insertAfter(new ItemStack(Items.TNT_MINECART), new ItemStack(STItems.WHITE_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.WHITE_TAG.get()), new ItemStack(STItems.LIGHT_GRAY_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.LIGHT_GRAY_TAG.get()), new ItemStack(STItems.GRAY_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.GRAY_TAG.get()), new ItemStack(STItems.BLACK_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.BLACK_TAG.get()), new ItemStack(STItems.BROWN_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.BROWN_TAG.get()), new ItemStack(STItems.RED_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.RED_TAG.get()), new ItemStack(STItems.ORANGE_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.ORANGE_TAG.get()), new ItemStack(STItems.YELLOW_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.YELLOW_TAG.get()), new ItemStack(STItems.LIME_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.LIME_TAG.get()), new ItemStack(STItems.GREEN_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.GREEN_TAG.get()), new ItemStack(STItems.CYAN_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.CYAN_TAG.get()), new ItemStack(STItems.LIGHT_BLUE_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.LIGHT_BLUE_TAG.get()), new ItemStack(STItems.BLUE_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.BLUE_TAG.get()), new ItemStack(STItems.PURPLE_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.PURPLE_TAG.get()), new ItemStack(STItems.MAGENTA_TAG.get()), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(STItems.MAGENTA_TAG.get()), new ItemStack(STItems.PINK_TAG.get()), PARENT_AND_SEARCH_TABS);
        }
    }

    /// Adds all variants of {@linkplain melonystudios.stancements.item.custom.DyedWaterBucketItem dyed water buckets} to the *"Tools & Utilities"* creative tab.
    /// @param event The {@link BuildCreativeModeTabContentsEvent}, in order to add the buckets.
    private static void addDyedWaterBuckets(BuildCreativeModeTabContentsEvent event) {
        event.insertAfter(new ItemStack(Items.WATER_BUCKET), new ItemStack(STItems.DYED_WATER_BUCKET.get()), PARENT_AND_SEARCH_TABS);
        if (!STOptions.POPULATE_DYED_WATER_BUCKETS.get()) return;

        List<Integer> colors = List.of(16383998, 15457757, 10329495, 4673362, 1908001, 8606770, 11546150, 16351261, 16701501, 8439583, 6192150, 1481884, 3847130, 8454080, 3949738, 8991416, 13061821, 15961002);
        ItemStack previousBucket = new ItemStack(STItems.DYED_WATER_BUCKET.get());
        for (Integer color : colors) {
            ItemStack stack = addBucket(color);
            event.insertAfter(previousBucket, stack, PARENT_AND_SEARCH_TABS);
            previousBucket = stack;
        }
    }

    /// Makes an {@link ItemStack} of a single dyed water bucket.
    /// @param color The color to apply to the bucket stack.
    private static ItemStack addBucket(int color) {
        ItemStack dyedBucket = new ItemStack(DYED_WATER_BUCKET.get());
        dyedBucket.set(DataComponents.DYED_COLOR, new DyedItemColor(color, true));
        return dyedBucket;
    }
}
