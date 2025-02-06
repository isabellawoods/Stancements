package melonystudios.stancements.block;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.custom.ShelfBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class STBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Stancements.MOD_ID);

    public static final RegistryObject<Block> OAK_SHELF = BLOCKS.register("oak_shelf", () -> new ShelfBlock(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> SPRUCE_SHELF = BLOCKS.register("spruce_shelf", () -> new ShelfBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_PLANKS)));
    public static final RegistryObject<Block> BIRCH_SHELF = BLOCKS.register("birch_shelf", () -> new ShelfBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_PLANKS)));
    public static final RegistryObject<Block> JUNGLE_SHELF = BLOCKS.register("jungle_shelf", () -> new ShelfBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS)));
    public static final RegistryObject<Block> ACACIA_SHELF = BLOCKS.register("acacia_shelf", () -> new ShelfBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistryObject<Block> DARK_OAK_SHELF = BLOCKS.register("dark_oak_shelf", () -> new ShelfBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_PLANKS)));
    public static final RegistryObject<Block> CRIMSON_SHELF = BLOCKS.register("crimson_shelf", () -> new ShelfBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_PLANKS)));
    public static final RegistryObject<Block> WARPED_SHELF = BLOCKS.register("warped_shelf", () -> new ShelfBlock(AbstractBlock.Properties.copy(Blocks.WARPED_PLANKS)));
}
