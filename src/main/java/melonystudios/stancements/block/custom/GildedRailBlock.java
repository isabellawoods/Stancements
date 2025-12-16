package melonystudios.stancements.block.custom;

import melonystudios.reutilities.api.ReAPI;
import melonystudios.stancements.Stancements;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class GildedRailBlock extends PoweredRailBlock {
    public static final float GILDED_RAIL_SPEED_MULTIPLIER = 1.5F;

    public GildedRailBlock(Properties properties) {
        super(properties, true);
    }

    @Override
    public float getRailMaxSpeed(BlockState state, Level world, BlockPos pos, AbstractMinecart minecart) {
        return super.getRailMaxSpeed(state, world, pos, minecart) * (state.getValue(SHAPE).isAscending() ? 1 : GILDED_RAIL_SPEED_MULTIPLIER);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (ReAPI.shouldDisplay(stack, Stancements.stancements("gilded_rail/tooltip"))) {
            tooltip.add(Component.translatable("tooltip.stancements.gilded_rail").withStyle(ChatFormatting.GRAY));
        }
    }
}
