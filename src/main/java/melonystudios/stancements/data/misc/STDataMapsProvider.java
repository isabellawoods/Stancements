package melonystudios.stancements.data.misc;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.tag.STItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
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
    protected void gather(HolderLookup.Provider registries) {
        // NeoForge data maps
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(STItemTags.SHELVES, new FurnaceFuel(300), false)
                .add(STItemTags.CRAFTING_TABLE_CLOTHS, new FurnaceFuel(67), false)
                .add(STItems.MUSIC_RECORDER.getKey(), new FurnaceFuel(300), false);
    }
}
