package melonystudios.stancements.misc.album;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.reutilities.api.ReAPI;
import melonystudios.stancements.misc.STRegistries;
import melonystudios.stancements.misc.recording.Track;
import melonystudios.stancements.util.STDebuggingFlags;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.text.NumberFormat;
import java.util.*;

// -- TRACK LISTING DEFINITION --
// contender: List<Listing>
// Listing :: either -> Track, List<Track>
//
// single track :: "minecraft:game/minecraft" -> Track
// optional track :: "id" -> Track, "required" -> boolean
// list of listings :: [{single, optional}] - all inside a single entry count as the same
// |--> "minecraft:game/minecraft", "minecraft:music/game/minecraft" and "minecraft:sounds/music/game/minecraft.ogg" are all "C418 - Minecraft" in the eyes of the album definition

// -- GENRE(s) -- (16 max.)
// electronic / ambient
// electronic / bass
// electronic / chill-out
// [x]: should author names be translatable?
// [-]: "songwriters" should be a list of Person (from fabric) so i can add an in-game link tree
// [x]: description should support a link for the source
// todo: `stancements:person` registry to make a proper linktree for album/song songwriters
public record Album(Component name, Component description, Optional<ResourceLocation> coverArt, TrackList trackListing, List<String> songwriters, Map<String, String> linkTree, List<String> genres) {
    private static final NumberFormat FORMATTER = Util.make(NumberFormat.getInstance(), formatter -> formatter.setMinimumIntegerDigits(2));
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Codec<Album> DIRECT_CODEC = RecordCodecBuilder.<Album>create(instance -> instance.group(
            ComponentSerialization.CODEC.fieldOf("name").forGetter(Album::name),
            ComponentSerialization.CODEC.fieldOf("description").forGetter(Album::description),
            ResourceLocation.CODEC.optionalFieldOf("cover_art").forGetter(Album::coverArt),
            TrackList.CODEC.fieldOf("track_listing").forGetter(Album::trackListing),
            Codec.STRING.listOf().optionalFieldOf("songwriters", List.of()).forGetter(Album::songwriters),
            Codec.unboundedMap(Codec.STRING, Codec.STRING).optionalFieldOf("link_tree", Map.of()).forGetter(Album::linkTree),
            Codec.STRING.listOf().optionalFieldOf("genre", List.of()).forGetter(Album::genres)
    ).apply(instance, Album::new)).validate(album -> {
        if (album.trackListing().listings().isEmpty()) return DataResult.error(() -> "Album must have at least one listing in its tracklist");

        if (STDebuggingFlags.LOGGING) {
            StringBuilder builder = new StringBuilder("Registered album contains " + album.trackListing().listings().size() + " listings:");
            for (int i = 0; i < album.trackListing().listings().size(); i++) {
                List<Track> tracks = album.trackListing().listings().get(i);
                if (!tracks.isEmpty()) builder.append("\n ").append(FORMATTER.format(i + 1)).append(" // ").append(tracks.getFirst().identifier());
                var trackVariants = new ArrayList<>(tracks);
                trackVariants.removeFirst();
                if (!trackVariants.isEmpty()) {
                    builder.append(" (with variant(s):");

                    for (Track track : trackVariants) builder.append(" \"").append(track.identifier()).append("\"");
                    builder.append(")");
                }
            }
            LOGGER.debug(builder.toString());
        }

        return DataResult.success(album);
    });
    public static final Codec<Holder<Album>> CODEC = RegistryFixedCodec.create(STRegistries.ALBUM);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Album>> STREAM_CODEC = ByteBufCodecs.holderRegistry(STRegistries.ALBUM);

    public static Builder album() {
        return new Builder();
    }

    /// @param listings <pre>{@code
    /// // simple, single
    /// "minecraft:music/game/sweden",
    /// // composed, single
    /// {
    ///   "id": "minecraft:game/sweden",
    ///   "resolved": true
    /// },
    /// // simple, multiple
    /// [
    ///   "minecraft:music/game/sweden",
    ///   "minecraft:game/sweden"
    /// ],
    /// // composed, multiple
    /// [
    ///   {
    ///     "id": "minecraft:music/game/sweden",
    ///     "resolved": false
    ///   },
    ///   {
    ///     "id": "minecraft:game/sweden",
    ///     "resolved": true
    ///   }
    /// ]
    /// }</pre>
    /// @apiNote each top level entry (a `List<Track>`) is an entry in the album UI, each track within one list represent the same song but in different formats (will use a matcher for both `music_data.id` and `jukebox_playable`, preferring the latter)
    public static record TrackList(List<List<Track>> listings) {
        public static final Codec<TrackList> CODEC = Track.LIST_CODEC.listOf().xmap(
                TrackList::new,
                TrackList::listings
        );
    }

