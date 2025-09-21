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
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class MilkCauldronBlock extends LayeredCauldronBlock {
    public MilkCauldronBlock(Properties properties) {
        super(Biome.Precipitation.NONE, STCauldronInteractions.MILK, properties);
    }

    @Override
    @NotNull
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader world, BlockPos pos, Player player) {
        return new ItemStack(Items.CAULDRON);
    }
}
