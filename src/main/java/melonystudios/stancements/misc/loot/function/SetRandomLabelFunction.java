package melonystudios.stancements.misc.loot.function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class SetRandomLabelFunction extends LootItemConditionalFunction {
    public static final MapCodec<SetRandomLabelFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> commonFields(instance).and(instance.group(
            NumberProviders.CODEC.fieldOf("range").forGetter(function -> function.range),
            Codec.BOOL.optionalFieldOf("round_to_nearest", false).forGetter(function -> function.roundToNearest)
    )).apply(instance, SetRandomLabelFunction::new));
    private final NumberProvider range;
    private final boolean roundToNearest;

    public SetRandomLabelFunction(List<LootItemCondition> conditions, NumberProvider range, boolean roundToNearest) {
        super(conditions);
        this.range = range;
        this.roundToNearest = roundToNearest;
    }

    @Override
    @NonNull
    public MapCodec<SetRandomLabelFunction> codec() {
        return CODEC;
    }

    @Override
    @NonNull
    protected ItemStack run(ItemStack stack, LootContext context) {
        if (this.roundToNearest) {
            stack.set(STDataComponents.LABEL, (float) this.range.getInt(context));
        } else {
            stack.set(STDataComponents.LABEL, this.range.getFloat(context));
        }
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> withLabel(NumberProvider range, boolean roundToNearest) {
        return simpleBuilder(conditions -> new SetRandomLabelFunction(conditions, range, roundToNearest));
    }

    public static LootItemConditionalFunction.Builder<?> withDefaultLabelRange() {
        return withLabel(UniformGenerator.between(RecordedDiscItem.DISC_LABEL_MIN, RecordedDiscItem.DISC_LABEL_MAX), true);
    }
}
