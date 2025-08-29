package melonystudios.stancements.misc;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

import static melonystudios.stancements.block.STBlocks.*;

public class STVanillaCompatibility {
    public static void flammables() {
        flammable(OAK_SHELF.get(), 5, 20);
        flammable(SPRUCE_SHELF.get(), 5, 20);
        flammable(BIRCH_SHELF.get(), 5, 20);
        flammable(JUNGLE_SHELF.get(), 5, 20);
        flammable(ACACIA_SHELF.get(), 5, 20);
        flammable(DARK_OAK_SHELF.get(), 5, 20);
    }

    public static void flammable(Block block, int encouragement, int flammability) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, encouragement, flammability);
    }
}
