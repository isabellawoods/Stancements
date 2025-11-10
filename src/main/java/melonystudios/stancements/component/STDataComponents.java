package melonystudios.stancements.component;

import melonystudios.reutilities.api.ReAPI;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.component.custom.MusicData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STDataComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Stancements.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> MUSIC_ID = COMPONENTS.registerComponentType("music_id",
            builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MusicData>> MUSIC_DATA = COMPONENTS.registerComponentType("music_data",
            builder -> builder.persistent(MusicData.CODEC).networkSynchronized(MusicData.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> LABEL = COMPONENTS.registerComponentType("label",
            builder -> builder.persistent(ReAPI.floatRange(1, 13)).networkSynchronized(ByteBufCodecs.FLOAT));
}
