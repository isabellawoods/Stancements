package melonystudios.stancements.block.custom;

import melonystudios.stancements.blockentity.custom.DyedWaterCauldronBlockEntity;
import melonystudios.stancements.util.STCauldronInteractions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DyedWaterCauldronBlock extends LayeredCauldronBlock implements EntityBlock {
    public DyedWaterCauldronBlock(Properties properties) {
        super(Biome.Precipitation.RAIN, STCauldronInteractions.DYED_WATER, properties);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyedWaterCauldronBlockEntity(pos, state);
    }

    @Override
    @NotNull
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return new ItemStack(Items.CAULDRON);
    }
}
