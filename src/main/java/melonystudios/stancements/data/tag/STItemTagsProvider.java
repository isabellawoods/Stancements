package melonystudios.stancements.data.tag;

import melonystudios.reutilities.util.tag.ReItemTags;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.tag.STItemTags;
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
        return Stancements.generatorName("Item Tags");
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        // Stancements' tags
        this.tag(STItemTags.SHELVES).add(OAK_SHELF.get(), SPRUCE_SHELF.get(), BIRCH_SHELF.get(), JUNGLE_SHELF.get(), ACACIA_SHELF.get(), DARK_OAK_SHELF.get(), MANGROVE_SHELF.get(), CHERRY_SHELF.get(),
                PALE_OAK_SHELF.get(), BAMBOO_SHELF.get(), CRIMSON_SHELF.get(), WARPED_SHELF.get());
        this.tag(STItemTags.CRAFTING_TABLE_CLOTHS).add(WHITE_CRAFTING_TABLE_CLOTH.get(), LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get(), GRAY_CRAFTING_TABLE_CLOTH.get(), BLACK_CRAFTING_TABLE_CLOTH.get(),
                BROWN_CRAFTING_TABLE_CLOTH.get(), RED_CRAFTING_TABLE_CLOTH.get(), ORANGE_CRAFTING_TABLE_CLOTH.get(), YELLOW_CRAFTING_TABLE_CLOTH.get(), LIME_CRAFTING_TABLE_CLOTH.get(),
                GREEN_CRAFTING_TABLE_CLOTH.get(), CYAN_CRAFTING_TABLE_CLOTH.get(), LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get(), BLUE_CRAFTING_TABLE_CLOTH.get(), PURPLE_CRAFTING_TABLE_CLOTH.get(),
                MAGENTA_CRAFTING_TABLE_CLOTH.get(), PINK_CRAFTING_TABLE_CLOTH.get());
        this.tag(STItemTags.TAGGING_RAILS).add(WHITE_TAGGING_RAIL.get(), LIGHT_GRAY_TAGGING_RAIL.get(), GRAY_TAGGING_RAIL.get(), BLACK_TAGGING_RAIL.get(), BROWN_TAGGING_RAIL.get(), RED_TAGGING_RAIL.get(),
                ORANGE_TAGGING_RAIL.get(), YELLOW_TAGGING_RAIL.get(), LIME_TAGGING_RAIL.get(), GREEN_TAGGING_RAIL.get(), CYAN_TAGGING_RAIL.get(), LIGHT_BLUE_TAGGING_RAIL.get(), BLUE_TAGGING_RAIL.get(),
                PURPLE_TAGGING_RAIL.get(), MAGENTA_TAGGING_RAIL.get(), PINK_TAGGING_RAIL.get());
        this.tag(STItemTags.VINYL_DISC_DYES).addTag(Tags.Items.DYES_LIGHT_GRAY).addTag(Tags.Items.DYES_GRAY);
        this.tag(STItemTags.MINECART_TAGS).add(WHITE_TAG.get(), LIGHT_GRAY_TAG.get(), GRAY_TAG.get(), BLACK_TAG.get(), BROWN_TAG.get(), RED_TAG.get(), ORANGE_TAG.get(), YELLOW_TAG.get(), LIME_TAG.get(),
                GREEN_TAG.get(), CYAN_TAG.get(), LIGHT_BLUE_TAG.get(), BLUE_TAG.get(), PURPLE_TAG.get(), MAGENTA_TAG.get(), PINK_TAG.get());

        // Common tags
        this.tag(Tags.Items.MUSIC_DISCS).add(VINYL_DISC.get(), RECORDED_DISC.get());
        this.tag(Tags.Items.BUCKETS).addTag(STItemTags.DYED_WATER_BUCKETS);
        this.tag(ReItemTags.LOGOS).add(STANCEMENTS_LOGO.get());
        this.tag(STItemTags.DYED_WATER_BUCKETS).add(DYED_WATER_BUCKET.get());

        this.tag(Tags.Items.DYED_WHITE).add(WHITE_CRAFTING_TABLE_CLOTH.get(), WHITE_TAGGING_RAIL.get(), WHITE_TAG.get());
        this.tag(Tags.Items.DYED_LIGHT_GRAY).add(LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get(), LIGHT_GRAY_TAGGING_RAIL.get(), LIGHT_GRAY_TAG.get());
        this.tag(Tags.Items.DYED_GRAY).add(GRAY_CRAFTING_TABLE_CLOTH.get(), GRAY_TAGGING_RAIL.get(), GRAY_TAG.get());
        this.tag(Tags.Items.DYED_BLACK).add(BLACK_CRAFTING_TABLE_CLOTH.get(), BLACK_TAGGING_RAIL.get(), BLACK_TAG.get());
        this.tag(Tags.Items.DYED_BROWN).add(BROWN_CRAFTING_TABLE_CLOTH.get(), BROWN_TAGGING_RAIL.get(), BROWN_TAG.get());
        this.tag(Tags.Items.DYED_RED).add(RED_CRAFTING_TABLE_CLOTH.get(), RED_TAGGING_RAIL.get(), RED_TAG.get());
        this.tag(Tags.Items.DYED_ORANGE).add(ORANGE_CRAFTING_TABLE_CLOTH.get(), ORANGE_TAGGING_RAIL.get(), ORANGE_TAG.get());
        this.tag(Tags.Items.DYED_YELLOW).add(YELLOW_CRAFTING_TABLE_CLOTH.get(), YELLOW_TAGGING_RAIL.get(), YELLOW_TAG.get());
        this.tag(Tags.Items.DYED_LIME).add(LIME_CRAFTING_TABLE_CLOTH.get(), LIME_TAGGING_RAIL.get(), LIME_TAG.get());
        this.tag(Tags.Items.DYED_GREEN).add(GREEN_CRAFTING_TABLE_CLOTH.get(), GREEN_TAGGING_RAIL.get(), GREEN_TAG.get());
        this.tag(Tags.Items.DYED_CYAN).add(CYAN_CRAFTING_TABLE_CLOTH.get(), CYAN_TAGGING_RAIL.get(), CYAN_TAG.get());
        this.tag(Tags.Items.DYED_LIGHT_BLUE).add(LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get(), LIGHT_BLUE_TAGGING_RAIL.get(), LIGHT_BLUE_TAG.get());
        this.tag(Tags.Items.DYED_BLUE).add(BLUE_CRAFTING_TABLE_CLOTH.get(), BLUE_TAGGING_RAIL.get(), BLUE_TAG.get());
        this.tag(Tags.Items.DYED_PURPLE).add(PURPLE_CRAFTING_TABLE_CLOTH.get(), PURPLE_TAGGING_RAIL.get(), PURPLE_TAG.get());
        this.tag(Tags.Items.DYED_MAGENTA).add(MAGENTA_CRAFTING_TABLE_CLOTH.get(), MAGENTA_TAGGING_RAIL.get(), MAGENTA_TAG.get());
        this.tag(Tags.Items.DYED_PINK).add(PINK_CRAFTING_TABLE_CLOTH.get(), PINK_TAGGING_RAIL.get(), PINK_TAG.get());

        // Minecraft tags
        this.tag(ItemTags.NON_FLAMMABLE_WOOD).add(CRIMSON_SHELF.get(), WARPED_SHELF.get());
        this.tag(ItemTags.DYEABLE).add(RECORDED_DISC.get(), DYED_WATER_BUCKET.get());
    }
}
