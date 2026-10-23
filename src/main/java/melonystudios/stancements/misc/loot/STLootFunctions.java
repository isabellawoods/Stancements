package melonystudios.stancements.misc.loot;

import com.mojang.serialization.MapCodec;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.loot.function.TransformRecordableFunction;
import melonystudios.stancements.misc.loot.function.SetRandomLabelFunction;
import melonystudios.stancements.misc.loot.function.StyleRecordableDiscFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STLootFunctions {
    public static final DeferredRegister<MapCodec<? extends LootItemFunction>> FUNCTIONS = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, Stancements.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<SetRandomLabelFunction>> SET_RANDOM_LABEL = FUNCTIONS.register("set_random_label",
            () -> SetRandomLabelFunction.CODEC);
    public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<StyleRecordableDiscFunction>> STYLE_RECORDABLE_DISC = FUNCTIONS.register("style_recordable_disc",
            () -> StyleRecordableDiscFunction.CODEC);
    public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<TransformRecordableFunction>> TRANSFORM_RECORDABLE = FUNCTIONS.register("transform_recordable",
            () -> TransformRecordableFunction.CODEC);
}
