package melonystudios.stancements.data.model;

import melonystudios.reutilities.data.model.ReBlockStateProvider;
import melonystudios.stancements.Stancements;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import static melonystudios.stancements.block.STBlocks.*;

public class STBlockStateProvider extends ReBlockStateProvider {
    public STBlockStateProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, Stancements.MOD_ID, fileHelper);
    }

    @Override
    @NotNull
    public String getName() {
        return "Stancements - Block States and Models";
    }

    @Override
    protected void registerStatesAndModels() {
        // Decorative
        shelf(OAK_SHELF.get(), "oak");
        shelf(SPRUCE_SHELF.get(), "spruce");
        shelf(BIRCH_SHELF.get(), "birch");
        shelf(JUNGLE_SHELF.get(), "jungle");
        shelf(ACACIA_SHELF.get(), "acacia");
        shelf(DARK_OAK_SHELF.get(), "dark_oak");
        shelf(CRIMSON_SHELF.get(), "crimson");
        shelf(WARPED_SHELF.get(), "warped");

        // Functional
        simpleBlock(MUSIC_RECORDER.get(), models().cubeBottomTop("music_recorder",
                modLoc("block/music_recorder_side"),
                modLoc("block/music_recorder_bottom"),
                modLoc("block/music_recorder_top")));
    }

    /// Makes the block states and models for a {@linkplain melonystudios.stancements.block.custom.ShelfBlock shelf block}.
    /// @param shelf The shelf block.
    /// @param woodType A string representing this shelf's wood type, used to get the textures.
    public void shelf(Block shelf, String woodType) {
        this.getVariantBuilder(shelf).forAllStatesExcept(state -> {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            ResourceLocation registry = BuiltInRegistries.BLOCK.getKey(shelf);

            return ConfiguredModel.builder().modelFile(this.models().getBuilder(registry.getPath())
                            .parent(this.models().getExistingFile(Stancements.stancements("block/template_shelf")))
                            .texture("shelf", registry.getNamespace() + ":block/" + woodType + "_shelf")
                            .texture("support", registry.getNamespace() + ":block/" + woodType + "_shelf_support"))
                    .rotationY((int) facing.toYRot()).build();
        }, BlockStateProperties.WATERLOGGED);
    }
}
