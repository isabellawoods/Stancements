package melonystudios.stancements.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CraftingTableClothBlock extends Block {
    public static final MapCodec<? extends CraftingTableClothBlock> CODEC = simpleCodec(CraftingTableClothBlock::new);
    public static final VoxelShape SHAPE = Shapes.join(Shapes.empty(), Shapes.box(-0.000625, -0.188125, -0.000625, 1.000625, 0.000625, 1.000625), BooleanOp.OR);

    @Override
    @NotNull
    public MapCodec<? extends CraftingTableClothBlock> codec() {
        return CODEC;
    }

    public CraftingTableClothBlock(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    @Nullable
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        if (belowState.getBlock() instanceof CraftingTableBlock) return belowState.getMenuProvider(level, pos.below());
        return super.getMenuProvider(state, level, pos);
    }

    @Override
    @NotNull
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return !level.isEmptyBlock(pos.below());
    }
}
