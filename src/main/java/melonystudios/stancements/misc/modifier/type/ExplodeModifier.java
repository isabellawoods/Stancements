package melonystudios.stancements.misc.modifier.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.ModifierComponentType;
import melonystudios.stancements.misc.modifier.VinylModifier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.particles.ExplosionParticleInfo;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/// Copy of {@link net.minecraft.world.item.enchantment.effects.ExplodeEffect ExplodeEffect} that works with vinyl modifiers.
public record ExplodeModifier(
        boolean attributeToRecordee,
        Optional<Holder<DamageType>> damageType,
        Optional<FloatProvider> knockbackMultiplier,
        Optional<HolderSet<Block>> immuneBlocks,
        Vec3 offset,
        FloatProvider radius,
        boolean createFire,
        Level.ExplosionInteraction blockInteraction,
        ParticleOptions smallParticle,
        ParticleOptions largeParticle,
        WeightedList<ExplosionParticleInfo> blockParticles,
        Holder<SoundEvent> sound
) implements ModifierComponentType {
    public static final Codec<ExplodeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("attribute_to_recordee", false).forGetter(ExplodeModifier::attributeToRecordee),
            DamageType.CODEC.optionalFieldOf("damage_type").forGetter(ExplodeModifier::damageType),
            FloatProviders.codec(0, Float.MAX_VALUE).optionalFieldOf("knockback_multiplier").forGetter(ExplodeModifier::knockbackMultiplier),
            RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("immune_blocks").forGetter(ExplodeModifier::immuneBlocks),
            Vec3.CODEC.optionalFieldOf("offset", Vec3.ZERO).forGetter(ExplodeModifier::offset),
            FloatProviders.codec(0, 128).fieldOf("radius").forGetter(ExplodeModifier::radius),
            Codec.BOOL.optionalFieldOf("create_fire", false).forGetter(ExplodeModifier::createFire),
            Level.ExplosionInteraction.CODEC.fieldOf("block_interaction").forGetter(ExplodeModifier::blockInteraction),
            ParticleTypes.CODEC.fieldOf("small_particle").forGetter(ExplodeModifier::smallParticle),
            ParticleTypes.CODEC.fieldOf("large_particle").forGetter(ExplodeModifier::largeParticle),
            WeightedList.codec(ExplosionParticleInfo.CODEC).optionalFieldOf("block_particles", WeightedList.of()).forGetter(ExplodeModifier::blockParticles),
            SoundEvent.CODEC.fieldOf("sound").forGetter(ExplodeModifier::sound)
    ).apply(instance, ExplodeModifier::new));

    @Override
    public void onApplyModifiers(ModificationContext context, Holder<VinylModifier> modifier) {
        Vec3 position = context.blockPosition().getCenter().add(this.offset);
        Player player = context.playerOrNull();

        context.level().explode(
                this.attributeToRecordee() && context.recorderOrNull() != null ? player : null,
                this.getDamageSource(player, position),
                new SimpleExplosionDamageCalculator(
                        this.blockInteraction != Level.ExplosionInteraction.NONE,
                        this.damageType.isPresent(),
                        this.knockbackMultiplier.map(provider -> provider.sample(context.level().getRandom())),
                        this.immuneBlocks
                ),
                position.x(),
                position.y(),
                position.z(),
                Math.max(this.radius.sample(context.level().getRandom()), 0),
                this.createFire,
                this.blockInteraction,
                this.smallParticle,
                this.largeParticle,
                this.blockParticles,
                this.sound
        );
    }

    @Nullable
    private DamageSource getDamageSource(@Nullable Entity entity, Vec3 pos) {
        return this.damageType().map(type -> this.attributeToRecordee() && entity != null ?
                new DamageSource(type, entity) :
                new DamageSource(type, pos)
        ).orElse(null);
    }
}