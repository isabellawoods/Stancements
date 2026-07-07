package melonystudios.stancements.misc.loot;

import melonystudios.stancements.Stancements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.function.Consumer;

public class STLootContextParamSets {
    public static final LootContextParamSet VINYL_MODIFIER = register("vinyl_modifier", builder -> builder
            .required(LootContextParams.ORIGIN)
            .required(LootContextParams.BLOCK_STATE)
            .optional(LootContextParams.BLOCK_ENTITY)
            .optional(LootContextParams.THIS_ENTITY)
    );

    private static LootContextParamSet register(String name, Consumer<LootContextParamSet.Builder> builderConsumer) {
        LootContextParamSet.Builder builder = new LootContextParamSet.Builder();
        builderConsumer.accept(builder);
        LootContextParamSet paramSet = builder.build();
        ResourceLocation location = Stancements.stancements(name);
        LootContextParamSet registeredSet = LootContextParamSets.REGISTRY.put(location, paramSet);

        if (registeredSet != null) {
            throw new IllegalStateException("Loot table parameter set '" + location + "' is already registered");
        } else {
            return paramSet;
        }
    }
}
