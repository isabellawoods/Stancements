package melonystudios.stancements.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;

/// Registers new plants that can be added to {@linkplain melonystudios.stancements.block.custom.croppot.CropPotBlock crop pots}.
/// @param cropPot The crop pot placed after using the equivalent seed item.
/// @param plantingSound The sound played when planting this item.
public record PotPlantable(Block cropPot, SoundEvent plantingSound) {
    public static final Codec<PotPlantable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("crop_pot").forGetter(PotPlantable::cropPot),
            BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("planting_sound").forGetter(PotPlantable::plantingSound)
    ).apply(instance, PotPlantable::new));

    public static PotPlantable defaultPlantingSound(Block cropPot) {
        return new PotPlantable(cropPot, SoundEvents.CROP_PLANTED);
    }
}
