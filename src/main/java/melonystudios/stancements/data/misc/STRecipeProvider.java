package melonystudios.stancements.data.misc;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.tag.STItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.Identifier;
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
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class STRecipeProvider extends RecipeProvider {
    /// Mod id for [*Railcraft Reborn*](https://modrinth.com/mod/railcraft-reborn).
    public static final String RAILCRAFT_MOD_ID = "railcraft";

    public STRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        @NotNull
        public String getName() {
            return Stancements.generatorName("Recipes");
        }

        @Override
        @NotNull
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new STRecipeProvider(registries, output);
        }
    }

    @Override
    protected void buildRecipes() {
        // Decorative blocks
        // Shelves
        this.addShelf(STItems.OAK_SHELF, Items.OAK_PLANKS);
        this.addShelf(STItems.SPRUCE_SHELF, Items.SPRUCE_PLANKS);
        this.addShelf(STItems.BIRCH_SHELF, Items.BIRCH_PLANKS);
        this.addShelf(STItems.JUNGLE_SHELF, Items.JUNGLE_PLANKS);
        this.addShelf(STItems.ACACIA_SHELF, Items.ACACIA_PLANKS);
        this.addShelf(STItems.DARK_OAK_SHELF, Items.DARK_OAK_PLANKS);
        this.addShelf(STItems.MANGROVE_SHELF, Items.MANGROVE_PLANKS);
        this.addShelf(STItems.CHERRY_SHELF, Items.CHERRY_PLANKS);
        this.addShelf(STItems.BAMBOO_SHELF, Items.BAMBOO_PLANKS);
        this.addShelf(STItems.CRIMSON_SHELF, Items.CRIMSON_PLANKS);
        this.addShelf(STItems.WARPED_SHELF, Items.WARPED_PLANKS);

        // Crafting table cloths
        this.addCraftingTableCloth(STItems.WHITE_CRAFTING_TABLE_CLOTH, Items.WHITE_CARPET);
        this.addCraftingTableCloth(STItems.LIGHT_GRAY_CRAFTING_TABLE_CLOTH, Items.LIGHT_GRAY_CARPET);
        this.addCraftingTableCloth(STItems.GRAY_CRAFTING_TABLE_CLOTH, Items.GRAY_CARPET);
        this.addCraftingTableCloth(STItems.BLACK_CRAFTING_TABLE_CLOTH, Items.BLACK_CARPET);
        this.addCraftingTableCloth(STItems.BROWN_CRAFTING_TABLE_CLOTH, Items.BROWN_CARPET);
        this.addCraftingTableCloth(STItems.RED_CRAFTING_TABLE_CLOTH, Items.RED_CARPET);
        this.addCraftingTableCloth(STItems.ORANGE_CRAFTING_TABLE_CLOTH, Items.ORANGE_CARPET);
        this.addCraftingTableCloth(STItems.YELLOW_CRAFTING_TABLE_CLOTH, Items.YELLOW_CARPET);
        this.addCraftingTableCloth(STItems.LIME_CRAFTING_TABLE_CLOTH, Items.LIME_CARPET);
        this.addCraftingTableCloth(STItems.GREEN_CRAFTING_TABLE_CLOTH, Items.GREEN_CARPET);
        this.addCraftingTableCloth(STItems.CYAN_CRAFTING_TABLE_CLOTH, Items.CYAN_CARPET);
        this.addCraftingTableCloth(STItems.LIGHT_BLUE_CRAFTING_TABLE_CLOTH, Items.LIGHT_BLUE_CARPET);
        this.addCraftingTableCloth(STItems.BLUE_CRAFTING_TABLE_CLOTH, Items.BLUE_CARPET);
        this.addCraftingTableCloth(STItems.PURPLE_CRAFTING_TABLE_CLOTH, Items.PURPLE_CARPET);
        this.addCraftingTableCloth(STItems.MAGENTA_CRAFTING_TABLE_CLOTH, Items.MAGENTA_CARPET);
        this.addCraftingTableCloth(STItems.PINK_CRAFTING_TABLE_CLOTH, Items.PINK_CARPET);

        // Crafting table cloth dyeing
        this.dyeCraftingTableCloth(STItems.WHITE_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_WHITE);
        this.dyeCraftingTableCloth(STItems.LIGHT_GRAY_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_LIGHT_GRAY);
        this.dyeCraftingTableCloth(STItems.GRAY_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_GRAY);
        this.dyeCraftingTableCloth(STItems.BLACK_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_BLACK);
        this.dyeCraftingTableCloth(STItems.BROWN_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_BROWN);
        this.dyeCraftingTableCloth(STItems.RED_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_RED);
        this.dyeCraftingTableCloth(STItems.ORANGE_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_ORANGE);
        this.dyeCraftingTableCloth(STItems.YELLOW_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_YELLOW);
        this.dyeCraftingTableCloth(STItems.LIME_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_LIME);
        this.dyeCraftingTableCloth(STItems.GREEN_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_GREEN);
        this.dyeCraftingTableCloth(STItems.CYAN_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_CYAN);
        this.dyeCraftingTableCloth(STItems.LIGHT_BLUE_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_LIGHT_BLUE);
        this.dyeCraftingTableCloth(STItems.BLUE_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_BLUE);
        this.dyeCraftingTableCloth(STItems.PURPLE_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_PURPLE);
        this.dyeCraftingTableCloth(STItems.MAGENTA_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_MAGENTA);
        this.dyeCraftingTableCloth(STItems.PINK_CRAFTING_TABLE_CLOTH, Tags.Items.DYES_PINK);

        // Concrete watering
        this.waterConcrete(Items.WHITE_CONCRETE_POWDER, Items.WHITE_CONCRETE);
        this.waterConcrete(Items.LIGHT_GRAY_CONCRETE_POWDER, Items.LIGHT_GRAY_CONCRETE);
        this.waterConcrete(Items.GRAY_CONCRETE_POWDER, Items.GRAY_CONCRETE);
        this.waterConcrete(Items.BLACK_CONCRETE_POWDER, Items.BLACK_CONCRETE);
        this.waterConcrete(Items.BROWN_CONCRETE_POWDER, Items.BROWN_CONCRETE);
        this.waterConcrete(Items.RED_CONCRETE_POWDER, Items.RED_CONCRETE);
        this.waterConcrete(Items.ORANGE_CONCRETE_POWDER, Items.ORANGE_CONCRETE);
        this.waterConcrete(Items.YELLOW_CONCRETE_POWDER, Items.YELLOW_CONCRETE);
        this.waterConcrete(Items.LIME_CONCRETE_POWDER, Items.LIME_CONCRETE);
        this.waterConcrete(Items.GREEN_CONCRETE_POWDER, Items.GREEN_CONCRETE);
        this.waterConcrete(Items.CYAN_CONCRETE_POWDER, Items.CYAN_CONCRETE);
        this.waterConcrete(Items.LIGHT_BLUE_CONCRETE_POWDER, Items.LIGHT_BLUE_CONCRETE);
        this.waterConcrete(Items.BLUE_CONCRETE_POWDER, Items.BLUE_CONCRETE);
        this.waterConcrete(Items.PURPLE_CONCRETE_POWDER, Items.PURPLE_CONCRETE);
        this.waterConcrete(Items.MAGENTA_CONCRETE_POWDER, Items.MAGENTA_CONCRETE);
        this.waterConcrete(Items.PINK_CONCRETE_POWDER, Items.PINK_CONCRETE);

        // Functional blocks
        this.shaped(RecipeCategory.REDSTONE, STItems.MUSIC_RECORDER).define('#', ItemTags.PLANKS).define('R', Tags.Items.DUSTS_REDSTONE).define('I', Tags.Items.GEMS_DIAMOND).define('D', Tags.Items.DYES)
                .pattern("#R#").pattern("DID").pattern("#R#").unlockedBy("has_diamond", this.has(Tags.Items.GEMS_DIAMOND))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, STItems.CROP_POT, 8).define('#', Items.TERRACOTTA)
                .pattern("# #").pattern(" # ").unlockedBy("has_terracotta", this.has(Items.TERRACOTTA))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, STItems.hoppingCropPot(8)).define('#', Items.TERRACOTTA).define('H', Items.HOPPER)
                .pattern("#H#").pattern(" # ").unlockedBy("has_terracotta", this.has(Items.TERRACOTTA)).unlockedBy("has_hopper", this.has(Items.HOPPER))
                .group("hopping_crop_pot").save(this.output, Stancements.stancements("hopping_crop_pot").toString());
        this.shaped(RecipeCategory.MISC, STItems.hoppingCropPot(8)).define('#', STItems.CROP_POT).define('H', Items.HOPPER)
                .pattern("###").pattern("#H#").pattern("###").unlockedBy("has_hopper", this.has(Items.HOPPER))
                .group("hopping_crop_pot").save(this.output, Stancements.stancements("hopping_crop_pot_from_existing").toString());

        // Rails
        this.shaped(RecipeCategory.TRANSPORTATION, STItems.GILDED_RAIL, 6).define('#', Tags.Items.INGOTS_GOLD).define('R', Tags.Items.DUSTS_REDSTONE).define('S', Tags.Items.RODS_WOODEN)
                .pattern("# #").pattern("#S#").pattern("#R#").unlockedBy("has_rail", this.has(Items.RAIL))
                .save(this.output.withConditions(new NotCondition(new ModLoadedCondition(RAILCRAFT_MOD_ID))));
        this.shaped(RecipeCategory.TRANSPORTATION, Items.POWERED_RAIL, 6).define('#', Tags.Items.INGOTS_COPPER).define('R', Tags.Items.DUSTS_REDSTONE).define('S', Tags.Items.RODS_WOODEN)
                .pattern("# #").pattern("#S#").pattern("#R#").unlockedBy("has_rail", this.has(Items.RAIL))
                .save(this.output.withConditions(new NotCondition(new ModLoadedCondition(RAILCRAFT_MOD_ID))));

        // Tagging rails
        this.addTaggingRail(STItems.WHITE_TAGGING_RAIL, Tags.Items.DYES_WHITE);
        this.addTaggingRail(STItems.LIGHT_GRAY_TAGGING_RAIL, Tags.Items.DYES_LIGHT_GRAY);
        this.addTaggingRail(STItems.GRAY_TAGGING_RAIL, Tags.Items.DYES_GRAY);
        this.addTaggingRail(STItems.BLACK_TAGGING_RAIL, Tags.Items.DYES_BLACK);
        this.addTaggingRail(STItems.BROWN_TAGGING_RAIL, Tags.Items.DYES_BROWN);
        this.addTaggingRail(STItems.RED_TAGGING_RAIL, Tags.Items.DYES_RED);
        this.addTaggingRail(STItems.ORANGE_TAGGING_RAIL, Tags.Items.DYES_ORANGE);
        this.addTaggingRail(STItems.YELLOW_TAGGING_RAIL, Tags.Items.DYES_YELLOW);
        this.addTaggingRail(STItems.LIME_TAGGING_RAIL, Tags.Items.DYES_LIME);
        this.addTaggingRail(STItems.GREEN_TAGGING_RAIL, Tags.Items.DYES_GREEN);
        this.addTaggingRail(STItems.CYAN_TAGGING_RAIL, Tags.Items.DYES_CYAN);
        this.addTaggingRail(STItems.LIGHT_BLUE_TAGGING_RAIL, Tags.Items.DYES_LIGHT_BLUE);
        this.addTaggingRail(STItems.BLUE_TAGGING_RAIL, Tags.Items.DYES_BLUE);
        this.addTaggingRail(STItems.PURPLE_TAGGING_RAIL, Tags.Items.DYES_PURPLE);
        this.addTaggingRail(STItems.MAGENTA_TAGGING_RAIL, Tags.Items.DYES_MAGENTA);
        this.addTaggingRail(STItems.PINK_TAGGING_RAIL, Tags.Items.DYES_PINK);

        // Tagging rail dyeing
        this.dyeTaggingRail(STItems.WHITE_TAGGING_RAIL, Tags.Items.DYES_WHITE);
        this.dyeTaggingRail(STItems.LIGHT_GRAY_TAGGING_RAIL, Tags.Items.DYES_LIGHT_GRAY);
        this.dyeTaggingRail(STItems.GRAY_TAGGING_RAIL, Tags.Items.DYES_GRAY);
        this.dyeTaggingRail(STItems.BLACK_TAGGING_RAIL, Tags.Items.DYES_BLACK);
        this.dyeTaggingRail(STItems.BROWN_TAGGING_RAIL, Tags.Items.DYES_BROWN);
        this.dyeTaggingRail(STItems.RED_TAGGING_RAIL, Tags.Items.DYES_RED);
        this.dyeTaggingRail(STItems.ORANGE_TAGGING_RAIL, Tags.Items.DYES_ORANGE);
        this.dyeTaggingRail(STItems.YELLOW_TAGGING_RAIL, Tags.Items.DYES_YELLOW);
        this.dyeTaggingRail(STItems.LIME_TAGGING_RAIL, Tags.Items.DYES_LIME);
        this.dyeTaggingRail(STItems.GREEN_TAGGING_RAIL, Tags.Items.DYES_GREEN);
        this.dyeTaggingRail(STItems.CYAN_TAGGING_RAIL, Tags.Items.DYES_CYAN);
        this.dyeTaggingRail(STItems.LIGHT_BLUE_TAGGING_RAIL, Tags.Items.DYES_LIGHT_BLUE);
        this.dyeTaggingRail(STItems.BLUE_TAGGING_RAIL, Tags.Items.DYES_BLUE);
        this.dyeTaggingRail(STItems.PURPLE_TAGGING_RAIL, Tags.Items.DYES_PURPLE);
        this.dyeTaggingRail(STItems.MAGENTA_TAGGING_RAIL, Tags.Items.DYES_MAGENTA);
        this.dyeTaggingRail(STItems.PINK_TAGGING_RAIL, Tags.Items.DYES_PINK);

        // Items
        this.shaped(RecipeCategory.MISC, STItems.VINYL_DISC, 4).define('C', ItemTags.COALS).define('D', STItemTags.VINYL_DISC_DYES).define('H', Items.HONEYCOMB)
                .pattern("DC").pattern("CH").unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
                .group("vinyl_disc").save(this.output);
        this.shapeless(RecipeCategory.MISC, STItems.VINYL_DISC).requires(STItems.RECORDED_DISC)
                .unlockedBy("has_recorded_disc", has(STItems.RECORDED_DISC))
                .group("vinyl_disc").save(this.output, Stancements.stancements("vinyl_disc_from_clearing").toString());

        // Minecart tags
        this.addMinecartTag(STItems.WHITE_TAG, Tags.Items.DYES_WHITE);
        this.addMinecartTag(STItems.LIGHT_GRAY_TAG, Tags.Items.DYES_LIGHT_GRAY);
        this.addMinecartTag(STItems.GRAY_TAG, Tags.Items.DYES_GRAY);
        this.addMinecartTag(STItems.BLACK_TAG, Tags.Items.DYES_BLACK);
        this.addMinecartTag(STItems.BROWN_TAG, Tags.Items.DYES_BROWN);
        this.addMinecartTag(STItems.RED_TAG, Tags.Items.DYES_RED);
        this.addMinecartTag(STItems.ORANGE_TAG, Tags.Items.DYES_ORANGE);
        this.addMinecartTag(STItems.YELLOW_TAG, Tags.Items.DYES_YELLOW);
        this.addMinecartTag(STItems.LIME_TAG, Tags.Items.DYES_LIME);
        this.addMinecartTag(STItems.GREEN_TAG, Tags.Items.DYES_GREEN);
        this.addMinecartTag(STItems.CYAN_TAG, Tags.Items.DYES_CYAN);
        this.addMinecartTag(STItems.LIGHT_BLUE_TAG, Tags.Items.DYES_LIGHT_BLUE);
        this.addMinecartTag(STItems.BLUE_TAG, Tags.Items.DYES_BLUE);
        this.addMinecartTag(STItems.PURPLE_TAG, Tags.Items.DYES_PURPLE);
        this.addMinecartTag(STItems.MAGENTA_TAG, Tags.Items.DYES_MAGENTA);
        this.addMinecartTag(STItems.PINK_TAG, Tags.Items.DYES_PINK);
    }

    /// Makes a shelf recipe.
    /// @param shelf The shelf item itself.
    /// @param planks The planks used to craft this shelf.
    public void addShelf(ItemLike shelf, ItemLike planks) {
        this.shaped(RecipeCategory.DECORATIONS, shelf, 3).define('#', planks).define('S', Tags.Items.RODS_WOODEN)
                .pattern("###").pattern("S S").unlockedBy("has_planks", this.has(planks))
                .group("wooden_shelves").save(this.output);
    }

    /// Makes a crafting table cloth recipe.
    /// @param tableCloth The table cloth item.
    /// @param carpet The carpet used to craft the table cloth.
    public void addCraftingTableCloth(ItemLike tableCloth, Item carpet) {
        this.shaped(RecipeCategory.DECORATIONS, tableCloth, 2).define('#', carpet)
                .pattern("##").unlockedBy("has_carpet", this.has(carpet))
                .group("crafting_table_cloths").save(this.output);
    }

    /// Makes a crafting table cloth coloring/dyeing recipe.
    /// @param tableCloth The table cloth item.
    /// @param dyeTag An item tag for the dye to color with, like {@link Tags.Items#DYES_LIGHT_BLUE #c:dyes/light_blue}.
    public void dyeCraftingTableCloth(ItemLike tableCloth, TagKey<Item> dyeTag) {
        Identifier location = BuiltInRegistries.ITEM.getKey(tableCloth.asItem());
        this.shapeless(RecipeCategory.DECORATIONS, tableCloth).requires(dyeTag).requires(DifferenceIngredient.of(Ingredient.of(this.items.getOrThrow(STItemTags.CRAFTING_TABLE_CLOTHS)), Ingredient.of(tableCloth)))
                .unlockedBy("has_needed_dye", this.has(dyeTag)).group("crafting_table_cloths")
                .save(this.output, Identifier.fromNamespaceAndPath(location.getNamespace(), "dye_" + location.getPath()).toString());
    }

    /// Makes a recipe for watering concrete powder into concrete using a water bucket.
    /// @param concretePowder The concrete powder item.
    /// @param concrete The concrete item.
    public void waterConcrete(ItemLike concretePowder, ItemLike concrete) {
        Identifier identifier = BuiltInRegistries.ITEM.getKey(concrete.asItem());
        this.shaped(RecipeCategory.BUILDING_BLOCKS, concrete, 8).define('#', concretePowder).define('W', Tags.Items.BUCKETS_WATER)
                .pattern("###").pattern("#W#").pattern("###").unlockedBy("has_concrete_powder", this.has(concretePowder))
                .group("dye_concrete").save(this.output, Stancements.stancements(identifier.getPath() + "_from_watering").toString());
    }

    /// Makes a minecart tag recipe.
    /// @param tag The tag item.
    /// @param dyeTag An item tag for the dye to color with, like {@link Tags.Items#DYES_PINK #c:dyes/pink}.
    public void addMinecartTag(ItemLike tag, TagKey<Item> dyeTag) {
        this.shapeless(RecipeCategory.TRANSPORTATION, tag).requires(Items.PAPER).requires(Tags.Items.STRINGS).requires(dyeTag)
                .unlockedBy("has_paper", this.has(Items.PAPER)).group("minecart_tags")
                .save(this.output);
    }

    /// Makes a tagging rail recipe.
    /// @param rail The tagging rail item.
    /// @param dyeTag An item tag for the dye to color with, like {@link Tags.Items#DYES_WHITE #c:dyes/white}.
    public void addTaggingRail(ItemLike rail, TagKey<Item> dyeTag) {
        this.shaped(RecipeCategory.TRANSPORTATION, rail, 6).define('#', Tags.Items.INGOTS_IRON).define('R', Tags.Items.DUSTS_REDSTONE).define('S', Items.STONE_PRESSURE_PLATE).define('D', dyeTag)
                .pattern("#D#").pattern("#R#").pattern("#S#").unlockedBy("has_rail", this.has(Items.RAIL))
                .save(this.output);
    }

    /// Makes a tagging rails dyeing/coloring recipe.
    /// @param rail The tagging rail item.
    /// @param dyeTag An item tag for the dye to color with, like {@link Tags.Items#DYES_PINK #c:dyes/pink}.
    public void dyeTaggingRail(ItemLike rail, TagKey<Item> dyeTag) {
        Ingredient ingredient = DifferenceIngredient.of(Ingredient.of(this.items.getOrThrow(STItemTags.TAGGING_RAILS)), Ingredient.of(rail));
        Identifier identifier = BuiltInRegistries.ITEM.getKey(rail.asItem());

        this.shapeless(RecipeCategory.TRANSPORTATION, rail, 6).requires(ingredient, 6).requires(dyeTag)
                .unlockedBy("has_tagging_rail", this.has(STItemTags.TAGGING_RAILS))
                .group("dye_tagging_rail").save(this.output, Identifier.fromNamespaceAndPath(identifier.getNamespace(), "dye_" + identifier.getPath()).toString());
    }
}
