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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.Optional;

/// Copy of {@link net.minecraft.world.item.enchantment.effects.ReplaceBlock ReplaceBlock} that works with vinyl modifiers.
public record ReplaceBlockModifier(Vec3i offset, Optional<BlockPredicate> predicate, BlockStateProvider blockState, Optional<Holder<GameEvent>> triggerGameEvent) implements ModifierComponentType {
    public static final Codec<ReplaceBlockModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3i.CODEC.optionalFieldOf("offset", Vec3i.ZERO).forGetter(ReplaceBlockModifier::offset),
            BlockPredicate.CODEC.optionalFieldOf("predicate").forGetter(ReplaceBlockModifier::predicate),
            BlockStateProvider.CODEC.fieldOf("block_state").forGetter(ReplaceBlockModifier::blockState),
            GameEvent.CODEC.optionalFieldOf("trigger_game_event").forGetter(ReplaceBlockModifier::triggerGameEvent)
    ).apply(instance, ReplaceBlockModifier::new));

    @Override
    public void onApplyModifiers(ModificationContext context, Holder<VinylModifier> modifier) {
        ServerLevel level = context.level();
        BlockPos pos = BlockPos.containing(context.blockPosition().getCenter()).offset(this.offset);
        if (this.predicate.map(predicate -> predicate.test(level, pos)).orElse(true) && level.setBlockAndUpdate(pos, this.blockState.getState(level, level.getRandom(), pos))) {
            this.triggerGameEvent.ifPresent(event -> level.gameEvent(context.playerOrNull(), event, pos));
        }
    }
}
