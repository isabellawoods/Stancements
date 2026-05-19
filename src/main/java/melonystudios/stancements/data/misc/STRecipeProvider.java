package melonystudios.stancements.data.misc;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.tag.STItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.crafting.DifferenceIngredient;

import java.util.concurrent.CompletableFuture;

public class STRecipeProvider extends RecipeProvider {
    /// Mod id for [*Railcraft Reborn*](https://modrinth.com/mod/railcraft-reborn).
    public static final String RAILCRAFT_MOD_ID = "railcraft";

    public STRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output, HolderLookup.Provider registries) {
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
        addCraftingTableCloth(output, STItems.BLUE_CRAFTING_TABLE_CLOTH, Items.BLUE_CARPET);
        addCraftingTableCloth(output, STItems.PURPLE_CRAFTING_TABLE_CLOTH, Items.PURPLE_CARPET);
        addCraftingTableCloth(output, STItems.MAGENTA_CRAFTING_TABLE_CLOTH, Items.MAGENTA_CARPET);
        addCraftingTableCloth(output, STItems.PINK_CRAFTING_TABLE_CLOTH, Items.PINK_CARPET);

        // Crafting table cloth dyeing
        dyeCraftingTableCloth(output, STItems.WHITE_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_WHITE);
        dyeCraftingTableCloth(output, STItems.LIGHT_GRAY_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_LIGHT_GRAY);
        dyeCraftingTableCloth(output, STItems.GRAY_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_GRAY);
        dyeCraftingTableCloth(output, STItems.BLACK_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_BLACK);
        dyeCraftingTableCloth(output, STItems.BROWN_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_BROWN);
        dyeCraftingTableCloth(output, STItems.RED_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_RED);
        dyeCraftingTableCloth(output, STItems.ORANGE_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_ORANGE);
        dyeCraftingTableCloth(output, STItems.YELLOW_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_YELLOW);
        dyeCraftingTableCloth(output, STItems.LIME_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_LIME);
        dyeCraftingTableCloth(output, STItems.GREEN_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_GREEN);
        dyeCraftingTableCloth(output, STItems.CYAN_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_CYAN);
        dyeCraftingTableCloth(output, STItems.LIGHT_BLUE_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_LIGHT_BLUE);
        dyeCraftingTableCloth(output, STItems.BLUE_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_BLUE);
        dyeCraftingTableCloth(output, STItems.PURPLE_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_PURPLE);
        dyeCraftingTableCloth(output, STItems.MAGENTA_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_MAGENTA);
        dyeCraftingTableCloth(output, STItems.PINK_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_PINK);

        // Concrete watering
        waterConcrete(output, Items.WHITE_CONCRETE_POWDER, Items.WHITE_CONCRETE);
        waterConcrete(output, Items.LIGHT_GRAY_CONCRETE_POWDER, Items.LIGHT_GRAY_CONCRETE);
        waterConcrete(output, Items.GRAY_CONCRETE_POWDER, Items.GRAY_CONCRETE);
        waterConcrete(output, Items.BLACK_CONCRETE_POWDER, Items.BLACK_CONCRETE);
        waterConcrete(output, Items.BROWN_CONCRETE_POWDER, Items.BROWN_CONCRETE);
        waterConcrete(output, Items.RED_CONCRETE_POWDER, Items.RED_CONCRETE);
        waterConcrete(output, Items.ORANGE_CONCRETE_POWDER, Items.ORANGE_CONCRETE);
        waterConcrete(output, Items.YELLOW_CONCRETE_POWDER, Items.YELLOW_CONCRETE);
        waterConcrete(output, Items.LIME_CONCRETE_POWDER, Items.LIME_CONCRETE);
        waterConcrete(output, Items.GREEN_CONCRETE_POWDER, Items.GREEN_CONCRETE);
        waterConcrete(output, Items.CYAN_CONCRETE_POWDER, Items.CYAN_CONCRETE);
        waterConcrete(output, Items.LIGHT_BLUE_CONCRETE_POWDER, Items.LIGHT_BLUE_CONCRETE);
        waterConcrete(output, Items.BLUE_CONCRETE_POWDER, Items.BLUE_CONCRETE);
        waterConcrete(output, Items.PURPLE_CONCRETE_POWDER, Items.PURPLE_CONCRETE);
        waterConcrete(output, Items.MAGENTA_CONCRETE_POWDER, Items.MAGENTA_CONCRETE);
        waterConcrete(output, Items.PINK_CONCRETE_POWDER, Items.PINK_CONCRETE);

        // Functional blocks
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, STItems.MUSIC_RECORDER).define('#', ItemTags.PLANKS).define('R', Tags.Items.DUSTS_REDSTONE).define('I', Tags.Items.GEMS_DIAMOND).define('D', Tags.Items.DYES)
                .pattern("#R#").pattern("DID").pattern("#R#").unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, STItems.CROP_POT, 8).define('#', Items.TERRACOTTA)
                .pattern("# #").pattern(" # ").unlockedBy("has_terracotta", has(Items.TERRACOTTA))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, STItems.hoppingCropPot(8)).define('#', Items.TERRACOTTA).define('H', Items.HOPPER)
                .pattern("#H#").pattern(" # ").unlockedBy("has_terracotta", has(Items.TERRACOTTA)).unlockedBy("has_hopper", has(Items.HOPPER))
                .group("hopping_crop_pot").save(output, Stancements.stancements("hopping_crop_pot"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, STItems.hoppingCropPot(8)).define('#', STItems.CROP_POT).define('H', Items.HOPPER)
                .pattern("###").pattern("#H#").pattern("###").unlockedBy("has_hopper", has(Items.HOPPER))
                .group("hopping_crop_pot").save(output, Stancements.stancements("hopping_crop_pot_from_existing"));

        // Rails
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, STItems.GILDED_RAIL, 6).define('#', Tags.Items.INGOTS_GOLD).define('R', Tags.Items.DUSTS_REDSTONE).define('S', Tags.Items.RODS_WOODEN)
                .pattern("# #").pattern("#S#").pattern("#R#").unlockedBy("has_rail", has(Items.RAIL))
                .save(output.withConditions(new NotCondition(new ModLoadedCondition(RAILCRAFT_MOD_ID))));
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, Items.POWERED_RAIL, 6).define('#', Tags.Items.INGOTS_COPPER).define('R', Tags.Items.DUSTS_REDSTONE).define('S', Tags.Items.RODS_WOODEN)
                .pattern("# #").pattern("#S#").pattern("#R#").unlockedBy("has_rail", has(Items.RAIL))
                .save(output.withConditions(new NotCondition(new ModLoadedCondition(RAILCRAFT_MOD_ID))));
        addTaggingRail(output, STItems.WHITE_TAGGING_RAIL, Tags.Items.DYES_WHITE);
        addTaggingRail(output, STItems.LIGHT_GRAY_TAGGING_RAIL, Tags.Items.DYES_LIGHT_GRAY);
        addTaggingRail(output, STItems.GRAY_TAGGING_RAIL, Tags.Items.DYES_GRAY);
        addTaggingRail(output, STItems.BLACK_TAGGING_RAIL, Tags.Items.DYES_BLACK);
        addTaggingRail(output, STItems.BROWN_TAGGING_RAIL, Tags.Items.DYES_BROWN);
        addTaggingRail(output, STItems.RED_TAGGING_RAIL, Tags.Items.DYES_RED);
        addTaggingRail(output, STItems.ORANGE_TAGGING_RAIL, Tags.Items.DYES_ORANGE);
        addTaggingRail(output, STItems.YELLOW_TAGGING_RAIL, Tags.Items.DYES_YELLOW);
        addTaggingRail(output, STItems.LIME_TAGGING_RAIL, Tags.Items.DYES_LIME);
        addTaggingRail(output, STItems.GREEN_TAGGING_RAIL, Tags.Items.DYES_GREEN);
        addTaggingRail(output, STItems.CYAN_TAGGING_RAIL, Tags.Items.DYES_CYAN);
        addTaggingRail(output, STItems.LIGHT_BLUE_TAGGING_RAIL, Tags.Items.DYES_LIGHT_BLUE);
        addTaggingRail(output, STItems.BLUE_TAGGING_RAIL, Tags.Items.DYES_BLUE);
        addTaggingRail(output, STItems.PURPLE_TAGGING_RAIL, Tags.Items.DYES_PURPLE);
        addTaggingRail(output, STItems.MAGENTA_TAGGING_RAIL, Tags.Items.DYES_MAGENTA);
        addTaggingRail(output, STItems.PINK_TAGGING_RAIL, Tags.Items.DYES_PINK);
        dyeTaggingRail(output, STItems.WHITE_TAGGING_RAIL, Tags.Items.DYES_WHITE);
        dyeTaggingRail(output, STItems.LIGHT_GRAY_TAGGING_RAIL, Tags.Items.DYES_LIGHT_GRAY);
        dyeTaggingRail(output, STItems.GRAY_TAGGING_RAIL, Tags.Items.DYES_GRAY);
        dyeTaggingRail(output, STItems.BLACK_TAGGING_RAIL, Tags.Items.DYES_BLACK);
        dyeTaggingRail(output, STItems.BROWN_TAGGING_RAIL, Tags.Items.DYES_BROWN);
        dyeTaggingRail(output, STItems.RED_TAGGING_RAIL, Tags.Items.DYES_RED);
        dyeTaggingRail(output, STItems.ORANGE_TAGGING_RAIL, Tags.Items.DYES_ORANGE);
        dyeTaggingRail(output, STItems.YELLOW_TAGGING_RAIL, Tags.Items.DYES_YELLOW);
        dyeTaggingRail(output, STItems.LIME_TAGGING_RAIL, Tags.Items.DYES_LIME);
        dyeTaggingRail(output, STItems.GREEN_TAGGING_RAIL, Tags.Items.DYES_GREEN);
        dyeTaggingRail(output, STItems.CYAN_TAGGING_RAIL, Tags.Items.DYES_CYAN);
        dyeTaggingRail(output, STItems.LIGHT_BLUE_TAGGING_RAIL, Tags.Items.DYES_LIGHT_BLUE);
        dyeTaggingRail(output, STItems.BLUE_TAGGING_RAIL, Tags.Items.DYES_BLUE);
        dyeTaggingRail(output, STItems.PURPLE_TAGGING_RAIL, Tags.Items.DYES_PURPLE);
        dyeTaggingRail(output, STItems.MAGENTA_TAGGING_RAIL, Tags.Items.DYES_MAGENTA);
        dyeTaggingRail(output, STItems.PINK_TAGGING_RAIL, Tags.Items.DYES_PINK);

        // Items
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, STItems.VINYL_DISC, 4).define('C', ItemTags.COALS).define('D', STItemTags.VINYL_DISC_DYES).define('H', Items.HONEYCOMB)
                .pattern("DC").pattern("CH").unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
                .group("vinyl_disc").save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, STItems.VINYL_DISC).requires(STItems.RECORDED_DISC)
                .unlockedBy("has_recorded_disc", has(STItems.RECORDED_DISC))
                .group("vinyl_disc").save(output, Stancements.stancements("vinyl_disc_from_clearing"));

        // Minecart tags
        addMinecartTag(output, STItems.WHITE_TAG, Tags.Items.DYES_WHITE);
        addMinecartTag(output, STItems.LIGHT_GRAY_TAG, Tags.Items.DYES_LIGHT_GRAY);
        addMinecartTag(output, STItems.GRAY_TAG, Tags.Items.DYES_GRAY);
        addMinecartTag(output, STItems.BLACK_TAG, Tags.Items.DYES_BLACK);
        addMinecartTag(output, STItems.BROWN_TAG, Tags.Items.DYES_BROWN);
        addMinecartTag(output, STItems.RED_TAG, Tags.Items.DYES_RED);
        addMinecartTag(output, STItems.ORANGE_TAG, Tags.Items.DYES_ORANGE);
        addMinecartTag(output, STItems.YELLOW_TAG, Tags.Items.DYES_YELLOW);
        addMinecartTag(output, STItems.LIME_TAG, Tags.Items.DYES_LIME);
        addMinecartTag(output, STItems.GREEN_TAG, Tags.Items.DYES_GREEN);
        addMinecartTag(output, STItems.CYAN_TAG, Tags.Items.DYES_CYAN);
        addMinecartTag(output, STItems.LIGHT_BLUE_TAG, Tags.Items.DYES_LIGHT_BLUE);
        addMinecartTag(output, STItems.BLUE_TAG, Tags.Items.DYES_BLUE);
        addMinecartTag(output, STItems.PURPLE_TAG, Tags.Items.DYES_PURPLE);
        addMinecartTag(output, STItems.MAGENTA_TAG, Tags.Items.DYES_MAGENTA);
        addMinecartTag(output, STItems.PINK_TAG, Tags.Items.DYES_PINK);
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

    /// Makes a crafting table cloth coloring/dyeing recipe.
    /// @param output Used to save the recipe to a `.json` file.
    /// @param tableCloth The table cloth item.
    /// @param dyeTag An item tag for the dye to color with, like {@link Tags.Items#DYES_LIGHT_BLUE #c:dyes/light_blue}.
    public static void dyeCraftingTableCloth(RecipeOutput output, ItemLike tableCloth, TagKey<Item> dyeTag) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(tableCloth.asItem());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, tableCloth).requires(dyeTag).requires(DifferenceIngredient.of(Ingredient.of(STItemTags.CRAFTING_TABLE_CLOTHS), Ingredient.of(tableCloth)))
                .unlockedBy("has_needed_dye", has(dyeTag)).group("crafting_table_cloths")
                .save(output, ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "dye_" + location.getPath()));
    }

    /// Makes a recipe for watering concrete powder into concrete using a water bucket.
    /// @param output Used to save the recipe to a `.json` file.
    /// @param concretePowder The concrete powder item.
    /// @param concrete The concrete item.
    public static void waterConcrete(RecipeOutput output, ItemLike concretePowder, ItemLike concrete) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(concrete.asItem());
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, concrete, 8).define('#', concretePowder).define('W', Tags.Items.BUCKETS_WATER)
                .pattern("###").pattern("#W#").pattern("###").unlockedBy("has_concrete_powder", has(concretePowder))
                .group("dye_concrete").save(output, Stancements.stancements(location.getPath() + "_from_watering"));
    }

    /// Makes a minecart tag recipe.
    /// @param output Used to save the recipe to a `.json` file.
    /// @param tag The tag item.
    /// @param dyeTag An item tag for the dye to color with, like {@link Tags.Items#DYES_PINK #c:dyes/pink}.
    public static void addMinecartTag(RecipeOutput output, ItemLike tag, TagKey<Item> dyeTag) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, tag).requires(Items.PAPER).requires(Tags.Items.STRINGS).requires(dyeTag)
                .unlockedBy("has_paper", has(Items.PAPER)).group("minecart_tags")
                .save(output);
    }

    /// Makes a tagging rail recipe.
    /// @param output Use to save the recipe to a `.json` file.
    /// @param rail The tagging rail item.
    /// @param dyeTag An item tag for the dye to color with, like {@link Tags.Items#DYES_WHITE #c:dyes/white}.
    public static void addTaggingRail(RecipeOutput output, ItemLike rail, TagKey<Item> dyeTag) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, rail, 6).define('#', Tags.Items.INGOTS_IRON).define('R', Tags.Items.DUSTS_REDSTONE).define('S', Items.STONE_PRESSURE_PLATE).define('D', dyeTag)
                .pattern("#D#").pattern("#R#").pattern("#S#").unlockedBy("has_rail", has(Items.RAIL))
                .save(output);
    }

    /// Makes a tagging rails dyeing/coloring recipe.
    /// @param output Used to save the recipe to a `.json` file.
    /// @param rail The tagging rail item.
    /// @param dyeTag An item tag for the dye to color with, like {@link Tags.Items#DYES_PINK #c:dyes/pink}.
    public static void dyeTaggingRail(RecipeOutput output, ItemLike rail, TagKey<Item> dyeTag) {
        Ingredient ingredient = DifferenceIngredient.of(Ingredient.of(STItemTags.TAGGING_RAILS), Ingredient.of(rail));
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(rail.asItem());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, rail, 6).requires(ingredient, 6).requires(dyeTag)
                .unlockedBy("has_tagging_rail", has(STItemTags.TAGGING_RAILS))
                .group("dye_tagging_rail").save(output, ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "dye_" + location.getPath()));
    }
}
