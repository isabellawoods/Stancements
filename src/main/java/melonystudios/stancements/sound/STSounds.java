package melonystudios.stancements.sound;

import melonystudios.stancements.Stancements;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, Stancements.MOD_ID);

    // Items
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_DYE = SOUNDS.register("item.cauldron.dye", () -> SoundEvent.createVariableRangeEvent(Stancements.stancements("item.cauldron.dye")));
    public static final DeferredHolder<SoundEvent, SoundEvent> TAG_MINECART = SOUNDS.register("item.tag.latch", () -> SoundEvent.createVariableRangeEvent(Stancements.stancements("item.tag.latch")));

    // Entities
    public static final DeferredHolder<SoundEvent, SoundEvent> SHEAR_MINECART = SOUNDS.register("entity.minecart.shear", () -> SoundEvent.createVariableRangeEvent(Stancements.stancements("entity.minecart.shear")));

    // Volume Alpha
    public static final DeferredHolder<SoundEvent, SoundEvent> MINECRAFT_SONG = register("music.game.minecraft");
    public static final DeferredHolder<SoundEvent, SoundEvent> CLARK_SONG = register("music.game.clark");
    public static final DeferredHolder<SoundEvent, SoundEvent> SWEDEN_SONG = register("music.game.sweden");
    public static final DeferredHolder<SoundEvent, SoundEvent> SUBWOOFER_LULLABY_SONG = register("music.game.subwoofer_lullaby");
    public static final DeferredHolder<SoundEvent, SoundEvent> LIVING_MICE_SONG = register("music.game.living_mice");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAGGSTROM_SONG = register("music.game.haggstrom");
    public static final DeferredHolder<SoundEvent, SoundEvent> DANNY_SONG = register("music.game.danny");
    public static final DeferredHolder<SoundEvent, SoundEvent> KEY_SONG = register("music.game.key");
    public static final DeferredHolder<SoundEvent, SoundEvent> OXYGENE_SONG = register("music.game.oxygene");
    public static final DeferredHolder<SoundEvent, SoundEvent> DRY_HANDS_SONG = register("music.game.dry_hands");
    public static final DeferredHolder<SoundEvent, SoundEvent> WET_HANDS_SONG = register("music.game.wet_hands");
    public static final DeferredHolder<SoundEvent, SoundEvent> MICE_ON_VENUS_SONG = register("music.game.mice_on_venus");

    // Volume Beta
    public static final DeferredHolder<SoundEvent, SoundEvent> BIOME_FEST_SONG = register("music.game.creative.biome_fest");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLIND_SPOTS_SONG = register("music.game.creative.blind_spots");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNT_MUSKIE_SONG = register("music.game.creative.haunt_muskie");
    public static final DeferredHolder<SoundEvent, SoundEvent> ARIA_MATH_SONG = register("music.game.creative.aria_math");
    public static final DeferredHolder<SoundEvent, SoundEvent> DREITON_SONG = register("music.game.creative.dreiton");
    public static final DeferredHolder<SoundEvent, SoundEvent> TASWELL_SONG = register("music.game.creative.taswell");
    public static final DeferredHolder<SoundEvent, SoundEvent> CONCRETE_HALLS_SONG = register("music.game.nether.concrete_halls");
    public static final DeferredHolder<SoundEvent, SoundEvent> DEAD_VOXEL_SONG = register("music.game.nether.dead_voxel");
    public static final DeferredHolder<SoundEvent, SoundEvent> WARMTH_SONG = register("music.game.nether.warmth");
    public static final DeferredHolder<SoundEvent, SoundEvent> BALLAD_OF_THE_CATS_SONG = register("music.game.nether.ballad_of_the_cats");
    public static final DeferredHolder<SoundEvent, SoundEvent> BOSS_SONG = register("music.game.end.boss");
    public static final DeferredHolder<SoundEvent, SoundEvent> THE_END_SONG = register("music.game.end.the_end");
    public static final DeferredHolder<SoundEvent, SoundEvent> ALPHA_SONG = register("music.game.end.alpha");

    // Underwater Singles
    public static final DeferredHolder<SoundEvent, SoundEvent> SHUNIJI_SONG = register("music.game.water.shuniji");
    public static final DeferredHolder<SoundEvent, SoundEvent> DRAGON_FISH_SONG = register("music.game.water.dragon_fish");
    public static final DeferredHolder<SoundEvent, SoundEvent> AXOLOTL_SONG = register("music.game.water.axolotl");

    // Nether Update (1.16)
    public static final DeferredHolder<SoundEvent, SoundEvent> RUBEDO_SONG = register("music.game.nether.nether_wastes.rubedo");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHRYSOPOEIA_SONG = register("music.game.nether.crimson_forest.chrysopoeia");
    public static final DeferredHolder<SoundEvent, SoundEvent> SO_BELOW_SONG = register("music.game.nether.soulsand_valley.so_below");

    // Caves & Cliffs (1.17 / 1.18)
    public static final DeferredHolder<SoundEvent, SoundEvent> STAND_TALL_SONG = register("music.game.stand_tall");
    public static final DeferredHolder<SoundEvent, SoundEvent> LEFT_TO_BLOOM_SONG = register("music.game.left_to_bloom");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANCESTRY_SONG = register("music.game.ancestry");
    public static final DeferredHolder<SoundEvent, SoundEvent> WENDING_SONG = register("music.game.wending");
    public static final DeferredHolder<SoundEvent, SoundEvent> INFINITE_AMETHYST_SONG = register("music.game.infinite_amethyst");
    public static final DeferredHolder<SoundEvent, SoundEvent> ONE_MORE_DAY_SONG = register("music.game.one_more_day");
    public static final DeferredHolder<SoundEvent, SoundEvent> FLOATING_DREAM_SONG = register("music.game.floating_dream");
    public static final DeferredHolder<SoundEvent, SoundEvent> COMFORTING_MEMORIES_SONG = register("music.game.comforting_memories");
    public static final DeferredHolder<SoundEvent, SoundEvent> AN_ORDINARY_DAY_SONG = register("music.game.an_ordinary_day");

    // The Wild Update (1.19)
    public static final DeferredHolder<SoundEvent, SoundEvent> FIREBUGS_SONG = register("music.game.swamp.firebugs");
    public static final DeferredHolder<SoundEvent, SoundEvent> AERIE_SONG = register("music.game.swamp.aerie");
    public static final DeferredHolder<SoundEvent, SoundEvent> LABYRINTHINE_SONG = register("music.game.swamp.labyrinthine");

    // Trails & Tales (1.20)
    public static final DeferredHolder<SoundEvent, SoundEvent> ECHO_IN_THE_WIND_SONG = register("music.game.echo_in_the_wind");
    public static final DeferredHolder<SoundEvent, SoundEvent> A_FAMILIAR_ROOM_SONG = register("music.game.a_familiar_room");
    public static final DeferredHolder<SoundEvent, SoundEvent> BROMELIAD_SONG = register("music.game.bromeliad");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRESCENT_DUNES_SONG = register("music.game.crescent_dunes");

    // Tricky Trials (1.21)
    public static final DeferredHolder<SoundEvent, SoundEvent> FEATHERFALL_SONG = register("music.game.featherfall");
    public static final DeferredHolder<SoundEvent, SoundEvent> WATCHER_SONG = register("music.game.watcher");
    public static final DeferredHolder<SoundEvent, SoundEvent> PUZZLEBOX_SONG = register("music.game.puzzlebox");
    public static final DeferredHolder<SoundEvent, SoundEvent> KOMOREBI_SONG = register("music.game.komorebi");
    public static final DeferredHolder<SoundEvent, SoundEvent> POKOPOKO_SONG = register("music.game.pokopoko");
    public static final DeferredHolder<SoundEvent, SoundEvent> YAKUSOKU_SONG = register("music.game.yakusoku");
    public static final DeferredHolder<SoundEvent, SoundEvent> DEEPER_SONG = register("music.game.deeper");
    public static final DeferredHolder<SoundEvent, SoundEvent> ELD_UNKNOWN_SONG = register("music.game.eld_unknown");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENDLESS_SONG = register("music.game.endless");

    public static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(Stancements.stancements(name)));
    }
}
