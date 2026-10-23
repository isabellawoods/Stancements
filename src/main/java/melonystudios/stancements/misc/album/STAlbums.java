package melonystudios.stancements.misc.album;

import melonystudios.stancements.misc.STRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public class STAlbums {
    public static final ResourceKey<Album> VOLUME_ALPHA = minecraft("volume_alpha");
    public static final ResourceKey<Album> VOLUME_BETA = minecraft("volume_beta");
    public static final ResourceKey<Album> NETHER_UPDATE = minecraft("nether_update");
    public static final ResourceKey<Album> CAVES_AND_CLIFFS = minecraft("caves_and_cliffs");
    public static final ResourceKey<Album> THE_WILD_UPDATE = minecraft("the_wild_update");
    public static final ResourceKey<Album> TRAILS_AND_TALES = minecraft("trails_and_tales");
    public static final ResourceKey<Album> TRICKY_TRIALS = minecraft("tricky_trials");

    public static void bootstrap(BootstrapContext<Album> context) {
        context.register(VOLUME_ALPHA, Album.album()
                .name(Component.translatable("album.minecraft.volume_alpha.title").withStyle(ChatFormatting.ITALIC))
                .description(Component.translatable("album.minecraft.volume_alpha.description").withColor(0xA0A0A0))
                .coverArt(Identifier.withDefaultNamespace("album/volume_alpha"))
                .addAuthors("C418")
                .addLink(LinkTreeSources.OWN_WEBSITE, "c418", "https://c418.org/albums/minecraft-volume-alpha/")
                .addLink(LinkTreeSources.BANDCAMP, "https://c418.bandcamp.com/album/minecraft-volume-alpha")
                .addLink(LinkTreeSources.SOUNDCLOUD, "https://soundcloud.com/c418/sets/minecraft-volume-alpha-4")
                .addLink(LinkTreeSources.YOUTUBE, "https://www.youtube.com/playlist?list=OLAK5uy_lNojWuj8PKhbqVFZBpSkUpCYcrJbYt8s4")
                .addLink(LinkTreeSources.YOUTUBE_MUSIC, "https://music.youtube.com/playlist?list=OLAK5uy_nhQ2EVQRbH-uWJbaesYXRQGZMzinN0qqg")
                .addLink(LinkTreeSources.SPOTIFY, "https://open.spotify.com/album/3Gt7rOjcZQoHCfnKl5AkK7")
                .addLink(LinkTreeSources.DEEZER, "https://deezer.com/album/9236757")
                .addLink(LinkTreeSources.APPLE_MUSIC, "https://music.apple.com/album/minecraft-volume-alpha/1867885113")
                .tagAs("ambient")
                // Tracklist
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/key"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/subwoofer_lullaby"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/living_mice"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/haggstrom"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/minecraft"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/oxygene"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/mice_on_venus"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/dry_hands"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/wet_hands"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/clark"))
                .resolvedListing(Identifier.withDefaultNamespace("13"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/sweden"))
                .resolvedListing(Identifier.withDefaultNamespace("cat"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/danny"))
                .build()
        );

        context.register(VOLUME_BETA, Album.album()
                .name(Component.translatable("album.minecraft.volume_beta.title").withStyle(ChatFormatting.ITALIC))
                .description(Component.translatable("album.minecraft.volume_beta.description").withColor(0xA0A0A0))
                .coverArt(Identifier.withDefaultNamespace("album/volume_beta"))
                .addAuthors("C418")
                .addLink(LinkTreeSources.OWN_WEBSITE, "c418", "https://c418.org/albums/minecraft-volume-beta/")
                .addLink(LinkTreeSources.BANDCAMP, "https://c418.bandcamp.com/album/minecraft-volume-beta")
                .addLink(LinkTreeSources.SOUNDCLOUD, "https://soundcloud.com/c418/sets/minecraft-volume-beta-2")
                .addLink(LinkTreeSources.YOUTUBE, "https://www.youtube.com/playlist?list=OLAK5uy_mSH30PsabObEwBb9LClNBzLhjGYZDSfec")
                .addLink(LinkTreeSources.YOUTUBE_MUSIC, "https://music.youtube.com/playlist?list=OLAK5uy_nnY0s7ogC6wEI85M_C9NrMLLv6lWOQxqY")
                .addLink(LinkTreeSources.SPOTIFY, "https://open.spotify.com/album/7CYDRyFCKtAYJBSpfovLyX")
                .addLink(LinkTreeSources.DEEZER, "https://deezer.com/album/9236763")
                .addLink(LinkTreeSources.APPLE_MUSIC, "https://music.apple.com/album/minecraft-volume-beta/1867890087")
                .tagAs("ambient")
                // Tracklist
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/end/alpha"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/nether/dead_voxel"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/creative/blind_spots"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/nether/concrete_halls"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/creative/biome_fest"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/creative/haunt_muskie"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/nether/warmth"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/creative/aria_math"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/nether/ballad_of_the_cats"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/creative/taswell"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/creative/dreiton"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/end/the_end"))
                .resolvedListing(Identifier.withDefaultNamespace("chirp"))
                .resolvedListing(Identifier.withDefaultNamespace("wait"))
                .resolvedListing(Identifier.withDefaultNamespace("mellohi"))
                .resolvedListing(Identifier.withDefaultNamespace("stal"))
                .resolvedListing(Identifier.withDefaultNamespace("strad"))
                // "11" is not part of volume beta -- "Eleven" is though
                .resolvedListing(Identifier.withDefaultNamespace("eleven"))
                .resolvedListing(Identifier.withDefaultNamespace("ward"))
                .resolvedListing(Identifier.withDefaultNamespace("mall"))
                .resolvedListing(Identifier.withDefaultNamespace("blocks"))
                .resolvedListing(Identifier.withDefaultNamespace("far"))
                .build()
        );

        context.register(NETHER_UPDATE, Album.album()
                .name(Component.translatable("album.minecraft.nether_update.title").withStyle(ChatFormatting.ITALIC))
                .description(Component.translatable("album.minecraft.nether_update.description").withColor(0xA0A0A0))
                .coverArt(Identifier.withDefaultNamespace("album/nether_update"))
                .addAuthors("Lena Raine")
                .addLink(LinkTreeSources.OWN_WEBSITE, "lena_raine", "https://lena.fyi/projects/2020/4/11/minecraft-nether-update-2020")
                .tagAs("ambient", "dubstep")
                // Tracklist
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/nether/crimson_forest/chrysopoeia"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/nether/nether_wastes/rubedo"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/nether/soulsand_valley/so_below"))
                .resolvedListing(Identifier.withDefaultNamespace("pigstep"))
                .build()
        );

        context.register(CAVES_AND_CLIFFS, Album.album()
                .name(Component.translatable("album.minecraft.caves_and_cliffs.title").withStyle(ChatFormatting.ITALIC))
                .description(Component.translatable("album.minecraft.caves_and_cliffs.description").withColor(0xA0A0A0))
                .coverArt(Identifier.withDefaultNamespace("album/caves_and_cliffs"))
                .addAuthors("Lena Raine", "Kumi Tanioka")
                .addLink(LinkTreeSources.OWN_WEBSITE, "lena_raine", "https://lena.fyi/projects/2021/12/31/minecraft-caves-amp-cliffs-2021")
                .addLink(LinkTreeSources.TWITTER, "kumi_tanioka", "https://twitter.com/tanikumi")
                .tagAs("ambient", "electronic")
                // Tracklist
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/stand_tall"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/left_to_bloom"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/ancestry"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/wending"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/infinite_amethyst"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/one_more_day"))
                .resolvedListing(Identifier.withDefaultNamespace("otherside"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/floating_dream"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/comforting_memories"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/an_ordinary_day"))
                .build()
        );

        context.register(THE_WILD_UPDATE, Album.album()
                .name(Component.translatable("album.minecraft.the_wild_update.title").withStyle(ChatFormatting.ITALIC))
                .description(Component.translatable("album.minecraft.the_wild_update.description").withColor(0xA0A0A0))
                .coverArt(Identifier.withDefaultNamespace("album/the_wild_update"))
                .addAuthors("Lena Raine", "Samuel Åberg")
                .addLink(LinkTreeSources.OWN_WEBSITE, "lena_raine", "https://lena.fyi/projects/2022/12/6/minecraft-the-wild-update-2022")
                .addLink(LinkTreeSources.TWITTER, "samuel_aberg", "https://twitter.com/slamp0000")
                .tagAs("ambient")
                // Tracklist
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/swamp/firebugs"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/swamp/aerie"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/swamp/labyrinthine"))
                .resolvedListing(Identifier.withDefaultNamespace("5"))
                .build()
        );

        context.register(TRAILS_AND_TALES, Album.album()
                .name(Component.translatable("album.minecraft.trails_and_tales.title").withStyle(ChatFormatting.ITALIC))
                .description(Component.translatable("album.minecraft.trails_and_tales.description").withColor(0xA0A0A0))
                .coverArt(Identifier.withDefaultNamespace("album/trails_and_tales"))
                .addAuthors("Aaron Cherof")
                .addLink(LinkTreeSources.BANDCAMP, "https://cherof.bandcamp.com/music")
                .tagAs("ambient", "electronic")
                // Tracklist
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/echo_in_the_wind"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/a_familiar_room"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/bromeliad"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/crescent_dunes"))
                .resolvedListing(Identifier.withDefaultNamespace("relic"))
                .build()
        );

        context.register(TRICKY_TRIALS, Album.album()
                .name(Component.translatable("album.minecraft.tricky_trials.title").withStyle(ChatFormatting.ITALIC))
                .description(Component.translatable("album.minecraft.tricky_trials.description").withColor(0xA0A0A0))
                .coverArt(Identifier.withDefaultNamespace("album/tricky_trials"))
                .addAuthors("Aaron Cherof", "Kumi Tanioka", "Lena Raine")
                .addLink(LinkTreeSources.BANDCAMP, "https://cherof.bandcamp.com/music")
                .addLink(LinkTreeSources.TWITTER, "kumi_tanioka", "https://twitter.com/tanikumi")
                .addLink(LinkTreeSources.OWN_WEBSITE, "lena_raine", "https://lena.fyi/")
                .tagAs("ambient", "electronic", "rock")
                // Tracklist
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/featherfall"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/watcher"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/puzzlebox"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/komorebi"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/pokopoko"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/yakusoku"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/deeper"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/eld_unknown"))
                .resolvedListingMusicPrefix(Identifier.withDefaultNamespace("game/endless"))
                .resolvedListing(Identifier.withDefaultNamespace("creator"))
                .resolvedListing(Identifier.withDefaultNamespace("creator_music_box"))
                .resolvedListing(Identifier.withDefaultNamespace("precipice"))
                .build()
        );
    }

    private static ResourceKey<Album> minecraft(String name) {
        return ResourceKey.create(STRegistries.ALBUM, Identifier.withDefaultNamespace(name));
    }
}
