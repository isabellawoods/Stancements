package melonystudios.stancements.component.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.misc.recording.Track;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record TrackStorage(List<Track> tracklist, int capacity) implements TooltipComponent {
    public static final Codec<TrackStorage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Track.CODEC.listOf().fieldOf("tracklist").forGetter(TrackStorage::tracklist),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("capacity").forGetter(TrackStorage::capacity)
    ).apply(instance, TrackStorage::new));
    public static final StreamCodec<ByteBuf, TrackStorage> STREAM_CODEC = StreamCodec.composite(
            Track.STREAM_CODEC.apply(ByteBufCodecs.list()),
            TrackStorage::tracklist,
            ByteBufCodecs.VAR_INT,
            TrackStorage::capacity,
            TrackStorage::new
    );
    public static final TrackStorage DEFAULT_15 = new TrackStorage(List.of(), 15);
    public static final TrackStorage DEFAULT_30 = new TrackStorage(List.of(), 30);
    public static final int TEXT_COLOR = 0xFDF4FE;

    public static ItemStack store(ItemStack stack, Track track) {
        TrackStorage storage = stack.get(STDataComponents.TRACK_STORAGE);
        if (storage == null) {
            stack.set(STDataComponents.TRACK_STORAGE, new TrackStorage(List.of(track), 15));
        } else if (storage.tracklist().size() < storage.capacity()) {
            List<Track> tracklist = new ArrayList<>(storage.tracklist());
            tracklist.add(track);
            stack.set(STDataComponents.TRACK_STORAGE, new TrackStorage(List.copyOf(tracklist), storage.capacity()));
        }
        return stack;
    }

    public static boolean isFull(ItemStack storageStack) {
        TrackStorage storage = storageStack.getOrDefault(STDataComponents.TRACK_STORAGE, DEFAULT_15);
        return storage.tracklist().size() >= storage.capacity();
    }

    public boolean isFull() {
        return this.tracklist().size() >= this.capacity();
    }

    public List<MutableComponent> getLinesToDisplay(Level level, boolean showAll, int maxLinesBeforeCollapsing) {
        var jukeboxSongs = level.registryAccess().registryOrThrow(Registries.JUKEBOX_SONG);

        if (showAll || this.tracklist().size() <= maxLinesBeforeCollapsing) {
            return this.tracklist().stream()
                    .map(track -> track.displayName(jukeboxSongs))
                    .toList();
        } else {
            List<MutableComponent> lines = new ArrayList<>();
            for (int i = 0; i < maxLinesBeforeCollapsing; i++) {
                lines.add(this.tracklist().get(i).displayName(jukeboxSongs));
            }

            lines.add(Component.translatable(
                    "tooltip.stancements.inventory_recorder.hold_shift",
                    Component.literal(Integer.toString(this.tracklist().size() - maxLinesBeforeCollapsing)).withStyle(ChatFormatting.UNDERLINE)
            ).withStyle(ChatFormatting.BOLD));
            return lines;
        }
    }

    public int getLineCountForHeight(boolean showAll, int maxLinesBeforeCollapsing) {
        if (showAll || this.tracklist().size() <= maxLinesBeforeCollapsing) {
            return this.tracklist().size();
        } else {
            return maxLinesBeforeCollapsing + 1;
        }
    }

    public Component getFullnessText() {
        return Component.translatable("tooltip.stancements.inventory_recorder.capacity", this.tracklist().size(), this.capacity());
    }

    public ResourceLocation backgroundSprite() {
        String suffix = "";
        if (this.isFull()) {
            suffix = "_full";
        } else if (this.tracklist().size() >= Math.round(this.capacity() * 0.75)) {
            suffix = "_near_full";
        }
        return Stancements.stancements("container/inventory_recorder/storage_capacity_background" + suffix);
    }

    public ResourceLocation fillSprite() {
        String suffix = this.isFull() ? "_full" : "";
        if (this.isFull()) {
            suffix = "_full";
        } else if (this.tracklist().size() >= Math.round(this.capacity() * 0.75)) {
            suffix = "_near_full";
        }
        return Stancements.stancements("container/inventory_recorder/storage_capacity_fill" + suffix);
    }
}
