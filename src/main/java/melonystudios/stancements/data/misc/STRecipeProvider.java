package melonystudios.stancements.data.misc;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.util.tag.STItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class STRecipeProvider extends RecipeProvider {
    public STRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output, HolderLookup.Provider provider) {
        // Decorative blocks
        addShelf(output, STItems.OAK_SHELF, Items.OAK_PLANKS);
        addShelf(output, STItems.SPRUCE_SHELF, Items.SPRUCE_PLANKS);
        addShelf(output, STItems.BIRCH_SHELF, Items.BIRCH_PLANKS);
        addShelf(output, STItems.JUNGLE_SHELF, Items.JUNGLE_PLANKS);
        addShelf(output, STItems.ACACIA_SHELF, Items.ACACIA_PLANKS);
        addShelf(output, STItems.DARK_OAK_SHELF, Items.DARK_OAK_PLANKS);
        addShelf(output, STItems.CRIMSON_SHELF, Items.CRIMSON_PLANKS);
        addShelf(output, STItems.WARPED_SHELF, Items.WARPED_PLANKS);

        // Functional blocks
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, STItems.MUSIC_RECORDER).define('#', ItemTags.PLANKS).define('R', Tags.Items.DUSTS_REDSTONE).define('D', Tags.Items.DYES)
                .pattern("###").pattern("DRD").pattern("###").unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .save(output);

        // Items
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, STItems.VINYL_DISC, 4).define('C', ItemTags.COALS).define('D', STItemTags.VINYL_DISC_DYES).define('H', Items.HONEYCOMB)
                .pattern("DC").pattern("CH").unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
                .group("vinyl_disc").save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, STItems.VINYL_DISC).requires(STItems.RECORDED_DISC)
                .unlockedBy("has_recorded_disc", has(STItems.RECORDED_DISC))
                .group("vinyl_disc").save(output, Stancements.stancements("vinyl_disc_from_clearing"));
    }

    /// Makes a shelf recipe.
    /// @param output Used to save the recipe to a <code>.json</code> file.
    /// @param shelf The shelf item itself.
    /// @param planks The planks used to craft this shelf.
    public static void addShelf(RecipeOutput output, ItemLike shelf, ItemLike planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, shelf, 3).define('#', planks).define('S', Tags.Items.RODS_WOODEN)
                .pattern("###").pattern("S S").unlockedBy("has_planks", has(planks))
                .group("wooden_shelves").save(output);
    }
}
