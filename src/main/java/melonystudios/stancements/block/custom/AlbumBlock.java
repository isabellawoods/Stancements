package melonystudios.stancements.block.custom;

import com.mojang.serialization.MapCodec;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.blockentity.custom.AlbumBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class AlbumBlock extends BaseEntityBlock {
    public static final DirectionProperty ATTACHMENT = STBlockStateProperties.ATTACHMENT;

    @Override
    protected MapCodec<? extends AlbumBlock> codec() {
        return simpleCodec(AlbumBlock::new);
    }

    public AlbumBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(ATTACHMENT, Direction.DOWN));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        BlockEntity entity = level.getBlockEntity(pos);

        if (entity instanceof AlbumBlockEntity album) {
            player.openMenu(album);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AlbumBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ATTACHMENT);
    }
}
