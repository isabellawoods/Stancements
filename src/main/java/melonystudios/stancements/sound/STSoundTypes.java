package melonystudios.stancements.sound;

import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;

public class STSoundTypes {
    public static final ForgeSoundType SPAWNER = new ForgeSoundType(1, 1, STSounds.SPAWNER_BREAK, STSounds.SPAWNER_STEP, STSounds.SPAWNER_PLACE, STSounds.SPAWNER_HIT, STSounds.SPAWNER_HIT);
    public static final ForgeSoundType COBWEB = new ForgeSoundType(1, 1, STSounds.COBWEB_BREAK, STSounds.COBWEB_STEP, STSounds.COBWEB_PLACE, STSounds.COBWEB_HIT, STSounds.COBWEB_FALL);
    public static final ForgeSoundType SPONGE = new ForgeSoundType(1, 1, STSounds.SPONGE_BREAK, STSounds.SPONGE_STEP, STSounds.SPONGE_PLACE, STSounds.SPONGE_HIT, STSounds.SPONGE_FALL);
    public static final ForgeSoundType WET_SPONGE = new ForgeSoundType(1, 1, STSounds.WET_SPONGE_BREAK, STSounds.WET_SPONGE_STEP, STSounds.WET_SPONGE_PLACE, STSounds.WET_SPONGE_HIT, STSounds.WET_SPONGE_FALL);
    public static final ForgeSoundType VINE = new ForgeSoundType(1, 1, STSounds.VINE_BREAK, () -> SoundEvents.VINE_STEP, STSounds.VINE_PLACE, STSounds.VINE_HIT, STSounds.VINE_FALL);
    public static final ForgeSoundType LILY_PAD = new ForgeSoundType(1, 1, STSounds.LILY_PAD_BREAK, STSounds.LILY_PAD_STEP, () -> SoundEvents.LILY_PAD_PLACE, STSounds.LILY_PAD_HIT, STSounds.LILY_PAD_FALL);
    public static final ForgeSoundType IRON = new ForgeSoundType(1, 1, STSounds.IRON_BREAK, STSounds.IRON_STEP, STSounds.IRON_PLACE, STSounds.IRON_HIT, STSounds.IRON_FALL);
    public static final ForgeSoundType ANVIL = new ForgeSoundType(0.3F, 1, STSounds.IRON_BREAK, STSounds.IRON_STEP, () -> SoundEvents.ANVIL_PLACE, STSounds.IRON_HIT, STSounds.IRON_FALL);
}
