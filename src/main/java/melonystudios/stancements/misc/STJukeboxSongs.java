package melonystudios.stancements.misc;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.sound.STSounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public class STJukeboxSongs {
    // Volume Alpha
    public static final ResourceKey<JukeboxSong> MINECRAFT = register("game/minecraft");
    public static final ResourceKey<JukeboxSong> CLARK = register("game/clark");
    public static final ResourceKey<JukeboxSong> SWEDEN = register("game/sweden");
    public static final ResourceKey<JukeboxSong> SUBWOOFER_LULLABY = register("game/subwoofer_lullaby");
    public static final ResourceKey<JukeboxSong> LIVING_MICE = register("game/living_mice");
    public static final ResourceKey<JukeboxSong> HAGGSTROM = register("game/haggstrom");
    public static final ResourceKey<JukeboxSong> DANNY = register("game/danny");
    public static final ResourceKey<JukeboxSong> KEY = register("game/key");
    public static final ResourceKey<JukeboxSong> OXYGENE = register("game/oxygene");
    public static final ResourceKey<JukeboxSong> DRY_HANDS = register("game/dry_hands");
    public static final ResourceKey<JukeboxSong> WET_HANDS = register("game/wet_hands");
    public static final ResourceKey<JukeboxSong> MICE_ON_VENUS = register("game/mice_on_venus");

    // Volume Beta
    public static final ResourceKey<JukeboxSong> BIOME_FEST = register("game/creative/biome_fest");
    public static final ResourceKey<JukeboxSong> BLIND_SPOTS = register("game/creative/blind_spots");
    public static final ResourceKey<JukeboxSong> HAUNT_MUSKIE = register("game/creative/haunt_muskie");
    public static final ResourceKey<JukeboxSong> ARIA_MATH = register("game/creative/aria_math");
    public static final ResourceKey<JukeboxSong> DREITON = register("game/creative/dreiton");
    public static final ResourceKey<JukeboxSong> TASWELL = register("game/creative/taswell");
    public static final ResourceKey<JukeboxSong> CONCRETE_HALLS = register("game/nether/concrete_halls");
    public static final ResourceKey<JukeboxSong> DEAD_VOXEL = register("game/nether/dead_voxel");
    public static final ResourceKey<JukeboxSong> WARMTH = register("game/nether/warmth");
    public static final ResourceKey<JukeboxSong> BALLAD_OF_THE_CATS = register("game/nether/ballad_of_the_cats");
    public static final ResourceKey<JukeboxSong> BOSS = register("game/end/boss");
    public static final ResourceKey<JukeboxSong> THE_END = register("game/end/the_end");

    // Underwater Singles (1.13)
    public static final ResourceKey<JukeboxSong> SHUNIJI = register("game/water/shuniji");
    public static final ResourceKey<JukeboxSong> DRAGON_FISH = register("game/water/dragon_fish");
    public static final ResourceKey<JukeboxSong> AXOLOTL = register("game/water/axolotl");

    // Nether Update (1.16)
    public static final ResourceKey<JukeboxSong> RUBEDO = register("game/nether/nether_wastes/rubedo");
    public static final ResourceKey<JukeboxSong> CHRYSOPOEIA = register("game/nether/crimson_forest/chrysopoeia");
    public static final ResourceKey<JukeboxSong> SO_BELOW = register("game/nether/soulsand_valley/so_below");

    // Caves & Cliffs (1.17 / 1.18)
    public static final ResourceKey<JukeboxSong> STAND_TALL = register("game/stand_tall");
    public static final ResourceKey<JukeboxSong> LEFT_TO_BLOOM = register("game/left_to_bloom");
    public static final ResourceKey<JukeboxSong> ANCESTRY = register("game/ancestry");
    public static final ResourceKey<JukeboxSong> WENDING = register("game/wending");
    public static final ResourceKey<JukeboxSong> INFINITE_AMETHYST = register("game/infinite_amethyst");
    public static final ResourceKey<JukeboxSong> ONE_MORE_DAY = register("game/one_more_day");
    public static final ResourceKey<JukeboxSong> FLOATING_DREAM = register("game/floating_dream");
    public static final ResourceKey<JukeboxSong> COMFORTING_MEMORIES = register("game/comforting_memories");
    public static final ResourceKey<JukeboxSong> AN_ORDINARY_DAY = register("game/an_ordinary_day");

    // The Wild Update (1.19)
    public static final ResourceKey<JukeboxSong> FIREBUGS = register("game/swamp/firebugs");
    public static final ResourceKey<JukeboxSong> AERIE = register("game/swamp/aerie");
    public static final ResourceKey<JukeboxSong> LABYRINTHINE = register("game/swamp/labyrinthine");

    // Trails & Tales (1.20)
    public static final ResourceKey<JukeboxSong> ECHO_IN_THE_WIND = register("game/echo_in_the_wind");
    public static final ResourceKey<JukeboxSong> A_FAMILIAR_ROOM = register("game/a_familiar_room");
    public static final ResourceKey<JukeboxSong> BROMELIAD = register("game/bromeliad");
    public static final ResourceKey<JukeboxSong> CRESCENT_DUNES = register("game/crescent_dunes");

    // Tricky Trials (1.21)
    public static final ResourceKey<JukeboxSong> FEATHERFALL = register("game/featherfall");
    public static final ResourceKey<JukeboxSong> WATCHER = register("game/watcher");
    public static final ResourceKey<JukeboxSong> PUZZLEBOX = register("game/puzzlebox");
    public static final ResourceKey<JukeboxSong> KOMOREBI = register("game/komorebi");
    public static final ResourceKey<JukeboxSong> POKOPOKO = register("game/pokopoko");
    public static final ResourceKey<JukeboxSong> YAKUSOKU = register("game/yakusoku");
    public static final ResourceKey<JukeboxSong> DEEPER = register("game/deeper");
    public static final ResourceKey<JukeboxSong> ELD_UNKNOWN = register("game/eld_unknown");
    public static final ResourceKey<JukeboxSong> ENDLESS = register("game/endless");

    // Chase the Skies (1.21.6)
    public static final ResourceKey<JukeboxSong> LILYPAD = register("game/lilypad");
    public static final ResourceKey<JukeboxSong> BELOW_AND_ABOVE = register("game/below_and_above");
    public static final ResourceKey<JukeboxSong> OS_PIANO = register("game/os_piano");
    public static final ResourceKey<JukeboxSong> BROKEN_CLOCKS = register("game/broken_clocks");
    public static final ResourceKey<JukeboxSong> FIREFLIES = register("game/fireflies");

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        // Volume Alpha
        register(context, MINECRAFT, STSounds.MINECRAFT_SONG.getKey(), 254);
        register(context, CLARK, STSounds.CLARK_SONG.getKey(), 191);
        register(context, SWEDEN, STSounds.SWEDEN_SONG.getKey(), 215);
        register(context, SUBWOOFER_LULLABY, STSounds.SUBWOOFER_LULLABY_SONG.getKey(), 208);
        register(context, LIVING_MICE, STSounds.LIVING_MICE_SONG.getKey(), 177);
        register(context, HAGGSTROM, STSounds.HAGGSTROM_SONG.getKey(), 204);
        register(context, DANNY, STSounds.DANNY_SONG.getKey(), 254);
        register(context, KEY, STSounds.KEY_SONG.getKey(), 65);
        register(context, OXYGENE, STSounds.OXYGENE_SONG.getKey(), 65);
        register(context, DRY_HANDS, STSounds.DRY_HANDS_SONG.getKey(), 68);
        register(context, WET_HANDS, STSounds.WET_HANDS_SONG.getKey(), 90);
        register(context, MICE_ON_VENUS, STSounds.MICE_ON_VENUS_SONG.getKey(), 281);

        // Volume Beta
        register(context, BIOME_FEST, STSounds.BIOME_FEST_SONG.getKey(), 378);
        register(context, BLIND_SPOTS, STSounds.BLIND_SPOTS_SONG.getKey(), 332);
        register(context, HAUNT_MUSKIE, STSounds.HAUNT_MUSKIE_SONG.getKey(), 361);
        register(context, ARIA_MATH, STSounds.ARIA_MATH_SONG.getKey(), 310);
        register(context, DREITON, STSounds.DREITON_SONG.getKey(), 497);
        register(context, TASWELL, STSounds.TASWELL_SONG.getKey(), 515);
        register(context, CONCRETE_HALLS, STSounds.CONCRETE_HALLS_SONG.getKey(), 254);
        register(context, DEAD_VOXEL, STSounds.DEAD_VOXEL_SONG.getKey(), 296);
        register(context, WARMTH, STSounds.WARMTH_SONG.getKey(), 239);
        register(context, BALLAD_OF_THE_CATS, STSounds.BALLAD_OF_THE_CATS_SONG.getKey(), 275);
        register(context, BOSS, STSounds.BOSS_SONG.getKey(), 344);
        register(context, THE_END, STSounds.THE_END_SONG.getKey(), 904);

        // Underwater Singles (1.13)
        register(context, SHUNIJI, STSounds.SHUNIJI_SONG.getKey(), 244);
        register(context, DRAGON_FISH, STSounds.DRAGON_FISH_SONG.getKey(), 372);
        register(context, AXOLOTL, STSounds.AXOLOTL_SONG.getKey(), 303);

        // Nether Update (1.16)
        register(context, RUBEDO, STSounds.RUBEDO_SONG.getKey(), 312);
        register(context, CHRYSOPOEIA, STSounds.CHRYSOPOEIA_SONG.getKey(), 303);
        register(context, SO_BELOW, STSounds.SO_BELOW_SONG.getKey(), 319);

        // Caves & Cliffs (1.17 / 1.18)
        register(context, STAND_TALL, STSounds.STAND_TALL_SONG.getKey(), 308);
        register(context, LEFT_TO_BLOOM, STSounds.LEFT_TO_BLOOM_SONG.getKey(), 342);
        register(context, ANCESTRY, STSounds.ANCESTRY_SONG.getKey(), 343);
        register(context, WENDING, STSounds.WENDING_SONG.getKey(), 314);
        register(context, INFINITE_AMETHYST, STSounds.INFINITE_AMETHYST_SONG.getKey(), 271);
        register(context, ONE_MORE_DAY, STSounds.ONE_MORE_DAY_SONG.getKey(), 278);
        register(context, FLOATING_DREAM, STSounds.FLOATING_DREAM_SONG.getKey(), 205);
        register(context, COMFORTING_MEMORIES, STSounds.COMFORTING_MEMORIES_SONG.getKey(), 275);
        register(context, AN_ORDINARY_DAY, STSounds.AN_ORDINARY_DAY_SONG.getKey(), 331);

        // The Wild Update (1.19)
        register(context, FIREBUGS, STSounds.FIREBUGS_SONG.getKey(), 312);
        register(context, AERIE, STSounds.AERIE_SONG.getKey(), 236);
        register(context, LABYRINTHINE, STSounds.LABYRINTHINE_SONG.getKey(), 324);

        // Trails & Tales (1.20)
        register(context, ECHO_IN_THE_WIND, STSounds.ECHO_IN_THE_WIND_SONG.getKey(), 296);
        register(context, A_FAMILIAR_ROOM, STSounds.A_FAMILIAR_ROOM_SONG.getKey(), 241);
        register(context, BROMELIAD, STSounds.BROMELIAD_SONG.getKey(), 312);
        register(context, CRESCENT_DUNES, STSounds.CRESCENT_DUNES_SONG.getKey(), 248);

        // Tricky Trials (1.21)
        register(context, FEATHERFALL, STSounds.FEATHERFALL_SONG.getKey(), 344);
        register(context, WATCHER, STSounds.WATCHER_SONG.getKey(), 332);
        register(context, PUZZLEBOX, STSounds.PUZZLEBOX_SONG.getKey(), 299);
        register(context, KOMOREBI, STSounds.KOMOREBI_SONG.getKey(), 287);
        register(context, POKOPOKO, STSounds.POKOPOKO_SONG.getKey(), 304);
        register(context, YAKUSOKU, STSounds.YAKUSOKU_SONG.getKey(), 271);
        register(context, DEEPER, STSounds.DEEPER_SONG.getKey(), 303);
        register(context, ELD_UNKNOWN, STSounds.ELD_UNKNOWN_SONG.getKey(), 296);
        register(context, ENDLESS, STSounds.ENDLESS_SONG.getKey(), 402);

        // Chase the Skies (1.21.6)
        register(context, LILYPAD, STSounds.LILYPAD_SONG.getKey(), 235);
        register(context, BELOW_AND_ABOVE, STSounds.BELOW_AND_ABOVE_SONG.getKey(), 212);
        register(context, OS_PIANO, STSounds.OS_PIANO_SONG.getKey(), 275);
        register(context, BROKEN_CLOCKS, STSounds.BROKEN_CLOCKS_SONG.getKey(), 213);
        register(context, FIREFLIES, STSounds.FIREFLIES_SONG.getKey(), 155);
    }

    public static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, ResourceKey<SoundEvent> sound, int lengthInSeconds) {
        var soundEvents = context.lookup(Registries.SOUND_EVENT);
        context.register(key, new JukeboxSong(soundEvents.getOrThrow(sound), Component.translatable("music.minecraft." + key.location().getPath().replace('/', '.')), lengthInSeconds, 0));
    }

    public static ResourceKey<JukeboxSong> register(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, Stancements.stancements(name));
    }
}
