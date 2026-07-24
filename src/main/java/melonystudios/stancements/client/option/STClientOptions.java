package melonystudios.stancements.client.option;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class STClientOptions {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Music recorder
    public static final ModConfigSpec.BooleanValue MUSIC_DISCS_BLOCK_AMBIENT_MUSIC = BUILDER.comment("Whether recorded discs of ambient music should block the game's default music when playing from a jukebox.", "Blocking music discs are controlled by the '#stancements:cancels_ambient_music' jukebox song tag.").translation("option.stancements.music_discs_block_ambient_music.jukebox").define("block.musicDiscsBlockAmbientMusic.jukebox", true);
    public static final ModConfigSpec.BooleanValue MUSIC_DISCS_BLOCK_AMBIENT_MUSIC_SC = BUILDER.comment("Whether recorded discs of ambient music should block the game's default music when playing from a Jukebox Upgrade from any Sophisticated Core-based container.", "Blocking music discs are controlled by the '#stancements:cancels_ambient_music' jukebox song tag.").translation("option.stancements.music_discs_block_ambient_music.sophisticated").define("block.musicDiscsBlockAmbientMusic.sophisticated", true);

    // Inventory recorders
    public static final ModConfigSpec.ConfigValue<List<? extends String>> SCREEN_MUSIC_BLACKLIST = BUILDER
            .comment("Which screens that, when open, should not send a recording packet due to them playing music of their own.", "The classes listed here must extend 'net.minecraft.client.gui.screens.Screen'.")
            .translation("option.stancements.screen_music_blacklist")
            .defineListAllowEmpty(
                    "item.screenMusicBlacklist",
                    Lists.newArrayList("net.minecraft.client.gui.screens.WinScreen"),
                    () -> "",
                    object -> {
                        if (!(object instanceof String string)) return false;
                        try {
                            Class<?> clazz = Class.forName(string);
                            return Screen.class.isAssignableFrom(clazz);
                        } catch (ClassNotFoundException ignored) {
                            return false;
                        }
                    });

    public static final ModConfigSpec SPEC = BUILDER.build();
}
