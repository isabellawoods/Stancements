package melonystudios.stancements.misc.recording;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import melonystudios.stancements.block.custom.MusicRecorderBlock;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.tag.STJukeboxSongTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.JukeboxSong;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/// An abstraction of a music ID specialized to handle *Stancements*' recording pipeline.
/// @param identifier A {@linkplain ResourceLocation resource location} of this track, such as `minecraft:music/game/mice_on_venus` or `minecraft:precipice`.
/// @param resolved Whether this track's ID represents an existing jukebox song. If not, it will pass through {@link RecordedDiscItem#getJukeboxSongLocation}.
public record Track(ResourceLocation identifier, boolean resolved) {
    public static final Codec<Track> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(Track::identifier),
            Codec.BOOL.optionalFieldOf("resolved", false).forGetter(Track::resolved)
    ).apply(instance, Track::new));
    public static final Codec<Track> CODEC = Codec.either(DIRECT_CODEC, ResourceLocation.CODEC).xmap(Track::fromEither, Track::asEither);
    public static final Codec<List<Track>> LIST_CODEC = Codec.either(CODEC, CODEC.listOf()).xmap(
            either -> either.map(List::of, List::copyOf),
            list -> list.size() == 1 ? Either.left(list.getFirst()) : Either.right(list)
    );
    public static final StreamCodec<ByteBuf, Track> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            Track::identifier,
            ByteBufCodecs.BOOL,
            Track::resolved,
            Track::new
    );

    /// An abstraction of a music ID specialized to handle *Stancements*' recording pipeline.
    /// @param trackID A {@linkplain ResourceLocation resource location} of this track, such as `minecraft:music/game/mice_on_venus`.
    public Track(ResourceLocation trackID) {
        this(trackID, false);
    }

    public static Track forJukeboxSong(Holder<JukeboxSong> song) {
        return new Track(ResourceLocation.parse(song.getRegisteredName()), true);
    }

    public Optional<Track> optionally() {
        return Optional.of(this);
    }

    public List<Track> listOf() {
        return List.of(this);
    }

    public Either<Track, ResourceLocation> asEither() {
        return this.resolved() ? Either.left(this) : Either.right(this.identifier());
    }

    public static Track fromEither(Either<Track, ResourceLocation> either) {
        return either.map(track -> new Track(track.identifier(), track.resolved()), Track::new);
    }

    public static boolean requiredForCompletion(Holder<JukeboxSong> song) {
        return !song.is(STJukeboxSongTags.NOT_REQUIRED_FOR_COMPLETION);
    }

    public ResourceLocation jukeboxSongID() {
        return this.resolved() ? this.identifier() : RecordedDiscItem.getJukeboxSongLocation(this.identifier());
    }

    public MutableComponent displayName(Registry<JukeboxSong> jukeboxSongs) {
        return MusicRecorderBlock.getSongName(jukeboxSongs, this.identifier());
    }

    public Optional<JukeboxSong> unwrap(Registry<JukeboxSong> jukeboxSongs) {
        return jukeboxSongs.getOptional(this.jukeboxSongID());
    }

    public Optional<? extends Holder<JukeboxSong>> unwrap(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Registries.JUKEBOX_SONG).get(ResourceKey.create(Registries.JUKEBOX_SONG, this.jukeboxSongID()));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other != null && this.getClass() == other.getClass()) {
            Track track = (Track) other;
            return this.identifier().equals(track.identifier()) && this.resolved() == track.resolved();
        } else {
            return false;
        }
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("Track[id=%s, resolved=%s]", this.identifier(), this.resolved());
    }
}
