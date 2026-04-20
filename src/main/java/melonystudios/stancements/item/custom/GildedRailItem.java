package melonystudios.stancements.item.custom;

import melonystudios.reutilities.api.ReAPI;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.option.STOptions;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.text.DecimalFormat;
import java.util.function.Consumer;

public class GildedRailItem extends BlockItem {
    public static final DecimalFormat FORMAT = new DecimalFormat("0.##");

    public GildedRailItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        if (ReAPI.shouldDisplay(stack, Stancements.stancements("gilded_rail/tooltip"))) {
            float speedMultiplier = STOptions.GILDED_RAIL_SPEED_MULTIPLIER.get().floatValue();
            tooltip.accept(Component.translatable("tooltip.stancements.gilded_rail",
                    FORMAT.format((speedMultiplier * speedMultiplier) * 100 - 100)
            ).withStyle(ChatFormatting.GRAY));
        }
    }
}