    public static record SourceBackedComponent(Component text, Optional<Component> source) {
        // this is sorta how mod menu does it: has text at the top, and a "Sources" / "Discord" / "Ko-fi" link below,
        // but it will include links for Bandcamp, SoundCloud, own webside, YouTube, etc.
        public static final Codec<SourceBackedComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ComponentSerialization.CODEC.fieldOf("text").forGetter(SourceBackedComponent::text),
                ComponentSerialization.CODEC.optionalFieldOf("source").forGetter(SourceBackedComponent::source)
        ).apply(instance, SourceBackedComponent::new));
    }

    public static class Builder {
        private Component name;
        private Component description = Component.empty();
        private ResourceLocation coverArt = null;
        private final List<List<Track>> listings = new ArrayList<>();
        private final List<String> authors = new ArrayList<>();
        private final Map<String, String> linkTree = new HashMap<>();
        private final List<String> genres = new ArrayList<>();

        private Builder() {}

        public Builder name(Component name) {
            this.name = name;
            return this;
        }

        public Builder description(Component description) {
            this.description = description;
            return this;
        }

        public Builder coverArt(ResourceLocation texturePath) {
            this.coverArt = ReAPI.toTexturePath(texturePath);
            return this;
        }

        public Builder listing(Track track) {
            this.getListingWithEntry(track).add(track);
            return this;
        }

        public Builder listingWithVariants(Track track, Track... variants) {
            var listing = this.getListingWithEntry(track);
            listing.add(track);
            listing.addAll(List.of(variants));
            return this;
        }

        public Builder resolvedListing(ResourceLocation trackID) {
            Track track = new Track(trackID, true);
            this.getListingWithEntry(track).add(track);
            return this;
        }

        public Builder resolvedListingMusicPrefix(ResourceLocation trackID) {
            Track track = new Track(trackID, true);
            Track track1 = new Track(trackID.withPrefix("music/"), false);
            this.getListingWithEntry(track).addAll(List.of(track, track1));
            return this;
        }

        public Builder resolvedListing(ResourceLocation trackID, ResourceLocation... trackIDs) {
            Track track = new Track(trackID, true);
            this.getListingWithEntry(track).add(track);

            for (ResourceLocation identifier : trackIDs) {
                Track track1 = new Track(identifier, true);
                this.getListingWithEntry(track1).add(track1);
            }
            return this;
        }

        public Builder listings(List<Track> tracks) {
            this.getListingWithEntry(tracks).addAll(tracks);
            return this;
        }

        public Builder addAuthors(String... authors) {
            this.authors.addAll(List.of(authors));
            return this;
        }

        public Builder addLink(String translationKey, String url) {
            this.linkTree.put(translationKey, url);
            return this;
        }

        public Builder addLink(LinkTreeSources source, String url) {
            this.linkTree.put("link_tree." + source, url);
            return this;
        }

        public Builder addLink(LinkTreeSources source, String songwriter, String url) {
            this.linkTree.put("link_tree." + source + "." + songwriter, url);
            return this;
        }

        public Builder categorizeAs(String... genre) {
            this.genres.addAll(List.of(genre));
            return this;
        }

        private List<Track> getListingWithEntry(Track track) {
            for (List<Track> listing : this.listings) {
                if (listing.contains(track)) return listing;
            }

            List<Track> list = new ArrayList<>();
            this.listings.add(list);
            return list;
        }

        private List<Track> getListingWithEntry(List<Track> tracks) {
            for (List<Track> listing : this.listings) {
                if (listing.equals(tracks)) return listing;
            }

            List<Track> list = new ArrayList<>();
            this.listings.add(list);
            return list;
        }

        public Album build() {
            if (this.name == null) throw new IllegalStateException("Album must have a defined 'name' component");
            if (this.listings.isEmpty()) throw new IllegalStateException("Album must have at least one listing in its tracklist");

            return new Album(this.name, this.description, Optional.ofNullable(this.coverArt), new TrackList(List.copyOf(this.listings)), List.copyOf(this.authors), Map.copyOf(this.linkTree), List.copyOf(this.genres));
        }
    }
}
