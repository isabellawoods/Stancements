package melonystudios.stancements.misc.modifier.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.misc.modifier.ModifierComponentType;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.VinylModifier;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;

public record EjectAfterTicksModifier(FloatProvider ejectionChance, IntProvider ticksUntilEjection) implements ModifierComponentType {
    public static final Codec<EjectAfterTicksModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            FloatProvider.codec(0, 1).fieldOf("ejection_chance").forGetter(EjectAfterTicksModifier::ejectionChance),
            IntProvider.codec(1, 72000).fieldOf("ticks_until_ejection").forGetter(EjectAfterTicksModifier::ticksUntilEjection)
    ).apply(instance, EjectAfterTicksModifier::new));

    public static EjectAfterTicksModifier tenToFifteenSeconds(float chance) {
        return new EjectAfterTicksModifier(ConstantFloat.of(chance), UniformInt.of(200, 300));
    }

    @Override
    public void onApplyModifiers(ModificationContext context, Holder<VinylModifier> modifier) {
        Level level = context.level();
        if (level != null && level.getRandom().nextFloat() <= this.ejectionChance().sample(level.getRandom())) {
            context.ejectionTicksCallback().accept(this.ticksUntilEjection().sample(level.getRandom()));
        }
    }
}
