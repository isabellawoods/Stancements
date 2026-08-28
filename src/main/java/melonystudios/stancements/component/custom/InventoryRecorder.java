package melonystudios.stancements.component.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.misc.advancement.STCriteriaTriggers;
import melonystudios.stancements.misc.recording.RecordingSource;
import melonystudios.stancements.misc.recording.Track;
import melonystudios.stancements.option.STCommonOptions;
import melonystudios.stancements.sound.STSounds;
import melonystudios.stancements.util.STCodecs;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static melonystudios.stancements.component.STDataComponents.INVENTORY_RECORDER;

public record InventoryRecorder(boolean active, Optional<Track> track, long recordingFinishTick, ItemStack item) implements TooltipProvider {
    public static final Codec<InventoryRecorder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("active", false).forGetter(InventoryRecorder::active),
            Track.CODEC.optionalFieldOf("track").forGetter(InventoryRecorder::track),
            STCodecs.longRange(0, Long.MAX_VALUE).optionalFieldOf("recording_finish_tick", 0L).forGetter(InventoryRecorder::recordingFinishTick),
            STCodecs.OPTIONAL_SINGLE_ITEM.fieldOf("item").forGetter(InventoryRecorder::item)
    ).apply(instance, InventoryRecorder::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, InventoryRecorder> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            InventoryRecorder::active,
            ByteBufCodecs.optional(Track.STREAM_CODEC),
            InventoryRecorder::track,
            ByteBufCodecs.VAR_LONG,
            InventoryRecorder::recordingFinishTick,
            ItemStack.OPTIONAL_STREAM_CODEC,
            InventoryRecorder::item,
            InventoryRecorder::new
    );
    public static final InventoryRecorder EMPTY = new InventoryRecorder(false, Optional.empty(), 0, ItemStack.EMPTY);

    public static void toggle(Player player, ItemStack stack) {
        InventoryRecorder recorder = stack.get(INVENTORY_RECORDER);
        if (recorder != null) {
            Mutable mutable = new Mutable(recorder).active(!recorder.active());
            if (!mutable.active) mutable.withTrack(null).finishesRecordingAt(0L);
            stack.set(INVENTORY_RECORDER, mutable.toImmutable());
            player.displayClientMessage(statusText(mutable.active, mutable.track != null), true);
            player.playSound(STSounds.INVENTORY_RECORDER_TOGGLE.get());
        }
    }

    public static void startRecording(Level level, ItemStack stack, Track track) {
        InventoryRecorder recorder = stack.get(INVENTORY_RECORDER);
        if (recorder == null || !recorder.active() || recorder.track().isPresent() || hasAlreadyRecorded(recorder.item(), track)) return;

        stack.set(INVENTORY_RECORDER, new Mutable(recorder).active(true).finishesRecordingAt(level.getGameTime() + STCommonOptions.DEFAULT_RECORDING_DURATION.get()).withTrack(track).toImmutable());
    }

    public static void tick(Level level, Entity entity, ItemStack stack) {
        InventoryRecorder recorder = stack.getOrDefault(INVENTORY_RECORDER, EMPTY);
        if (recorder.recordingFinishTick() <= 0) return;

        // only record if the time matches exactly and it's actually recording
        if (recorder.recordingFinishTick() == level.getGameTime() && recorder.active() && recorder.track().isPresent()) {
            stack.set(INVENTORY_RECORDER, new Mutable(recorder)
                    .withTrack(null)
                    .finishesRecordingAt(0L)
                    .insertTrackStorage(TrackStorage.store(recorder.item().copy(), recorder.track().get()))
                    .toImmutable()
            );

            if (!(entity instanceof ServerPlayer player)) return;
            STCriteriaTriggers.RECORD_SONG.trigger(recorder.track().get(), RecordingSource.INVENTORY_RECORDER, false, List.of(recorder.track().get()), player);

            TrackStorage storage = recorder.item().get(STDataComponents.TRACK_STORAGE);
            if (storage == null) return;

            if (storage.tracklist().size() + 1 >= storage.capacity()) {
                player.displayClientMessage(Component.translatable("tooltip.stancements.inventory_recorder.full", stack.getHoverName()).withStyle(ChatFormatting.RED), true);
                player.playSound(STSounds.INVENTORY_RECORDER_FULL.get());
            } else if (storage.tracklist().size() + 1 == Math.round(storage.capacity() * 0.75)) {
                player.displayClientMessage(Component.translatable("tooltip.stancements.inventory_recorder.near_full", stack.getHoverName()).withColor(State.PAUSED.color()), true);
                player.playSound(STSounds.INVENTORY_RECORDER_FULL.get());
            }

        // if above the recording time, simply clear the track data without saving
        } else if (recorder.recordingFinishTick() < level.getGameTime()) {
            stack.set(INVENTORY_RECORDER, new Mutable(recorder).withTrack(null).finishesRecordingAt(0L).toImmutable());
        }
    }

    public static boolean hasAlreadyRecorded(ItemStack storageStack, @Nullable Track track) {
        if (storageStack.isEmpty() || track == null) return false;

        TrackStorage contents = storageStack.get(STDataComponents.TRACK_STORAGE);
        return contents != null && contents.tracklist().contains(track);
    }

    public static boolean canRecord(ItemStack invRecorder, Track track) {
        InventoryRecorder recorder = invRecorder.getOrDefault(INVENTORY_RECORDER, EMPTY);
        if (recorder.item().isEmpty()) return false;

        return !TrackStorage.isFull(recorder.item()) && !hasAlreadyRecorded(recorder.item(), track);
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag flag) {
        if (this.track().isPresent() && context.level() != null) {
            long ticks = this.recordingFinishTick() - context.level().getGameTime();
            tooltip.accept(Component.translatable(
                    "tooltip.stancements.recording",
                    this.track().get().displayName(context.level().registryAccess().registryOrThrow(Registries.JUKEBOX_SONG)).withColor(TrackStorage.TEXT_COLOR),
                    formatLongTickDuration(ticks, context.tickRate())
            ).withStyle(ticks < 0 ? ChatFormatting.RED : ChatFormatting.GRAY));
        }

        tooltip.accept(statusText(this.active(), this.track().isPresent()));
    }

    public static Component statusText(boolean active, boolean recording) {
        State state = State.get(active, recording);
        return Component.translatable(
                "tooltip.stancements.inventory_recorder.status",
                Component.translatable("tooltip.stancements.inventory_recorder.status." + state).withColor(state.color())
        ).withStyle(ChatFormatting.GRAY);
    }

    public static String formatLongTickDuration(long ticks, float ticksPerSecond) {
        int seconds = Mth.floor((float) ticks / ticksPerSecond);
        int minutes = seconds / 60;
        seconds %= 60;
        int hours = minutes / 60;
        minutes %= 60;
        return hours > 0 ? String.format(Locale.ROOT, "%02d:%02d:%02d", hours, minutes, seconds) : String.format(Locale.ROOT, "%02d:%02d", minutes, seconds);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other != null && this.getClass() == other.getClass()) {
            InventoryRecorder recorder = (InventoryRecorder) other;
            return this.active() == recorder.active() && Objects.equals(this.recordingFinishTick(), recorder.recordingFinishTick()) && ItemStack.matches(this.item(), recorder.item());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 * Boolean.hashCode(this.active()) + this.track().hashCode() + Long.hashCode(this.recordingFinishTick()) + ItemStack.hashItemAndComponents(this.item());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("InventoryRecorder[active=%s, track=%s, recordingFinishTick=%s, item=%s]", this.active(), this.track(), this.recordingFinishTick(), this.item());
    }

    public enum State implements StringRepresentable {
        PAUSED(0, "paused", 0xEE7B32),
        IDLE(1, "idle", 0x48B7FF),
        RECORDING(2, "recording", 0x8DC617);

        public static final State[] VALUES = State.values();
        private final int id;
        private final String name;
        private final int color;

        State(int id, String name, int color) {
            this.id = id;
            this.name = name;
            this.color = color;
        }

        public static State get(boolean active, boolean recording) {
            return !active ? PAUSED : (recording ? RECORDING : IDLE);
        }

        public int id() {
            return this.id;
        }

        public int color() {
            return this.color;
        }

        @Override
        @NotNull
        public String getSerializedName() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.getSerializedName();
        }
    }

    public static class Mutable {
        private boolean active;
        private Track track;
        private long recordingFinishTick;
        private ItemStack item;

        public Mutable(InventoryRecorder recorder) {
            this.active = recorder.active();
            this.track = recorder.track().orElse(null);
            this.recordingFinishTick = recorder.recordingFinishTick();
            this.item = recorder.item();
        }

        public ItemStack item() {
            return this.item;
        }

        public Mutable active(boolean active) {
            this.active = active;
            return this;
        }

        public Mutable withTrack(@Nullable Track track) {
            this.track = track;
            return this;
        }

        public Mutable finishesRecordingAt(long gameTick) {
            this.recordingFinishTick = gameTick;
            return this;
        }

        public Mutable insertTrackStorage(ItemStack item) {
            this.item = item.copy();
            return this;
        }

        public InventoryRecorder toImmutable() {
            return new InventoryRecorder(this.active, Optional.ofNullable(this.track), this.recordingFinishTick, this.item);
        }
    }
}
