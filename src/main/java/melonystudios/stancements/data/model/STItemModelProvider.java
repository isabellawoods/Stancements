package melonystudios.stancements.data.model;

import melonystudios.stancements.Stancements;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class STItemModelProvider extends ItemModelProvider {
    public STItemModelProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, Stancements.MOD_ID, fileHelper);
    }

    @Nonnull
    public String getName() {
        return "Stancements - Item Models";
    }

    @Override
    protected void registerModels() {
        ModelFile generated = getExistingFile(mcLoc("item/generated"));

        block("oak_shelf");
        block("spruce_shelf");
        block("birch_shelf");
        block("jungle_shelf");
        block("acacia_shelf");
        block("dark_oak_shelf");
        block("crimson_shelf");
        block("warped_shelf");

        block("music_recorder");

        standard(generated, "vinyl_disc");
        recordedDisc(generated, "recorded_disc");
    }

    public void standard(ModelFile parent, String name) {
        getBuilder(name).parent(parent).texture("layer0", "item/" + name);
    }

    public void block(String name) {
        withExistingParent(name, modLoc("block/" + name));
    }

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
