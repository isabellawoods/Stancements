package melonystudios.stancements.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class ShelfBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape NORTH_SHAPE = Stream.of(Shapes.box(0, 0.8125, 0, 1, 1, 1),
            Shapes.box(0.125, 0.5625, 0.6875, 0.25, 0.8125, 1),
            Shapes.box(0.75, 0.5625, 0.6875, 0.875, 0.8125, 1)).reduce((shape, shape1) -> Shapes.join(shape, shape1, BooleanOp.OR)).get();
    public static final VoxelShape SOUTH_SHAPE = Stream.of(Shapes.box(0, 0.8125, 0, 1, 1, 1),
            Shapes.box(0.75, 0.5625, 0, 0.875, 0.8125, 0.3125),
            Shapes.box(0.125, 0.5625, 0, 0.25, 0.8125, 0.3125)).reduce((shape, shape1) -> Shapes.join(shape, shape1, BooleanOp.OR)).get();
    public static final VoxelShape EAST_SHAPE = Stream.of(Shapes.box(0, 0.8125, 0, 1, 1, 1),
            Shapes.box(0.6875, 0.5625, 0.75, 1, 0.8125, 0.875),
            Shapes.box(0.6875, 0.5625, 0.125, 1, 0.8125, 0.25)).reduce((shape, shape1) -> Shapes.join(shape, shape1, BooleanOp.OR)).get();
    public static final VoxelShape WEST_SHAPE = Stream.of(Shapes.box(0, 0.8125, 0, 1, 1, 1),
            Shapes.box(0, 0.5625, 0.125, 0.3125, 0.8125, 0.25),
            Shapes.box(0, 0.5625, 0.75, 0.3125, 0.8125, 0.875)).reduce((shape, shape1) -> Shapes.join(shape, shape1, BooleanOp.OR)).get();

    public ShelfBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    @NotNull
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    @NotNull
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    @NotNull
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> SOUTH_SHAPE;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }
}
