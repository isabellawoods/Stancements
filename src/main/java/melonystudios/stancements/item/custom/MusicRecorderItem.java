package melonystudios.stancements.item.custom;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.util.ReAPI;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class MusicRecorderItem extends BlockItem {
    public MusicRecorderItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        if (ReAPI.shouldDisplay(stack, Stancements.stancements("music_recorder/tooltip"))) {
            tooltip.accept(Component.translatable("tooltip.stancements.music_recorder").withStyle(ChatFormatting.GRAY));
        }
    }
}
