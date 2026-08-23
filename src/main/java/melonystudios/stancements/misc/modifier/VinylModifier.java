package melonystudios.stancements.misc.modifier;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.blockentity.BlockBasedMusicPlayer;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import melonystudios.stancements.misc.STRegistries;
import melonystudios.stancements.misc.loot.ModificationContextAware;
import melonystudios.stancements.misc.loot.STLootContextParamSets;
import melonystudios.stancements.misc.modifier.type.EjectAfterTicksModifier;
import melonystudios.stancements.misc.modifier.type.ModifyRecordableDiscModifier;
import melonystudios.stancements.tag.STVinylModifierTags;
import melonystudios.stancements.util.STDebuggingFlags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.*;

/// A **vinyl modifier** is a set of *modifier components* that are applied to recordable discs when starting and finishing a recording in the {@linkplain MusicRecorderBlockEntity music recorder}.
///
/// Vinyl modifiers can be defined using JSON files in a data pack at the path `data/<namespace>/stancemets/vinyl_modifier/`.
///
/// Modifiers in the {@linkplain STVinylModifierTags#PRIORITY_MODIFICATION `#stancements:priority_modification` tag} run before other modifiers.
/// @see ModifierComponentType
/// @author isabellawoods.
/// @param recordingText A **text component** shown when starting and/or finishing a recording, such as *"Finished recording!"*.
/// @param strategies Whether this modifier runs when starting or finishing a recording (or both). Can be one of/both `before` and/or `after`, but not neither.
/// @param targets A jukebox song ID, list of IDs or tag of which music discs this modifier acts on. Settings this to an empty list makes it run for **all** music discs, including unrecognized `musicID`s.
/// @param effects A list of {@linkplain ModifierComponentType modifier components} that are applied by this modifier.
/// @param modifiesWhenCopying Whether this modifier applies when copying a music disc, instead of only when recording.
public record VinylModifier(Component recordingText, List<ModificationStrategy> strategies, HolderSet<JukeboxSong> targets, DataComponentMap effects, boolean modifiesWhenCopying) {
    public static final Codec<VinylModifier> DIRECT_CODEC = RecordCodecBuilder.<VinylModifier>create(instance -> instance.group(
            ComponentSerialization.CODEC.fieldOf("recording_text").forGetter(VinylModifier::recordingText),
            ModificationStrategy.CODEC.listOf().fieldOf("strategies").forGetter(VinylModifier::strategies),
            RegistryCodecs.homogeneousList(Registries.JUKEBOX_SONG).fieldOf("targets").forGetter(VinylModifier::targets),
            ModifierComponentType.CODEC.optionalFieldOf("effects", DataComponentMap.EMPTY).forGetter(VinylModifier::effects),
            Codec.BOOL.optionalFieldOf("modifies_when_copying", false).forGetter(VinylModifier::modifiesWhenCopying)
    ).apply(instance, VinylModifier::new)).validate(modifier -> modifier.strategies().isEmpty() ? DataResult.error(() -> "Vinyl modifier doesn't have any targeted strategies") : DataResult.success(modifier));
    public static final Codec<Holder<VinylModifier>> CODEC = RegistryFixedCodec.create(STRegistries.VINYL_MODIFIER);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<VinylModifier>> STREAM_CODEC = ByteBufCodecs.holderRegistry(STRegistries.VINYL_MODIFIER);
    private static final Logger LOGGER = LogUtils.getLogger();

    /// Creates an instance of the **vinyl modifier builder**.
    /// @param targets Which jukebox songs this modifier acts on.
    public static Builder modifier(HolderSet<JukeboxSong> targets) {
        return new Builder(targets);
    }

    /// Whether this modifier acts on the provided jukebox song.
    /// @param song A jukebox song.
    /// @return `true` if {@link #targets} is empty or if `targets` contains the provided song.
    public boolean actsOn(@Nullable Holder<JukeboxSong> song) {
        return song == null || this.targets().size() == 0 || this.targets().contains(song);
    }

    /// Whether this modifier can be applied to copies.
    /// @param copyingSong Whether the recorder is copying a song.
    public boolean appliesForCopies(boolean copyingSong) {
        return this.modifiesWhenCopying() || !copyingSong;
    }

