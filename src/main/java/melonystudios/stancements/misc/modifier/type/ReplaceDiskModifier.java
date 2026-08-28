package melonystudios.stancements.misc.modifier.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.ModifierComponentType;
import melonystudios.stancements.misc.modifier.VinylModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.Optional;

/// Copy of {@link net.minecraft.world.item.enchantment.effects.ReplaceDisk ReplaceDisk} that works with vinyl modifiers
public record ReplaceDiskModifier(
        FloatProvider radius,
        FloatProvider height,
        Vec3i offset,
        Optional<BlockPredicate> predicate,
        BlockStateProvider blockState,
        Optional<Holder<GameEvent>> triggerGameEvent
) implements ModifierComponentType {
    public static final Codec<ReplaceDiskModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            FloatProvider.CODEC.fieldOf("radius").forGetter(ReplaceDiskModifier::radius),
            FloatProvider.CODEC.fieldOf("height").forGetter(ReplaceDiskModifier::height),
            Vec3i.CODEC.optionalFieldOf("offset", Vec3i.ZERO).forGetter(ReplaceDiskModifier::offset),
            BlockPredicate.CODEC.optionalFieldOf("predicate").forGetter(ReplaceDiskModifier::predicate),
            BlockStateProvider.CODEC.fieldOf("block_state").forGetter(ReplaceDiskModifier::blockState),
            GameEvent.CODEC.optionalFieldOf("trigger_game_event").forGetter(ReplaceDiskModifier::triggerGameEvent)
    ).apply(instance, ReplaceDiskModifier::new));

    @Override
    public void onApplyModifiers(ModificationContext context, Holder<VinylModifier> modifier) {
        BlockPos blockPos = BlockPos.containing(context.blockPosition().getCenter()).offset(this.offset);
        RandomSource random = context.level().getRandom();
        int radius = Math.round(this.radius.sample(random));
        int height = Math.round(this.height.sample(random));

        for (BlockPos pos : BlockPos.betweenClosed(blockPos.offset(-radius, 0, -radius), blockPos.offset(radius, Math.min(height - 1, 0), radius))) {
            if (pos.distToCenterSqr(context.blockPosition().getX(), (double) pos.getY() + 0.5, context.blockPosition().getZ()) < (double) Mth.square(radius)
                    && this.predicate.map(predicate -> predicate.test(context.level(), pos)).orElse(true)
                    && context.level().setBlockAndUpdate(pos, this.blockState.getState(random, pos))) {
                this.triggerGameEvent.ifPresent(event -> context.level().gameEvent(context.playerOrNull(), event, pos));
            }
        }
    }
}
