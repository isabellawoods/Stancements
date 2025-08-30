package melonystudios.stancements.data.misc;

import melonystudios.stancements.item.STItems;
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
        return "Stancements - Data Maps";
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(STItems.OAK_SHELF.getKey(), new FurnaceFuel(300), false)
                .add(STItems.SPRUCE_SHELF.getKey(), new FurnaceFuel(300), false)
                .add(STItems.BIRCH_SHELF.getKey(), new FurnaceFuel(300), false)
                .add(STItems.JUNGLE_SHELF.getKey(), new FurnaceFuel(300), false)
                .add(STItems.ACACIA_SHELF.getKey(), new FurnaceFuel(300), false)
                .add(STItems.DARK_OAK_SHELF.getKey(), new FurnaceFuel(300), false)
                .add(STItems.MUSIC_RECORDER.getKey(), new FurnaceFuel(300), false);
    }
}
