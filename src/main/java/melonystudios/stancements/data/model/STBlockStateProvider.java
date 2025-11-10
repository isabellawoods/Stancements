package melonystudios.stancements.data.model;

import melonystudios.reutilities.data.model.ReBlockStateProvider;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.block.custom.croppot.WheatCropPotBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static melonystudios.stancements.block.STBlocks.*;

public class STBlockStateProvider extends ReBlockStateProvider {
    public STBlockStateProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, Stancements.MOD_ID, fileHelper);
    }

    @Override
    @NotNull
    public String getName() {
        return Stancements.generatorName("Block States and Models");
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
        layeredCauldron(DYED_WATER_CAULDRON.get(), this.mcLoc("block/water_still"));
        layeredCauldron(MILK_CAULDRON.get(), ResourceLocation.fromNamespaceAndPath("neoforge", "block/milk_still"));
        emptyCropPot(CROP_POT.get());
        fullCropPot((WheatCropPotBlock) WHEAT_CROP_POT.get(), age -> this.mcLoc("block/wheat_stage" + wheatAgeIndex(age)));
        fullCropPot((WheatCropPotBlock) CARROT_CROP_POT.get(), age -> this.mcLoc("block/carrots_stage" + potatoAgeIndex(age)));
        fullCropPot((WheatCropPotBlock) POTATO_CROP_POT.get(), age -> this.mcLoc("block/potatoes_stage" + potatoAgeIndex(age)));
        fullCropPot((WheatCropPotBlock) BEETROOT_CROP_POT.get(), age -> this.mcLoc("block/beetroots_stage" + wheatAgeIndex(age)));
        fullCropPot((WheatCropPotBlock) NETHER_WART_CROP_POT.get(), age -> this.modLoc("block/nether_wart_stage" + netherWartAgeIndex(age)));
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

    /// Makes the block states and models for an {@linkplain melonystudios.stancements.block.custom.croppot.CropPotBlock empty crop pot block}.
    /// @param cropPot The crop pot block.
    public void emptyCropPot(Block cropPot) {
        this.getVariantBuilder(cropPot).forAllStates(state -> {
            ResourceLocation registry = BuiltInRegistries.BLOCK.getKey(cropPot);
            boolean hopping = state.getValue(STBlockStateProperties.HOPPING);
            String name = hopping ? "hopping_" : "";

            return ConfiguredModel.builder().modelFile(this.models().getBuilder(name + registry.getPath())
                    .parent(this.models().getExistingFile(this.modLoc("block/template_crop_pot")))
                    .texture("pot", this.modLoc("block/" + name + "crop_pot"))
            ).build();
        });
    }

    /// Makes the block states and models for a {@linkplain WheatCropPotBlock full crop pot block}.
    /// @param cropPot The crop pot block.
    /// @param crop A function for getting the correct crop texture.
    public void fullCropPot(WheatCropPotBlock cropPot, Function<Integer, ResourceLocation> crop) {
        this.getVariantBuilder(cropPot).forAllStates(state -> {
            ResourceLocation registry = BuiltInRegistries.BLOCK.getKey(cropPot);
            boolean hopping = state.getValue(STBlockStateProperties.HOPPING);
            int age = state.getValue(cropPot.getAgeProperty());
            String name = hopping ? "hopping_" : "";

            return ConfiguredModel.builder().modelFile(this.models().getBuilder(name + registry.getPath() + "_stage" + age)
                    .parent(this.models().getExistingFile(this.modLoc("block/template_full_crop_pot")))
                    .texture("pot", this.modLoc("block/" + name + "crop_pot"))
                    .texture("crop", crop.apply(age))
            ).build();
        });
    }
}
