package melonystudios.stancements.misc.loot;

import melonystudios.stancements.Stancements;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.function.Consumer;

public class STLootContextParamSets {
    public static final ContextKeySet VINYL_MODIFIER = register("vinyl_modifier", builder -> builder
            .required(LootContextParams.ORIGIN)
            .required(LootContextParams.BLOCK_STATE)
            .optional(LootContextParams.BLOCK_ENTITY)
            .optional(LootContextParams.THIS_ENTITY)
    );

    private static ContextKeySet register(String name, Consumer<ContextKeySet.Builder> builderConsumer) {
        ContextKeySet.Builder builder = new ContextKeySet.Builder();
        builderConsumer.accept(builder);
        ContextKeySet paramSet = builder.build();
        Identifier identifier = Stancements.stancements(name);
        ContextKeySet registeredSet = LootContextParamSets.REGISTRY.put(identifier, paramSet);

        if (registeredSet != null) {
            throw new IllegalStateException("Loot table parameter set '" + identifier + "' is already registered");
        } else {
            return paramSet;
        }
    }
}
