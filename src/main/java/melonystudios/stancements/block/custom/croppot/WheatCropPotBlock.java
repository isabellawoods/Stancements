package melonystudios.stancements.block.custom.croppot;

import melonystudios.stancements.STConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class WheatCropPotBlock extends CropPotBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    public WheatCropPotBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0).setValue(HOPPING, false));
    }

    public int getMaxAge() {
        return 7;
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    public final boolean isMaxAge(BlockState state) {
        return this.getAge(state) == this.getMaxAge();
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
        if (state.getValue(HOPPING) && this.getAge(state) == this.getMaxAge()) this.harvestCrop(world, state, pos);

        if (world.isAreaLoaded(pos, 1) && world.getRawBrightness(pos, 0) >= 9) {
            int growthChance = STConfigs.CROP_POT_GROWTH_CHANCE.get();
            int age = this.getAge(state);

            if (age < this.getMaxAge()) {
                if (CommonHooks.canCropGrow(world, pos, state, rand.nextInt(growthChance) == 0)) {
                    this.growAndHarvestCrop(world, state, pos, age + 1);
                    CommonHooks.fireCropGrowPost(world, pos, state);
                }
            }
        }
    }

    private void growAndHarvestCrop(ServerLevel world, BlockState state, BlockPos pos, int age) {
        if (state.getValue(HOPPING) && this.isMaxAge(state)) {
            this.harvestCrop(world, state, pos);
        } else {
            world.setBlock(pos, state.setValue(this.getAgeProperty(), age).setValue(HOPPING, state.getValue(HOPPING)), 2);
        }
    }

    @Override
    protected InteractionResult harvestCrop(Level world, BlockState state, BlockPos pos) {
        if (!this.isMaxAge(state)) return InteractionResult.FAIL;
        BlockState equivalentCrop = this.getEquivalentCrop(state);
        SoundType type = equivalentCrop.getSoundType(world, pos, null);

        if (world instanceof ServerLevel serverWorld) this.getCropDrops(serverWorld, state, pos).forEach(stack -> {
            if (world.getBlockEntity(pos.below()) instanceof Container container) {
                ItemStack hopperStack = HopperBlockEntity.addItem(null, container, stack, Direction.DOWN);
                if (!hopperStack.isEmpty()) Block.popResource(world, pos, hopperStack);
            } else Block.popResource(world, pos, stack);
        });
        world.playSound(null, pos, type.getBreakSound(), SoundSource.BLOCKS, (type.getVolume() + 1) / 2, type.getPitch() * 0.8F);
//        world.playLocalSound(pos, type.getBreakSound(), SoundSource.BLOCKS, (type.getVolume() + 1) / 2, type.getPitch() * 0.8F, false);
        world.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
        world.addDestroyBlockEffect(pos, equivalentCrop);
        world.setBlock(pos, state.setValue(this.getAgeProperty(), 0).setValue(HOPPING, state.getValue(HOPPING)), 2);

        return InteractionResult.SUCCESS;
    }

    protected List<ItemStack> getCropDrops(ServerLevel world, BlockState state, BlockPos pos) {
        BlockState equivalentCrop = this.getEquivalentCrop(state);
        ResourceKey<LootTable> lootTable = equivalentCrop.getBlock().getLootTable();
        if (lootTable == BuiltInLootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            LootParams params = new LootParams.Builder(world)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                    .withParameter(LootContextParams.BLOCK_STATE, equivalentCrop)
                    .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                    .withOptionalParameter(LootContextParams.BLOCK_ENTITY, world.getBlockEntity(pos))
                    .create(LootContextParamSets.BLOCK);

            return world.getServer().reloadableRegistries().getLootTable(lootTable).getRandomItems(params);
        }
    }

    @Override
    protected InteractionResult removeSeed(Level world, BlockState state, BlockPos pos) {
        BlockState equivalentCrop = this.getEquivalentCrop(state);
        SoundType type = equivalentCrop.getSoundType(world, pos, null);
        Block.popResource(world, pos, this.getSeedItem().asItem().getDefaultInstance());
        world.playLocalSound(pos, type.getBreakSound(), SoundSource.BLOCKS, (type.getVolume() + 1) / 2, type.getPitch() * 0.8F, false);
        world.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
        world.addDestroyBlockEffect(pos, equivalentCrop);

        super.removeSeed(world, state, pos);
        return InteractionResult.SUCCESS;
    }

    protected int getBonemealAgeIncrease(RandomSource rand) {
        return Mth.nextInt(rand, 2, 5);
    }

    protected BlockState getEquivalentCrop(BlockState state) {
        return Blocks.WHEAT.defaultBlockState().setValue(this.getAgeProperty(), this.getAge(state));
    }

    protected ItemLike getSeedItem() {
        return Items.WHEAT_SEEDS;
    }

    @Override
    @NotNull
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader world, BlockPos pos, Player player) {
        return new ItemStack(this.getSeedItem());
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return !this.isMaxAge(state);
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource rand, BlockPos pos, BlockState state) {
        int newAge = this.getAge(state) + this.getBonemealAgeIncrease(rand);
        int maxAge = this.getMaxAge();
        if (newAge > maxAge) newAge = maxAge;

        this.growAndHarvestCrop(world, state, pos, newAge);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HOPPING);
    }
}
