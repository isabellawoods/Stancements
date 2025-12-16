package melonystudios.stancements.misc.attachment;

import com.google.common.collect.Lists;
import melonystudios.stancements.item.STItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.List;

public class MinecartTags implements INBTSerializable<CompoundTag> {
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
            case WHITE -> STItems.WHITE_TAG.get();
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
        };
    }

    @Override
    @UnknownNullability
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        if (!this.tagColors.isEmpty()) {
            ListTag colors = new ListTag();
            for (DyeColor color : this.tagColors) colors.add(StringTag.valueOf(color.getName()));
            tag.put("colors", colors);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        if (tag.contains("colors", Tag.TAG_LIST)) {
            ListTag colors = tag.getList("colors", Tag.TAG_STRING);
            List<DyeColor> dyeColors = new ArrayList<>(16);

            for (Tag color : colors) {
                if (color instanceof StringTag string) {
                    DyeColor dyeColor = DyeColor.byName(string.getAsString(), null);
                    if (dyeColor != null) dyeColors.add(dyeColor);
                }
            }
            this.tagColors = dyeColors;
        }
    }

    public static class TagsSyncHandler implements AttachmentSyncHandler<MinecartTags> {
        public static final TagsSyncHandler INSTANCE = new TagsSyncHandler();

        @Override
        public void write(RegistryFriendlyByteBuf buffer, MinecartTags tags, boolean initialSync) {
            buffer.writeInt(tags.tagColors().size());
            for (DyeColor color : tags.tagColors()) buffer.writeInt(color.getId());
        }

        @Override
        @Nullable
        public MinecartTags read(IAttachmentHolder holder, RegistryFriendlyByteBuf buffer, @Nullable MinecartTags previousValue) {
            List<DyeColor> colors = Lists.newArrayList();
            int size = buffer.readInt();
            for (int i = 0; i < size; ++i) colors.add(DyeColor.byId(buffer.readInt()));
            return new MinecartTags(holder, colors);
        }
    }
}
