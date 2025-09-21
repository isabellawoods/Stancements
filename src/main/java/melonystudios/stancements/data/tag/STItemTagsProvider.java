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
        this.tag(STItemTags.SHELVES).add(OAK_SHELF.get(), SPRUCE_SHELF.get(), BIRCH_SHELF.get(), JUNGLE_SHELF.get(), ACACIA_SHELF.get(), MANGROVE_SHELF.get(), CHERRY_SHELF.get(),
                BAMBOO_SHELF.get(), DARK_OAK_SHELF.get(), CRIMSON_SHELF.get(), WARPED_SHELF.get());
        this.tag(STItemTags.CRAFTING_TABLE_CLOTHS).add(WHITE_CRAFTING_TABLE_CLOTH.get(), LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get(), GRAY_CRAFTING_TABLE_CLOTH.get(), BLACK_CRAFTING_TABLE_CLOTH.get(),
                BROWN_CRAFTING_TABLE_CLOTH.get(), RED_CRAFTING_TABLE_CLOTH.get(), ORANGE_CRAFTING_TABLE_CLOTH.get(), YELLOW_CRAFTING_TABLE_CLOTH.get(), LIME_CRAFTING_TABLE_CLOTH.get(),
                GREEN_CRAFTING_TABLE_CLOTH.get(), CYAN_CRAFTING_TABLE_CLOTH.get(), LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get(), BLUE_CRAFTING_TABLE_CLOTH.get(), PURPLE_CRAFTING_TABLE_CLOTH.get(),
                MAGENTA_CRAFTING_TABLE_CLOTH.get(), PINK_CRAFTING_TABLE_CLOTH.get());
        this.tag(STItemTags.VINYL_DISC_DYES).addTag(Tags.Items.DYES_LIGHT_GRAY).addTag(Tags.Items.DYES_GRAY);
        this.tag(STItemTags.RECORDABLE_DISCS).add(VINYL_DISC.get());

        // Common tags
        this.tag(Tags.Items.MUSIC_DISCS).add(VINYL_DISC.get(), RECORDED_DISC.get());
        this.tag(STItemTags.DYED_WATER_BUCKETS).add(DYED_WATER_BUCKET.get());
        this.tag(Tags.Items.BUCKETS).addTag(STItemTags.DYED_WATER_BUCKETS);

        this.tag(Tags.Items.DYED_WHITE).add(WHITE_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_LIGHT_GRAY).add(LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_GRAY).add(GRAY_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_BLACK).add(BLACK_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_BROWN).add(BROWN_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_RED).add(RED_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_ORANGE).add(ORANGE_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_YELLOW).add(YELLOW_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_LIME).add(LIME_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_GREEN).add(GREEN_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_CYAN).add(CYAN_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_LIGHT_BLUE).add(LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_BLUE).add(BLUE_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_PURPLE).add(PURPLE_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_MAGENTA).add(MAGENTA_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Items.DYED_PINK).add(PINK_CRAFTING_TABLE_CLOTH.get());

        // Minecraft tags
        this.tag(ItemTags.NON_FLAMMABLE_WOOD).add(CRIMSON_SHELF.get(), WARPED_SHELF.get());
        this.tag(ItemTags.DYEABLE).add(RECORDED_DISC.get(), DYED_WATER_BUCKET.get());
    }
}
