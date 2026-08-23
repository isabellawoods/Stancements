package melonystudios.stancements.misc.modifier;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.misc.STRegistries;
import melonystudios.stancements.misc.loot.condition.ItemRecorderStateCondition;
import melonystudios.stancements.misc.loot.function.ApplyRecordingTurnsIntoFunction;
import melonystudios.stancements.misc.loot.function.SetRandomLabelFunction;
import melonystudios.stancements.misc.loot.function.StyleDiscFromRegistryFunction;
import melonystudios.stancements.misc.modifier.type.PlaySoundModifier;
import melonystudios.stancements.misc.modifier.type.ReplaceRecordableDiscModifier;
import melonystudios.stancements.sound.STSounds;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.JukeboxSongs;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.level.storage.loot.functions.SetRandomDyesFunction;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.Sum;

import java.util.List;
import java.util.Optional;

public class STVinylModifiers {
    public static final ResourceKey<VinylModifier> FINISH_RECORDING = register("pipeline/finish_recording");
    public static final ResourceKey<VinylModifier> MUSIC_DISC_11 = register("music_disc_11");
    public static final ResourceKey<VinylModifier> MUSIC_DISC_13 = register("music_disc_13");
    public static final ResourceKey<VinylModifier> MUSIC_DISC_5 = register("music_disc_5");

    public static void bootstrap(BootstrapContext<VinylModifier> context) {
        var jukeboxSongs = context.lookup(Registries.JUKEBOX_SONG);

        context.register(FINISH_RECORDING, VinylModifier.modifier(HolderSet.empty())
                .recordingText(Component.translatable("tooltip.stancements.finished_recording").withColor(Stancements.ACCENT_COLOR))
                .withFunction(ApplyRecordingTurnsIntoFunction.apply().build())
                .withFunction(StyleDiscFromRegistryFunction.styleFromRegistry(
                        recordedDiscDyes(),
                        (SetRandomLabelFunction) SetRandomLabelFunction.withDefaultLabelRange().build()
                ).build())
                .modifiesAtFinish()
                .modifiesWhenCopying()
                .build()
        );

        // haunted music discs
        context.register(MUSIC_DISC_11, VinylModifier.modifier(HolderSet.direct(jukeboxSongs.getOrThrow(JukeboxSongs.ELEVEN)))
                .recordingText(Component.translatable("vinyl_modifier.stancements.music_disc_11").withColor(0x808080))
                .withSpecialModifier(STModifierComponents.REPLACE_RECORDED_DISC.get(), List.of(
                        new ConditionalEffect<>(
                                new ReplaceRecordableDiscModifier(new ItemStackTemplate(STItems.SHATTERED_DISC.get())),
                                Optional.of(new ItemRecorderStateCondition(HolderSet.direct(STItems.VINYL_DISC)))
                        ),
                        new ConditionalEffect<>(
                                new ReplaceRecordableDiscModifier(new ItemStackTemplate(STItems.SCULK_INFESTED_SHATTERED_DISC)),
                                Optional.of(new ItemRecorderStateCondition(HolderSet.direct(STItems.SCULK_INFESTED_VINYL_DISC)))
                        )
                ))
                .withModifier(STModifierComponents.PLAY_SOUND.get(), new PlaySoundModifier(
                        STSounds.SHATTER_MUSIC_DISC,
                        ConstantFloat.of(1),
                        ConstantFloat.of(0.2F)
                ))
                .ejectAfter15Seconds()
                .modifiesAtStart()
                .modifiesWhenCopying()
                .build()
        );

        context.register(MUSIC_DISC_13, VinylModifier.modifier(HolderSet.direct(jukeboxSongs.getOrThrow(JukeboxSongs.THIRTEEN)))
                .recordingText(Component.translatable("vinyl_modifier.stancements.music_disc_13").withColor(0xBFAD53))
                .withModifier(STModifierComponents.PLAY_SOUND.get(), new PlaySoundModifier(
                        SoundEvents.AMBIENT_CAVE,
                        ConstantFloat.of(1),
                        ConstantFloat.of(0.2F)
                ))
                .ejectAfter15Seconds()
                .modifiesAtStart()
                .modifiesWhenCopying()
                .build()
        );

        context.register(MUSIC_DISC_5, VinylModifier.modifier(HolderSet.direct(jukeboxSongs.getOrThrow(JukeboxSongs.FIVE)))
                .recordingText(Component.translatable("vinyl_modifier.stancements.music_disc_5").withColor(0x05625D))
                .withSpecialModifier(STModifierComponents.REPLACE_RECORDED_DISC.get(), List.of(new ConditionalEffect<>(
                        new ReplaceRecordableDiscModifier(new ItemStackTemplate(STItems.SCULK_INFESTED_VINYL_DISC)),
                        Optional.of(new ItemRecorderStateCondition(HolderSet.direct(STItems.VINYL_DISC)))
                )))
                .ejectAfter15Seconds()
                .modifiesAtStart()
                .modifiesWhenCopying()
                .build()
        );
    }

    public static SetRandomDyesFunction recordedDiscDyes() {
        return (SetRandomDyesFunction) SetRandomDyesFunction.withCount(
                Sum.sum(ConstantValue.exactly(1), BinomialDistributionGenerator.binomial(2, 0.75F))
        ).build();
    }

    public static ResourceKey<VinylModifier> register(String name) {
        return ResourceKey.create(STRegistries.VINYL_MODIFIER, Stancements.stancements(name));
    }
}
