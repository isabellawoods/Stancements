package melonystudios.stancements.misc.datamap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;

/// Data map value for {@linkplain STDataMaps#POT_PLANTABLES pottable plants} allowing mods to register new plants that can be added to {@linkplain melonystudios.stancements.block.custom.croppot.CropPotBlock crop pots}.
/// @param cropPot The crop pot placed after using the equivalent seed item.
/// @param plantingSound The sound played when planting this item.
public record PotPlantables(Block cropPot, SoundEvent plantingSound) {
    public static final Codec<PotPlantables> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("crop_pot").forGetter(PotPlantables::cropPot),
            BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("planting_sound").forGetter(PotPlantables::plantingSound)
    ).apply(instance, PotPlantables::new));

    public static PotPlantables defaultPlantingSound(Block cropPot) {
        return new PotPlantables(cropPot, SoundEvents.CROP_PLANTED);
    }
}
