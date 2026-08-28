package melonystudios.stancements.data.misc;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.block.custom.croppot.CropPotBlock;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.misc.advancement.RecordSongTrigger;
import melonystudios.stancements.misc.recording.RecordingSource;
import melonystudios.stancements.misc.recording.Track;
import melonystudios.stancements.misc.recording.Tracks;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.function.Consumer;

public class Stadvancements implements AdvancementProvider.AdvancementGenerator { // stancements advancements
    @Override
    @SuppressWarnings("removal") // todo: figure out a way to make this not crash to replace deprecated .parent() call ~isa 08-11-25
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper fileHelper) {
        ResourceLocation sweetDreamsLocation = ResourceLocation.withDefaultNamespace("adventure/sleep_in_bed");
        ResourceLocation aSeedyPlaceLocation = ResourceLocation.withDefaultNamespace("husbandry/plant_seed");

        AdvancementHolder recordSong = Advancement.Builder.advancement()
                .parent(sweetDreamsLocation)
                .display(
                        STItems.MUSIC_RECORDER,
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
                        RecordSongTrigger.TriggerInstance.recordedAnySong(RecordingSource.MUSIC_RECORDER, false, List.of())
                )
                .save(saver, Stancements.stancements("adventure/record_song").toString());

        Advancement.Builder.advancement()
                .parent(recordSong)
                .requirements(AdvancementRequirements.Strategy.AND)
                .display(
                        createCopyDiscIcon(),
                        Component.translatable("advancements.stancements.copy_disc.title"),
                        Component.translatable("advancements.stancements.copy_disc.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion(
                        "copy_disc_except_alpha",
                        RecordSongTrigger.TriggerInstance.recordedAnySong(RecordingSource.MUSIC_RECORDER, true, Tracks.C418_ALPHA.listOf())
                )
                .save(saver, Stancements.stancements("adventure/copy_disc").toString());

        Advancement.Builder.advancement()
                .parent(recordSong)
                .display(
                        createRecordAllSongsIcon(),
                        Component.translatable("advancements.stancements.record_all_songs.title"),
                        Component.translatable("advancements.stancements.record_all_songs.description"),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .rewards(AdvancementRewards.Builder.experience(500))
                // Volume Alpha
                .addCriterion("recorded/minecraft", recordVanilla("game/minecraft"))
                .addCriterion("recorded/clark", recordVanilla("game/clark"))
                .addCriterion("recorded/sweden", recordVanilla("game/sweden"))
                .addCriterion("recorded/subwoofer_lullaby", recordVanilla("game/subwoofer_lullaby"))
                .addCriterion("recorded/living_mice", recordVanilla("game/living_mice"))
                .addCriterion("recorded/haggstrom", recordVanilla("game/haggstrom"))
                .addCriterion("recorded/danny", recordVanilla("game/danny"))
                .addCriterion("recorded/key", recordVanilla("game/key"))
                .addCriterion("recorded/oxygene", recordVanilla("game/oxygene"))
                .addCriterion("recorded/dry_hands", recordVanilla("game/dry_hands"))
                .addCriterion("recorded/wet_hands", recordVanilla("game/wet_hands"))
                .addCriterion("recorded/mice_on_venus", recordVanilla("game/mice_on_venus"))

                // Volume Beta (creative songs not included for obvious reasons)
                .addCriterion("recorded/concrete_halls", recordVanilla("game/nether/concrete_halls"))
                .addCriterion("recorded/dead_voxel", recordVanilla("game/nether/dead_voxel"))
                .addCriterion("recorded/warmth", recordVanilla("game/nether/warmth"))
                .addCriterion("recorded/ballad_of_the_cats", recordVanilla("game/nether/ballad_of_the_cats"))
                .addCriterion("recorded/boss", recordVanilla("game/end/boss"))
                .addCriterion("recorded/the_end", recordVanilla("game/end/the_end"))
                .addCriterion("recorded/alpha", recordVanilla("game/end/alpha", true))

                // Underwater Singles (1.13)
                .addCriterion("recorded/shuniji", recordVanilla("game/water/shuniji"))
                .addCriterion("recorded/dragon_fish", recordVanilla("game/water/dragon_fish"))
                .addCriterion("recorded/axolotl", recordVanilla("game/water/axolotl"))

                // Nether Update (1.16)
                .addCriterion("recorded/rubedo", recordVanilla("game/nether/nether_wastes/rubedo"))
                .addCriterion("recorded/chrysopoeia", recordVanilla("game/nether/crimson_forest/chrysopoeia"))
                .addCriterion("recorded/so_below", recordVanilla("game/nether/soulsand_valley/so_below"))

                // Caves & Cliffs (1.17 / 1.18)
                .addCriterion("recorded/stand_tall", recordVanilla("game/stand_tall"))
                .addCriterion("recorded/left_to_bloom", recordVanilla("game/left_to_bloom"))
                .addCriterion("recorded/ancestry", recordVanilla("game/ancestry"))
                .addCriterion("recorded/wending", recordVanilla("game/wending"))
                .addCriterion("recorded/infinite_amethyst", recordVanilla("game/infinite_amethyst"))
                .addCriterion("recorded/one_more_day", recordVanilla("game/one_more_day"))
                .addCriterion("recorded/floating_dream", recordVanilla("game/floating_dream"))
                .addCriterion("recorded/comforting_memories", recordVanilla("game/comforting_memories"))
                .addCriterion("recorded/an_ordinary_day", recordVanilla("game/an_ordinary_day"))

                // The Wild Update (1.19)
                .addCriterion("recorded/firebugs", recordVanilla("game/swamp/firebugs"))
                .addCriterion("recorded/aerie", recordVanilla("game/swamp/aerie"))
                .addCriterion("recorded/labyrinthine", recordVanilla("game/swamp/labyrinthine"))

                // Trails & Tales (1.20)
                .addCriterion("recorded/echo_in_the_wind", recordVanilla("game/echo_in_the_wind"))
                .addCriterion("recorded/a_familiar_room", recordVanilla("game/a_familiar_room"))
                .addCriterion("recorded/bromeliad", recordVanilla("game/bromeliad"))
                .addCriterion("recorded/crescent_dunes", recordVanilla("game/crescent_dunes"))

                // Tricky Trials (1.21)
                .addCriterion("recorded/featherfall", recordVanilla("game/featherfall"))
                .addCriterion("recorded/watcher", recordVanilla("game/watcher"))
                .addCriterion("recorded/puzzlebox", recordVanilla("game/puzzlebox"))
                .addCriterion("recorded/komorebi", recordVanilla("game/komorebi"))
                .addCriterion("recorded/pokopoko", recordVanilla("game/pokopoko"))
                .addCriterion("recorded/yakusoku", recordVanilla("game/yakusoku"))
                .addCriterion("recorded/deeper", recordVanilla("game/deeper"))
                .addCriterion("recorded/eld_unknown", recordVanilla("game/eld_unknown"))
                .addCriterion("recorded/endless", recordVanilla("game/endless"))

                // Chase the Skies (1.21.6) (whenever I update the mod)

                // Chaos Cubed (26.2) (when updated)
                .save(saver, Stancements.stancements("adventure/record_all_songs").toString());

        Advancement.Builder.advancement()
                .parent(recordSong)
                .display(
                        STItems.POCKET_RECORDER,
                        Component.translatable("advancements.stancements.record_song_with_pocket.title"),
                        Component.translatable("advancements.stancements.record_song_with_pocket.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion(
                        "record_using_inventory_recorder",
                        RecordSongTrigger.TriggerInstance.recordedAnySong(RecordingSource.INVENTORY_RECORDER, false, List.of())
                )
                .save(saver, Stancements.stancements("adventure/record_song_with_pocket").toString());

        AdvancementHolder plantInCropPot = Advancement.Builder.advancement()
                .parent(aSeedyPlaceLocation)
                .display(
                        STItems.cropPot(1, false),
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
                                LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(STBlocks.CROP_POT.get())),
                                ItemPredicate.Builder.item().of(Items.WHEAT_SEEDS, Items.CARROT, Items.POTATO, Items.BEETROOT_SEEDS, Items.NETHER_WART)
                        )
                )
                .save(saver, Stancements.stancements("husbandry/plant_in_crop_pot").toString());

        Advancement.Builder.advancement()
                .parent(plantInCropPot)
                .display(
                        STItems.cropPot(1, true),
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
                                        .of(STBlocks.CROP_POT.get())
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropPotBlock.HOPPING, true))
                                ),
                                ItemPredicate.Builder.item().of(Items.WHEAT_SEEDS, Items.CARROT, Items.POTATO, Items.BEETROOT_SEEDS, Items.NETHER_WART)
                        )
                )
                .save(saver, Stancements.stancements("husbandry/plant_in_hopping_pot").toString());
    }

    private static ItemStack createRecordAllSongsIcon() {
        ItemStack stack = STItems.RECORDED_DISC.toStack();
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(0xAA00AA, false));
        stack.set(STDataComponents.LABEL, 7F);
        return stack;
    }

    private static ItemStack createCopyDiscIcon() {
        ItemStack stack = STItems.RECORDED_DISC.toStack();
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(0xFFDD99, false));
        stack.set(STDataComponents.LABEL, 10F);
        return stack;
    }

    private static Criterion<?> recordVanilla(String id) {
        return recordVanilla(id, false);
    }

    private static Criterion<?> recordVanilla(String id, boolean copying) {
        return RecordSongTrigger.TriggerInstance.recordedSong(new Track(ResourceLocation.withDefaultNamespace(id), true), copying);
    }
}
