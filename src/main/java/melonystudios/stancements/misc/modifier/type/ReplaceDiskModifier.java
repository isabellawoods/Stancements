package melonystudios.stancements.misc.modifier.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.ModifierComponentType;
import melonystudios.stancements.misc.modifier.VinylModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.Optional;

/// Copy of {@link net.minecraft.world.item.enchantment.effects.ReplaceDisk ReplaceDisk} that works with vinyl modifiers
public record ReplaceDiskModifier(
        IntProvider radius,
        IntProvider height,
        Vec3i offset,
        Optional<BlockPredicate> predicate,
        BlockStateProvider blockState,
        Optional<Holder<GameEvent>> triggerGameEvent
) implements ModifierComponentType {
    public static final Codec<ReplaceDiskModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IntProviders.CODEC.fieldOf("radius").forGetter(ReplaceDiskModifier::radius),
            IntProviders.CODEC.fieldOf("height").forGetter(ReplaceDiskModifier::height),
            Vec3i.CODEC.optionalFieldOf("offset", Vec3i.ZERO).forGetter(ReplaceDiskModifier::offset),
            BlockPredicate.CODEC.optionalFieldOf("predicate").forGetter(ReplaceDiskModifier::predicate),
            BlockStateProvider.CODEC.fieldOf("block_state").forGetter(ReplaceDiskModifier::blockState),
            GameEvent.CODEC.optionalFieldOf("trigger_game_event").forGetter(ReplaceDiskModifier::triggerGameEvent)
    ).apply(instance, ReplaceDiskModifier::new));

    @Override
    public void onApplyModifiers(ModificationContext context, Holder<VinylModifier> modifier) {
        ServerLevel level = context.level();
        BlockPos blockPos = BlockPos.containing(context.blockPosition().getCenter()).offset(this.offset);
        RandomSource random = level.getRandom();
        int radius = this.radius.sample(random);
        int height = this.height.sample(random);

        for (BlockPos pos : BlockPos.betweenClosed(blockPos.offset(-radius, 0, -radius), blockPos.offset(radius, Math.min(height - 1, 0), radius))) {
            if (pos.distToCenterSqr(context.blockPosition().getX(), (double) pos.getY() + 0.5, context.blockPosition().getZ()) < (double) Mth.square(radius)
                    && this.predicate.map(predicate -> predicate.test(level, pos)).orElse(true)
                    && level.setBlockAndUpdate(pos, this.blockState.getState(level, random, pos))) {
                this.triggerGameEvent.ifPresent(event -> level.gameEvent(context.playerOrNull(), event, pos));
            }
        }
    }
}
