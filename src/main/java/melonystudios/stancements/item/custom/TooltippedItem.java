package melonystudios.stancements.item.custom;

import melonystudios.stancements.util.ReAPI;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class TooltippedItem extends Item {
    private final Component tooltip;

    public TooltippedItem(Component tooltip, Properties properties) {
        super(properties);
        this.tooltip = tooltip;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        if (ReAPI.shouldDisplay(stack, BuiltInRegistries.ITEM.getKey(stack.getItem()).withSuffix("/tooltip"))) {
            tooltip.accept(this.tooltip.copy());
        }
    }
}
