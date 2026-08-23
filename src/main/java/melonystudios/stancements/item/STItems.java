package melonystudios.stancements.item;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.RecordingTurnsInto;
import melonystudios.stancements.item.custom.*;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
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
    // todo: vanilla has shelves now... what do i do? ~isa 19-04-26
    public static final DeferredItem<Item> OAK_SHELF = ITEMS.registerItem("oak_shelf", properties -> new BlockItem(STBlocks.OAK_SHELF.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> SPRUCE_SHELF = ITEMS.registerItem("spruce_shelf", properties -> new BlockItem(STBlocks.SPRUCE_SHELF.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> BIRCH_SHELF = ITEMS.registerItem("birch_shelf", properties -> new BlockItem(STBlocks.BIRCH_SHELF.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> JUNGLE_SHELF = ITEMS.registerItem("jungle_shelf", properties -> new BlockItem(STBlocks.JUNGLE_SHELF.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> ACACIA_SHELF = ITEMS.registerItem("acacia_shelf", properties -> new BlockItem(STBlocks.ACACIA_SHELF.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> DARK_OAK_SHELF = ITEMS.registerItem("dark_oak_shelf", properties -> new BlockItem(STBlocks.DARK_OAK_SHELF.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MANGROVE_SHELF = ITEMS.registerItem("mangrove_shelf", properties -> new BlockItem(STBlocks.MANGROVE_SHELF.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> CHERRY_SHELF = ITEMS.registerItem("cherry_shelf", properties -> new BlockItem(STBlocks.CHERRY_SHELF.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> PALE_OAK_SHELF = ITEMS.registerItem("pale_oak_shelf", properties -> new BlockItem(STBlocks.PALE_OAK_SHELF.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> BAMBOO_SHELF = ITEMS.registerItem("bamboo_shelf", properties -> new BlockItem(STBlocks.BAMBOO_SHELF.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> CRIMSON_SHELF = ITEMS.registerItem("crimson_shelf", properties -> new BlockItem(STBlocks.CRIMSON_SHELF.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> WARPED_SHELF = ITEMS.registerItem("warped_shelf", properties -> new BlockItem(STBlocks.WARPED_SHELF.get(), properties.useBlockDescriptionPrefix()));

    // Crafting table cloths
    public static final DeferredItem<Item> WHITE_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("white_crafting_table_cloth", properties -> new BlockItem(STBlocks.WHITE_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> LIGHT_GRAY_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("light_gray_crafting_table_cloth", properties -> new BlockItem(STBlocks.LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> GRAY_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("gray_crafting_table_cloth", properties -> new BlockItem(STBlocks.GRAY_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> BLACK_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("black_crafting_table_cloth", properties -> new BlockItem(STBlocks.BLACK_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> BROWN_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("brown_crafting_table_cloth", properties -> new BlockItem(STBlocks.BROWN_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> RED_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("red_crafting_table_cloth", properties -> new BlockItem(STBlocks.RED_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> ORANGE_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("orange_crafting_table_cloth", properties -> new BlockItem(STBlocks.ORANGE_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> YELLOW_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("yellow_crafting_table_cloth", properties -> new BlockItem(STBlocks.YELLOW_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> LIME_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("lime_crafting_table_cloth", properties -> new BlockItem(STBlocks.LIME_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> GREEN_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("green_crafting_table_cloth", properties -> new BlockItem(STBlocks.GREEN_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> CYAN_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("cyan_crafting_table_cloth", properties -> new BlockItem(STBlocks.CYAN_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> LIGHT_BLUE_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("light_blue_crafting_table_cloth", properties -> new BlockItem(STBlocks.LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> BLUE_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("blue_crafting_table_cloth", properties -> new BlockItem(STBlocks.BLUE_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> PURPLE_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("purple_crafting_table_cloth", properties -> new BlockItem(STBlocks.PURPLE_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MAGENTA_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("magenta_crafting_table_cloth", properties -> new BlockItem(STBlocks.MAGENTA_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> PINK_CRAFTING_TABLE_CLOTH = ITEMS.registerItem("pink_crafting_table_cloth", properties -> new BlockItem(STBlocks.PINK_CRAFTING_TABLE_CLOTH.get(), properties.useBlockDescriptionPrefix()));

    // Functional blocks
    public static final DeferredItem<Item> MUSIC_RECORDER = ITEMS.registerItem("music_recorder", properties -> new MusicRecorderItem(STBlocks.MUSIC_RECORDER.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> CROP_POT = ITEMS.registerItem("crop_pot", properties -> new CropPotBlockItem(STBlocks.CROP_POT.get(), properties.useBlockDescriptionPrefix()));

    // Rails
    public static final DeferredItem<Item> GILDED_RAIL = ITEMS.registerItem("gilded_rail", properties -> new GildedRailItem(STBlocks.GILDED_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> WHITE_TAGGING_RAIL = ITEMS.registerItem("white_tagging_rail", properties -> new TaggingRailItem(STBlocks.WHITE_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> LIGHT_GRAY_TAGGING_RAIL = ITEMS.registerItem("light_gray_tagging_rail", properties -> new TaggingRailItem(STBlocks.LIGHT_GRAY_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> GRAY_TAGGING_RAIL = ITEMS.registerItem("gray_tagging_rail", properties -> new TaggingRailItem(STBlocks.GRAY_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> BLACK_TAGGING_RAIL = ITEMS.registerItem("black_tagging_rail", properties -> new TaggingRailItem(STBlocks.BLACK_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> BROWN_TAGGING_RAIL = ITEMS.registerItem("brown_tagging_rail", properties -> new TaggingRailItem(STBlocks.BROWN_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> RED_TAGGING_RAIL = ITEMS.registerItem("red_tagging_rail", properties -> new TaggingRailItem(STBlocks.RED_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> ORANGE_TAGGING_RAIL = ITEMS.registerItem("orange_tagging_rail", properties -> new TaggingRailItem(STBlocks.ORANGE_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> YELLOW_TAGGING_RAIL = ITEMS.registerItem("yellow_tagging_rail", properties -> new TaggingRailItem(STBlocks.YELLOW_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> LIME_TAGGING_RAIL = ITEMS.registerItem("lime_tagging_rail", properties -> new TaggingRailItem(STBlocks.LIME_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> GREEN_TAGGING_RAIL = ITEMS.registerItem("green_tagging_rail", properties -> new TaggingRailItem(STBlocks.GREEN_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> CYAN_TAGGING_RAIL = ITEMS.registerItem("cyan_tagging_rail", properties -> new TaggingRailItem(STBlocks.CYAN_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> LIGHT_BLUE_TAGGING_RAIL = ITEMS.registerItem("light_blue_tagging_rail", properties -> new TaggingRailItem(STBlocks.LIGHT_BLUE_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> BLUE_TAGGING_RAIL = ITEMS.registerItem("blue_tagging_rail", properties -> new TaggingRailItem(STBlocks.BLUE_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> PURPLE_TAGGING_RAIL = ITEMS.registerItem("purple_tagging_rail", properties -> new TaggingRailItem(STBlocks.PURPLE_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> MAGENTA_TAGGING_RAIL = ITEMS.registerItem("magenta_tagging_rail", properties -> new TaggingRailItem(STBlocks.MAGENTA_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));
    public static final DeferredItem<Item> PINK_TAGGING_RAIL = ITEMS.registerItem("pink_tagging_rail", properties -> new TaggingRailItem(STBlocks.PINK_TAGGING_RAIL.get(), properties.useBlockDescriptionPrefix()));

    // Items
    public static final DeferredItem<Item> STANCEMENTS_LOGO = ITEMS.registerItem("stancements_logo", properties -> new ReLogoItem(Stancements.ACCENT_COLOR, properties.fireResistant().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> VINYL_DISC = ITEMS.registerItem("vinyl_disc", properties -> new Item(properties.stacksTo(16).component(STDataComponents.RECORDING_TURNS_INTO, RecordingTurnsInto.vinylDisc())));
    public static final DeferredItem<Item> RECORDED_DISC = ITEMS.registerItem("recorded_disc", properties -> new RecordedDiscItem(properties.rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final DeferredItem<Item> SHATTERED_DISC = ITEMS.registerItem("shattered_disc", properties -> new TooltippedItem(Component.translatable("tooltip.stancements.shattered_disc").withColor(0x808080), properties));
    public static final DeferredItem<Item> SCULK_INFESTED_VINYL_DISC = ITEMS.registerItem("sculk_infested_vinyl_disc", properties -> new TooltippedItem(Component.translatable("tooltip.stancements.sculk_infested_vinyl_disc").withColor(0x05625D), properties.rarity(Rarity.RARE).stacksTo(16).component(STDataComponents.RECORDING_TURNS_INTO, RecordingTurnsInto.sculkInfestedVinylDisc())));
    public static final DeferredItem<Item> SCULK_INFESTED_RECORDED_DISC = ITEMS.registerItem("sculk_infested_recorded_disc", properties -> new RecordedDiscItem(properties.rarity(Rarity.RARE).stacksTo(1)));
    public static final DeferredItem<Item> SCULK_INFESTED_SHATTERED_DISC = ITEMS.registerItem("sculk_infested_shattered_disc", properties -> new TooltippedItem(Component.translatable("tooltip.stancements.shattered_disc").withColor(0x808080), properties.rarity(Rarity.RARE)));
    public static final DeferredItem<Item> DYED_WATER_BUCKET = ITEMS.registerItem("dyed_water_bucket", properties -> new DyedWaterBucketItem(properties.stacksTo(1).craftRemainder(Items.BUCKET).component(DataComponents.DYED_COLOR, new DyedItemColor(DyedWaterBucketItem.DEFAULT_WATER_COLOR))));

    // Minecart tags
    public static final DeferredItem<Item> WHITE_TAG = ITEMS.registerItem("white_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.WHITE))));
    public static final DeferredItem<Item> LIGHT_GRAY_TAG = ITEMS.registerItem("light_gray_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.LIGHT_GRAY))));
    public static final DeferredItem<Item> GRAY_TAG = ITEMS.registerItem("gray_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.GRAY))));
    public static final DeferredItem<Item> BLACK_TAG = ITEMS.registerItem("black_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.BLACK))));
    public static final DeferredItem<Item> BROWN_TAG = ITEMS.registerItem("brown_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.BROWN))));
    public static final DeferredItem<Item> RED_TAG = ITEMS.registerItem("red_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.RED))));
    public static final DeferredItem<Item> ORANGE_TAG = ITEMS.registerItem("orange_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.ORANGE))));
    public static final DeferredItem<Item> YELLOW_TAG = ITEMS.registerItem("yellow_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.YELLOW))));
    public static final DeferredItem<Item> LIME_TAG = ITEMS.registerItem("lime_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.LIME))));
    public static final DeferredItem<Item> GREEN_TAG = ITEMS.registerItem("green_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.GREEN))));
    public static final DeferredItem<Item> CYAN_TAG = ITEMS.registerItem("cyan_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.CYAN))));
    public static final DeferredItem<Item> LIGHT_BLUE_TAG = ITEMS.registerItem("light_blue_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.LIGHT_BLUE))));
    public static final DeferredItem<Item> BLUE_TAG = ITEMS.registerItem("blue_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.BLUE))));
    public static final DeferredItem<Item> PURPLE_TAG = ITEMS.registerItem("purple_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.PURPLE))));
    public static final DeferredItem<Item> MAGENTA_TAG = ITEMS.registerItem("magenta_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.MAGENTA))));
    public static final DeferredItem<Item> PINK_TAG = ITEMS.registerItem("pink_tag", properties -> new Item(properties.stacksTo(16).component(STDataComponents.MINECART_TAG_COLOR, of(DyeColor.PINK))));

    /// Makes an {@link ItemStackTemplate} of a crop pot with a variable count and "hopper-ness".
    /// @param count The amount of pots in the stack.
    /// @param hopping Whether this crop pot has a built-in hopper.
    public static ItemStackTemplate cropPot(int count, boolean hopping) {
        return new ItemStackTemplate(
                STItems.CROP_POT.get(),
                count,
                DataComponentPatch.builder()
                .set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(STBlockStateProperties.HOPPING, hopping))
                .build()
        );
    }
}
