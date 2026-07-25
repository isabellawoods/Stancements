package melonystudios.stancements.client.option;

import net.neoforged.neoforge.common.ModConfigSpec;

public class STClientOptions {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Music recorder
    public static final ModConfigSpec.BooleanValue MUSIC_DISCS_BLOCK_AMBIENT_MUSIC = BUILDER.comment("Whether playing recorded ambient music should stop the current music from playing.", "Blocking music discs are controlled by the '#stancements:cancels_ambient_music' jukebox song tag.").translation("option.stancements.music_discs_block_ambient_music").define("block.musicDiscsBlockAmbientMusic", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
