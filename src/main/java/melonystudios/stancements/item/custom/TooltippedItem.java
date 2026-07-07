package melonystudios.stancements.item.custom;

import melonystudios.reutilities.api.ReAPI;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class TooltippedItem extends Item {
    private final Component tooltip;

    public TooltippedItem(Component tooltip, Properties properties) {
        super(properties);
        this.tooltip = tooltip;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (ReAPI.shouldDisplay(stack, BuiltInRegistries.ITEM.getKey(stack.getItem()).withSuffix("/tooltip"))) {
            tooltip.add(this.tooltip.copy());
        }
    }
}
