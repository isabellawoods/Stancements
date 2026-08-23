package melonystudios.stancements.misc.modifier.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.ModifierComponentType;
import melonystudios.stancements.misc.modifier.VinylModifier;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.world.level.Level;

/// Copy of {@link net.minecraft.world.item.enchantment.effects.PlaySoundEffect PlaySoundEffect} that works with vinyl modifiers.
public record PlaySoundModifier(Holder<SoundEvent> soundEvent, FloatProvider volume, FloatProvider pitch) implements ModifierComponentType {
    public static final Codec<PlaySoundModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SoundEvent.CODEC.fieldOf("sound").forGetter(PlaySoundModifier::soundEvent),
            FloatProviders.codec(1.0E-5F, 10).fieldOf("volume").forGetter(PlaySoundModifier::volume),
            FloatProviders.codec(1.0E-5F, 2).fieldOf("pitch").forGetter(PlaySoundModifier::pitch)
    ).apply(instance, PlaySoundModifier::new));

    @Override
    public void onApplyModifiers(ModificationContext context, Holder<VinylModifier> modifier) {
        Level level = context.level();
        if (level != null) {
            level.playSound(
                    null,
                    context.blockPosition().getX(),
                    context.blockPosition().getY(),
                    context.blockPosition().getZ(),
                    this.soundEvent(),
                    SoundSource.BLOCKS,
                    this.volume().sample(level.getRandom()),
                    this.pitch().sample(level.getRandom())
            );
        }
    }
}
