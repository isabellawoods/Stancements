package melonystudios.stancements.block;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import melonystudios.stancements.misc.attachment.MinecartTags;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.IntFunction;

public enum TagMatcherType implements StringRepresentable {
    ALL(0, "all", (tags, railDetects) -> new HashSet<>(tags.tagColors()).containsAll(railDetects)),
    ANY_OF(1, "any_of", (tags, railDetects) -> tags.tagColors().stream().anyMatch(railDetects::contains));

    public static final TagMatcherType[] VALUES = TagMatcherType.values();
    public static final IntFunction<TagMatcherType> BY_ID = ByIdMap.continuous(TagMatcherType::id, VALUES, ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final Codec<TagMatcherType> CODEC = StringRepresentable.fromEnum(() -> VALUES);
    public static final StreamCodec<ByteBuf, TagMatcherType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, TagMatcherType::id);
    private final int id;
    private final String name;
    private final BiPredicate<MinecartTags, List<DyeColor>> minecartMatcher;

    TagMatcherType(int id, String name, BiPredicate<MinecartTags, List<DyeColor>> minecartMatcher) {
        this.id = id;
        this.name = name;
        this.minecartMatcher = minecartMatcher;
    }

    public int id() {
        return this.id;
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return this.name;
    }

    public BiPredicate<MinecartTags, List<DyeColor>> matcher() {
        return this.minecartMatcher;
    }
}
