package melonystudios.stancements.util;

import net.minecraft.item.Rarity;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;

public class STUtils {
    @Nullable
    private static final Rarity POTATO = Rarity.create("POTATO", TextFormatting.GREEN);

    public static Rarity potatoRarity() {
        return POTATO != null ? POTATO : Rarity.COMMON;
    }
}
