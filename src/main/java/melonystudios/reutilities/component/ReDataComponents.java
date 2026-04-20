package melonystudios.reutilities.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ReDataComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "reutilities");

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Identifier>>> HIDE_COMPONENTS = COMPONENTS.registerComponentType("hide_components",
            builder -> builder.persistent(Identifier.CODEC.listOf()).cacheEncoding());
}
