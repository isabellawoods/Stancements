package melonystudios.stancements.block;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.custom.*;
import melonystudios.stancements.block.custom.croppot.*;
import melonystudios.stancements.mixin.BlockBehaviourIDAccessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Stancements.MOD_ID);

    // Decorative
    // Shelves
    public static final DeferredBlock<Block> OAK_SHELF = BLOCKS.registerBlock("oak_shelf", properties -> new STShelfBlock(ofFullCopy(properties, Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> SPRUCE_SHELF = BLOCKS.registerBlock("spruce_shelf", properties -> new STShelfBlock(ofFullCopy(properties, Blocks.SPRUCE_PLANKS)));
    public static final DeferredBlock<Block> BIRCH_SHELF = BLOCKS.registerBlock("birch_shelf", properties -> new STShelfBlock(ofFullCopy(properties, Blocks.BIRCH_PLANKS)));
    public static final DeferredBlock<Block> JUNGLE_SHELF = BLOCKS.registerBlock("jungle_shelf", properties -> new STShelfBlock(ofFullCopy(properties, Blocks.JUNGLE_PLANKS)));
    public static final DeferredBlock<Block> ACACIA_SHELF = BLOCKS.registerBlock("acacia_shelf", properties -> new STShelfBlock(ofFullCopy(properties, Blocks.ACACIA_PLANKS)));
    public static final DeferredBlock<Block> DARK_OAK_SHELF = BLOCKS.registerBlock("dark_oak_shelf", properties -> new STShelfBlock(ofFullCopy(properties, Blocks.DARK_OAK_PLANKS)));
    public static final DeferredBlock<Block> MANGROVE_SHELF = BLOCKS.registerBlock("mangrove_shelf", properties -> new STShelfBlock(ofFullCopy(properties, Blocks.MANGROVE_PLANKS)));
    public static final DeferredBlock<Block> CHERRY_SHELF = BLOCKS.registerBlock("cherry_shelf", properties -> new STShelfBlock(ofFullCopy(properties, Blocks.CHERRY_PLANKS)));
    public static final DeferredBlock<Block> BAMBOO_SHELF = BLOCKS.registerBlock("bamboo_shelf", properties -> new STShelfBlock(ofFullCopy(properties, Blocks.BAMBOO_PLANKS)));
    public static final DeferredBlock<Block> CRIMSON_SHELF = BLOCKS.registerBlock("crimson_shelf", properties -> new STShelfBlock(ofFullCopy(properties, Blocks.CRIMSON_PLANKS)));
    public static final DeferredBlock<Block> WARPED_SHELF = BLOCKS.registerBlock("warped_shelf", properties -> new STShelfBlock(ofFullCopy(properties, Blocks.WARPED_PLANKS)));

    // Crafting Table Cloths
    public static final DeferredBlock<Block> WHITE_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("white_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.WHITE_CARPET)));
    public static final DeferredBlock<Block> LIGHT_GRAY_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("light_gray_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.LIGHT_GRAY_CARPET)));
    public static final DeferredBlock<Block> GRAY_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("gray_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.GRAY_CARPET)));
    public static final DeferredBlock<Block> BLACK_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("black_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.BLACK_CARPET)));
    public static final DeferredBlock<Block> BROWN_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("brown_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.BROWN_CARPET)));
    public static final DeferredBlock<Block> RED_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("red_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.RED_CARPET)));
    public static final DeferredBlock<Block> ORANGE_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("orange_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.ORANGE_CARPET)));
    public static final DeferredBlock<Block> YELLOW_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("yellow_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.YELLOW_CARPET)));
    public static final DeferredBlock<Block> LIME_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("lime_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.LIME_CARPET)));
    public static final DeferredBlock<Block> GREEN_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("green_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.GREEN_CARPET)));
    public static final DeferredBlock<Block> CYAN_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("cyan_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.CYAN_CARPET)));
    public static final DeferredBlock<Block> LIGHT_BLUE_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("light_blue_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.LIGHT_BLUE_CARPET)));
    public static final DeferredBlock<Block> BLUE_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("blue_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.BLUE_CARPET)));
    public static final DeferredBlock<Block> PURPLE_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("purple_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.PURPLE_CARPET)));
    public static final DeferredBlock<Block> MAGENTA_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("magenta_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.MAGENTA_CARPET)));
    public static final DeferredBlock<Block> PINK_CRAFTING_TABLE_CLOTH = BLOCKS.registerBlock("pink_crafting_table_cloth", properties -> new CraftingTableClothBlock(ofFullCopy(properties, Blocks.PINK_CARPET)));

    // Functional
    public static final DeferredBlock<Block> MUSIC_RECORDER = BLOCKS.registerBlock("music_recorder", properties -> new MusicRecorderBlock(ofFullCopy(properties, Blocks.JUKEBOX)));
    public static final DeferredBlock<Block> DYED_WATER_CAULDRON = BLOCKS.registerBlock("dyed_water_cauldron", properties -> new DyedWaterCauldronBlock(ofFullCopy(properties, Blocks.CAULDRON)));
    public static final DeferredBlock<Block> MILK_CAULDRON = BLOCKS.registerBlock("milk_cauldron", properties -> new MilkCauldronBlock(ofFullCopy(properties, Blocks.CAULDRON)));
    public static final DeferredBlock<Block> CROP_POT = BLOCKS.registerBlock("crop_pot", properties -> new CropPotBlock(properties.noOcclusion().pushReaction(PushReaction.DESTROY).requiresCorrectToolForDrops().strength(1.25F, 4.2F)));
    public static final DeferredBlock<Block> WHEAT_CROP_POT = BLOCKS.registerBlock("wheat_crop_pot", properties -> new WheatCropPotBlock(ofFullCopy(properties, CROP_POT.get()).randomTicks()));
    public static final DeferredBlock<Block> CARROT_CROP_POT = BLOCKS.registerBlock("carrot_crop_pot", properties -> new CarrotCropPotBlock(ofFullCopy(properties, CROP_POT.get()).randomTicks()));
    public static final DeferredBlock<Block> POTATO_CROP_POT = BLOCKS.registerBlock("potato_crop_pot", properties -> new PotatoCropPotBlock(ofFullCopy(properties, CROP_POT.get()).randomTicks()));
    public static final DeferredBlock<Block> BEETROOT_CROP_POT = BLOCKS.registerBlock("beetroot_crop_pot", properties -> new BeetrootCropPotBlock(ofFullCopy(properties, CROP_POT.get()).randomTicks()));
    public static final DeferredBlock<Block> NETHER_WART_CROP_POT = BLOCKS.registerBlock("nether_wart_crop_pot", properties -> new NetherWartCropPotBlock(ofFullCopy(properties, CROP_POT.get()).randomTicks()));

    // Rails
    public static final DeferredBlock<Block> GILDED_RAIL = BLOCKS.registerBlock("gilded_rail", properties -> new GildedRailBlock(ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> WHITE_TAGGING_RAIL = BLOCKS.registerBlock("white_tagging_rail", properties -> new TaggingRailBlock(DyeColor.WHITE, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> LIGHT_GRAY_TAGGING_RAIL = BLOCKS.registerBlock("light_gray_tagging_rail", properties -> new TaggingRailBlock(DyeColor.LIGHT_GRAY, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> GRAY_TAGGING_RAIL = BLOCKS.registerBlock("gray_tagging_rail", properties -> new TaggingRailBlock(DyeColor.GRAY, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> BLACK_TAGGING_RAIL = BLOCKS.registerBlock("black_tagging_rail", properties -> new TaggingRailBlock(DyeColor.BLACK, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> BROWN_TAGGING_RAIL = BLOCKS.registerBlock("brown_tagging_rail", properties -> new TaggingRailBlock(DyeColor.BROWN, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> RED_TAGGING_RAIL = BLOCKS.registerBlock("red_tagging_rail", properties -> new TaggingRailBlock(DyeColor.RED, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> ORANGE_TAGGING_RAIL = BLOCKS.registerBlock("orange_tagging_rail", properties -> new TaggingRailBlock(DyeColor.ORANGE, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> YELLOW_TAGGING_RAIL = BLOCKS.registerBlock("yellow_tagging_rail", properties -> new TaggingRailBlock(DyeColor.YELLOW, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> LIME_TAGGING_RAIL = BLOCKS.registerBlock("lime_tagging_rail", properties -> new TaggingRailBlock(DyeColor.LIME, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> GREEN_TAGGING_RAIL = BLOCKS.registerBlock("green_tagging_rail", properties -> new TaggingRailBlock(DyeColor.GREEN, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> CYAN_TAGGING_RAIL = BLOCKS.registerBlock("cyan_tagging_rail", properties -> new TaggingRailBlock(DyeColor.CYAN, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> LIGHT_BLUE_TAGGING_RAIL = BLOCKS.registerBlock("light_blue_tagging_rail", properties -> new TaggingRailBlock(DyeColor.LIGHT_BLUE, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> BLUE_TAGGING_RAIL = BLOCKS.registerBlock("blue_tagging_rail", properties -> new TaggingRailBlock(DyeColor.BLUE, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> PURPLE_TAGGING_RAIL = BLOCKS.registerBlock("purple_tagging_rail", properties -> new TaggingRailBlock(DyeColor.PURPLE, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> MAGENTA_TAGGING_RAIL = BLOCKS.registerBlock("magenta_tagging_rail", properties -> new TaggingRailBlock(DyeColor.MAGENTA, ofFullCopy(properties, Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> PINK_TAGGING_RAIL = BLOCKS.registerBlock("pink_tagging_rail", properties -> new TaggingRailBlock(DyeColor.PINK, ofFullCopy(properties, Blocks.POWERED_RAIL)));

    public static BlockBehaviour.Properties ofFullCopy(BlockBehaviour.Properties source, BlockBehaviour block) {
        ResourceKey<Block> id = ((BlockBehaviourIDAccessor) source).stancements$id();
        BlockBehaviour.Properties properties = block.properties();
        if (id != null) properties.setId(id);
        return properties;
    }
}
