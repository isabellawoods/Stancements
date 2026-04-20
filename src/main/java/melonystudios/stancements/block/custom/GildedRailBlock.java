package melonystudios.stancements.block.custom;

import melonystudios.stancements.option.STOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;

public class GildedRailBlock extends PoweredRailBlock {
    public GildedRailBlock(Properties properties) {
        super(properties, true);
    }

    @Override
    public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart minecart) {
        return super.getRailMaxSpeed(state, level, pos, minecart) * (state.getValue(SHAPE).isSlope() ? 1 : STOptions.GILDED_RAIL_SPEED_MULTIPLIER.get().floatValue());
    }
}
