package melonystudios.stancements.block.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.block.STBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class CasingBlock extends Block {
    public static final MapCodec<CasingBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.BOOL.fieldOf("hides_remixer_contents").forGetter(block -> block.hidesRemixerContents),
            propertiesCodec()
    ).apply(instance, CasingBlock::new));
    protected static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 6, 16);
    public static final BooleanProperty SUPPORTED = STBlockStateProperties.SUPPORTED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final boolean hidesRemixerContents; // mostly just for tinted glass

    @Override
    protected MapCodec<? extends CasingBlock> codec() {
        return CODEC;
    }

    public CasingBlock(boolean hidesRemixerContents, Properties properties) {
        super(properties);
        this.hidesRemixerContents = hidesRemixerContents;
        this.registerDefaultState(this.getStateDefinition().any().setValue(SUPPORTED, false).setValue(WATERLOGGED, false));
    }

    public CasingBlock(Properties properties) {
        this(false, properties);
    }

    public boolean hidesRemixerContents() {
        return this.hidesRemixerContents;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(SUPPORTED, !Block.canSupportRigidBlock(context.getLevel(), context.getClickedPos().below()))
                .setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random)
                .setValue(SUPPORTED, !Block.canSupportRigidBlock(level, pos.below()));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SUPPORTED, WATERLOGGED);
    }
}
