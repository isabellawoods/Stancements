package melonystudios.stancements.misc.modifier;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.component.custom.RecordableTransform;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.misc.STRegistries;
import melonystudios.stancements.misc.loot.condition.ItemRecorderStateCondition;
import melonystudios.stancements.misc.loot.function.*;
import melonystudios.stancements.misc.loot.number.Sum;
import melonystudios.stancements.misc.modifier.type.EjectAfterTicksModifier;
import melonystudios.stancements.misc.modifier.type.PlaySoundModifier;
import melonystudios.stancements.misc.modifier.type.ReplaceRecordableDiscModifier;
import melonystudios.stancements.misc.recording.Track;
import melonystudios.stancements.sound.STSounds;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.JukeboxSongs;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Optional;

public class STVinylModifiers {
    public static final ResourceKey<VinylModifier> FINISH_RECORDING = register("pipeline/finish_recording");
    public static final ResourceKey<VinylModifier> SCULK_EJECTION_CHANCE = register("pipeline/sculk_ejection_chance");
    public static final ResourceKey<VinylModifier> MUSIC_DISC_11 = register("music_disc_11");
    public static final ResourceKey<VinylModifier> MUSIC_DISC_13 = register("music_disc_13");
    public static final ResourceKey<VinylModifier> MUSIC_DISC_5 = register("music_disc_5");

    public static void bootstrap(BootstrapContext<VinylModifier> context) {
        var jukeboxSongs = context.lookup(Registries.JUKEBOX_SONG);

        // pipeline
        context.register(FINISH_RECORDING, VinylModifier.modifier(List.of())
                .recordingText(Component.translatable("tooltip.stancements.finished_recording").withColor(Stancements.ACCENT_COLOR))
                .withFunction(TransformRecordableFunction.withTransform(RecordableTransform.Transforms.ON_RECORD).build())
                .withFunction(StyleDiscFromRegistryFunction.styleFromRegistry(
                        recordedDiscDyes(),
                        (SetRandomLabelFunction) SetRandomLabelFunction.withDefaultLabelRange().build()
                ).build())
                .modifiesAtFinish()
                .modifiesCopies()
                .build()
        );

        context.register(SCULK_EJECTION_CHANCE, VinylModifier.modifier(List.of())
                .withSpecialModifier(STModifierComponents.EJECT_AFTER_TICKS.get(), List.of(new ConditionalEffect<>(
                        new EjectAfterTicksModifier(ConstantFloat.of(0.15F), ConstantInt.of(400)),
                        Optional.of(new ItemRecorderStateCondition(HolderSet.direct(STItems.SCULK_INFESTED_RECORDED_DISC)))
                )))
                .modifiesAtStart()
                .modifiesCopies()
                .build()
        );

        // haunted music discs
        context.register(MUSIC_DISC_11, VinylModifier.modifier(Track.forJukeboxSong(jukeboxSongs.getOrThrow(JukeboxSongs.ELEVEN)))
                .recordingText(Component.translatable("vinyl_modifier.stancements.music_disc_11").withColor(0x808080))
                .withSpecialModifier(STModifierComponents.REPLACE_RECORDED_DISC.get(), List.of(
                        new ConditionalEffect<>(
                                new ReplaceRecordableDiscModifier(STItems.SHATTERED_DISC.toStack()),
                                Optional.of(new ItemRecorderStateCondition(HolderSet.direct(STItems.VINYL_DISC)))
                        ),
                        new ConditionalEffect<>(
                                new ReplaceRecordableDiscModifier(STItems.SCULK_INFESTED_SHATTERED_DISC.toStack()),
                                Optional.of(new ItemRecorderStateCondition(HolderSet.direct(STItems.SCULK_INFESTED_VINYL_DISC)))
                        )
                ))
                .withModifier(STModifierComponents.PLAY_SOUND.get(), new PlaySoundModifier(
                        STSounds.SHATTER_MUSIC_DISC,
                        ConstantFloat.of(0.4F),
                        ConstantFloat.of(0.2F)
                ))
                .ejectAfter15Seconds()
                .modifiesAtStart()
                .modifiesCopies()
                .build()
        );

        context.register(MUSIC_DISC_13, VinylModifier.modifier(Track.forJukeboxSong(jukeboxSongs.getOrThrow(JukeboxSongs.THIRTEEN)))
                .recordingText(Component.translatable("vinyl_modifier.stancements.music_disc_13").withColor(0xBFAD53))
                .withModifier(STModifierComponents.PLAY_SOUND.get(), new PlaySoundModifier(
                        SoundEvents.AMBIENT_CAVE,
                        ConstantFloat.of(0.4F),
                        ConstantFloat.of(1)
                ))
                .ejectAfter15Seconds()
                .modifiesAtStart()
                .modifiesCopies()
                .build()
        );

        context.register(MUSIC_DISC_5, VinylModifier.modifier(Track.forJukeboxSong(jukeboxSongs.getOrThrow(JukeboxSongs.FIVE)))
                .recordingText(Component.translatable("vinyl_modifier.stancements.music_disc_5").withColor(0x05625D))
                .withSpecialModifier(STModifierComponents.REPLACE_RECORDED_DISC.get(), List.of(new ConditionalEffect<>(
                        new ReplaceRecordableDiscModifier(STItems.SCULK_INFESTED_VINYL_DISC.toStack()),
                        Optional.of(new ItemRecorderStateCondition(HolderSet.direct(STItems.VINYL_DISC)))
                )))
                .ejectAfter15Seconds()
                .modifiesAtStart()
                .modifiesCopies()
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
