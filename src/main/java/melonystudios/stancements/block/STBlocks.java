package melonystudios.stancements.block;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.custom.MusicRecorderBlock;
import melonystudios.stancements.block.custom.ShelfBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Stancements.MOD_ID);

    // Decorative
    public static final DeferredBlock<Block> OAK_SHELF = BLOCKS.register("oak_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> SPRUCE_SHELF = BLOCKS.register("spruce_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final DeferredBlock<Block> BIRCH_SHELF = BLOCKS.register("birch_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_PLANKS)));
    public static final DeferredBlock<Block> JUNGLE_SHELF = BLOCKS.register("jungle_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS)));
    public static final DeferredBlock<Block> ACACIA_SHELF = BLOCKS.register("acacia_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final DeferredBlock<Block> DARK_OAK_SHELF = BLOCKS.register("dark_oak_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS)));
    public static final DeferredBlock<Block> CRIMSON_SHELF = BLOCKS.register("crimson_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS)));
    public static final DeferredBlock<Block> WARPED_SHELF = BLOCKS.register("warped_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_PLANKS)));

    // Functional
    public static final DeferredBlock<Block> MUSIC_RECORDER = BLOCKS.register("music_recorder", () -> new MusicRecorderBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUKEBOX)));
}
