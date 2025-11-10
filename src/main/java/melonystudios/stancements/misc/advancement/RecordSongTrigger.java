package melonystudios.stancements.misc.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class RecordSongTrigger extends SimpleCriterionTrigger<RecordSongTrigger.TriggerInstance> {
    @Override
    @NotNull
    public Codec<RecordSongTrigger.TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ResourceLocation musicID, boolean copyingSong, ServerPlayer player) {
        this.trigger(player, instance -> instance.matches(musicID, copyingSong));
    }

    public record TriggerInstance(Optional<ResourceLocation> musicID, boolean copyingSong, Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.optionalFieldOf("music_id").forGetter(TriggerInstance::musicID),
                Codec.BOOL.optionalFieldOf("copying_song", false).forGetter(TriggerInstance::copyingSong),
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
        ).apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> recordedSong(ResourceLocation musicID, boolean copyingSong, ContextAwarePredicate player) {
            return STCriteriaTriggers.RECORD_SONG.createCriterion(new TriggerInstance(Optional.of(musicID), copyingSong, Optional.of(player)));
        }

        public static Criterion<TriggerInstance> recordedSong(ResourceLocation musicID, boolean copyingSong) {
            return STCriteriaTriggers.RECORD_SONG.createCriterion(new TriggerInstance(Optional.of(musicID), copyingSong, Optional.empty()));
        }

        public static Criterion<TriggerInstance> recordedSong(ResourceLocation musicID) {
            return STCriteriaTriggers.RECORD_SONG.createCriterion(new TriggerInstance(Optional.of(musicID), false, Optional.empty()));
        }

        public static Criterion<TriggerInstance> recordedAnySong(ContextAwarePredicate player, boolean copyingSong) {
            return STCriteriaTriggers.RECORD_SONG.createCriterion(new TriggerInstance(Optional.empty(), copyingSong, Optional.of(player)));
        }

        public static Criterion<TriggerInstance> recordedAnySong(boolean copyingSong) {
            return STCriteriaTriggers.RECORD_SONG.createCriterion(new TriggerInstance(Optional.empty(), copyingSong, Optional.empty()));
        }

        public boolean matches(ResourceLocation musicID, boolean copyingSong) {
            return (this.musicID.isEmpty() || this.musicID.get().equals(musicID)) && this.copyingSong == copyingSong;
        }
    }
}
