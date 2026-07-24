package melonystudios.stancements.component.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import melonystudios.stancements.component.STDataComponents;
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

    public static MusicData unknownSong(ResourceLocation musicID, boolean copied) {
        return new MusicData(Optional.of(musicID), copied);
    }

    public static MusicData copiedDisc() {
        return new MusicData(Optional.empty(), true);
    }

    public static MusicData data() {
        return new MusicData(Optional.empty(), false);
    }

    public static boolean isCopied(ItemStack stack) {
        return stack.has(STDataComponents.MUSIC_DATA) && stack.get(STDataComponents.MUSIC_DATA).copied();
    }

    public MusicData withID(ResourceLocation id) {
        return new MusicData(Optional.of(id), this.copied());
    }

    public MusicData markCopied(boolean copied) {
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
