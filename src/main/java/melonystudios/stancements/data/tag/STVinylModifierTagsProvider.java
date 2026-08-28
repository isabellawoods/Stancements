package melonystudios.stancements.data.tag;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.STRegistries;
import melonystudios.stancements.misc.modifier.VinylModifier;
import melonystudios.stancements.tag.STVinylModifierTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static melonystudios.stancements.misc.modifier.STVinylModifiers.*;

public class STVinylModifierTagsProvider extends TagsProvider<VinylModifier> {
    public STVinylModifierTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper fileHelper) {
        super(output, STRegistries.VINYL_MODIFIER, registries, Stancements.MOD_ID, fileHelper);
    }

    @Override
    @NotNull
    public String getName() {
        return Stancements.generatorName("Vinyl Modifier Tags");
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Stancements
        this.tag(STVinylModifierTags.PRIORITY_MODIFICATION).add(FINISH_RECORDING, SCULK_EJECTION_CHANCE);
    }
}
