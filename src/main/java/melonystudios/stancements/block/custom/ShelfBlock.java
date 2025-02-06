package melonystudios.stancements.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class ShelfBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape NORTH_SHAPE = Stream.of(VoxelShapes.box(0, 0.875, 0.375, 1, 1, 1),
            VoxelShapes.box(0.0625, 0.625, 0.9375, 0.1875, 1, 1.0625),
            VoxelShapes.box(0.8125, 0.625, 0.9375, 0.9375, 1, 1.0625)).reduce((shape, shape1) -> VoxelShapes.join(shape, shape1, IBooleanFunction.OR)).get();
    public static final VoxelShape SOUTH_SHAPE = Stream.of(VoxelShapes.box(0, 0.875, 0, 1, 1, 0.625),
            VoxelShapes.box(0.8125, 0.625, -0.0625, 0.9375, 1, 0.0625),
            VoxelShapes.box(0.0625, 0.625, -0.0625, 0.1875, 1, 0.0625)).reduce((shape, shape1) -> VoxelShapes.join(shape, shape1, IBooleanFunction.OR)).get();
    public static final VoxelShape EAST_SHAPE = Stream.of(VoxelShapes.box(0.375, 0.875, 0, 1, 1, 1),
            VoxelShapes.box(0.9375, 0.625, 0.8125, 1.0625, 1, 0.9375),
            VoxelShapes.box(0.9375, 0.625, 0.0625, 1.0625, 1, 0.1875)).reduce((shape, shape1) -> VoxelShapes.join(shape, shape1, IBooleanFunction.OR)).get();
    public static final VoxelShape WEST_SHAPE = Stream.of(VoxelShapes.box(0, 0.875, 0, 0.625, 1, 1),
            VoxelShapes.box(-0.0625, 0.625, 0.0625, 0.0625, 1, 0.1875),
            VoxelShapes.box(-0.0625, 0.625, 0.8125, 0.0625, 1, 0.9375)).reduce((shape, shape1) -> VoxelShapes.join(shape, shape1, IBooleanFunction.OR)).get();

    public ShelfBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = this.defaultBlockState();
        IBlockReader world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction[] lookingDirections = context.getNearestLookingDirections();

        for (Direction direction : lookingDirections) {
            if (direction.getAxis().isHorizontal()) {
                state = state.setValue(FACING, direction).setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
                if (!world.getBlockState(pos.relative(direction)).canBeReplaced(context)) return state;
            }
        }

        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER);
    }

    @Nonnull
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nonnull
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Nonnull
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Nonnull
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nonnull
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(FACING)) {
            case SOUTH: return NORTH_SHAPE;
            case EAST: return EAST_SHAPE;
            case WEST: return WEST_SHAPE;
            default: return SOUTH_SHAPE;
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }
}
