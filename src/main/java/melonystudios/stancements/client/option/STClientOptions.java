package melonystudios.stancements.client.option;

import net.neoforged.neoforge.common.ModConfigSpec;

public class STClientOptions {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Music recorder
    public static final ModConfigSpec.BooleanValue MUSIC_DISCS_BLOCK_AMBIENT_MUSIC = BUILDER.comment("Whether recorded discs of ambient music should block the game's default music when playing from a jukebox.", "Blocking music discs are controlled by the '#stancements:cancels_ambient_music' jukebox song tag.").translation("option.stancements.music_discs_block_ambient_music.jukebox").define("block.musicDiscsBlockAmbientMusic.jukebox", true);
    public static final ModConfigSpec.BooleanValue MUSIC_DISCS_BLOCK_AMBIENT_MUSIC_SC = BUILDER.comment("Whether recorded discs of ambient music should block the game's default music when playing from a Jukebox Upgrade from any Sophisticated Core-based container.", "Blocking music discs are controlled by the '#stancements:cancels_ambient_music' jukebox song tag.").translation("option.stancements.music_discs_block_ambient_music.sophisticated").define("block.musicDiscsBlockAmbientMusic.sophisticated", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