    /// Runs the **music recording pipeline** (runs all available vinyl modifiers).
    /// @param recorder The music recorder to grab the *modification context*.
    /// @param strategy Which stage of the recording this is. Only modifiers that run in this stage will be applied.
    /// @return The {@link ModificationResult} containing the modified recordable disc.
    public static ModificationResult recordingPipeline(MusicRecorderBlockEntity recorder, ModificationStrategy strategy) {
        return recordingPipeline(ModificationContext.fromBlockEntity(recorder), strategy);
    }

    /// Runs the **music recording pipeline** (runs all available vinyl modifiers).
    /// @param recorder The music recorder to grab the *modification context*.
    /// @param song The jukebox song being recorded.
    /// @param strategy Which stage of the recording this is. Only modifiers that run in this stage will be applied.
    /// @return The {@link ModificationResult} containing the modified recordable disc.
    public static ModificationResult recordingPipeline(MusicRecorderBlockEntity recorder, @Nullable Holder<JukeboxSong> song, ModificationStrategy strategy) {
        return recordingPipeline(ModificationContext.fromBlockEntity(recorder), song, strategy);
    }

    /// Runs the **music recording pipeline** (runs all available vinyl modifiers).
    /// @param context The *modification context* for the pipeline. This provides all the information necessary for the modifiers to run.
    /// @param strategy Which stage of the recording this is. Only modifiers that run in this stage will be applied.
    /// @return The {@link ModificationResult} containing the modified recordable disc.
    public static ModificationResult recordingPipeline(ModificationContext context, ModificationStrategy strategy) {
        var jukeboxSong = BlockBasedMusicPlayer.findJukeboxSongFromID(context.level().registryAccess(), context.musicID(), !context.copyingSong());
        return recordingPipeline(context, jukeboxSong.orElse(null), strategy);
    }

    /// Runs the **music recording pipeline** (runs all available vinyl modifiers).
    /// @param context The *modification context* for the pipeline. This provides all the information necessary for the modifiers to run.
    /// @param song The jukebox song being recorded.
    /// @param strategy Which stage of the recording this is. Only modifiers that run in this stage will be applied.
    /// @return The {@link ModificationResult} containing the modified recordable disc.
    public static ModificationResult recordingPipeline(ModificationContext context, @Nullable Holder<JukeboxSong> song, ModificationStrategy strategy) {
        if (context.level().isClientSide()) return new ModificationResult(ItemStackTemplate.fromNonEmptyStack(context.musicDisc()), Component.empty());
        Level level = context.level();
        Component recordingText = Component.empty();

        var allModifiers = level.registryAccess().lookupOrThrow(STRegistries.VINYL_MODIFIER);
        var pipelineModifiers = allModifiers.getTagOrEmpty(STVinylModifierTags.PRIORITY_MODIFICATION);
        context.withTransientStack(context.musicDisc().copy());

        // first run all modifiers that are part of the recording pipeline
        for (Holder<VinylModifier> modifier : pipelineModifiers) {
            if (modifier.is(STVinylModifierTags.PRIORITY_MODIFICATION)) {
                recordingText = checkAndRun(context, recordingText, song, modifier, strategy, context.copyingSong());
            }
        }

        // then run all other modifiers
        for (VinylModifier modifier : allModifiers) {
            var modifierHolder = allModifiers.wrapAsHolder(modifier);
            if (!modifierHolder.is(STVinylModifierTags.PRIORITY_MODIFICATION)) {
                recordingText = checkAndRun(context, recordingText, song, modifierHolder, strategy, context.copyingSong());
            }
        }

        // if the recorder's stack was modified in this process, throw an exception
        if (!ItemStack.matches(context.musicDisc(), context.musicDiscImmutable)) {
            throw new IllegalStateException("Recorder's disc stack was modified in the recording pipeline. Use 'ModificationContext.withTransientStack()' for this instead!");
        }

        return new ModificationResult(ItemStackTemplate.fromNonEmptyStack(context.transientStack().isEmpty() ? context.musicDisc() : context.transientStack()), recordingText);
    }

