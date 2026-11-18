package melonystudios.stancements.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.blockentity.custom.MusicRemixerBlockEntity;
import melonystudios.stancements.sound.STSounds;
import melonystudios.stancements.tag.STBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class MusicRemixerBlock extends BaseEntityBlock {
    public static final MapCodec<MusicRemixerBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("casing").forGetter(block -> block.casing),
            propertiesCodec()
    ).apply(instance, MusicRemixerBlock::new));
    protected static final VoxelShape OPENED_SHAPE = Block.box(0, 0, 0, 16, 10, 16);
    private static final Map<Holder<Block>, Block> REMIXER_TO_CASINGS = new HashMap<>();
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty RECORDING = STBlockStateProperties.RECORDING;
    private final Holder<Block> casing;

    @Override
    protected MapCodec<? extends MusicRemixerBlock> codec() {
        return CODEC;
    }

    public MusicRemixerBlock(Holder<Block> casing, Properties properties) {
        super(properties);
        this.casing = casing;
        REMIXER_TO_CASINGS.put(casing, this);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false).setValue(OPEN, false).setValue(RECORDING, false));
    }

    public Holder<Block> casing() {
        return this.casing;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MusicRemixerBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(OPEN) ? OPENED_SHAPE : super.getShape(state, level, pos, context);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return OPENED_SHAPE;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        level.scheduleTick(pos, this, 1);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
        boolean hasNeighboringSignal = level.hasNeighborSignal(pos);
        boolean powered = state.getValue(POWERED);
        level.scheduleTick(pos, this, 1);

        if (hasNeighboringSignal && !powered) {
            level.setBlock(pos, state.setValue(POWERED, true), 2);
        } else if (!hasNeighboringSignal && powered) {
            level.setBlock(pos, state.setValue(POWERED, false), 2);
        }

        super.neighborChanged(state, level, pos, neighborBlock, orientation, movedByPiston);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED)) {
            this.openRemixer(level, state, pos);
        } else {
            this.closeRemixer(level, state, pos);
        }
    }

    public boolean replaceableByCasing(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.is(STBlockTags.REPLACEABLE_BY_CASINGS);
    }

    private void openRemixer(ServerLevel level, BlockState state, BlockPos pos) {
        BlockPos abovePos = pos.above();
        if (!state.getValue(OPEN) && this.replaceableByCasing(level, abovePos)) {
            BlockState casingState = pushEntitiesUp(
                    level.getBlockState(abovePos),
                    this.casing.value().defaultBlockState().setValue(STBlockStateProperties.SUPPORTED, true),
                    level, abovePos
            );
            level.destroyBlock(abovePos, true);
            level.setBlockAndUpdate(abovePos, casingState);
            level.setBlock(pos, state.setValue(OPEN, true), 2);
            level.gameEvent(GameEvent.BLOCK_OPEN, abovePos, GameEvent.Context.of(level.getBlockState(abovePos)));
            level.playSound(null, pos, STSounds.MUSIC_REMIXER_OPEN.get(), SoundSource.BLOCKS, 1, level.getRandom().nextFloat() * 0.25F + 0.6F);
        }
    }

    private void closeRemixer(ServerLevel level, BlockState state, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        Holder<Block> aboveHolder = aboveState.typeHolder();

        if (state.getValue(OPEN) && REMIXER_TO_CASINGS.containsKey(aboveHolder)) {
            BlockState remixerState = REMIXER_TO_CASINGS.get(aboveHolder).defaultBlockState().setValue(OPEN, false);
            level.removeBlock(abovePos, false);
            level.setBlockAndUpdate(pos, remixerState);
            level.gameEvent(GameEvent.BLOCK_CLOSE, pos, GameEvent.Context.of(remixerState));
            level.playSound(null, pos, STSounds.MUSIC_REMIXER_CLOSE.get(), SoundSource.BLOCKS, 1, level.getRandom().nextFloat() * 0.15F + 0.6F);
        }
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
        ItemStack stack = super.getCloneItemStack(level, pos, state, includeData, player);
        stack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(OPEN, state.getValue(OPEN)));
        return stack;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, OPEN, RECORDING);
    }
}
