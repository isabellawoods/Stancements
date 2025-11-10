package melonystudios.stancements.data.misc;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.misc.datamap.PotPlantables;
import melonystudios.stancements.misc.datamap.STDataMaps;
import melonystudios.stancements.misc.datamap.RecordedDiscStyles;
import melonystudios.stancements.util.tag.STItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.JukeboxSongs;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class STDataMapsProvider extends DataMapProvider {
    public STDataMapsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    @NotNull
    public String getName() {
        return Stancements.generatorName("Data Maps");
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void gather(HolderLookup.Provider provider) {
        // Stancements data maps
        this.builder(STDataMaps.POT_PLANTABLES)
                .add(Items.WHEAT_SEEDS.builtInRegistryHolder(), PotPlantables.defaultPlantingSound(STBlocks.WHEAT_CROP_POT.get()), false)
                .add(Items.CARROT.builtInRegistryHolder(), PotPlantables.defaultPlantingSound(STBlocks.CARROT_CROP_POT.get()), false)
                .add(Items.POTATO.builtInRegistryHolder(), PotPlantables.defaultPlantingSound(STBlocks.POTATO_CROP_POT.get()), false)
                .add(Items.BEETROOT_SEEDS.builtInRegistryHolder(), PotPlantables.defaultPlantingSound(STBlocks.BEETROOT_CROP_POT.get()), false)
                .add(Items.NETHER_WART.builtInRegistryHolder(), new PotPlantables(STBlocks.NETHER_WART_CROP_POT.get(), SoundEvents.NETHER_WART_PLANTED), false);

        this.builder(STDataMaps.RECORDED_DISC_STYLES)
                .add(JukeboxSongs.THIRTEEN, new RecordedDiscStyles(0xFFD800, 9), false)
                .add(JukeboxSongs.CAT, new RecordedDiscStyles(0x4CFF00, 2), false)
                .add(JukeboxSongs.BLOCKS, new RecordedDiscStyles(0xE2543B, 1), false)
                .add(JukeboxSongs.CHIRP, new RecordedDiscStyles(0xFF0004, 3), false)
                .add(JukeboxSongs.FAR, new RecordedDiscStyles(0xB6FF00, 4), false)
                .add(JukeboxSongs.MALL, new RecordedDiscStyles(0x9A75FF, 2), false)
                .add(JukeboxSongs.MELLOHI, new RecordedDiscStyles(0xB200FF, 5), false)
                .add(JukeboxSongs.STAL, new RecordedDiscStyles(0x000000, 1), false)
                .add(JukeboxSongs.STRAD, new RecordedDiscStyles(0xFFFFFF, 1), false)
                .add(JukeboxSongs.WARD, new RecordedDiscStyles(0x8EC600, 9), false)
                .add(JukeboxSongs.ELEVEN, new RecordedDiscStyles(0x000000, 1), false)
                .add(JukeboxSongs.WAIT, new RecordedDiscStyles(0x81A9E2, 12), false)
                .add(JukeboxSongs.PIGSTEP, new RecordedDiscStyles(0xFDF55F, 6), false)
                .add(JukeboxSongs.OTHERSIDE, new RecordedDiscStyles(0x1E8B8C, 2), false)
                .add(JukeboxSongs.FIVE, new RecordedDiscStyles(0x29DFEB, 11), false)
                .add(JukeboxSongs.RELIC, new RecordedDiscStyles(0x88E6FF, 6), false)
                .add(JukeboxSongs.PRECIPICE, new RecordedDiscStyles(0x7AB799, 7), false)
                .add(JukeboxSongs.CREATOR, new RecordedDiscStyles(0xFFDD99, 10), false)
                .add(JukeboxSongs.CREATOR_MUSIC_BOX, new RecordedDiscStyles(0xFFDD99, 10), false);

        // NeoForge data maps
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(STItemTags.SHELVES, new FurnaceFuel(300), false)
                .add(STItemTags.CRAFTING_TABLE_CLOTHS, new FurnaceFuel(67), false)
                .add(STItems.MUSIC_RECORDER.getKey(), new FurnaceFuel(300), false);
    }
}
