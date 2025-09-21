package melonystudios.stancements.item.custom;

import melonystudios.reutilities.api.ReAPI;
import melonystudios.stancements.Stancements;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.List;

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

    /// Sets the "dyed color" component of the item stack to a specific color, setting the default `show_in_tooltip` field to false
    /// to only display *Stancements*' tooltip.
    /// @param stack The item stack to put the color in.
    /// @param color The color to apply.
    public static void setColor(ItemStack stack, int color) {
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(color, false));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (stack.has(DataComponents.DYED_COLOR) && ReAPI.shouldDisplay(stack, Stancements.stancements("dyed_water_bucket/color"))) {
            DyedItemColor color = stack.get(DataComponents.DYED_COLOR);
            if (color == null) return;
            tooltip.add(Component.translatable("tooltip.stancements.dyed_water_color", Component.literal(String.format("#%06X", color.rgb())).withColor(color.rgb())).withStyle(ChatFormatting.GRAY));
        }
    }
}
