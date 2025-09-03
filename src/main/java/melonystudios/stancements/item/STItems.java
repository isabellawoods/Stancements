package melonystudios.stancements.item;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.item.custom.VinylDiscItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

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

    // Items
    public static final DeferredItem<Item> VINYL_DISC = ITEMS.register("vinyl_disc", () -> new VinylDiscItem(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> RECORDED_DISC = ITEMS.register("recorded_disc", () -> new RecordedDiscItem(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
}
