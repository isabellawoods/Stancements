package melonystudios.stancements.data.misc;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.block.custom.croppot.CropPotBlock;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.misc.advancement.RecordSongTrigger;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class Stadvancements implements AdvancementSubProvider { // stancements advancements
    @Override
    @SuppressWarnings("removal") // todo: figure out a way to make this not crash to replace deprecated .parent() call ~isa 08-11-25
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {
        Identifier sweetDreamsIdentifier = Identifier.withDefaultNamespace("adventure/sleep_in_bed");
        Identifier aSeedyPlaceIdentifier = Identifier.withDefaultNamespace("husbandry/plant_seed");
        var items = registries.lookupOrThrow(Registries.ITEM);
        var blocks = registries.lookupOrThrow(Registries.BLOCK);

        AdvancementHolder recordSong = Advancement.Builder.advancement()
                .parent(sweetDreamsIdentifier)
                .display(
                        STItems.MUSIC_RECORDER.get(),
                        Component.translatable("advancements.stancements.record_song.title"),
                        Component.translatable("advancements.stancements.record_song.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion(
                        "record_song",
                        RecordSongTrigger.TriggerInstance.recordedAnySong(false)
                )
                .save(saver, Stancements.stancements("adventure/record_song").toString());

        Advancement.Builder.advancement()
                .parent(recordSong)
                .display(
                        createIconStack(),
                        Component.translatable("advancements.stancements.record_all_songs.title"),
                        Component.translatable("advancements.stancements.record_all_songs.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(150))
                // Volume Alpha
                .addCriterion("recorded/minecraft", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/minecraft")))
                .addCriterion("recorded/clark", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/clark")))
                .addCriterion("recorded/sweden", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/sweden")))
                .addCriterion("recorded/subwoofer_lullaby", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/subwoofer_lullaby")))
                .addCriterion("recorded/living_mice", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/living_mice")))
                .addCriterion("recorded/haggstrom", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/haggstrom")))
                .addCriterion("recorded/danny", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/danny")))
                .addCriterion("recorded/key", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/key")))
                .addCriterion("recorded/oxygene", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/oxygene")))
                .addCriterion("recorded/dry_hands", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/dry_hands")))
                .addCriterion("recorded/wet_hands", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/wet_hands")))
                .addCriterion("recorded/mice_on_venus", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/mice_on_venus")))

                // Volume Beta (creative songs not included for obvious reasons)
                .addCriterion("recorded/concrete_halls", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/nether/concrete_halls")))
                .addCriterion("recorded/dead_voxel", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/nether/dead_voxel")))
                .addCriterion("recorded/warmth", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/nether/warmth")))
                .addCriterion("recorded/ballad_of_the_cats", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/nether/ballad_of_the_cats")))
                .addCriterion("recorded/boss", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/end/boss")))
                .addCriterion("recorded/the_end", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/end/the_end")))
                .addCriterion("recorded/alpha", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/end/alpha")))

                // Underwater Singles (1.13)
                .addCriterion("recorded/shuniji", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/water/shuniji")))
                .addCriterion("recorded/dragon_fish", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/water/dragon_fish")))
                .addCriterion("recorded/axolotl", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/water/axolotl")))

                // Nether Update (1.16)
                .addCriterion("recorded/rubedo", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/nether/nether_wastes/rubedo")))
                .addCriterion("recorded/chrysopoeia", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/nether/crimson_forest/chrysopoeia")))
                .addCriterion("recorded/so_below", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/nether/soulsand_valley/so_below")))

                // Caves & Cliffs (1.17 / 1.18)
                .addCriterion("recorded/stand_tall", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/stand_tall")))
                .addCriterion("recorded/left_to_bloom", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/left_to_bloom")))
                .addCriterion("recorded/ancestry", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/ancestry")))
                .addCriterion("recorded/wending", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/wending")))
                .addCriterion("recorded/infinite_amethyst", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/infinite_amethyst")))
                .addCriterion("recorded/one_more_day", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/one_more_day")))
                .addCriterion("recorded/floating_dream", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/floating_dream")))
                .addCriterion("recorded/comforting_memories", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/comforting_memories")))
                .addCriterion("recorded/an_ordinary_day", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/an_ordinary_day")))

                // The Wild Update (1.19)
                .addCriterion("recorded/firebugs", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/swamp/firebugs")))
                .addCriterion("recorded/aerie", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/swamp/aerie")))
                .addCriterion("recorded/labyrinthine", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/swamp/labyrinthine")))

                // Trails & Tales (1.20)
                .addCriterion("recorded/echo_in_the_wind", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/echo_in_the_wind")))
                .addCriterion("recorded/a_familiar_room", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/a_familiar_room")))
                .addCriterion("recorded/bromeliad", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/bromeliad")))
                .addCriterion("recorded/crescent_dunes", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/crescent_dunes")))

                // Tricky Trials (1.21)
                .addCriterion("recorded/featherfall", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/featherfall")))
                .addCriterion("recorded/watcher", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/watcher")))
                .addCriterion("recorded/puzzlebox", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/puzzlebox")))
                .addCriterion("recorded/komorebi", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/komorebi")))
                .addCriterion("recorded/pokopoko", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/pokopoko")))
                .addCriterion("recorded/yakusoku", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/yakusoku")))
                .addCriterion("recorded/deeper", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/deeper")))
                .addCriterion("recorded/eld_unknown", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/eld_unknown")))
                .addCriterion("recorded/endless", RecordSongTrigger.TriggerInstance.recordedSong(Identifier.withDefaultNamespace("game/endless")))

                // Chase the Skies (1.21.6) (whenever I update the mod)
                .save(saver, Stancements.stancements("adventure/record_all_songs").toString());

        AdvancementHolder plantInCropPot = Advancement.Builder.advancement()
                .parent(aSeedyPlaceIdentifier)
                .display(
                        STItems.CROP_POT.get(),
                        Component.translatable("advancements.stancements.plant_in_crop_pot.title"),
                        Component.translatable("advancements.stancements.plant_in_crop_pot.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion(
                        "plant_crop",
                        ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                                LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blocks, STBlocks.CROP_POT.get())),
                                ItemPredicate.Builder.item().of(items, Items.WHEAT_SEEDS, Items.CARROT, Items.POTATO, Items.BEETROOT_SEEDS, Items.NETHER_WART)
                        )
                )
                .save(saver, Stancements.stancements("husbandry/plant_in_crop_pot").toString());

        Advancement.Builder.advancement()
                .parent(plantInCropPot)
                .display(
                        STItems.hoppingCropPot(1),
                        Component.translatable("advancements.stancements.plant_in_hopping_pot.title"),
                        Component.translatable("advancements.stancements.plant_in_hopping_pot.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion(
                        "plant_crop",
                        ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                                LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block()
                                        .of(blocks, STBlocks.CROP_POT.get())
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropPotBlock.HOPPING, true))
                                ),
                                ItemPredicate.Builder.item().of(items, Items.WHEAT_SEEDS, Items.CARROT, Items.POTATO, Items.BEETROOT_SEEDS, Items.NETHER_WART)
                        )
                )
                .save(saver, Stancements.stancements("husbandry/plant_in_hopping_pot").toString());
    }

    private static ItemStackTemplate createIconStack() {
        return new ItemStackTemplate(
                STItems.RECORDED_DISC.get(),
                DataComponentPatch.builder()
                .set(DataComponents.DYED_COLOR, new DyedItemColor(0x55FF55))
                .set(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT.withHidden(DataComponents.DYED_COLOR, true))
                .set(STDataComponents.LABEL.get(), 7F)
                .build()
        );
    }
}
