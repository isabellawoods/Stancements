package melonystudios.stancements.data.model;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class STBlockStateProvider extends BlockStateProvider {
    public STBlockStateProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, Stancements.MOD_ID, fileHelper);
    }

    @Nonnull
    public String getName() {
        return "Stancements - Block State Definitions & Models";
    }

    @Override
    protected void registerStatesAndModels() {
        // Shelves
        shelf(STBlocks.OAK_SHELF.get(), "oak");
        shelf(STBlocks.SPRUCE_SHELF.get(), "spruce");
        shelf(STBlocks.BIRCH_SHELF.get(), "birch");
        shelf(STBlocks.JUNGLE_SHELF.get(), "jungle");
        shelf(STBlocks.ACACIA_SHELF.get(), "acacia");
        shelf(STBlocks.DARK_OAK_SHELF.get(), "dark_oak");
        shelf(STBlocks.CRIMSON_SHELF.get(), "crimson");
        shelf(STBlocks.WARPED_SHELF.get(), "warped");
    }

    public void shelf(Block shelf, String woodType) {
        getVariantBuilder(shelf).forAllStatesExcept(state -> {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            ResourceLocation registry = shelf.getRegistryName();

            return ConfiguredModel.builder().modelFile(models().getBuilder(shelf.getRegistryName().getPath())
                            .parent(models().getExistingFile(modLoc("block/template_shelf")))
                            .texture("shelf", registry.getNamespace() + ":block/" + woodType + "_shelf")
                            .texture("support", registry.getNamespace() + ":block/" + woodType + "_shelf_support"))
                    .rotationY((int) facing.toYRot()).build();
        }, BlockStateProperties.WATERLOGGED);
    }
}
