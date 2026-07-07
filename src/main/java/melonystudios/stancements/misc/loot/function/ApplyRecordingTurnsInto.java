package melonystudios.stancements.misc.loot.function;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.misc.loot.STLootFunctions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ApplyRecordingTurnsInto extends LootItemConditionalFunction {
    public static final MapCodec<ApplyRecordingTurnsInto> CODEC = RecordCodecBuilder.mapCodec(instance ->
            commonFields(instance).apply(instance, ApplyRecordingTurnsInto::new));

    public ApplyRecordingTurnsInto(List<LootItemCondition> conditions) {
        super(conditions);
    }

    @Override
    @NotNull
    protected ItemStack run(ItemStack stack, LootContext context) {
        if (stack.has(STDataComponents.RECORDING_TURNS_INTO)) {
            return stack.transmuteCopy(stack.get(STDataComponents.RECORDING_TURNS_INTO).whenRecorded().value());
        }
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> apply() {
        return simpleBuilder(ApplyRecordingTurnsInto::new);
    }

    @Override
    @NotNull
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return STLootFunctions.APPLY_RECORDING_TURNS_INTO.get();
    }
}
