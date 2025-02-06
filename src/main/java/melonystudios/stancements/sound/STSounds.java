package melonystudios.stancements.sound;

import melonystudios.stancements.Stancements;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class STSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Stancements.MOD_ID);

    public static final RegistryObject<SoundEvent> SPAWNER_PLACE = SOUNDS.register("block.spawner.place", () -> new SoundEvent(Stancements.stancements("block.spawner.place")));
    public static final RegistryObject<SoundEvent> SPAWNER_BREAK = SOUNDS.register("block.spawner.break", () -> new SoundEvent(Stancements.stancements("block.spawner.break")));
    public static final RegistryObject<SoundEvent> SPAWNER_HIT = SOUNDS.register("block.spawner.hit", () -> new SoundEvent(Stancements.stancements("block.spawner.hit")));
    public static final RegistryObject<SoundEvent> SPAWNER_STEP = SOUNDS.register("block.spawner.step", () -> new SoundEvent(Stancements.stancements("block.spawner.step")));

    public static final RegistryObject<SoundEvent> COBWEB_PLACE = SOUNDS.register("block.cobweb.place", () -> new SoundEvent(Stancements.stancements("block.cobweb.place")));
    public static final RegistryObject<SoundEvent> COBWEB_BREAK = SOUNDS.register("block.cobweb.break", () -> new SoundEvent(Stancements.stancements("block.cobweb.break")));
    public static final RegistryObject<SoundEvent> COBWEB_HIT = SOUNDS.register("block.cobweb.hit", () -> new SoundEvent(Stancements.stancements("block.cobweb.hit")));
    public static final RegistryObject<SoundEvent> COBWEB_FALL = SOUNDS.register("block.cobweb.fall", () -> new SoundEvent(Stancements.stancements("block.cobweb.fall")));
    public static final RegistryObject<SoundEvent> COBWEB_STEP = SOUNDS.register("block.cobweb.step", () -> new SoundEvent(Stancements.stancements("block.cobweb.step")));

    public static final RegistryObject<SoundEvent> SPONGE_PLACE = SOUNDS.register("block.sponge.place", () -> new SoundEvent(Stancements.stancements("block.sponge.place")));
    public static final RegistryObject<SoundEvent> SPONGE_BREAK = SOUNDS.register("block.sponge.break", () -> new SoundEvent(Stancements.stancements("block.sponge.break")));
    public static final RegistryObject<SoundEvent> SPONGE_HIT = SOUNDS.register("block.sponge.hit", () -> new SoundEvent(Stancements.stancements("block.sponge.hit")));
    public static final RegistryObject<SoundEvent> SPONGE_FALL = SOUNDS.register("block.sponge.fall", () -> new SoundEvent(Stancements.stancements("block.sponge.fall")));
    public static final RegistryObject<SoundEvent> SPONGE_STEP = SOUNDS.register("block.sponge.step", () -> new SoundEvent(Stancements.stancements("block.sponge.step")));

    public static final RegistryObject<SoundEvent> WET_SPONGE_PLACE = SOUNDS.register("block.wet_sponge.place", () -> new SoundEvent(Stancements.stancements("block.wet_sponge.place")));
    public static final RegistryObject<SoundEvent> WET_SPONGE_BREAK = SOUNDS.register("block.wet_sponge.break", () -> new SoundEvent(Stancements.stancements("block.wet_sponge.break")));
    public static final RegistryObject<SoundEvent> WET_SPONGE_HIT = SOUNDS.register("block.wet_sponge.hit", () -> new SoundEvent(Stancements.stancements("block.wet_sponge.hit")));
    public static final RegistryObject<SoundEvent> WET_SPONGE_FALL = SOUNDS.register("block.wet_sponge.fall", () -> new SoundEvent(Stancements.stancements("block.wet_sponge.fall")));
    public static final RegistryObject<SoundEvent> WET_SPONGE_STEP = SOUNDS.register("block.wet_sponge.step", () -> new SoundEvent(Stancements.stancements("block.wet_sponge.step")));

    public static final RegistryObject<SoundEvent> VINE_PLACE = SOUNDS.register("block.vine.place", () -> new SoundEvent(Stancements.stancements("block.vine.place")));
    public static final RegistryObject<SoundEvent> VINE_BREAK = SOUNDS.register("block.vine.break", () -> new SoundEvent(Stancements.stancements("block.vine.break")));
    public static final RegistryObject<SoundEvent> VINE_HIT = SOUNDS.register("block.vine.hit", () -> new SoundEvent(Stancements.stancements("block.vine.hit")));
    public static final RegistryObject<SoundEvent> VINE_FALL = SOUNDS.register("block.vine.fall", () -> new SoundEvent(Stancements.stancements("block.vine.fall")));

    public static final RegistryObject<SoundEvent> LILY_PAD_BREAK = SOUNDS.register("block.lily_pad.break", () -> new SoundEvent(Stancements.stancements("block.lily_pad.break")));
    public static final RegistryObject<SoundEvent> LILY_PAD_HIT = SOUNDS.register("block.lily_pad.hit", () -> new SoundEvent(Stancements.stancements("block.lily_pad.hit")));
    public static final RegistryObject<SoundEvent> LILY_PAD_FALL = SOUNDS.register("block.lily_pad.fall", () -> new SoundEvent(Stancements.stancements("block.lily_pad.fall")));
    public static final RegistryObject<SoundEvent> LILY_PAD_STEP = SOUNDS.register("block.lily_pad.step", () -> new SoundEvent(Stancements.stancements("block.lily_pad.step")));

    public static final RegistryObject<SoundEvent> IRON_PLACE = SOUNDS.register("block.iron.place", () -> new SoundEvent(Stancements.stancements("block.iron.place")));
    public static final RegistryObject<SoundEvent> IRON_BREAK = SOUNDS.register("block.iron.break", () -> new SoundEvent(Stancements.stancements("block.iron.break")));
    public static final RegistryObject<SoundEvent> IRON_HIT = SOUNDS.register("block.iron.hit", () -> new SoundEvent(Stancements.stancements("block.iron.hit")));
    public static final RegistryObject<SoundEvent> IRON_FALL = SOUNDS.register("block.iron.fall", () -> new SoundEvent(Stancements.stancements("block.iron.fall")));
    public static final RegistryObject<SoundEvent> IRON_STEP = SOUNDS.register("block.iron.step", () -> new SoundEvent(Stancements.stancements("block.iron.step")));
}
