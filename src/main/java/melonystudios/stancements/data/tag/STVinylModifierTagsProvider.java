package melonystudios.stancements.data.tag;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.STRegistries;
import melonystudios.stancements.misc.modifier.STVinylModifiers;
import melonystudios.stancements.misc.modifier.VinylModifier;
import melonystudios.stancements.tag.STVinylModifierTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class STVinylModifierTagsProvider extends KeyTagProvider<VinylModifier> {
    public STVinylModifierTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, STRegistries.VINYL_MODIFIER, registries, Stancements.MOD_ID);
    }

    @Override
    @NonNull
    public String getName() {
        return Stancements.generatorName("Vinyl Modifier Tags");
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        // Stancements tags
        this.tag(STVinylModifierTags.PRIORITY_MODIFICATION).add(STVinylModifiers.FINISH_RECORDING);
    }
}
