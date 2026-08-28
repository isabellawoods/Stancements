package melonystudios.stancements.component.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.misc.recording.Track;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record MusicData(Optional<ResourceLocation> id, boolean copied) {
    public static final Codec<MusicData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("id").forGetter(MusicData::id),
            Codec.BOOL.optionalFieldOf("copied", false).forGetter(MusicData::copied)
    ).apply(instance, MusicData::new));
    public static final StreamCodec<ByteBuf, MusicData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), MusicData::id,
            ByteBufCodecs.BOOL, MusicData::copied,
            MusicData::new
    );

    public static boolean isCopied(ItemStack stack) {
        return stack.getOrDefault(STDataComponents.MUSIC_DATA, data()).copied();
    }

    public static MusicData of(Track track, boolean copied) {
        return new MusicData(Optional.of(track.identifier()), copied);
    }

    public static MusicData data() {
        return new MusicData(Optional.empty(), false);
    }

    public MusicData withTrack(Track track) {
        return new MusicData(Optional.of(track.identifier()), this.copied());
    }

    public MusicData markAsCopy(boolean copied) {
        return new MusicData(this.id(), copied);
    }

    @Override
    @NotNull
    public String toString() {
        if (this.id().isPresent()) {
            return String.format("MusicData[id=%s, copied=%s]", this.id().get(), this.copied());
        } else {
            return String.format("MusicData[copied=%s]", this.copied());
        }
    }
}
