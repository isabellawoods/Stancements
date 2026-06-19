package melonystudios.stancements.data.model;

import melonystudios.reutilities.data.model.ReItemModelProvider;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static melonystudios.stancements.item.custom.RecordedDiscItem.DISC_LABEL_MAX;
import static melonystudios.stancements.item.custom.RecordedDiscItem.DISC_LABEL_MIN;

public class STItemModelProvider extends ReItemModelProvider {
    public static final DyeColor[] COLORS = DyeColor.values();

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
        block("pale_oak_shelf");
        block("bamboo_shelf");
        block("crimson_shelf");
        block("warped_shelf");

        // Crafting Table Cloths
        forAllColors(color -> block(color + "_crafting_table_cloth"));

        // Functional blocks
        block("music_recorder");
        cropPot(this.generated, "crop_pot");

        // Rails
        blockItem("gilded_rail");
        forAllColors(color -> blockItem(color + "_tagging_rail"));

        // Items
        standard("stancements_logo");
        standard("vinyl_disc");
        recordedDisc(this.generated, "recorded_disc");
        forAllColors(color -> standard(color + "_tag"));
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

    /// Makes a {@linkplain melonystudios.stancements.item.custom.RecordedDiscItem recorded disc} model, with {@linkplain RecordedDiscItem#DISC_LABEL_MAX **14**} different
    /// override models for each music disc {@linkplain melonystudios.stancements.component.STDataComponents#LABEL label}.
    /// @param parent The location of the parent model, usually `item/generated`.
    /// @param name The item's registry ID, used for the model name and texture locations.
    public void recordedDisc(ModelFile parent, String name) {
        ResourceLocation label = Stancements.stancements("label");

        // recorded disc submodels (for each label)
        for (int i = DISC_LABEL_MIN; i <= DISC_LABEL_MAX; ++i) {
            getBuilder(name + "_label_" + i).parent(parent).texture("layer0", modLoc("item/" + name)).texture("layer1", modLoc("item/" + name + "_label_" + i));
        }

        ResourceLocation outputLocation = this.modLoc("item/" + name);

        // main recorded disc model (with all model overrides)
        // rewritten for easy addition of new labels ~isa 17-03-26
        this.generatedModels.computeIfAbsent(outputLocation, location -> {
            ItemModelBuilder model = new ItemModelBuilder(location, this.existingFileHelper);
            model.parent(parent).texture("layer0", location).texture("layer1", this.modLoc("item/" + name + "_label_1"));

            for (int i = DISC_LABEL_MIN; i <= DISC_LABEL_MAX; ++i) {
                model.override().predicate(label, i).model(this.getExistingFile(this.modLoc("item/" + name + "_label_" + i))).end();
            }
            return model;
        });
        this.existingFileHelper.trackGenerated(outputLocation, MODEL);
    }

    /// Runs the provided consumer for every registered {@link DyeColor} in the game.
    /// @param forColor The consumer, ran for every color.
    public void forAllColors(Consumer<String> forColor) {
        for (DyeColor color : COLORS) forColor.accept(color.getName());
    }
}
