package melonystudios.stancements.misc;

import melonystudios.stancements.Stancements;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STStatistics {
    public static final DeferredRegister<ResourceLocation> STATS = DeferredRegister.create(Registries.CUSTOM_STAT, Stancements.MOD_ID);

    public static final DeferredHolder<ResourceLocation, ResourceLocation> SONGS_RECORDED = STATS.register("songs_recorded", () -> Stancements.stancements("songs_recorded"));
}
