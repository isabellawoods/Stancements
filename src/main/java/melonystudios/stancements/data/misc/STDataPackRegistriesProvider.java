package melonystudios.stancements.data.misc;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.STJukeboxSongs;
import melonystudios.stancements.misc.STRegistries;
import melonystudios.stancements.misc.discstyle.STRecordedDiscStyles;
import melonystudios.stancements.misc.modifier.STVinylModifiers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import org.jspecify.annotations.NonNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class STDataPackRegistriesProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            // Other
            .add(Registries.JUKEBOX_SONG, STJukeboxSongs::bootstrap)
            .add(STRegistries.RECORDED_DISC_STYLE, STRecordedDiscStyles::bootstrap)
            .add(STRegistries.VINYL_MODIFIER, STVinylModifiers::bootstrap);

    public STDataPackRegistriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Stancements.MOD_ID, "minecraft"));
    }

    @Override
    @NonNull
    public String getName() {
        return Stancements.generatorName("Data Pack Registries");
    }
}
