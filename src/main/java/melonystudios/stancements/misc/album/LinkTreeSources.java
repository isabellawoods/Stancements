package melonystudios.stancements.misc.album;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum LinkTreeSources implements StringRepresentable {
    OWN_WEBSITE("own_website"),
    // streaming platforms
    BANDCAMP("bandcamp"),
    SOUNDCLOUD("soundcloud"),
    YOUTUBE("youtube"),
    YOUTUBE_MUSIC("youtube_music"),
    SPOTIFY("spotify"),
    APPLE_MUSIC("apple_music"),
    DEEZER("deezer"),
    // donation websites
    KO_FI("ko_fi"),
    PATREON("patreon"),
    BUY_ME_A_COFFEE("buy_me_a_coffee"),
    // social media
    TWITTER("twitter"),
    BLUESKY("bluesky");

    private final String name;

    LinkTreeSources(String name) {
        this.name = name;
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "link_tree." + this.getSerializedName();
    }
}
