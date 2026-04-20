package melonystudios.stancements.compat.jade;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.TagMatcherType;
import melonystudios.stancements.item.custom.TaggingRailItem;
import melonystudios.stancements.misc.attachment.STCapabilities;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;

import java.util.List;
import java.util.Optional;

public class MinecartTagsProvider implements StreamServerDataProvider<EntityAccessor, MinecartTagsProvider.TagData> {
    public static final MinecartTagsProvider INSTANCE = new MinecartTagsProvider();
    public static final Identifier ID = Stancements.stancements("minecart_tags");

    @Override
    public boolean shouldRequestData(EntityAccessor accessor) {
        var tags = accessor.getEntity().getCapability(STCapabilities.MINECART_TAGS);
        return accessor.getEntity() instanceof AbstractMinecart && tags != null;
    }

    @Override
    @Nullable
    public TagData streamData(EntityAccessor accessor) {
        var tags = accessor.getEntity().getCapability(STCapabilities.MINECART_TAGS);
        assert tags != null;
        return new TagData(tags.tagColors());
    }

    @Override
    @NotNull
    public StreamCodec<RegistryFriendlyByteBuf, TagData> streamCodec() {
        return TagData.STREAM_CODEC;
    }

    @Override
    @NotNull
    public Identifier getUid() {
        return ID;
    }

    public static class Client implements IEntityComponentProvider {
        public static final Client INSTANCE = new Client();

        @Override
        public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
            Optional<TagData> data = MinecartTagsProvider.INSTANCE.decodeFromData(accessor);
            if (data.isEmpty() || data.get().colors().isEmpty()) return;

            tooltip.add(Component.translatable("tooltip.stancements.tags", TaggingRailItem.prettyPrintTagColors(TagMatcherType.ALL, data.get().colors(), false)));
        }

        @Override
        @NotNull
        public Identifier getUid() {
            return ID;
        }
    }

    public record TagData(List<DyeColor> colors) {
        public static final StreamCodec<RegistryFriendlyByteBuf, TagData> STREAM_CODEC = StreamCodec.composite(
                DyeColor.STREAM_CODEC.apply(ByteBufCodecs.list()),
                TagData::colors,
                TagData::new
        );
    }
}
