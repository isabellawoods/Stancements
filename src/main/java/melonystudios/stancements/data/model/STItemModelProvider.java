package melonystudios.stancements.data.model;

import melonystudios.stancements.Stancements;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class STItemModelProvider extends ItemModelProvider {
    public STItemModelProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, Stancements.MOD_ID, fileHelper);
    }

    @Override
    @NotNull
    public String getName() {
        return "Stancements - Item Models";
    }

    @Override
    protected void registerModels() {
        ModelFile generated = getExistingFile(mcLoc("item/generated"));

        // Decorative blocks
        block("oak_shelf");
        block("spruce_shelf");
        block("birch_shelf");
        block("jungle_shelf");
        block("acacia_shelf");
        block("dark_oak_shelf");
        block("crimson_shelf");
        block("warped_shelf");

        // Functional blocks
        block("music_recorder");

        // Items
        standard(generated, "vinyl_disc");
        recordedDisc(generated, "recorded_disc");
    }

    /// Makes a model for an item.
    /// @param parent The location of the parent model, usually <code>item/generated</code> or <code>item/handheld</code>.
    /// @param name The item's registry id, used to locate the texture.
    public void standard(ModelFile parent, String name) {
        getBuilder(name).parent(parent).texture("layer0", "item/" + name);
    }

    /// Makes a model for a block, using the <code>models/block</code> folder as the source.
    /// @param name The block's registry id and model file name.
    public void block(String name) {
        withExistingParent(name, modLoc("block/" + name));
    }

    /// Makes a {@linkplain melonystudios.stancements.item.custom.RecordedDiscItem recorded disc} model, with <b>11</b> different
    /// override models for each music disc {@linkplain melonystudios.stancements.component.STDataComponents#LABEL label}.
    /// @param parent The location of the parent model, usually <code>item/generated</code>.
    /// @param name The item's registry id, used for the model name and texture locations.
    public void recordedDisc(ModelFile parent, String name) {
        ResourceLocation label = Stancements.stancements("label");

        for (int i = 1; i <= 11; ++i) {
            getBuilder(name + "_label_" + i).parent(parent).texture("layer0", modLoc("item/" + name)).texture("layer1", modLoc("item/" + name + "_label_" + i));
        }

        getBuilder(name).parent(parent).texture("layer0", modLoc("item/" + name)).texture("layer1", modLoc("item/" + name + "_label_1"))
                .override().predicate(label, 1).model(getExistingFile(modLoc("item/" + name + "_label_1"))).end()
                .override().predicate(label, 2).model(getExistingFile(modLoc("item/" + name + "_label_2"))).end()
                .override().predicate(label, 3).model(getExistingFile(modLoc("item/" + name + "_label_3"))).end()
                .override().predicate(label, 4).model(getExistingFile(modLoc("item/" + name + "_label_4"))).end()
                .override().predicate(label, 5).model(getExistingFile(modLoc("item/" + name + "_label_1"))).end()
                .override().predicate(label, 6).model(getExistingFile(modLoc("item/" + name + "_label_6"))).end()
                .override().predicate(label, 7).model(getExistingFile(modLoc("item/" + name + "_label_7"))).end()
                .override().predicate(label, 8).model(getExistingFile(modLoc("item/" + name + "_label_8"))).end()
                .override().predicate(label, 9).model(getExistingFile(modLoc("item/" + name + "_label_9"))).end()
                .override().predicate(label, 10).model(getExistingFile(modLoc("item/" + name + "_label_10"))).end()
                .override().predicate(label, 11).model(getExistingFile(modLoc("item/" + name + "_label_11"))).end();
    }
}
