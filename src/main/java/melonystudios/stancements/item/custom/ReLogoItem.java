package melonystudios.stancements.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ReLogoItem extends Item {
    private final Style style;

    public ReLogoItem(int color, Properties properties) {
        this(Style.EMPTY.withColor(color), properties);
    }

    public ReLogoItem(Style style, Properties properties) {
        super(properties);
        this.style = style;
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(this.style);
    }
}
