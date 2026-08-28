package melonystudios.stancements.block;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.custom.*;
import melonystudios.stancements.block.custom.croppot.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Stancements.MOD_ID);

    // Decorative
    // Shelves
    public static final DeferredBlock<Block> OAK_SHELF = BLOCKS.register("oak_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> SPRUCE_SHELF = BLOCKS.register("spruce_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final DeferredBlock<Block> BIRCH_SHELF = BLOCKS.register("birch_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_PLANKS)));
    public static final DeferredBlock<Block> JUNGLE_SHELF = BLOCKS.register("jungle_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS)));
    public static final DeferredBlock<Block> ACACIA_SHELF = BLOCKS.register("acacia_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final DeferredBlock<Block> DARK_OAK_SHELF = BLOCKS.register("dark_oak_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS)));
    public static final DeferredBlock<Block> MANGROVE_SHELF = BLOCKS.register("mangrove_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_PLANKS)));
    public static final DeferredBlock<Block> CHERRY_SHELF = BLOCKS.register("cherry_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS)));
    public static final DeferredBlock<Block> PALE_OAK_SHELF = BLOCKS.register("pale_oak_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.QUARTZ)));
    public static final DeferredBlock<Block> BAMBOO_SHELF = BLOCKS.register("bamboo_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredBlock<Block> CRIMSON_SHELF = BLOCKS.register("crimson_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS)));
    public static final DeferredBlock<Block> WARPED_SHELF = BLOCKS.register("warped_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS)));

    // Crafting Table Cloths
    public static final DeferredBlock<Block> WHITE_CRAFTING_TABLE_CLOTH = BLOCKS.register("white_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CARPET)));
    public static final DeferredBlock<Block> LIGHT_GRAY_CRAFTING_TABLE_CLOTH = BLOCKS.register("light_gray_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_GRAY_CARPET)));
    public static final DeferredBlock<Block> GRAY_CRAFTING_TABLE_CLOTH = BLOCKS.register("gray_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAY_CARPET)));
    public static final DeferredBlock<Block> BLACK_CRAFTING_TABLE_CLOTH = BLOCKS.register("black_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_CARPET)));
    public static final DeferredBlock<Block> BROWN_CRAFTING_TABLE_CLOTH = BLOCKS.register("brown_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_CARPET)));
    public static final DeferredBlock<Block> RED_CRAFTING_TABLE_CLOTH = BLOCKS.register("red_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET)));
    public static final DeferredBlock<Block> ORANGE_CRAFTING_TABLE_CLOTH = BLOCKS.register("orange_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ORANGE_CARPET)));
    public static final DeferredBlock<Block> YELLOW_CRAFTING_TABLE_CLOTH = BLOCKS.register("yellow_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_CARPET)));
    public static final DeferredBlock<Block> LIME_CRAFTING_TABLE_CLOTH = BLOCKS.register("lime_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIME_CARPET)));
    public static final DeferredBlock<Block> GREEN_CRAFTING_TABLE_CLOTH = BLOCKS.register("green_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GREEN_CARPET)));
    public static final DeferredBlock<Block> CYAN_CRAFTING_TABLE_CLOTH = BLOCKS.register("cyan_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CYAN_CARPET)));
    public static final DeferredBlock<Block> LIGHT_BLUE_CRAFTING_TABLE_CLOTH = BLOCKS.register("light_blue_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_BLUE_CARPET)));
    public static final DeferredBlock<Block> BLUE_CRAFTING_TABLE_CLOTH = BLOCKS.register("blue_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_CARPET)));
    public static final DeferredBlock<Block> PURPLE_CRAFTING_TABLE_CLOTH = BLOCKS.register("purple_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_CARPET)));
    public static final DeferredBlock<Block> MAGENTA_CRAFTING_TABLE_CLOTH = BLOCKS.register("magenta_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MAGENTA_CARPET)));
    public static final DeferredBlock<Block> PINK_CRAFTING_TABLE_CLOTH = BLOCKS.register("pink_crafting_table_cloth", () -> new CraftingTableClothBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_CARPET)));

    // Functional
    public static final DeferredBlock<Block> MUSIC_RECORDER = BLOCKS.register("music_recorder", () -> new MusicRecorderBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUKEBOX)));
    public static final DeferredBlock<Block> ALBUM = BLOCKS.register("album", () -> new AlbumBlock(BlockBehaviour.Properties.of().strength(1).noOcclusion().sound(SoundType.BAMBOO_WOOD)));
    public static final DeferredBlock<Block> DYED_WATER_CAULDRON = BLOCKS.register("dyed_water_cauldron", () -> new DyedWaterCauldronBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)));
    public static final DeferredBlock<Block> MILK_CAULDRON = BLOCKS.register("milk_cauldron", () -> new MilkCauldronBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)));
    public static final DeferredBlock<Block> CROP_POT = BLOCKS.register("crop_pot", () -> new CropPotBlock(BlockBehaviour.Properties.of().noOcclusion().pushReaction(PushReaction.DESTROY).strength(1, 4.2F)));
    public static final DeferredBlock<Block> WHEAT_CROP_POT = BLOCKS.register("wheat_crop_pot", () -> new WheatCropPotBlock(Items.WHEAT_SEEDS, PotPlantable::defaultPlantingSound, BlockBehaviour.Properties.ofFullCopy(CROP_POT.get()).randomTicks()));
    public static final DeferredBlock<Block> CARROT_CROP_POT = BLOCKS.register("carrot_crop_pot", () -> new CarrotCropPotBlock(Items.CARROT, PotPlantable::defaultPlantingSound, BlockBehaviour.Properties.ofFullCopy(CROP_POT.get()).randomTicks()));
    public static final DeferredBlock<Block> POTATO_CROP_POT = BLOCKS.register("potato_crop_pot", () -> new PotatoCropPotBlock(Items.POTATO, PotPlantable::defaultPlantingSound, BlockBehaviour.Properties.ofFullCopy(CROP_POT.get()).randomTicks()));
    public static final DeferredBlock<Block> BEETROOT_CROP_POT = BLOCKS.register("beetroot_crop_pot", () -> new BeetrootCropPotBlock(Items.BEETROOT_SEEDS, PotPlantable::defaultPlantingSound, BlockBehaviour.Properties.ofFullCopy(CROP_POT.get()).randomTicks()));
    public static final DeferredBlock<Block> NETHER_WART_CROP_POT = BLOCKS.register("nether_wart_crop_pot", () -> new NetherWartCropPotBlock(Items.NETHER_WART, block -> new PotPlantable(block, SoundEvents.NETHER_WART_PLANTED), BlockBehaviour.Properties.ofFullCopy(CROP_POT.get()).randomTicks()));

    // Rails
    public static final DeferredBlock<Block> GILDED_RAIL = BLOCKS.register("gilded_rail", () -> new GildedRailBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> WHITE_TAGGING_RAIL = BLOCKS.register("white_tagging_rail", () -> new TaggingRailBlock(DyeColor.WHITE, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> LIGHT_GRAY_TAGGING_RAIL = BLOCKS.register("light_gray_tagging_rail", () -> new TaggingRailBlock(DyeColor.LIGHT_GRAY, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> GRAY_TAGGING_RAIL = BLOCKS.register("gray_tagging_rail", () -> new TaggingRailBlock(DyeColor.GRAY, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> BLACK_TAGGING_RAIL = BLOCKS.register("black_tagging_rail", () -> new TaggingRailBlock(DyeColor.BLACK, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> BROWN_TAGGING_RAIL = BLOCKS.register("brown_tagging_rail", () -> new TaggingRailBlock(DyeColor.BROWN, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> RED_TAGGING_RAIL = BLOCKS.register("red_tagging_rail", () -> new TaggingRailBlock(DyeColor.RED, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> ORANGE_TAGGING_RAIL = BLOCKS.register("orange_tagging_rail", () -> new TaggingRailBlock(DyeColor.ORANGE, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> YELLOW_TAGGING_RAIL = BLOCKS.register("yellow_tagging_rail", () -> new TaggingRailBlock(DyeColor.YELLOW, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> LIME_TAGGING_RAIL = BLOCKS.register("lime_tagging_rail", () -> new TaggingRailBlock(DyeColor.LIME, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> GREEN_TAGGING_RAIL = BLOCKS.register("green_tagging_rail", () -> new TaggingRailBlock(DyeColor.GREEN, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> CYAN_TAGGING_RAIL = BLOCKS.register("cyan_tagging_rail", () -> new TaggingRailBlock(DyeColor.CYAN, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> LIGHT_BLUE_TAGGING_RAIL = BLOCKS.register("light_blue_tagging_rail", () -> new TaggingRailBlock(DyeColor.LIGHT_BLUE, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> BLUE_TAGGING_RAIL = BLOCKS.register("blue_tagging_rail", () -> new TaggingRailBlock(DyeColor.BLUE, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> PURPLE_TAGGING_RAIL = BLOCKS.register("purple_tagging_rail", () -> new TaggingRailBlock(DyeColor.PURPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> MAGENTA_TAGGING_RAIL = BLOCKS.register("magenta_tagging_rail", () -> new TaggingRailBlock(DyeColor.MAGENTA, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
    public static final DeferredBlock<Block> PINK_TAGGING_RAIL = BLOCKS.register("pink_tagging_rail", () -> new TaggingRailBlock(DyeColor.PINK, BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
}
