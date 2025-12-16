package melonystudios.stancements.data.model;

import melonystudios.reutilities.data.model.ReItemModelProvider;
import melonystudios.stancements.Stancements;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class STItemModelProvider extends ReItemModelProvider {
    public STItemModelProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, Stancements.MOD_ID, fileHelper);
    }

    @Override
    @NotNull
    public String getName() {
        return Stancements.generatorName("Item Models");
    }

    @Override
    protected void registerModels() {
        // Decorative blocks
        // Shelves
        block("oak_shelf");
        block("spruce_shelf");
        block("birch_shelf");
        block("jungle_shelf");
        block("acacia_shelf");
        block("dark_oak_shelf");
        block("mangrove_shelf");
        block("cherry_shelf");
        block("bamboo_shelf");
        block("crimson_shelf");
        block("warped_shelf");

        // Crafting Table Cloths
        block("white_crafting_table_cloth");
        block("light_gray_crafting_table_cloth");
        block("gray_crafting_table_cloth");
        block("black_crafting_table_cloth");
        block("brown_crafting_table_cloth");
        block("red_crafting_table_cloth");
        block("orange_crafting_table_cloth");
        block("yellow_crafting_table_cloth");
        block("lime_crafting_table_cloth");
        block("green_crafting_table_cloth");
        block("cyan_crafting_table_cloth");
        block("light_blue_crafting_table_cloth");
        block("blue_crafting_table_cloth");
        block("purple_crafting_table_cloth");
        block("magenta_crafting_table_cloth");
        block("pink_crafting_table_cloth");

        // Functional blocks
        blockItem("gilded_rail");
        block("music_recorder");
        cropPot(this.generated, "crop_pot");

        // Items
        standard("stancements_logo");
        standard("vinyl_disc");
        recordedDisc(this.generated, "recorded_disc");
        standard("white_tag");
        standard("light_gray_tag");
        standard("gray_tag");
        standard("black_tag");
        standard("brown_tag");
        standard("red_tag");
        standard("orange_tag");
        standard("yellow_tag");
        standard("lime_tag");
        standard("green_tag");
        standard("cyan_tag");
        standard("light_blue_tag");
        standard("blue_tag");
        standard("purple_tag");
        standard("magenta_tag");
        standard("pink_tag");
        this.getBuilder("dyed_water_bucket").parent(this.generated).texture("layer0", this.modLoc("item/dyed_water_bucket_overlay")).texture("layer1", this.modLoc("item/dyed_water_bucket"));
    }

    /// Makes a {@linkplain melonystudios.stancements.item.custom.CropPotBlockItem crop pot} model, with a **hopping** variation using the `stancements:hopping` override.
    /// @param parent The location of the parent model, usually `item/generated`.
    /// @param name The item's registry id, used for the model name and texture locations.
    private void cropPot(ModelFile parent, String name) {
        ResourceLocation hopping = Stancements.stancements("hopping");
        this.standard(parent, "hopping_" + name);

        this.getBuilder(name).parent(parent).texture("layer0", this.modLoc("item/" + name))
                .override().predicate(hopping, 1).model(this.getExistingFile(this.modLoc("item/hopping_" + name))).end();
    }

    /// Makes a {@linkplain melonystudios.stancements.item.custom.RecordedDiscItem recorded disc} model, with **13** different
    /// override models for each music disc {@linkplain melonystudios.stancements.component.STDataComponents#LABEL label}.
    /// @param parent The location of the parent model, usually `item/generated`.
    /// @param name The item's registry id, used for the model name and texture locations.
    public void recordedDisc(ModelFile parent, String name) {
        ResourceLocation label = Stancements.stancements("label");

        for (int i = 1; i <= 13; ++i) {
            getBuilder(name + "_label_" + i).parent(parent).texture("layer0", modLoc("item/" + name)).texture("layer1", modLoc("item/" + name + "_label_" + i));
        }

        getBuilder(name).parent(parent).texture("layer0", modLoc("item/" + name)).texture("layer1", modLoc("item/" + name + "_label_1"))
                .override().predicate(label, 1).model(getExistingFile(modLoc("item/" + name + "_label_1"))).end()
                .override().predicate(label, 2).model(getExistingFile(modLoc("item/" + name + "_label_2"))).end()
                .override().predicate(label, 3).model(getExistingFile(modLoc("item/" + name + "_label_3"))).end()
                .override().predicate(label, 4).model(getExistingFile(modLoc("item/" + name + "_label_4"))).end()
                .override().predicate(label, 5).model(getExistingFile(modLoc("item/" + name + "_label_5"))).end()
                .override().predicate(label, 6).model(getExistingFile(modLoc("item/" + name + "_label_6"))).end()
                .override().predicate(label, 7).model(getExistingFile(modLoc("item/" + name + "_label_7"))).end()
                .override().predicate(label, 8).model(getExistingFile(modLoc("item/" + name + "_label_8"))).end()
                .override().predicate(label, 9).model(getExistingFile(modLoc("item/" + name + "_label_9"))).end()
                .override().predicate(label, 10).model(getExistingFile(modLoc("item/" + name + "_label_10"))).end()
                .override().predicate(label, 11).model(getExistingFile(modLoc("item/" + name + "_label_11"))).end()
                .override().predicate(label, 12).model(getExistingFile(modLoc("item/" + name + "_label_12"))).end()
                .override().predicate(label, 13).model(getExistingFile(modLoc("item/" + name + "_label_13"))).end();
    }
}
