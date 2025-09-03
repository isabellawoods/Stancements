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
        // Shelves
        shelf(OAK_SHELF.get());
        shelf(SPRUCE_SHELF.get());
        shelf(BIRCH_SHELF.get());
        shelf(JUNGLE_SHELF.get());
        shelf(ACACIA_SHELF.get());
        shelf(DARK_OAK_SHELF.get());
        shelf(MANGROVE_SHELF.get());
        shelf(CHERRY_SHELF.get());
        shelf(BAMBOO_SHELF.get());
        shelf(CRIMSON_SHELF.get());
        shelf(WARPED_SHELF.get());

        // Crafting Table Cloths
        craftingTableCloth(WHITE_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(GRAY_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(BLACK_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(BROWN_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(RED_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(ORANGE_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(YELLOW_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(LIME_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(GREEN_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(CYAN_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(BLUE_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(PURPLE_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(MAGENTA_CRAFTING_TABLE_CLOTH.get());
        craftingTableCloth(PINK_CRAFTING_TABLE_CLOTH.get());

        // Functional
        simpleBlock(MUSIC_RECORDER.get(), models().cubeBottomTop("music_recorder",
                modLoc("block/music_recorder_side"),
                modLoc("block/music_recorder_bottom"),
                modLoc("block/music_recorder_top")));
    }

    /// Makes the block states and models for a {@linkplain melonystudios.stancements.block.custom.ShelfBlock shelf block}.
    /// @param shelf The shelf block.
    public void shelf(Block shelf) {
        this.getVariantBuilder(shelf).forAllStatesExcept(state -> {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            ResourceLocation registry = BuiltInRegistries.BLOCK.getKey(shelf);

            return ConfiguredModel.builder().modelFile(this.models().getBuilder(registry.getPath())
                            .parent(this.models().getExistingFile(Stancements.stancements("block/template_shelf")))
                            .texture("shelf", registry.getNamespace() + ":block/" + registry.getPath())
                            .texture("support", registry.getNamespace() + ":block/" + registry.getPath() + "_support"))
                    .rotationY((int) facing.toYRot()).build();
        }, BlockStateProperties.WATERLOGGED);
    }

    /// Makes the block states and models for a {@linkplain melonystudios.stancements.block.custom.CraftingTableClothBlock crafting table cloth block}.
    /// @param tableCloth The table cloth block.
    public void craftingTableCloth(Block tableCloth) {
        ResourceLocation registry = BuiltInRegistries.BLOCK.getKey(tableCloth);
        this.simpleBlock(tableCloth, this.models().withExistingParent(registry.getPath(), Stancements.stancements("block/template_crafting_table_cloth"))
                .texture("top", registry.getNamespace() + ":block/" + registry.getPath() + "_top")
                .texture("side", registry.getNamespace() + ":block/" + registry.getPath() + "_side"));
    }
}
