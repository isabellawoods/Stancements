package melonystudios.stancements.data.tag;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.util.tag.STItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static melonystudios.stancements.item.STItems.*;

public class STItemTagsProvider extends ItemTagsProvider {
    public STItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper fileHelper) {
        super(output, registries, blockTags, Stancements.MOD_ID, fileHelper);
    }

    @Override
    @NotNull
    public String getName() {
        return "Stancements - Item Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Stancements' tags
        this.tag(STItemTags.SHELVES).add(OAK_SHELF.get(), SPRUCE_SHELF.get(), BIRCH_SHELF.get(), JUNGLE_SHELF.get(), ACACIA_SHELF.get(), DARK_OAK_SHELF.get(), CRIMSON_SHELF.get(), WARPED_SHELF.get());
        this.tag(STItemTags.VINYL_DISC_DYES).addTag(Tags.Items.DYES_LIGHT_GRAY).addTag(Tags.Items.DYES_GRAY);

        // Common tags
        this.tag(Tags.Items.MUSIC_DISCS).add(VINYL_DISC.get(), RECORDED_DISC.get());

        // Minecraft tags
        this.tag(ItemTags.NON_FLAMMABLE_WOOD).add(CRIMSON_SHELF.get(), WARPED_SHELF.get());
        this.tag(ItemTags.DYEABLE).add(RECORDED_DISC.get());
    }
}
