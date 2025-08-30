package melonystudios.stancements.misc;

import static melonystudios.reutilities.api.ReAPI.flammable;
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
}
