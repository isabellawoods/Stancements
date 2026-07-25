package melonystudios.stancements.compat.jei;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.STItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

@JeiPlugin
public class STJEICompat implements IModPlugin {
    @Override
    @NonNull
    public Identifier getPluginUid() {
        return Stancements.stancements("jei_plugin");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(STItems.DYED_WATER_BUCKET.get(), DyedWaterBucketSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(STItems.CROP_POT.get(), CropPotSubtypeInterpreter.INSTANCE);
    }
}
