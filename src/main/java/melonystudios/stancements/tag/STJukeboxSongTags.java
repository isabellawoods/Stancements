package melonystudios.stancements.tag;

import melonystudios.stancements.Stancements;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.JukeboxSong;

public class STJukeboxSongTags {
    // Stancements tags
    public static final TagKey<JukeboxSong> AMBIENT_MUSIC = stancements("ambient_music");
    /// Jukebox songs in this tag are considered "ambient" (recorded from the client's {@link net.minecraft.client.sounds.MusicManager MusicManager}).
    /// If any song in this tag start playing, the music in `MusicManager` is stopped.
    /// @see melonystudios.stancements.client.option.STClientOptions#MUSIC_DISCS_BLOCK_AMBIENT_MUSIC Music Discs Block Ambient Music option
    public static final TagKey<JukeboxSong> CANCELS_AMBIENT_MUSIC = stancements("cancels_ambient_music");
    /// Jukebox songs in this tag, when applied to a music disc, cannot be copied by the music recorder.
    ///
    /// When attempting to copy, the message "This music disc cannot be copied by design" will be shown.
    public static final TagKey<JukeboxSong> COPYING_PROHIBITED = stancements("copying_prohibited");

    // Minecraft tags
    public static final TagKey<JukeboxSong> VOLUME_ALPHA = minecraft("album/volume_alpha");
    public static final TagKey<JukeboxSong> VOLUME_BETA = minecraft("album/volume_beta");
    public static final TagKey<JukeboxSong> NETHER_UPDATE = minecraft("album/nether_update");
    public static final TagKey<JukeboxSong> CAVES_AND_CLIFFS = minecraft("album/caves_and_cliffs");
    public static final TagKey<JukeboxSong> THE_WILD_UPDATE = minecraft("album/the_wild_update");
    public static final TagKey<JukeboxSong> TRAILS_AND_TALES = minecraft("album/trails_and_tales");
    public static final TagKey<JukeboxSong> TRICKY_TRIALS = minecraft("album/tricky_trials");

    public static final TagKey<JukeboxSong> UPDATE_AQUATIC = minecraft("update_aquatic");

    public static TagKey<JukeboxSong> stancements(String name) {
        return TagKey.create(Registries.JUKEBOX_SONG, Stancements.stancements(name));
    }

    public static TagKey<JukeboxSong> minecraft(String name) {
        return TagKey.create(Registries.JUKEBOX_SONG, ResourceLocation.withDefaultNamespace(name));
    }
}
