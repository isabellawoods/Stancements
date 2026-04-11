package melonystudios.stancements.item;

import melonystudios.reutilities.item.custom.LogoItem;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.RecordingTurnsInto;
import melonystudios.stancements.item.custom.CropPotBlockItem;
import melonystudios.stancements.item.custom.DyedWaterBucketItem;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static melonystudios.stancements.component.custom.MinecartTagColor.of;

public class STItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Stancements.MOD_ID);

    // Decorative blocks
    // Shelves
    public static final DeferredItem<Item> OAK_SHELF = ITEMS.register("oak_shelf", () -> new BlockItem(STBlocks.OAK_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> SPRUCE_SHELF = ITEMS.register("spruce_shelf", () -> new BlockItem(STBlocks.SPRUCE_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> BIRCH_SHELF = ITEMS.register("birch_shelf", () -> new BlockItem(STBlocks.BIRCH_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> JUNGLE_SHELF = ITEMS.register("jungle_shelf", () -> new BlockItem(STBlocks.JUNGLE_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> ACACIA_SHELF = ITEMS.register("acacia_shelf", () -> new BlockItem(STBlocks.ACACIA_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> DARK_OAK_SHELF = ITEMS.register("dark_oak_shelf", () -> new BlockItem(STBlocks.DARK_OAK_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> MANGROVE_SHELF = ITEMS.register("mangrove_shelf", () -> new BlockItem(STBlocks.MANGROVE_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> CHERRY_SHELF = ITEMS.register("cherry_shelf", () -> new BlockItem(STBlocks.CHERRY_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> BAMBOO_SHELF = ITEMS.register("bamboo_shelf", () -> new BlockItem(STBlocks.BAMBOO_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> CRIMSON_SHELF = ITEMS.register("crimson_shelf", () -> new BlockItem(STBlocks.CRIMSON_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> WARPED_SHELF = ITEMS.register("warped_shelf", () -> new BlockItem(STBlocks.WARPED_SHELF.get(), new Item.Properties()));

    // Crafting Table Cloths
    public static final DeferredItem<Item> WHITE_CRAFTING_TABLE_CLOTH = ITEMS.register("white_crafting_table_cloth", () -> new BlockItem(STBlocks.WHITE_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> LIGHT_GRAY_CRAFTING_TABLE_CLOTH = ITEMS.register("light_gray_crafting_table_cloth", () -> new BlockItem(STBlocks.LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> GRAY_CRAFTING_TABLE_CLOTH = ITEMS.register("gray_crafting_table_cloth", () -> new BlockItem(STBlocks.GRAY_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> BLACK_CRAFTING_TABLE_CLOTH = ITEMS.register("black_crafting_table_cloth", () -> new BlockItem(STBlocks.BLACK_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> BROWN_CRAFTING_TABLE_CLOTH = ITEMS.register("brown_crafting_table_cloth", () -> new BlockItem(STBlocks.BROWN_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> RED_CRAFTING_TABLE_CLOTH = ITEMS.register("red_crafting_table_cloth", () -> new BlockItem(STBlocks.RED_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> ORANGE_CRAFTING_TABLE_CLOTH = ITEMS.register("orange_crafting_table_cloth", () -> new BlockItem(STBlocks.ORANGE_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> YELLOW_CRAFTING_TABLE_CLOTH = ITEMS.register("yellow_crafting_table_cloth", () -> new BlockItem(STBlocks.YELLOW_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> LIME_CRAFTING_TABLE_CLOTH = ITEMS.register("lime_crafting_table_cloth", () -> new BlockItem(STBlocks.LIME_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> GREEN_CRAFTING_TABLE_CLOTH = ITEMS.register("green_crafting_table_cloth", () -> new BlockItem(STBlocks.GREEN_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> CYAN_CRAFTING_TABLE_CLOTH = ITEMS.register("cyan_crafting_table_cloth", () -> new BlockItem(STBlocks.CYAN_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> LIGHT_BLUE_CRAFTING_TABLE_CLOTH = ITEMS.register("light_blue_crafting_table_cloth", () -> new BlockItem(STBlocks.LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> BLUE_CRAFTING_TABLE_CLOTH = ITEMS.register("blue_crafting_table_cloth", () -> new BlockItem(STBlocks.BLUE_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> PURPLE_CRAFTING_TABLE_CLOTH = ITEMS.register("purple_crafting_table_cloth", () -> new BlockItem(STBlocks.PURPLE_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> MAGENTA_CRAFTING_TABLE_CLOTH = ITEMS.register("magenta_crafting_table_cloth", () -> new BlockItem(STBlocks.MAGENTA_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));
    public static final DeferredItem<Item> PINK_CRAFTING_TABLE_CLOTH = ITEMS.register("pink_crafting_table_cloth", () -> new BlockItem(STBlocks.PINK_CRAFTING_TABLE_CLOTH.get(), new Item.Properties()));

    // Functional blocks
    public static final DeferredItem<Item> MUSIC_RECORDER = ITEMS.register("music_recorder", () -> new BlockItem(STBlocks.MUSIC_RECORDER.get(), new Item.Properties()));
    public static final DeferredItem<Item> CROP_POT = ITEMS.register("crop_pot", () -> new CropPotBlockItem(STBlocks.CROP_POT.get(), new Item.Properties()));

    // Rails
    public static final DeferredItem<Item> GILDED_RAIL = ITEMS.register("gilded_rail", () -> new BlockItem(STBlocks.GILDED_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> WHITE_TAGGING_RAIL = ITEMS.register("white_tagging_rail", () -> new BlockItem(STBlocks.WHITE_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> LIGHT_GRAY_TAGGING_RAIL = ITEMS.register("light_gray_tagging_rail", () -> new BlockItem(STBlocks.LIGHT_GRAY_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> GRAY_TAGGING_RAIL = ITEMS.register("gray_tagging_rail", () -> new BlockItem(STBlocks.GRAY_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> BLACK_TAGGING_RAIL = ITEMS.register("black_tagging_rail", () -> new BlockItem(STBlocks.BLACK_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> BROWN_TAGGING_RAIL = ITEMS.register("brown_tagging_rail", () -> new BlockItem(STBlocks.BROWN_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> RED_TAGGING_RAIL = ITEMS.register("red_tagging_rail", () -> new BlockItem(STBlocks.RED_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> ORANGE_TAGGING_RAIL = ITEMS.register("orange_tagging_rail", () -> new BlockItem(STBlocks.ORANGE_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> YELLOW_TAGGING_RAIL = ITEMS.register("yellow_tagging_rail", () -> new BlockItem(STBlocks.YELLOW_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> LIME_TAGGING_RAIL = ITEMS.register("lime_tagging_rail", () -> new BlockItem(STBlocks.LIME_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> GREEN_TAGGING_RAIL = ITEMS.register("green_tagging_rail", () -> new BlockItem(STBlocks.GREEN_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> CYAN_TAGGING_RAIL = ITEMS.register("cyan_tagging_rail", () -> new BlockItem(STBlocks.CYAN_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> LIGHT_BLUE_TAGGING_RAIL = ITEMS.register("light_blue_tagging_rail", () -> new BlockItem(STBlocks.LIGHT_BLUE_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> BLUE_TAGGING_RAIL = ITEMS.register("blue_tagging_rail", () -> new BlockItem(STBlocks.BLUE_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> PURPLE_TAGGING_RAIL = ITEMS.register("purple_tagging_rail", () -> new BlockItem(STBlocks.PURPLE_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> MAGENTA_TAGGING_RAIL = ITEMS.register("magenta_tagging_rail", () -> new BlockItem(STBlocks.MAGENTA_TAGGING_RAIL.get(), new Item.Properties()));
    public static final DeferredItem<Item> PINK_TAGGING_RAIL = ITEMS.register("pink_tagging_rail", () -> new BlockItem(STBlocks.PINK_TAGGING_RAIL.get(), new Item.Properties()));

    // Items
    public static final DeferredItem<Item> STANCEMENTS_LOGO = ITEMS.register("stancements_logo", () -> new LogoItem(Stancements.ACCENT_COLOR, new Item.Properties().fireResistant().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> VINYL_DISC = ITEMS.register("vinyl_disc", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.RECORDING_TURNS_INTO, RecordingTurnsInto.vinylDisc())));
    public static final DeferredItem<Item> RECORDED_DISC = ITEMS.register("recorded_disc", () -> new RecordedDiscItem(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final DeferredItem<Item> DYED_WATER_BUCKET = ITEMS.register("dyed_water_bucket", () -> new DyedWaterBucketItem(new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET).component(DataComponents.DYED_COLOR, new DyedItemColor(DyedWaterBucketItem.DEFAULT_WATER_COLOR, true))));

    // Minecart tags
    public static final DeferredItem<Item> WHITE_TAG = ITEMS.register("white_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.WHITE))));
    public static final DeferredItem<Item> LIGHT_GRAY_TAG = ITEMS.register("light_gray_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.LIGHT_GRAY))));
    public static final DeferredItem<Item> GRAY_TAG = ITEMS.register("gray_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.GRAY))));
    public static final DeferredItem<Item> BLACK_TAG = ITEMS.register("black_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.BLACK))));
    public static final DeferredItem<Item> BROWN_TAG = ITEMS.register("brown_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.BROWN))));
    public static final DeferredItem<Item> RED_TAG = ITEMS.register("red_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.RED))));
    public static final DeferredItem<Item> ORANGE_TAG = ITEMS.register("orange_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.ORANGE))));
    public static final DeferredItem<Item> YELLOW_TAG = ITEMS.register("yellow_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.YELLOW))));
    public static final DeferredItem<Item> LIME_TAG = ITEMS.register("lime_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.LIME))));
    public static final DeferredItem<Item> GREEN_TAG = ITEMS.register("green_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.GREEN))));
    public static final DeferredItem<Item> CYAN_TAG = ITEMS.register("cyan_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.CYAN))));
    public static final DeferredItem<Item> LIGHT_BLUE_TAG = ITEMS.register("light_blue_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.LIGHT_BLUE))));
    public static final DeferredItem<Item> BLUE_TAG = ITEMS.register("blue_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.BLUE))));
    public static final DeferredItem<Item> PURPLE_TAG = ITEMS.register("purple_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.PURPLE))));
    public static final DeferredItem<Item> MAGENTA_TAG = ITEMS.register("magenta_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.MAGENTA))));
    public static final DeferredItem<Item> PINK_TAG = ITEMS.register("pink_tag", () -> new Item(new Item.Properties().stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.PINK))));

    /// Makes an {@link ItemStack} of a hopping crop pot with a variable count.
    /// @param count The amount of pots in the stack.
    public static ItemStack hoppingCropPot(int count) {
        ItemStack stack = new ItemStack(STItems.CROP_POT.get(), count);
        stack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(STBlockStateProperties.HOPPING, true));
        return stack;
    }
}
