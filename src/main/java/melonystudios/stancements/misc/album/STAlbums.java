package melonystudios.stancements.misc.album;

import melonystudios.stancements.misc.STRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

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
                .coverArt(ResourceLocation.withDefaultNamespace("album/volume_alpha"))
                .addAuthors("C418")
                .addLink(LinkTreeSources.OWN_WEBSITE, "c418", "https://c418.org/albums/minecraft-volume-alpha/")
                .addLink(LinkTreeSources.BANDCAMP, "https://c418.bandcamp.com/album/minecraft-volume-alpha")
                .addLink(LinkTreeSources.SOUNDCLOUD, "https://soundcloud.com/c418/sets/minecraft-volume-alpha-4")
                .addLink(LinkTreeSources.YOUTUBE, "https://www.youtube.com/playlist?list=OLAK5uy_lNojWuj8PKhbqVFZBpSkUpCYcrJbYt8s4")
                .addLink(LinkTreeSources.YOUTUBE_MUSIC, "https://music.youtube.com/playlist?list=OLAK5uy_nhQ2EVQRbH-uWJbaesYXRQGZMzinN0qqg")
                .addLink(LinkTreeSources.SPOTIFY, "https://open.spotify.com/album/3Gt7rOjcZQoHCfnKl5AkK7")
                .addLink(LinkTreeSources.DEEZER, "https://deezer.com/album/9236757")
                .addLink(LinkTreeSources.APPLE_MUSIC, "https://music.apple.com/album/minecraft-volume-alpha/1867885113")
                .categorizeAs("ambient")
                // Tracklist
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/key"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/subwoofer_lullaby"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/living_mice"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/haggstrom"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/minecraft"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/oxygene"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/mice_on_venus"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/dry_hands"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/wet_hands"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/clark"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("13"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/sweden"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("cat"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/danny"))
                .build()
        );

        context.register(VOLUME_BETA, Album.album()
                .name(Component.translatable("album.minecraft.volume_beta.title").withStyle(ChatFormatting.ITALIC))
                .description(Component.translatable("album.minecraft.volume_beta.description").withColor(0xA0A0A0))
                .coverArt(ResourceLocation.withDefaultNamespace("album/volume_beta"))
                .addAuthors("C418")
                .addLink(LinkTreeSources.OWN_WEBSITE, "c418", "https://c418.org/albums/minecraft-volume-beta/")
                .addLink(LinkTreeSources.BANDCAMP, "https://c418.bandcamp.com/album/minecraft-volume-beta")
                .addLink(LinkTreeSources.SOUNDCLOUD, "https://soundcloud.com/c418/sets/minecraft-volume-beta-2")
                .addLink(LinkTreeSources.YOUTUBE, "https://www.youtube.com/playlist?list=OLAK5uy_mSH30PsabObEwBb9LClNBzLhjGYZDSfec")
                .addLink(LinkTreeSources.YOUTUBE_MUSIC, "https://music.youtube.com/playlist?list=OLAK5uy_nnY0s7ogC6wEI85M_C9NrMLLv6lWOQxqY")
                .addLink(LinkTreeSources.SPOTIFY, "https://open.spotify.com/album/7CYDRyFCKtAYJBSpfovLyX")
                .addLink(LinkTreeSources.DEEZER, "https://deezer.com/album/9236763")
                .addLink(LinkTreeSources.APPLE_MUSIC, "https://music.apple.com/album/minecraft-volume-beta/1867890087")
                .categorizeAs("ambient")
                // Tracklist
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/end/alpha"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/nether/dead_voxel"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/creative/blind_spots"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/nether/concrete_halls"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/creative/biome_fest"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/creative/haunt_muskie"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/nether/warmth"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/creative/aria_math"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/nether/ballad_of_the_cats"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/creative/taswell"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/creative/dreiton"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/end/the_end"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("chirp"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("wait"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("mellohi"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("stal"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("strad"))
                // "11" is not part of volume beta -- "Eleven" is though
                .resolvedListing(ResourceLocation.withDefaultNamespace("eleven"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("ward"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("mall"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("blocks"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("far"))
                .build()
        );

        context.register(NETHER_UPDATE, Album.album()
                .name(Component.translatable("album.minecraft.nether_update.title").withStyle(ChatFormatting.ITALIC))
                .description(Component.translatable("album.minecraft.nether_update.description").withColor(0xA0A0A0))
                .coverArt(ResourceLocation.withDefaultNamespace("album/nether_update"))
                .addAuthors("Lena Raine")
                .addLink(LinkTreeSources.OWN_WEBSITE, "lena_raine", "https://lena.fyi/projects/2020/4/11/minecraft-nether-update-2020")
                .categorizeAs("ambient", "dubstep")
                // Tracklist
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/nether/crimson_forest/chrysopoeia"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/nether/nether_wastes/rubedo"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/nether/soulsand_valley/so_below"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("pigstep"))
                .build()
        );

        context.register(CAVES_AND_CLIFFS, Album.album()
                .name(Component.translatable("album.minecraft.caves_and_cliffs.title").withStyle(ChatFormatting.ITALIC))
                .description(Component.translatable("album.minecraft.caves_and_cliffs.description").withColor(0xA0A0A0))
                .coverArt(ResourceLocation.withDefaultNamespace("album/caves_and_cliffs"))
                .addAuthors("Lena Raine", "Kumi Tanioka")
                .addLink(LinkTreeSources.OWN_WEBSITE, "lena_raine", "https://lena.fyi/projects/2021/12/31/minecraft-caves-amp-cliffs-2021")
                .addLink(LinkTreeSources.TWITTER, "kumi_tanioka", "https://twitter.com/tanikumi")
                .categorizeAs("ambient", "electronic")
                // Tracklist
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/stand_tall"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/left_to_bloom"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/ancestry"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/wending"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/infinite_amethyst"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/one_more_day"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("otherside"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/floating_dream"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/comforting_memories"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/an_ordinary_day"))
                .build()
        );

        context.register(THE_WILD_UPDATE, Album.album()
                .name(Component.translatable("album.minecraft.the_wild_update.title").withStyle(ChatFormatting.ITALIC))
                .description(Component.translatable("album.minecraft.the_wild_update.description").withColor(0xA0A0A0))
                .coverArt(ResourceLocation.withDefaultNamespace("album/the_wild_update"))
                .addAuthors("Lena Raine", "Samuel Åberg")
                .addLink(LinkTreeSources.OWN_WEBSITE, "lena_raine", "https://lena.fyi/projects/2022/12/6/minecraft-the-wild-update-2022")
                .addLink(LinkTreeSources.TWITTER, "samuel_aberg", "https://twitter.com/slamp0000")
                .categorizeAs("ambient")
                // Tracklist
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/swamp/firebugs"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/swamp/aerie"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/swamp/labyrinthine"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("5"))
                .build()
        );

        context.register(TRAILS_AND_TALES, Album.album()
                .name(Component.translatable("album.minecraft.trails_and_tales.title").withStyle(ChatFormatting.ITALIC))
                .description(Component.translatable("album.minecraft.trails_and_tales.description").withColor(0xA0A0A0))
                .coverArt(ResourceLocation.withDefaultNamespace("album/trails_and_tales"))
                .addAuthors("Aaron Cherof")
                .addLink(LinkTreeSources.BANDCAMP, "https://cherof.bandcamp.com/music")
                .categorizeAs("ambient", "electronic")
                // Tracklist
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/echo_in_the_wind"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/a_familiar_room"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/bromeliad"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/crescent_dunes"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("relic"))
                .build()
        );

        context.register(TRICKY_TRIALS, Album.album()
                .name(Component.translatable("album.minecraft.tricky_trials.title").withStyle(ChatFormatting.ITALIC))
                .description(Component.translatable("album.minecraft.tricky_trials.description").withColor(0xA0A0A0))
                .coverArt(ResourceLocation.withDefaultNamespace("album/tricky_trials"))
                .addAuthors("Aaron Cherof", "Kumi Tanioka", "Lena Raine")
                .addLink(LinkTreeSources.BANDCAMP, "https://cherof.bandcamp.com/music")
                .addLink(LinkTreeSources.TWITTER, "kumi_tanioka", "https://twitter.com/tanikumi")
                .addLink(LinkTreeSources.OWN_WEBSITE, "lena_raine", "https://lena.fyi/")
                .categorizeAs("ambient", "electronic", "rock")
                // Tracklist
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/featherfall"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/watcher"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/puzzlebox"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/komorebi"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/pokopoko"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/yakusoku"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/deeper"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/eld_unknown"))
                .resolvedListingMusicPrefix(ResourceLocation.withDefaultNamespace("game/endless"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("creator"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("creator_music_box"))
                .resolvedListing(ResourceLocation.withDefaultNamespace("precipice"))
                .build()
        );
    }

    private static ResourceKey<Album> minecraft(String name) {
        return ResourceKey.create(STRegistries.ALBUM, ResourceLocation.withDefaultNamespace(name));
    }
}
