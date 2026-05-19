package melonystudios.stancements.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class DyedWaterBucketItem extends Item {
    public static final int DEFAULT_WATER_COLOR = 0x3F76E4;

    public DyedWaterBucketItem(Properties properties) {
        super(properties);
    }

    /// Returns the water color for this item stack.
    /// @param stack The item stack to get the "dyed color" component, usually a dyed water bucket.
    /// @return The water color from the component, or `#3F76E4` if it isn't available.
    public static int getColor(ItemStack stack) {
        return DyedItemColor.getOrDefault(stack, DEFAULT_WATER_COLOR);
    }

    /// Sets the "dyed color" component of the item stack to a specific color.
    /// @param stack The item stack to put the color in.
    /// @param color The color to apply.
    public static void setColor(ItemStack stack, int color) {
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(color, true));
    }
}
