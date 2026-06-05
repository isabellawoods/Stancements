package melonystudios.stancements.misc;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.discstyle.RecordedDiscStyle;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class STRegistries {
    public static final ResourceKey<Registry<RecordedDiscStyle>> RECORDED_DISC_STYLE = ResourceKey.createRegistryKey(Stancements.stancements("recorded_disc_style"));
}
