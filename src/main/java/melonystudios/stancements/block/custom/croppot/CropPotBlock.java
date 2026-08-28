package melonystudios.stancements.block.custom.croppot;

import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.misc.STStatistics;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CropPotBlock extends Block {
    public static final BooleanProperty HOPPING = STBlockStateProperties.HOPPING;
    protected static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 6, 11);

    public CropPotBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HOPPING, false));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.isShiftKeyDown()) return this.removeSeed(level, state, pos);
        return this.harvestCrop(level, state, pos);
    }

    protected InteractionResult removeSeed(Level level, BlockState state, BlockPos pos) {
        level.setBlockAndUpdate(pos, STBlocks.CROP_POT.get().defaultBlockState().setValue(HOPPING, state.getValue(HOPPING)));
        return InteractionResult.PASS;
    }

    protected InteractionResult harvestCrop(Level level, BlockState state, BlockPos pos) {
        return InteractionResult.PASS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getBlock() != STBlocks.CROP_POT.get()) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }

        ItemStack handStack = player.getItemInHand(hand);
        var plantable = WheatCropPotBlock.PLANTABLE_IN_POTS.get(handStack.getItem());
        if (plantable != null) return this.placeSeed(level, state, pos, handStack, player, plantable.cropPot(), plantable.plantingSound());

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    private ItemInteractionResult placeSeed(Level level, BlockState state, BlockPos pos, ItemStack stack, Player player, Block cropPot, SoundEvent placingSound) {
        stack.consume(1, player);
        if (player instanceof ServerPlayer serverPlayer) CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
        level.playLocalSound(pos, placingSound, SoundSource.BLOCKS, 1, 0.8F, false);
        level.setBlockAndUpdate(pos, cropPot.defaultBlockState().setValue(HOPPING, state.getValue(HOPPING)));
        level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(player, state));
        player.awardStat(STStatistics.SEEDS_PLANTED_IN_CROP_POTS.get());
        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        ItemStack stack = super.getCloneItemStack(state, target, level, pos, player);
        stack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(HOPPING, state.getValue(HOPPING)));
        return stack;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HOPPING);
    }
}
