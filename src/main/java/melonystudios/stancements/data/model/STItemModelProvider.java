package melonystudios.stancements.data.model;

import melonystudios.stancements.Stancements;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
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
        block("oak_shelf");
        block("spruce_shelf");
        block("birch_shelf");
        block("jungle_shelf");
        block("acacia_shelf");
        block("dark_oak_shelf");
        block("crimson_shelf");
        block("warped_shelf");
    }

    public void block(String name) {
        withExistingParent(name, modLoc("block/" + name));
    }
}
