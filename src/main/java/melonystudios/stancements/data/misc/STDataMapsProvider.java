package melonystudios.stancements.data.misc;

import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.misc.datamap.PotPlantable;
import melonystudios.stancements.misc.datamap.STDataMaps;
import melonystudios.stancements.util.tag.STItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
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
        return "Stancements - Data Maps";
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        // Stancements data maps
        this.builder(STDataMaps.POT_PLANTABLE)
                .add(Items.WHEAT_SEEDS.builtInRegistryHolder(), PotPlantable.defaultPlantingSound(STBlocks.WHEAT_CROP_POT.get()), false)
                .add(Items.CARROT.builtInRegistryHolder(), PotPlantable.defaultPlantingSound(STBlocks.CARROT_CROP_POT.get()), false)
                .add(Items.POTATO.builtInRegistryHolder(), PotPlantable.defaultPlantingSound(STBlocks.POTATO_CROP_POT.get()), false)
                .add(Items.BEETROOT_SEEDS.builtInRegistryHolder(), PotPlantable.defaultPlantingSound(STBlocks.BEETROOT_CROP_POT.get()), false)
                .add(Items.NETHER_WART.builtInRegistryHolder(), new PotPlantable(STBlocks.NETHER_WART_CROP_POT.get(), SoundEvents.NETHER_WART_PLANTED), false);

        // NeoForge data maps
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(STItemTags.SHELVES, new FurnaceFuel(300), false)
                .add(STItemTags.CRAFTING_TABLE_CLOTHS, new FurnaceFuel(67), false)
                .add(STItems.MUSIC_RECORDER.getKey(), new FurnaceFuel(300), false);
    }
}
