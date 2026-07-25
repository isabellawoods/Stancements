package melonystudios.stancements.block.custom;

import melonystudios.stancements.util.STCauldronInteractions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MilkCauldronBlock extends LayeredCauldronBlock {
    public MilkCauldronBlock(Properties properties) {
        super(Biome.Precipitation.NONE, STCauldronInteractions.MILK, properties);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
        return new ItemStack(Items.CAULDRON);
    }
}
