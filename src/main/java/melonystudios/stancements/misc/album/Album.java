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

/// An **album** defines a list of tracks that an [**album block**][melonystudios.stancements.blockentity.custom.AlbumBlockEntity] will store, and provides information about the album, its songwriter(s) and its description.
///
/// Albums can be defined using JSON files in a data pack at the path `data/<namespace>/stancements/album/`, and can have tags defined at the path `data/<namespace>/tags/stancements/album/`.
///
/// @author isabellawoods on [**Informational Mod Features**](https://github.com/isabellawoods/Informational-Mod-Features/blob/main/Stancements/Docs/Album.md)
/// @param name A [text component][Component] for the name of this album.
/// @param description A [text component][Component] for the album's description.
/// @param coverArt *(optional)* An [identifier][ResourceLocation] — Points to a texture to use in the album block's side panel.
/// @param trackListing A list of [track lists][Track#LIST_CODEC] — Every top-level entry represents one song within the album, and each entry within these entries are aliases for the original song.<br>For example, if the album looks for the jukebox song `minecraft:game/ebb`, but a recorded disc has `minecraft:music/game/ebb` stored within its data, that would also count for the album.<br>**One or more track aliases.**
/// @param songwriters *(optional)* A list of strings — Each one is the name of a person that participated in the production of this album.
/// @param linkTree *(optional)* A map of translation keys (string) to a valid URL (string) — This will be shown in the album block's interface.<br>**Example:** `"link_tree": {"link_tree.bandcamp": "https://c418.bandcamp.com"}`
/// @param tags *(optional)* A list of strings — Each one is a tag that categorizes this album. *Stancements* provides translations for many tags by default in its [translation files](https://github.com/isabellawoods/Stancements/blob/neoforge-1.21.1/src/main/resources/assets/stancements/lang/en_us.json#L328-L359).<br>The translation key for genres is `musical_tag.<tag>`.
public record Album(Component name, Component description, Optional<ResourceLocation> coverArt, TrackList trackListing, List<String> songwriters, Map<String, String> linkTree, List<String> tags) {
    private static final NumberFormat FORMATTER = Util.make(NumberFormat.getInstance(), formatter -> formatter.setMinimumIntegerDigits(2));
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Codec<Album> DIRECT_CODEC = RecordCodecBuilder.<Album>create(instance -> instance.group(
            ComponentSerialization.CODEC.fieldOf("name").forGetter(Album::name),
            ComponentSerialization.CODEC.fieldOf("description").forGetter(Album::description),
            ResourceLocation.CODEC.optionalFieldOf("cover_art").forGetter(Album::coverArt),
            TrackList.CODEC.fieldOf("track_listing").forGetter(Album::trackListing),
            Codec.STRING.listOf().optionalFieldOf("songwriters", List.of()).forGetter(Album::songwriters),
            Codec.unboundedMap(Codec.STRING, Codec.STRING).optionalFieldOf("link_tree", Map.of()).forGetter(Album::linkTree),
            Codec.STRING.listOf().optionalFieldOf("tags", List.of()).forGetter(Album::tags)
    ).apply(instance, Album::new)).validate(album -> {
        if (album.trackListing().listings().isEmpty()) return DataResult.error(() -> "Album must have at least one listing in its tracklist");

        if (STDebuggingFlags.LOGGING) {
            StringBuilder builder = new StringBuilder("Registered album contains " + album.trackListing().listings().size() + " listings:");
            for (int i = 0; i < album.trackListing().listings().size(); i++) {
                List<Track> tracks = album.trackListing().listings().get(i);
                if (!tracks.isEmpty()) builder.append("\n ").append(FORMATTER.format(i + 1)).append(" // ").append(tracks.getFirst().identifier());
                List<Track> trackVariants = new ArrayList<>(tracks);
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

    /// Creates an instance of the **album definition builder**.
    public static Builder album() {
        return new Builder();
    }

    /// @param listings A list of [track lists][Track#LIST_CODEC] — Each top level entry (a `List<Track>`) is an entry in the [album UI][melonystudios.stancements.container.custom.AlbumMenu], and each track within one list represents the same song but in different formats.<br>This will use a matcher for both `music_data.id` and `jukebox_playable`, preferring the latter.<br><pre>{@code
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
    public record TrackList(List<List<Track>> listings) {
        public static final Codec<TrackList> CODEC = Track.LIST_CODEC.listOf().xmap(TrackList::new, TrackList::listings);
    }

    public static class Builder {
        private Component name;
        private Component description = Component.empty();
        private ResourceLocation coverArt = null;
        private final List<List<Track>> listings = new ArrayList<>();
        private final List<String> authors = new ArrayList<>();
        private final Map<String, String> linkTree = new HashMap<>();
        private final List<String> tags = new ArrayList<>();

        /// Creates a new instance of the **album definition builder**.
        private Builder() {}

        /// Sets the name of this album, displayed in the album UI and in music disc tooltips.
        /// @param name A *text component* for the name.
        public Builder name(Component name) {
            this.name = name;
            return this;
        }

        /// Sets the description of this album, displayed in the album UI.
        /// @param description A *text component* for the description.
        public Builder description(Component description) {
            this.description = description;
            return this;
        }

        /// Defines a texture to be used as the cover art for this album.
        /// @param texturePath The path to the cover art, optionally omitting the `textures/` prefix and `.png` suffix.<br>These textures are usually located in `textures/gui/album_cover_art/`.
        public Builder coverArt(ResourceLocation texturePath) {
            this.coverArt = ReAPI.toTexturePath(texturePath);
            return this;
        }

        /// Adds a [track][Track] to this album's track listing.
        /// @param track The track to add.
        public Builder listing(Track track) {
            this.getListingWithEntry(track).add(track);
            return this;
        }

        /// Adds a [track][Track] with variants to this album's track listing.
        /// @param track The main track to add.
        /// @param variants Variants of the main track.
        public Builder listingWithVariants(Track track, Track... variants) {
            var listing = this.getListingWithEntry(track);
            listing.add(track);
            listing.addAll(List.of(variants));
            return this;
        }

        /// Creates a resolved [track][Track] off of an [identifier][ResourceLocation] and adds it to this album's track listing.
        /// @param trackID The identifier of this track.
        public Builder resolvedListing(ResourceLocation trackID) {
            Track track = new Track(trackID, true);
            this.getListingWithEntry(track).add(track);
            return this;
        }

        /// Creates a resolved [track][Track] off of an [identifier][ResourceLocation], adds an unresolved variant prefixed with `music/` to it,
        /// and adds it to this album's track listing.
        /// @param trackID The main identifier of these tracks.
        public Builder resolvedListingMusicPrefix(ResourceLocation trackID) {
            Track track = new Track(trackID, true);
            Track track1 = new Track(trackID.withPrefix("music/"), false);
            this.getListingWithEntry(track).addAll(List.of(track, track1));
            return this;
        }

        /// Creates a resolved [track][Track] with variants off of an [identifier][ResourceLocation] and adds it to this album's track listing.
        /// @param trackID The identifier of the main track.
        /// @param trackIDs Identifiers of the variants to add to the main track.
        public Builder resolvedListing(ResourceLocation trackID, ResourceLocation... trackIDs) {
            Track track = new Track(trackID, true);
            this.getListingWithEntry(track).add(track);

            for (ResourceLocation identifier : trackIDs) {
                Track track1 = new Track(identifier, true);
                this.getListingWithEntry(track1).add(track1);
            }
            return this;
        }

        /// Adds a list of [track][Track]s to this album's track listing
        /// @param tracks The tracks to add.
        public Builder listings(List<Track> tracks) {
            this.getListingWithEntry(tracks).addAll(tracks);
            return this;
        }

        /// Marks the people listed here as the authors of this album.
        /// @param authors A list of authors.
        public Builder addAuthors(String... authors) {
            this.authors.addAll(List.of(authors));
            return this;
        }

        /// Adds an URL (with its translation key) to this album's link tree.
        /// @param translationKey The translation key used in the album UI.
        /// @param url The URL opened when the line on the album is clicked.
        public Builder addLink(String translationKey, String url) {
            this.linkTree.put(translationKey, url);
            return this;
        }

        /// Adds an URL (with its translation key) to this album's link tree.
        /// @param source A translation key to be used in the album UI.
        /// @param url The URL opened when the line on the album is clicked.
        public Builder addLink(LinkTreeSources source, String url) {
            this.linkTree.put(source.toString(), url);
            return this;
        }

        /// Adds an author-specific URL (with its translation key) to this album's link tree.
        /// @param source The base translation key to be used in the album UI.
        /// @param songwriter The author that the URL refers to. This can be used, for example, to link to two authors Bandcamp pages.
        /// @param url The URL opened when the line on the album is clicked.
        public Builder addLink(LinkTreeSources source, String songwriter, String url) {
            this.linkTree.put(source + "." + songwriter, url);
            return this;
        }

        /// Tags this album with the provided tag(s). The translation key for a tag is `musical_tag.<tag>`.
        /// @param tag The tag(s) to add to this album.
        public Builder tagAs(String... tag) {
            this.tags.addAll(List.of(tag));
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

        /// Builds this builder into an album definition.
        public Album build() {
            if (this.name == null) throw new IllegalStateException("Album must have a defined 'name' field, even if its empty");
            if (this.listings.isEmpty()) throw new IllegalStateException("Album must have at least one listing in its tracklist");

            return new Album(this.name, this.description, Optional.ofNullable(this.coverArt), new TrackList(List.copyOf(this.listings)), List.copyOf(this.authors), Map.copyOf(this.linkTree), List.copyOf(this.tags));
        }
    }
}
