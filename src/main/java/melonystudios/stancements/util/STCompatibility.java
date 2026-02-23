package melonystudios.stancements.util;

import melonystudios.stancements.dispenser.TaggingDispenseBehavior;
import melonystudios.stancements.item.STItems;

import static melonystudios.reutilities.api.ReAPI.flammable;
import static melonystudios.stancements.block.STBlocks.*;
import static net.minecraft.world.level.block.DispenserBlock.registerBehavior;

public class STCompatibility {
    /// Adds all of *Stancements*' blocks into the flammability map.
    public static void flammables() {
        // Shelves
        flammable(OAK_SHELF.get(), 5, 20);
        flammable(SPRUCE_SHELF.get(), 5, 20);
        flammable(BIRCH_SHELF.get(), 5, 20);
        flammable(JUNGLE_SHELF.get(), 5, 20);
        flammable(ACACIA_SHELF.get(), 5, 20);
        flammable(DARK_OAK_SHELF.get(), 5, 20);
        flammable(MANGROVE_SHELF.get(), 5, 20);
        flammable(CHERRY_SHELF.get(), 5, 20);
        flammable(BAMBOO_SHELF.get(), 5, 20);

        // Crafting Table Cloths
        flammable(WHITE_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(GRAY_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(BLACK_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(BROWN_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(RED_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(ORANGE_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(YELLOW_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(LIME_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(GREEN_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(CYAN_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(BLUE_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(PURPLE_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(MAGENTA_CRAFTING_TABLE_CLOTH.get(), 60, 20);
        flammable(PINK_CRAFTING_TABLE_CLOTH.get(), 60, 20);
    }

    public static void dispenserBehaviors() {
        // Minecart tags
        registerBehavior(STItems.WHITE_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.LIGHT_GRAY_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.GRAY_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.BLACK_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.BROWN_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.RED_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.ORANGE_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.YELLOW_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.LIME_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.GREEN_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.CYAN_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.LIGHT_BLUE_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.BLUE_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.PURPLE_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.MAGENTA_TAG.get(), new TaggingDispenseBehavior());
        registerBehavior(STItems.PINK_TAG.get(), new TaggingDispenseBehavior());
    }
}
