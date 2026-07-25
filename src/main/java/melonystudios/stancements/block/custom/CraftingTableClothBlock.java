package melonystudios.stancements.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class CraftingTableClothBlock extends Block {
    public static final MapCodec<? extends CraftingTableClothBlock> CODEC = simpleCodec(CraftingTableClothBlock::new);
    public static final VoxelShape SHAPE = Shapes.join(Shapes.empty(), Shapes.box(-0.000625, -0.188125, -0.000625, 1.000625, 0.000625, 1.000625), BooleanOp.OR);

    @Override
    public MapCodec<? extends CraftingTableClothBlock> codec() {
        return CODEC;
    }

    public CraftingTableClothBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return Shapes.empty();
    }

    @Override
    @Nullable
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        if (belowState.getBlock() instanceof CraftingTableBlock) return belowState.getMenuProvider(level, pos.below());
        return super.getMenuProvider(state, level, pos);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbor, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, ticks, pos, directionToNeighbor, neighborPos, neighborState, random);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return !level.isEmptyBlock(pos.below());
    }
}