    private static Component checkAndRun(ModificationContext context, Component recordingText, @Nullable Holder<JukeboxSong> song, Holder<VinylModifier> modifierHolder, ModificationStrategy strategy, boolean copyingSong) {
        VinylModifier modifier = modifierHolder.value();
        if (STDebuggingFlags.LOGGING) {
            Marker marker = MarkerFactory.getMarker(modifierHolder.getRegisteredName());
            LOGGER.debug(marker, Component.translatable("logger.stancements.vinyl_modifier.strategies", modifier.strategies(), strategy).getString());
            LOGGER.debug(marker, Component.translatable("logger.stancements.vinyl_modifier.acts_on", song != null ? song.getRegisteredName() : Component.translatable("logger.stancements.vinyl_modifier.not_available").getString(), modifier.actsOn(song)).getString());
            LOGGER.debug(marker, Component.translatable("logger.stancements.vinyl_modifier.copy_state", modifier.modifiesWhenCopying(), copyingSong).getString());
        }

        if (modifier.strategies().contains(strategy) && modifier.actsOn(song) && modifier.appliesForCopies(copyingSong)) {
            modifier.effects().stream().forEach(component -> {
                // run all component that extend ModifierComponentType
                if (component.value() instanceof ModifierComponentType type) {
                    type.onApplyModifiers(context, modifierHolder);

                    // then run all List<ConditionalEffect<? extends ModifierComponentType>>
                } else if (component.value() instanceof List<?> list) {
                    list.forEach(entry -> checkAndRunConditional(context, modifierHolder, entry));
                }
            });
            if (!modifier.recordingText().getString().isBlank() && recordingText.getString().isBlank()) recordingText = modifier.recordingText().copy();
        }
        return recordingText;
    }

    private static void checkAndRunConditional(ModificationContext context, Holder<VinylModifier> modifierHolder, Object entry) {
        if (!(entry instanceof ConditionalEffect<?> effect)) return;

        // first set the context of the loot condition (if possible)
        if (effect.requirements().isPresent() && effect.requirements().get() instanceof ModificationContextAware condition) {
            condition.withContext(context);
        }

        // then run the function based on whether the condition passes
        if (effect.matches(modifierContext(context)) && effect.effect() instanceof ModifierComponentType type) {
            type.onApplyModifiers(context, modifierHolder);
        }
    }

    /// Creates the loot context for the {@link ModifyRecordableDiscModifier stancements:modify_recordable_disc} modifier
    /// and for any {@linkplain ConditionalEffect conditional} modifiers, used mostly for *loot conditions*.
    /// @param context The *modification context* of this current run of the pipeline.
    public static LootContext modifierContext(ModificationContext context) {
        LootParams params = new LootParams.Builder(context.level())
                .withParameter(LootContextParams.ORIGIN, context.blockPosition().getCenter())
                .withParameter(LootContextParams.BLOCK_STATE, context.blockState())
                .withOptionalParameter(LootContextParams.BLOCK_ENTITY, context.recorderOrNull())
                .withOptionalParameter(LootContextParams.THIS_ENTITY, context.playerOrNull())
                .create(STLootContextParamSets.VINYL_MODIFIER);
        return new LootContext.Builder(params).create(Optional.empty());
    }

    public static class Builder {
        private Component recordingText = Component.empty();
        private final List<ModificationStrategy> modifiesAt = new ArrayList<>();
        private final HolderSet<JukeboxSong> targets;
        private final Map<DataComponentType<?>, List<?>> modifiersList = new HashMap<>();
        private final DataComponentMap.Builder modifierMapBuilder = DataComponentMap.builder();
        private boolean modifiesWhenCopying = false;

        /// Creates an instance of the **vinyl modifier builder**.
        /// @param targets Which jukebox songs this modifier acts on.
        public Builder(HolderSet<JukeboxSong> targets) {
            this.targets = targets;
        }

        /// Sets the action bar text shown when the recording starts/finishes.
        /// @param component A text component.
        public Builder recordingText(Component component) {
            this.recordingText = component;
            return this;
        }

