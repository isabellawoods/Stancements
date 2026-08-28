package melonystudios.stancements.item.tab;

import melonystudios.stancements.option.STCommonOptions;
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
        if (!STCommonOptions.ADD_ITEMS_TO_VANILLA_TABS.get()) return;

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.insertAfter(new ItemStack(Items.OAK_BUTTON), STItems.OAK_SHELF.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.SPRUCE_BUTTON), STItems.SPRUCE_SHELF.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.BIRCH_BUTTON), STItems.BIRCH_SHELF.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.JUNGLE_BUTTON), STItems.JUNGLE_SHELF.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.ACACIA_BUTTON), STItems.ACACIA_SHELF.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.DARK_OAK_BUTTON), STItems.DARK_OAK_SHELF.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.MANGROVE_BUTTON), STItems.MANGROVE_SHELF.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.CHERRY_BUTTON), STItems.CHERRY_SHELF.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.BAMBOO_BUTTON), STItems.BAMBOO_SHELF.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.CRIMSON_BUTTON), STItems.CRIMSON_SHELF.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.WARPED_BUTTON), STItems.WARPED_SHELF.toStack(), PARENT_AND_SEARCH_TABS);

            ResourceKey<Item> paleOakButton = ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("pale_oak_button"));
            event.getParameters().holders().lookupOrThrow(Registries.ITEM).get(paleOakButton).ifPresent(
                    item -> event.insertAfter(new ItemStack(item), STItems.PALE_OAK_SHELF.toStack(), PARENT_AND_SEARCH_TABS)
            );
        }

        if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            event.insertAfter(new ItemStack(Items.PINK_STAINED_GLASS_PANE), STItems.WHITE_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.WHITE_CRAFTING_TABLE_CLOTH.toStack(), STItems.LIGHT_GRAY_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.LIGHT_GRAY_CRAFTING_TABLE_CLOTH.toStack(), STItems.GRAY_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.GRAY_CRAFTING_TABLE_CLOTH.toStack(), STItems.BLACK_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.BLACK_CRAFTING_TABLE_CLOTH.toStack(), STItems.BROWN_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.BROWN_CRAFTING_TABLE_CLOTH.toStack(), STItems.RED_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.RED_CRAFTING_TABLE_CLOTH.toStack(), STItems.ORANGE_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.ORANGE_CRAFTING_TABLE_CLOTH.toStack(), STItems.YELLOW_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.YELLOW_CRAFTING_TABLE_CLOTH.toStack(), STItems.LIME_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.LIME_CRAFTING_TABLE_CLOTH.toStack(), STItems.GREEN_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.GREEN_CRAFTING_TABLE_CLOTH.toStack(), STItems.CYAN_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.CYAN_CRAFTING_TABLE_CLOTH.toStack(), STItems.LIGHT_BLUE_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.LIGHT_BLUE_CRAFTING_TABLE_CLOTH.toStack(), STItems.BLUE_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.BLUE_CRAFTING_TABLE_CLOTH.toStack(), STItems.PURPLE_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.PURPLE_CRAFTING_TABLE_CLOTH.toStack(), STItems.MAGENTA_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.MAGENTA_CRAFTING_TABLE_CLOTH.toStack(), STItems.PINK_CRAFTING_TABLE_CLOTH.toStack(), PARENT_AND_SEARCH_TABS);
        }

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS || event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.insertAfter(new ItemStack(Items.JUKEBOX), STItems.MUSIC_RECORDER.toStack(), PARENT_AND_SEARCH_TABS);
        }

        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS || event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.insertAfter(new ItemStack(Items.POWERED_RAIL), STItems.GILDED_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.DETECTOR_RAIL), STItems.WHITE_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.WHITE_TAGGING_RAIL.toStack(), STItems.LIGHT_GRAY_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.LIGHT_GRAY_TAGGING_RAIL.toStack(), STItems.GRAY_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.GRAY_TAGGING_RAIL.toStack(), STItems.BLACK_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.BLACK_TAGGING_RAIL.toStack(), STItems.BROWN_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.BROWN_TAGGING_RAIL.toStack(), STItems.RED_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.RED_TAGGING_RAIL.toStack(), STItems.ORANGE_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.ORANGE_TAGGING_RAIL.toStack(), STItems.YELLOW_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.YELLOW_TAGGING_RAIL.toStack(), STItems.LIME_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.LIME_TAGGING_RAIL.toStack(), STItems.GREEN_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.GREEN_TAGGING_RAIL.toStack(), STItems.CYAN_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.CYAN_TAGGING_RAIL.toStack(), STItems.LIGHT_BLUE_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.LIGHT_BLUE_TAGGING_RAIL.toStack(), STItems.BLUE_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.BLUE_TAGGING_RAIL.toStack(), STItems.PURPLE_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.PURPLE_TAGGING_RAIL.toStack(), STItems.MAGENTA_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.MAGENTA_TAGGING_RAIL.toStack(), STItems.PINK_TAGGING_RAIL.toStack(), PARENT_AND_SEARCH_TABS);
        }

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.insertAfter(new ItemStack(Items.FLOWER_POT), STItems.cropPot(1, false), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.cropPot(1, false), STItems.cropPot(1, true), PARENT_AND_SEARCH_TABS);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.insertBefore(new ItemStack(Items.MUSIC_DISC_13), STItems.VINYL_DISC.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.VINYL_DISC.toStack(), STItems.RECORDED_DISC.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.RECORDED_DISC.toStack(), STItems.SCULK_INFESTED_VINYL_DISC.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.SCULK_INFESTED_VINYL_DISC.toStack(), STItems.SCULK_INFESTED_RECORDED_DISC.toStack(), PARENT_AND_SEARCH_TABS);

            event.insertBefore(STItems.VINYL_DISC.toStack(), STItems.POCKET_RECORDER.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.POCKET_RECORDER.toStack(), STItems.SHORT_CASSETTE_TAPE.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.SHORT_CASSETTE_TAPE.toStack(), STItems.LONG_CASSETTE_TAPE.toStack(), PARENT_AND_SEARCH_TABS);

            addDyedWaterBuckets(event);

            event.insertAfter(new ItemStack(Items.TNT_MINECART), STItems.WHITE_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.WHITE_TAG.toStack(), STItems.LIGHT_GRAY_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.LIGHT_GRAY_TAG.toStack(), STItems.GRAY_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.GRAY_TAG.toStack(), STItems.BLACK_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.BLACK_TAG.toStack(), STItems.BROWN_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.BROWN_TAG.toStack(), STItems.RED_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.RED_TAG.toStack(), STItems.ORANGE_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.ORANGE_TAG.toStack(), STItems.YELLOW_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.YELLOW_TAG.toStack(), STItems.LIME_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.LIME_TAG.toStack(), STItems.GREEN_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.GREEN_TAG.toStack(), STItems.CYAN_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.CYAN_TAG.toStack(), STItems.LIGHT_BLUE_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.LIGHT_BLUE_TAG.toStack(), STItems.BLUE_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.BLUE_TAG.toStack(), STItems.PURPLE_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.PURPLE_TAG.toStack(), STItems.MAGENTA_TAG.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.MAGENTA_TAG.toStack(), STItems.PINK_TAG.toStack(), PARENT_AND_SEARCH_TABS);
        }

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.insertBefore(new ItemStack(Items.DISC_FRAGMENT_5), STItems.SHATTERED_DISC.toStack(), PARENT_AND_SEARCH_TABS);
            event.insertAfter(STItems.SHATTERED_DISC.toStack(), STItems.SCULK_INFESTED_SHATTERED_DISC.toStack(), PARENT_AND_SEARCH_TABS);
        }
    }

    /// Adds all variants of {@linkplain melonystudios.stancements.item.custom.DyedWaterBucketItem dyed water buckets} to the *"Tools & Utilities"* creative tab.
    /// @param event The {@link BuildCreativeModeTabContentsEvent}, in order to add the buckets.
    private static void addDyedWaterBuckets(BuildCreativeModeTabContentsEvent event) {
        event.insertAfter(new ItemStack(Items.WATER_BUCKET), STItems.DYED_WATER_BUCKET.toStack(), PARENT_AND_SEARCH_TABS);
        if (!STCommonOptions.POPULATE_DYED_WATER_BUCKETS.get()) return;

        List<Integer> colors = List.of(16383998, 15457757, 10329495, 4673362, 1908001, 8606770, 11546150, 16351261, 16701501, 8439583, 6192150, 1481884, 3847130, 8454080, 3949738, 8991416, 13061821, 15961002);
        ItemStack previousBucket = STItems.DYED_WATER_BUCKET.toStack();
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
