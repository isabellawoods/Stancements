package melonystudios.stancements.misc.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.misc.recording.RecordingSource;
import melonystudios.stancements.misc.recording.Track;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class RecordSongTrigger extends SimpleCriterionTrigger<RecordSongTrigger.TriggerInstance> {
    @Override
    @NotNull
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(Track track, @Nullable RecordingSource source, boolean copying, ServerPlayer player) {
        this.trigger(track, source, copying, List.of(), player);
    }

    public void trigger(Track track, @Nullable RecordingSource source, boolean copying, List<Track> excluded, ServerPlayer player) {
        this.trigger(player, instance -> instance.matches(track, source, copying, excluded));
    }

    public record TriggerInstance(Optional<Track> track, Optional<RecordingSource> source, boolean copying, List<Track> excluded, Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Track.CODEC.optionalFieldOf("track").forGetter(TriggerInstance::track),
                RecordingSource.CODEC.optionalFieldOf("source").forGetter(TriggerInstance::source),
                Codec.BOOL.optionalFieldOf("copying", false).forGetter(TriggerInstance::copying),
                Track.LIST_CODEC.optionalFieldOf("excluded", List.of()).forGetter(TriggerInstance::excluded),
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
        ).apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> recordedSong(Track track, RecordingSource source, boolean copying, ContextAwarePredicate player) {
            return STCriteriaTriggers.RECORD_SONG.createCriterion(new TriggerInstance(track.optionally(), Optional.of(source), copying, List.of(), Optional.of(player)));
        }

        public static Criterion<TriggerInstance> recordedSong(Track track, RecordingSource source, boolean copying) {
            return STCriteriaTriggers.RECORD_SONG.createCriterion(new TriggerInstance(track.optionally(), Optional.of(source), copying, List.of(), Optional.empty()));
        }

        public static Criterion<TriggerInstance> recordedSong(Track track, RecordingSource source) {
            return STCriteriaTriggers.RECORD_SONG.createCriterion(new TriggerInstance(track.optionally(), Optional.of(source), false, List.of(), Optional.empty()));
        }

        public static Criterion<TriggerInstance> recordedSong(Track track, boolean copying) {
            return STCriteriaTriggers.RECORD_SONG.createCriterion(new TriggerInstance(track.optionally(), Optional.empty(), copying, List.of(), Optional.empty()));
        }

        public static Criterion<TriggerInstance> recordedSong(Track track) {
            return STCriteriaTriggers.RECORD_SONG.createCriterion(new TriggerInstance(track.optionally(), Optional.empty(), false, List.of(), Optional.empty()));
        }

        public static Criterion<TriggerInstance> recordedAnySong(ContextAwarePredicate player, RecordingSource source, boolean copying) {
            return STCriteriaTriggers.RECORD_SONG.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(source), copying, List.of(), Optional.of(player)));
        }

        public static Criterion<TriggerInstance> recordedAnySong(RecordingSource source, boolean copying, List<Track> excluded) {
            return STCriteriaTriggers.RECORD_SONG.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(source), copying, excluded, Optional.empty()));
        }

        public boolean matches(Track track, @Nullable RecordingSource source, boolean copying, List<Track> excluded) {
            return (this.track.isEmpty() || this.track.get().equals(track)) && (this.source().isEmpty() || this.source().get().equals(source)) && this.copying == copying && this.excluded.stream().noneMatch(excluded::contains);
        }
    }
}
