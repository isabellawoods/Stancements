package melonystudios.stancements.item.tab;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.custom.DyedWaterBucketItem;
import melonystudios.stancements.option.STCommonOptions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static melonystudios.stancements.item.STItems.*;

public class STCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Stancements.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .icon(() -> STANCEMENTS_LOGO.get().getDefaultInstance()).title(Component.translatable("tab.stancements.main").withColor(Stancements.ACCENT_COLOR)).displayItems(((parameters, output) -> {
                if (STCommonOptions.ADD_ITEMS_TO_VANILLA_TABS.get()) return;
                // Functional blocks
                output.accept(MUSIC_RECORDER);
                // Vinyl & recorded discs (items but should be grouped together with the recorder)
                output.accept(VINYL_DISC);
                output.accept(RECORDED_DISC);
                output.accept(SHATTERED_DISC);
                output.accept(SCULK_INFESTED_VINYL_DISC);
                output.accept(SCULK_INFESTED_RECORDED_DISC);
                output.accept(SCULK_INFESTED_SHATTERED_DISC);

                // Decorative blocks
                // Shelves
                output.accept(OAK_SHELF);
                output.accept(SPRUCE_SHELF);
                output.accept(BIRCH_SHELF);
                output.accept(JUNGLE_SHELF);
                output.accept(ACACIA_SHELF);
                output.accept(DARK_OAK_SHELF);
                output.accept(MANGROVE_SHELF);
                output.accept(CHERRY_SHELF);
                output.accept(PALE_OAK_SHELF);
                output.accept(BAMBOO_SHELF);
                output.accept(CRIMSON_SHELF);
                output.accept(WARPED_SHELF);

                // Crafting Table Cloths
                output.accept(WHITE_CRAFTING_TABLE_CLOTH);
                output.accept(LIGHT_GRAY_CRAFTING_TABLE_CLOTH);
                output.accept(GRAY_CRAFTING_TABLE_CLOTH);
                output.accept(BLACK_CRAFTING_TABLE_CLOTH);
                output.accept(BROWN_CRAFTING_TABLE_CLOTH);
                output.accept(RED_CRAFTING_TABLE_CLOTH);
                output.accept(ORANGE_CRAFTING_TABLE_CLOTH);
                output.accept(YELLOW_CRAFTING_TABLE_CLOTH);
                output.accept(LIME_CRAFTING_TABLE_CLOTH);
                output.accept(GREEN_CRAFTING_TABLE_CLOTH);
                output.accept(CYAN_CRAFTING_TABLE_CLOTH);
                output.accept(LIGHT_BLUE_CRAFTING_TABLE_CLOTH);
                output.accept(BLUE_CRAFTING_TABLE_CLOTH);
                output.accept(PURPLE_CRAFTING_TABLE_CLOTH);
                output.accept(MAGENTA_CRAFTING_TABLE_CLOTH);
                output.accept(PINK_CRAFTING_TABLE_CLOTH);

                // Rails
                output.accept(GILDED_RAIL);
                output.accept(WHITE_TAGGING_RAIL);
                output.accept(LIGHT_GRAY_TAGGING_RAIL);
                output.accept(GRAY_TAGGING_RAIL);
                output.accept(BLACK_TAGGING_RAIL);
                output.accept(BROWN_TAGGING_RAIL);
                output.accept(RED_TAGGING_RAIL);
                output.accept(ORANGE_TAGGING_RAIL);
                output.accept(YELLOW_TAGGING_RAIL);
                output.accept(LIME_TAGGING_RAIL);
                output.accept(GREEN_TAGGING_RAIL);
                output.accept(CYAN_TAGGING_RAIL);
                output.accept(LIGHT_BLUE_TAGGING_RAIL);
                output.accept(BLUE_TAGGING_RAIL);
                output.accept(PURPLE_TAGGING_RAIL);
                output.accept(MAGENTA_TAGGING_RAIL);
                output.accept(PINK_TAGGING_RAIL);

                // Functional blocks
                output.accept(cropPot(1, false).create());
                output.accept(cropPot(1, true).create());

                // Items
                output.accept(WHITE_TAG);
                output.accept(LIGHT_GRAY_TAG);
                output.accept(GRAY_TAG);
                output.accept(BLACK_TAG);
                output.accept(BROWN_TAG);
                output.accept(RED_TAG);
                output.accept(ORANGE_TAG);
                output.accept(YELLOW_TAG);
                output.accept(LIME_TAG);
                output.accept(GREEN_TAG);
                output.accept(CYAN_TAG);
                output.accept(LIGHT_BLUE_TAG);
                output.accept(BLUE_TAG);
                output.accept(PURPLE_TAG);
                output.accept(MAGENTA_TAG);
                output.accept(PINK_TAG);
                addDyedWaterBuckets(output);
            })).build());

    /// Adds all variants of {@linkplain DyedWaterBucketItem dyed water buckets} to *Stancements*' main creative tab.
    /// @param output The tab's item adder.
    private static void addDyedWaterBuckets(CreativeModeTab.Output output) {
        addBucket(output, DyedWaterBucketItem.DEFAULT_WATER_COLOR);
        if (!STCommonOptions.POPULATE_DYED_WATER_BUCKETS.get()) return;

        List<Integer> colors = List.of(16383998, 15457757, 10329495, 4673362, 1908001, 8606770, 11546150, 16351261, 16701501, 8439583, 6192150, 1481884, 3847130, 8454080, 3949738, 8991416, 13061821, 15961002);
        for (Integer color : colors) {
            addBucket(output, color);
        }
    }

    /// Adds a single dyed water bucket to *Stancements*' main creative tab.
    /// @param output The tab's item adder.
    /// @param color The color to apply to the bucket stack.
    private static void addBucket(CreativeModeTab.Output output, Integer color) {
        ItemStack dyedBucket = new ItemStack(DYED_WATER_BUCKET.get());
        dyedBucket.set(DataComponents.DYED_COLOR, new DyedItemColor(color));
        output.accept(dyedBucket);
    }
}
