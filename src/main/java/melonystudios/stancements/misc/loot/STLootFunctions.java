package melonystudios.stancements.misc.loot;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.loot.function.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STLootFunctions {
    public static final DeferredRegister<LootItemFunctionType<?>> FUNCTIONS = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, Stancements.MOD_ID);

    public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<SetRandomDyesFunction>> SET_RANDOM_DYES = FUNCTIONS.register("set_random_dyes",
            () -> new LootItemFunctionType<>(SetRandomDyesFunction.CODEC));
    public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<SetRandomLabelFunction>> SET_RANDOM_LABEL = FUNCTIONS.register("set_random_label",
            () -> new LootItemFunctionType<>(SetRandomLabelFunction.CODEC));
    public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<StyleDiscFromRegistryFunction>> STYLE_DISC_FROM_REGISTRY = FUNCTIONS.register("style_disc_from_registry",
            () -> new LootItemFunctionType<>(StyleDiscFromRegistryFunction.CODEC));
    public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<ApplyRecordingTurnsInto>> APPLY_RECORDING_TURNS_INTO = FUNCTIONS.register("apply_recording_turns_into",
            () -> new LootItemFunctionType<>(ApplyRecordingTurnsInto.CODEC));
}
