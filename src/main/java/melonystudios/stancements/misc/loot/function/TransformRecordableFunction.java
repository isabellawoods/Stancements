package melonystudios.stancements.misc.loot.function;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.RecordableTransform;
import melonystudios.stancements.misc.loot.STLootFunctions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TransformRecordableFunction extends LootItemConditionalFunction {
    public static final MapCodec<TransformRecordableFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> commonFields(instance).and(
            RecordableTransform.Transforms.CODEC.fieldOf("transform").forGetter(function -> function.transform)
    ).apply(instance, TransformRecordableFunction::new));
    private final RecordableTransform.Transforms transform;

    public TransformRecordableFunction(List<LootItemCondition> conditions, RecordableTransform.Transforms transform) {
        super(conditions);
        this.transform = transform;
    }

    @Override
    @NotNull
    protected ItemStack run(ItemStack stack, LootContext context) {
        RecordableTransform transform = stack.get(STDataComponents.RECORDABLE_TRANSFORM);
        if (transform != null) return stack.transmuteCopy(this.transform.itemFor(transform));
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> withTransform(RecordableTransform.Transforms transform) {
        return simpleBuilder(conditions -> new TransformRecordableFunction(conditions, transform));
    }

    @Override
    @NotNull
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return STLootFunctions.TRANSFORM_RECORDABLE.get();
    }
}
