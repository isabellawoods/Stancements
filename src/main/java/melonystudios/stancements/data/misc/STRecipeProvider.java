package melonystudios.stancements.data.misc;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.util.tag.STItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
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
        // Shelves
        addShelf(output, STItems.OAK_SHELF, Items.OAK_PLANKS);
        addShelf(output, STItems.SPRUCE_SHELF, Items.SPRUCE_PLANKS);
        addShelf(output, STItems.BIRCH_SHELF, Items.BIRCH_PLANKS);
        addShelf(output, STItems.JUNGLE_SHELF, Items.JUNGLE_PLANKS);
        addShelf(output, STItems.ACACIA_SHELF, Items.ACACIA_PLANKS);
        addShelf(output, STItems.DARK_OAK_SHELF, Items.DARK_OAK_PLANKS);
        addShelf(output, STItems.MANGROVE_SHELF, Items.MANGROVE_PLANKS);
        addShelf(output, STItems.CHERRY_SHELF, Items.CHERRY_PLANKS);
        addShelf(output, STItems.BAMBOO_SHELF, Items.BAMBOO_PLANKS);
        addShelf(output, STItems.CRIMSON_SHELF, Items.CRIMSON_PLANKS);
        addShelf(output, STItems.WARPED_SHELF, Items.WARPED_PLANKS);

        // Crafting Table Cloths
        addCraftingTableCloth(output, STItems.WHITE_CRAFTING_TABLE_CLOTH, Items.WHITE_CARPET);
        addCraftingTableCloth(output, STItems.LIGHT_GRAY_CRAFTING_TABLE_CLOTH, Items.LIGHT_GRAY_CARPET);
        addCraftingTableCloth(output, STItems.GRAY_CRAFTING_TABLE_CLOTH, Items.GRAY_CARPET);
        addCraftingTableCloth(output, STItems.BLACK_CRAFTING_TABLE_CLOTH, Items.BLACK_CARPET);
        addCraftingTableCloth(output, STItems.BROWN_CRAFTING_TABLE_CLOTH, Items.BROWN_CARPET);
        addCraftingTableCloth(output, STItems.RED_CRAFTING_TABLE_CLOTH, Items.RED_CARPET);
        addCraftingTableCloth(output, STItems.ORANGE_CRAFTING_TABLE_CLOTH, Items.ORANGE_CARPET);
        addCraftingTableCloth(output, STItems.YELLOW_CRAFTING_TABLE_CLOTH, Items.YELLOW_CARPET);
        addCraftingTableCloth(output, STItems.LIME_CRAFTING_TABLE_CLOTH, Items.LIME_CARPET);
        addCraftingTableCloth(output, STItems.GREEN_CRAFTING_TABLE_CLOTH, Items.GREEN_CARPET);
        addCraftingTableCloth(output, STItems.CYAN_CRAFTING_TABLE_CLOTH, Items.CYAN_CARPET);
        addCraftingTableCloth(output, STItems.LIGHT_BLUE_CRAFTING_TABLE_CLOTH, Items.LIGHT_BLUE_CARPET);
        addCraftingTableCloth(output, STItems.PURPLE_CRAFTING_TABLE_CLOTH, Items.PURPLE_CARPET);
        addCraftingTableCloth(output, STItems.MAGENTA_CRAFTING_TABLE_CLOTH, Items.MAGENTA_CARPET);
        addCraftingTableCloth(output, STItems.PINK_CRAFTING_TABLE_CLOTH, Items.PINK_CARPET);

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
    /// @param output Used to save the recipe to a `.json` file.
    /// @param shelf The shelf item itself.
    /// @param planks The planks used to craft this shelf.
    public static void addShelf(RecipeOutput output, ItemLike shelf, ItemLike planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, shelf, 3).define('#', planks).define('S', Tags.Items.RODS_WOODEN)
                .pattern("###").pattern("S S").unlockedBy("has_planks", has(planks))
                .group("wooden_shelves").save(output);
    }

    /// Makes a crafting table cloth recipe.
    /// @param output Used to save the recipe to a `.json` file.
    /// @param tableCloth The table cloth item.
    /// @param carpet The carpet used to craft the table cloth.
    public static void addCraftingTableCloth(RecipeOutput output, ItemLike tableCloth, Item carpet) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, tableCloth, 2).define('#', carpet)
                .pattern("##").unlockedBy("has_carpet", has(carpet))
                .group("crafting_table_cloths").save(output);
    }
}
