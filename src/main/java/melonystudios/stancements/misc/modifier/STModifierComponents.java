package melonystudios.stancements.misc.modifier;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.STRegistries;
import melonystudios.stancements.misc.modifier.type.*;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static melonystudios.stancements.misc.loot.STLootContextParamSets.VINYL_MODIFIER;

public class STModifierComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(STRegistries.VINYL_MODIFIER_COMPONENT_TYPE_KEY, Stancements.MOD_ID);

    // general use case components
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ModifyRecordableDiscModifier>> MODIFY_RECORDABLE_DISC = COMPONENTS.registerComponentType("modify_recordable_disc",
            builder -> builder.persistent(ModifyRecordableDiscModifier.CODEC));

    // for haunted discs (5, 11, 13, "decay" from Enderscape), idea by Reitrix
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EjectAfterTicksModifier>>>> EJECT_AFTER_TICKS = COMPONENTS.registerComponentType("eject_after_ticks",
            builder -> builder.persistent(ConditionalEffect.codec(EjectAfterTicksModifier.CODEC, VINYL_MODIFIER).listOf()));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<ReplaceRecordableDiscModifier>>>> REPLACE_RECORDED_DISC = COMPONENTS.registerComponentType("replace_recorded_disc",
            builder -> builder.persistent(ConditionalEffect.codec(ReplaceRecordableDiscModifier.CODEC, VINYL_MODIFIER).listOf()));

    // copied over from the location-based enchantment effects
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<ExplodeModifier>>>> EXPLODE = COMPONENTS.registerComponentType("explode",
            builder -> builder.persistent(ConditionalEffect.codec(ExplodeModifier.CODEC, VINYL_MODIFIER).listOf()));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<PlaySoundModifier>>>> PLAY_SOUND = COMPONENTS.registerComponentType("play_sound",
            builder -> builder.persistent(ConditionalEffect.codec(PlaySoundModifier.CODEC, VINYL_MODIFIER).listOf()));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<ReplaceBlockModifier>>>> REPLACE_BLOCK = COMPONENTS.registerComponentType("replace_block",
            builder -> builder.persistent(ConditionalEffect.codec(ReplaceBlockModifier.CODEC, VINYL_MODIFIER).listOf()));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<ReplaceDiskModifier>>>> REPLACE_DISK = COMPONENTS.registerComponentType("replace_disk",
            builder -> builder.persistent(ConditionalEffect.codec(ReplaceDiskModifier.CODEC, VINYL_MODIFIER).listOf()));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<RunFunctionModifier>>>> RUN_FUNCTION = COMPONENTS.registerComponentType("run_function",
            builder -> builder.persistent(ConditionalEffect.codec(RunFunctionModifier.CODEC, VINYL_MODIFIER).listOf()));
}
