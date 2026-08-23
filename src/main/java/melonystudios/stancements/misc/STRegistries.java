package melonystudios.stancements.misc;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.discstyle.RecordedDiscStyle;
import melonystudios.stancements.misc.modifier.VinylModifier;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class STRegistries {
    public static final ResourceKey<Registry<RecordedDiscStyle>> RECORDED_DISC_STYLE = ResourceKey.createRegistryKey(Stancements.stancements("recorded_disc_style"));

    // Vinyl modifier components
    public static final ResourceKey<Registry<VinylModifier>> VINYL_MODIFIER = ResourceKey.createRegistryKey(Stancements.stancements("vinyl_modifier"));
    public static final ResourceKey<Registry<DataComponentType<?>>> VINYL_MODIFIER_COMPONENT_TYPE_KEY = ResourceKey.createRegistryKey(Stancements.stancements("vinyl_modifier_component_type"));
    public static final Registry<DataComponentType<?>> VINYL_MODIFIER_COMPONENT_TYPE = new RegistryBuilder<>(VINYL_MODIFIER_COMPONENT_TYPE_KEY).sync(true).create();
}
