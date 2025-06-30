package melonystudios.stancements.misc;

import melonystudios.stancements.Stancements;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class STStats {
    public static final ResourceLocation SONGS_RECORDED = register("songs_recorded");

    private static ResourceLocation register(String name) {
        ResourceLocation registryName = Stancements.stancements(name);
        Registry.register(Registry.CUSTOM_STAT, name, registryName);
        Stats.CUSTOM.get(registryName, IStatFormatter.DEFAULT);
        return registryName;
    }

    public static void init() {}
}
