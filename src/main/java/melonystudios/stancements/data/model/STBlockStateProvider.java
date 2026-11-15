package melonystudios.stancements.data.model;

import melonystudios.reutilities.data.model.ReBlockStateProvider;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.block.custom.CasingBlock;
import melonystudios.stancements.block.custom.croppot.FilledCropPotBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
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
        shelf(PALE_OAK_SHELF.get());
        shelf(POPLAR_SHELF.get());
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
        simpleBlock(MUSIC_RECORDER.get(), this.models().cubeBottomTop("music_recorder",
                this.modLoc("block/music_recorder_side"),
                this.modLoc("block/music_recorder_bottom"),
                this.modLoc("block/music_recorder_top")));
        musicRemixer(MUSIC_REMIXER.get(), "cutout", this.modLoc("block/glass_casing_side"), this.modLoc("block/glass_casing_end"));
        musicRemixer(TINTED_MUSIC_REMIXER.get(), "translucent", this.modLoc("block/tinted_glass_casing_side"), this.modLoc("block/tinted_glass_casing_end"));
        layeredCauldron(DYED_WATER_CAULDRON.get(), this.mcLoc("block/water_still"));
        layeredCauldron(MILK_CAULDRON.get(), ResourceLocation.fromNamespaceAndPath("neoforge", "block/milk_still"));
        emptyCropPot(CROP_POT.get());
        fullCropPot((FilledCropPotBlock) WHEAT_CROP_POT.get(), age -> this.mcLoc("block/wheat_stage" + wheatAgeIndex(age)), ReBlockStateProvider::wheatAgeIndex);
        fullCropPot((FilledCropPotBlock) CARROT_CROP_POT.get(), age -> this.mcLoc("block/carrots_stage" + potatoAgeIndex(age)), ReBlockStateProvider::potatoAgeIndex);
        fullCropPot((FilledCropPotBlock) POTATO_CROP_POT.get(), age -> this.mcLoc("block/potatoes_stage" + potatoAgeIndex(age)), ReBlockStateProvider::potatoAgeIndex);
        fullCropPot((FilledCropPotBlock) BEETROOT_CROP_POT.get(), age -> this.mcLoc("block/beetroots_stage" + wheatAgeIndex(age)), ReBlockStateProvider::wheatAgeIndex);
        fullCropPot((FilledCropPotBlock) NETHER_WART_CROP_POT.get(), age -> this.modLoc("block/nether_wart_stage" + netherWartAgeIndex(age)), ReBlockStateProvider::netherWartAgeIndex);

        // Rails
        straightRail((BaseRailBlock) GILDED_RAIL.get());
        straightRail((BaseRailBlock) WHITE_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) LIGHT_GRAY_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) GRAY_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) BLACK_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) BROWN_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) RED_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) ORANGE_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) YELLOW_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) LIME_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) GREEN_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) CYAN_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) LIGHT_BLUE_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) BLUE_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) PURPLE_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) MAGENTA_TAGGING_RAIL.get());
        straightRail((BaseRailBlock) PINK_TAGGING_RAIL.get());

        // Glass casings
        casing((CasingBlock) GLASS_CASING.get(), "cutout");
        casing((CasingBlock) TINTED_GLASS_CASING.get(), "translucent");
    }

    /// Makes the block states and models for a [*Stancements* shelf block][melonystudios.stancements.block.custom.ShelfBlock].
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

    /// Makes the block states and models for a [crafting table cloth block][melonystudios.stancements.block.custom.CraftingTableClothBlock].
    /// @param tableCloth The table cloth block.
    public void craftingTableCloth(Block tableCloth) {
        ResourceLocation registry = BuiltInRegistries.BLOCK.getKey(tableCloth);
        this.simpleBlock(tableCloth, this.models().withExistingParent(registry.getPath(), Stancements.stancements("block/template_crafting_table_cloth"))
                .texture("top", registry.getNamespace() + ":block/" + registry.getPath() + "_top")
                .texture("side", registry.getNamespace() + ":block/" + registry.getPath() + "_side"));
    }

    /// Makes the block states and models for an [empty crop pot block][melonystudios.stancements.block.custom.croppot.EmptyCropPotBlock].
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

    /// Makes the block states and models for a [filled crop pot block][FilledCropPotBlock].
    /// @param cropPot The crop pot block.
    /// @param crop A [Function] for getting the correct crop texture.
    /// @param modelAge A `Function` for getting the age of the crop pot model, as it may not necessarily match the block's `age` property.
    public void fullCropPot(FilledCropPotBlock cropPot, Function<Integer, ResourceLocation> crop, Function<Integer, Integer> modelAge) {
        this.getVariantBuilder(cropPot).forAllStates(state -> {
            ResourceLocation registry = BuiltInRegistries.BLOCK.getKey(cropPot);
            boolean hopping = state.getValue(STBlockStateProperties.HOPPING);
            int age = state.getValue(cropPot.getAgeProperty());
            String name = hopping ? "hopping_" : "";

            return ConfiguredModel.builder().modelFile(this.models().getBuilder(name + registry.getPath() + "_stage" + modelAge.apply(age))
                    .parent(this.models().getExistingFile(this.modLoc("block/template_full_crop_pot")))
                    .texture("pot", this.modLoc("block/" + name + "crop_pot"))
                    .texture("crop", crop.apply(age))
            ).build();
        });
    }

    /// Makes the block states and models for an [axis-aligned rail][net.minecraft.world.level.block.PoweredRailBlock],
    /// like powered or detector rails.
    /// @param rail The rail block.
    // if I ever make a method for making curved rails, I'll call it "gayRail" ~isa 19-02-26
    public void straightRail(BaseRailBlock rail) {
        this.getVariantBuilder(rail).forAllStatesExcept(state -> {
            ResourceLocation registry = BuiltInRegistries.BLOCK.getKey(rail);
            boolean powered = state.getValue(BlockStateProperties.POWERED);
            RailShape shape = state.getValue(BlockStateProperties.RAIL_SHAPE_STRAIGHT);
            String name = String.format("%s%s%s", registry.getPath(), powered ? "_on" : "", shape.isAscending() ? this.shortenName(shape) : "");

            int railRotation = switch (shape) {
                case EAST_WEST, ASCENDING_WEST, ASCENDING_EAST -> 90;
                default -> 0;
            };
            String parent = !shape.isAscending() ? "rail_flat" : "template_rail" + this.shortenName(shape);

            return ConfiguredModel.builder().modelFile(this.models().getBuilder(name)
                    .parent(this.models().getExistingFile(this.mcLoc("block/" + parent)))
                    .texture("rail", registry.getNamespace() + ":block/" + registry.getPath() + (powered ? "_on" : ""))
                    .renderType("cutout")
            ).rotationY(railRotation).build();
        }, BlockStateProperties.WATERLOGGED);
    }

    private String shortenName(RailShape name) {
        return switch (name) {
            case ASCENDING_NORTH, ASCENDING_EAST -> "_raised_ne";
            case ASCENDING_SOUTH, ASCENDING_WEST -> "_raised_sw";
            default -> "";
        };
    }

    /// Makes the block states and models for a [casing block][CasingBlock].
    /// @param casing The casing block.
    /// @param renderType Which render type to use when rendering. Uses `cutout` for regular glass casings and `translucent` for tinted and stained glass casings.
    public void casing(CasingBlock casing, String renderType) {
        this.getVariantBuilder(casing).forAllStatesExcept(state -> {
            ResourceLocation registry = BuiltInRegistries.BLOCK.getKey(casing);
            boolean supported = state.getValue(STBlockStateProperties.SUPPORTED);
            String supportedName = (supported ? "supported_" : "");

            return ConfiguredModel.builder().modelFile(this.models().getBuilder(supportedName + registry.getPath())
                    .parent(this.models().getExistingFile(Stancements.stancements("block/template_" + supportedName + "casing")))
                    .texture("side", registry.getNamespace() + ":block/" + registry.getPath() + "_side")
                    .texture("end", registry.getNamespace() + ":block/" + registry.getPath() + "_end")
                    .renderType(renderType)
            ).build();
        }, BlockStateProperties.WATERLOGGED);
    }

    /// Makes the block states and models for a [music remixer][melonystudios.stancements.block.custom.MusicRemixerBlock].
    /// @param remixer The music remixer block.
    /// @param renderType Which render type to use when rendering. Uses `cutout` for regular glass casings and `translucent` for tinted and stained glass casings.
    /// @param casingSide An [identifier][ResourceLocation] of the glass casing side texture.
    /// @param casingEnd An identifier of the glass casing top and bottom textures.
    public void musicRemixer(Block remixer, String renderType, ResourceLocation casingSide, ResourceLocation casingEnd) {
        this.getVariantBuilder(remixer).forAllStatesExcept(state -> {
            ResourceLocation registry = BuiltInRegistries.BLOCK.getKey(remixer);
            boolean open = state.getValue(BlockStateProperties.OPEN);
            String suffix = open ? "_open" : "_closed";

            BlockModelBuilder modelBuilder = this.models().getBuilder(registry.getPath() + suffix);
            modelBuilder.parent(this.models().getExistingFile(Stancements.stancements("block/template_music_remixer" + suffix)));
            if (!open) modelBuilder.renderType(renderType).texture("casing_side", casingSide).texture("casing_end", casingEnd);

            return ConfiguredModel.builder().modelFile(modelBuilder).build();
        }, STBlockStateProperties.RECORDING, BlockStateProperties.POWERED);
    }
}
