package melonystudios.stancements.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import melonystudios.stancements.Stancements;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class STDataComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Stancements.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> MUSIC_ID = COMPONENTS.registerComponentType("music_id",
            builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> LABEL = COMPONENTS.registerComponentType("label",
            builder -> builder.persistent(floatRange(1, 11, value -> "Value must be within range [1;11]: " + value)).networkSynchronized(ByteBufCodecs.FLOAT));

    public static Codec<Float> floatRange(float min, float max, Function<Float, String> errorMessage) {
        return Codec.FLOAT.validate(value -> value.compareTo(min) > 0 && value.compareTo(max) <= 0 ? DataResult.success(value) : DataResult.error(() -> errorMessage.apply(value)));
    }
}
