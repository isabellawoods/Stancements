package melonystudios.stancements.block.custom.croppot;

import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.misc.STStatistics;
import melonystudios.stancements.misc.datamap.STDataMaps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
import org.jetbrains.annotations.NotNull;

public class CropPotBlock extends Block {
    public static final BooleanProperty HOPPING = STBlockStateProperties.HOPPING;
    protected static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 6, 11);

    public CropPotBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HOPPING, false));
    }

    @Override
    @NotNull
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    @NotNull
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.isShiftKeyDown()) return this.removeSeed(world, state, pos);
        return this.harvestCrop(world, state, pos);
    }

    protected InteractionResult removeSeed(Level world, BlockState state, BlockPos pos) {
        world.setBlockAndUpdate(pos, STBlocks.CROP_POT.get().defaultBlockState().setValue(HOPPING, state.getValue(HOPPING)));
        return InteractionResult.PASS;
    }

    protected InteractionResult harvestCrop(Level world, BlockState state, BlockPos pos) {
        return InteractionResult.PASS;
    }

    @Override
    @NotNull
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack handStack = player.getItemInHand(hand);
        ItemInteractionResult defaultInteraction = super.useItemOn(stack, state, world, pos, player, hand, hitResult);
        if (!state.is(STBlocks.CROP_POT)) return defaultInteraction;

        var items = world.registryAccess().registry(Registries.ITEM);
        if (items.isPresent()) {
            var potPlantables = items.get().getDataMap(STDataMaps.POT_PLANTABLE);
            for (ResourceKey<Item> item : potPlantables.keySet()) {
                if (handStack.is(items.get().get(item))) {
                    return this.placeSeed(world, state, pos, handStack, player, potPlantables.get(item).cropPot(), potPlantables.get(item).plantingSound());
                }
            }
        }

        return defaultInteraction;
    }

    private ItemInteractionResult placeSeed(Level world, BlockState state, BlockPos pos, ItemStack stack, Player player, Block cropPot, SoundEvent placingSound) {
        stack.consume(1, player);
        world.playLocalSound(pos, placingSound, SoundSource.BLOCKS, 1, 0.8F, false);
        world.setBlockAndUpdate(pos, cropPot.defaultBlockState().setValue(HOPPING, state.getValue(HOPPING)));
        world.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(player, state));
        player.awardStat(STStatistics.SEEDS_PLANTED_IN_CROP_POTS.get());
        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        return ItemInteractionResult.sidedSuccess(world.isClientSide);
    }

    @Override
    @NotNull
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader world, BlockPos pos, Player player) {
        ItemStack stack = super.getCloneItemStack(state, target, world, pos, player);
        if (state.getValue(HOPPING)) stack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(HOPPING, true));
        return stack;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HOPPING);
    }
}
