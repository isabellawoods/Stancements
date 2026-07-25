package melonystudios.stancements.misc.attachment;

import melonystudios.stancements.item.STItems;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class MinecartTags implements ValueIOSerializable {
    private List<DyeColor> tagColors;
    private final AbstractMinecart minecart;

    public MinecartTags(IAttachmentHolder holder, List<DyeColor> tagColors) {
        if (!(holder instanceof AbstractMinecart minecart)) {
            throw new IllegalArgumentException("Holder entity must be an instance of AbstractMinecart");
        }
        this.minecart = minecart;
        this.tagColors = tagColors;
    }

    public AbstractMinecart minecart() {
        return this.minecart;
    }

    public List<DyeColor> tagColors() {
        return this.tagColors;
    }

    public boolean addTag(DyeColor color) {
        if (!this.tagColors().contains(color)) return this.tagColors.add(color);
        return false;
    }

    public void clearTags() {
        this.tagColors.clear();
    }

    public static Item getTagForColor(DyeColor color) {
        return switch (color) {
            case LIGHT_GRAY -> STItems.LIGHT_GRAY_TAG.get();
            case GRAY -> STItems.GRAY_TAG.get();
            case BLACK -> STItems.BLACK_TAG.get();
            case BROWN -> STItems.BROWN_TAG.get();
            case RED -> STItems.RED_TAG.get();
            case ORANGE -> STItems.ORANGE_TAG.get();
            case YELLOW -> STItems.YELLOW_TAG.get();
            case LIME -> STItems.LIME_TAG.get();
            case GREEN -> STItems.GREEN_TAG.get();
            case CYAN -> STItems.CYAN_TAG.get();
            case LIGHT_BLUE -> STItems.LIGHT_BLUE_TAG.get();
            case BLUE -> STItems.BLUE_TAG.get();
            case PURPLE -> STItems.PURPLE_TAG.get();
            case MAGENTA -> STItems.MAGENTA_TAG.get();
            case PINK -> STItems.PINK_TAG.get();
            default -> STItems.WHITE_TAG.get();
        };
    }

    @Override
    public void serialize(ValueOutput output) {
        if (!this.tagColors().isEmpty()) output.store("colors", DyeColor.CODEC.listOf(), this.tagColors());
    }

    @Override
    public void deserialize(ValueInput input) {
        input.read("colors", DyeColor.CODEC.listOf()).ifPresent(colors -> this.tagColors = colors);
    }

    public static class TagsSyncHandler implements AttachmentSyncHandler<MinecartTags> {
        public static final TagsSyncHandler INSTANCE = new TagsSyncHandler();

        @Override
        public void write(RegistryFriendlyByteBuf buffer, MinecartTags tags, boolean initialSync) {
            DyeColor.STREAM_CODEC.apply(ByteBufCodecs.list()).encode(buffer, tags.tagColors());
        }

        @Override
        @Nullable
        public MinecartTags read(IAttachmentHolder holder, RegistryFriendlyByteBuf buffer, @Nullable MinecartTags previousValue) {
            return new MinecartTags(holder, DyeColor.STREAM_CODEC.apply(ByteBufCodecs.list()).decode(buffer));
        }
    }
}
