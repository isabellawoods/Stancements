package melonystudios.stancements.misc.loot.function;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.component.STDataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class ApplyRecordingTurnsIntoFunction extends LootItemConditionalFunction {
    public static final MapCodec<ApplyRecordingTurnsIntoFunction> CODEC = RecordCodecBuilder.mapCodec(instance ->
            commonFields(instance).apply(instance, ApplyRecordingTurnsIntoFunction::new));

    public ApplyRecordingTurnsIntoFunction(List<LootItemCondition> conditions) {
        super(conditions);
    }

    @Override
    @NonNull
    public MapCodec<ApplyRecordingTurnsIntoFunction> codec() {
        return CODEC;
    }

    @Override
    @NonNull
    protected ItemStack run(ItemStack stack, LootContext context) {
        if (stack.has(STDataComponents.RECORDING_TURNS_INTO)) {
            return stack.transmuteCopy(stack.get(STDataComponents.RECORDING_TURNS_INTO).whenRecorded().value());
        }
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> apply() {
        return simpleBuilder(ApplyRecordingTurnsIntoFunction::new);
    }
}