        /// Makes this vinyl modifier run right as the recording starts.
        public Builder modifiesAtStart() {
            if (!this.modifiesAt.contains(ModificationStrategy.START)) this.modifiesAt.add(ModificationStrategy.START);
            return this;
        }

        /// Makes this vinyl modifier run when the recording finishes.
        public Builder modifiesAtFinish() {
            if (!this.modifiesAt.contains(ModificationStrategy.FINISH)) this.modifiesAt.add(ModificationStrategy.FINISH);
            return this;
        }

        /// Adds a **loot function** to the {@link ModifyRecordableDiscModifier stancements:modify_recordable_disc} modifier.
        /// @param function The loot function.
        public Builder withFunction(LootItemFunction function) {
            this.getFunctionsList(STModifierComponents.MODIFY_RECORDABLE_DISC.get()).add(function);
            return this;
        }

        /// Makes the recordable disc be ejected out of the music recorder after **10 to 15 seconds** after being inserted.
        ///
        /// This makes use of the {@link EjectAfterTicksModifier stancements:eject_after_ticks} modifier
        public Builder ejectAfter15Seconds() {
            this.withSpecialModifier(STModifierComponents.EJECT_AFTER_TICKS.get(), List.of(new ConditionalEffect<>(
                    EjectAfterTicksModifier.tenToFifteenSeconds(1),
                    Optional.empty()
            )));
            return this;
        }

        /// Adds a *modifier component* to this vinyl modifier. The modifier must be a `List<ConditionalEffect<E>>`.
        /// @param <E> The component type. It is recommended to extend {@link ModifierComponentType} so it runs properly
        /// @param component The data component attached to the modifier.
        /// @param effect The modifier.
        public <E> Builder withModifier(DataComponentType<List<ConditionalEffect<E>>> component, E effect) {
            this.getEffectsList(component).add(new ConditionalEffect<>(effect, Optional.empty()));
            return this;
        }

        /// Adds a *modifier component* to this vinyl modifier.
        /// @param <E> The component type. It is recommended to extend {@link ModifierComponentType} so it runs properly
        /// @param component The data component attached to the modifier.
        /// @param value The modifier.
        public <E> Builder withSpecialModifier(DataComponentType<E> component, E value) {
            this.modifierMapBuilder.set(component, value);
            return this;
        }

        /// Adds a *modifier component* to this vinyl modifier. This component must be of the {@link Unit} type.
        /// @param component The data component attached to the modifier.
        public Builder withModifier(DataComponentType<Unit> component) {
            this.modifierMapBuilder.set(component, Unit.INSTANCE);
            return this;
        }

        /// Makes this vinyl modifier act when the music recorder is writing a copy.
        public Builder modifiesWhenCopying() {
            this.modifiesWhenCopying = true;
            return this;
        }

        @SuppressWarnings("unchecked")
        private List<LootItemFunction> getFunctionsList(DataComponentType<ModifyRecordableDiscModifier> componentType) {
            return (List<LootItemFunction>) this.modifiersList.computeIfAbsent(componentType, _ -> {
                ModifyRecordableDiscModifier list = new ModifyRecordableDiscModifier(new ArrayList<>());
                this.modifierMapBuilder.set(componentType, list);
                return list.functions();
            });
        }

        @SuppressWarnings("unchecked")
        private <E> List<E> getEffectsList(DataComponentType<List<E>> componentType) {
            return (List<E>) this.modifiersList.computeIfAbsent(componentType, _ -> {
                ArrayList<E> list = new ArrayList<>();
                this.modifierMapBuilder.set(componentType, list);
                return list;
            });
        }

        /// Builds this builder into a vinyl modifier.
        public VinylModifier build() {
            if (this.modifiesAt.isEmpty()) {
                throw new IllegalStateException("Vinyl modifier doesn't have any targeted strategies. Use 'modifiesAtStart()' and/or 'modifiesAtFinish()' to set one");
            }
            return new VinylModifier(this.recordingText, this.modifiesAt, this.targets, this.modifierMapBuilder.build(), this.modifiesWhenCopying);
        }
    }
}
