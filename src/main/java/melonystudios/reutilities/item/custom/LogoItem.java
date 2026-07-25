package melonystudios.reutilities.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class LogoItem extends Item {
    private final Style style;

    public LogoItem(int color, Properties properties) {
        this(Style.EMPTY.withColor(color), properties);
    }

    public LogoItem(Style style, Properties properties) {
        super(properties);
        this.style = style;
    }

    @Override
    @NonNull
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(this.style);
    }
}
