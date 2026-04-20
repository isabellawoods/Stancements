package melonystudios.stancements.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.JukeboxSong;

public class STJukeboxSongTags {
    // Minecraft tags
    public static final TagKey<JukeboxSong> VOLUME_ALPHA = minecraft("album/volume_alpha");
    public static final TagKey<JukeboxSong> VOLUME_BETA = minecraft("album/volume_beta");
    public static final TagKey<JukeboxSong> NETHER_UPDATE = minecraft("album/nether_update");
    public static final TagKey<JukeboxSong> CAVES_AND_CLIFFS = minecraft("album/caves_and_cliffs");
    public static final TagKey<JukeboxSong> WILD_UPDATE = minecraft("album/wild_update");
    public static final TagKey<JukeboxSong> TRAILS_AND_TALES = minecraft("album/trails_and_tales");
    public static final TagKey<JukeboxSong> TRICKY_TRIALS = minecraft("album/tricky_trials");
    public static final TagKey<JukeboxSong> CHASE_THE_SKIES = minecraft("album/chase_the_skies");

    public static final TagKey<JukeboxSong> UPDATE_AQUATIC = minecraft("update_aquatic");

    public static TagKey<JukeboxSong> minecraft(String name) {
        return TagKey.create(Registries.JUKEBOX_SONG, Identifier.withDefaultNamespace(name));
    }
}
