package melonystudios.stancements.tag;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.STRegistries;
import melonystudios.stancements.misc.modifier.VinylModifier;
import net.minecraft.tags.TagKey;

public class STVinylModifierTags {
    /// Vinyl modifiers in this tag run before any other modifier. This guarantees the *recording pipeline* does its part before
    /// actual modification are applied.
    /// @see VinylModifier#checkAndRun
    public static final TagKey<VinylModifier> PRIORITY_MODIFICATION = stancements("priority_modification");

    public static TagKey<VinylModifier> stancements(String name) {
        return TagKey.create(STRegistries.VINYL_MODIFIER, Stancements.stancements(name));
    }
}
