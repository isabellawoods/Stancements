package melonystudios.stancements.misc.modifier;

import com.mojang.serialization.Codec;
import melonystudios.stancements.misc.STRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;

/// Interface representing a single **vinyl modifier component type**.
public interface ModifierComponentType {
    Codec<DataComponentType<?>> COMPONENT_CODEC = Codec.lazyInitialized(STRegistries.VINYL_MODIFIER_COMPONENT_TYPE::byNameCodec);
    Codec<DataComponentMap> CODEC = DataComponentMap.makeCodec(COMPONENT_CODEC);

    /// Applies this vinyl modifier, based on the {@linkplain ModificationContext context}.
    /// @param context The *modification context* of this run of the pipeline. This provides all the information necessary for this modifier to run.
    /// @param modifier A {@link Holder} of this modifier.
    /// @see VinylModifier#checkAndRun
    void onApplyModifiers(ModificationContext context, Holder<VinylModifier> modifier);
}
