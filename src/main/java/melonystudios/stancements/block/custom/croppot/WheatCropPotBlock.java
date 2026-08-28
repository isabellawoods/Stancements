package melonystudios.stancements.block.custom.croppot;

import melonystudios.stancements.block.PotPlantable;
import melonystudios.stancements.option.STCommonOptions;
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
import net.minecraft.world.item.Item;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class WheatCropPotBlock extends CropPotBlock implements BonemealableBlock {
    protected static final Map<Item, PotPlantable> PLANTABLE_IN_POTS = new HashMap<>();
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    public WheatCropPotBlock(Item seed, Function<Block, PotPlantable> plantable, Properties properties) {
        super(properties);
        PLANTABLE_IN_POTS.put(seed, plantable.apply(this));
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
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(HOPPING) && this.getAge(state) == this.getMaxAge()) this.harvestCrop(level, state, pos);

        if (level.isAreaLoaded(pos, 1) && level.getRawBrightness(pos, 0) >= 9) {
            int growthChance = STCommonOptions.CROP_POT_GROWTH_CHANCE.get();
            int age = this.getAge(state);

            if (age < this.getMaxAge()) {
                if (CommonHooks.canCropGrow(level, pos, state, random.nextInt(growthChance) == 0)) {
                    this.growAndHarvestCrop(level, state, pos, age + 1);
                    CommonHooks.fireCropGrowPost(level, pos, state);
                }
            }
        }
    }

    private void growAndHarvestCrop(ServerLevel level, BlockState state, BlockPos pos, int age) {
        if (state.getValue(HOPPING) && this.isMaxAge(state)) {
            this.harvestCrop(level, state, pos);
        } else {
            level.setBlock(pos, state.setValue(this.getAgeProperty(), age).setValue(HOPPING, state.getValue(HOPPING)), 2);
        }
    }

    @Override
    protected InteractionResult harvestCrop(Level level, BlockState state, BlockPos pos) {
        if (!this.isMaxAge(state)) return InteractionResult.FAIL;
        BlockState equivalentCrop = this.getEquivalentCrop(state);
        SoundType type = equivalentCrop.getSoundType(level, pos, null);

        if (level instanceof ServerLevel serverLevel) this.getCropDrops(serverLevel, state, pos).forEach(stack -> {
            if (level.getBlockEntity(pos.below()) instanceof Container container) {
                ItemStack hopperStack = HopperBlockEntity.addItem(null, container, stack, Direction.DOWN);
                if (!hopperStack.isEmpty()) Block.popResource(level, pos, hopperStack);
            } else Block.popResource(level, pos, stack);
        });
        level.playSound(null, pos, type.getBreakSound(), SoundSource.BLOCKS, (type.getVolume() + 1) / 2, type.getPitch() * 0.8F);
        level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
        level.addDestroyBlockEffect(pos, equivalentCrop);
        level.setBlock(pos, state.setValue(this.getAgeProperty(), 0).setValue(HOPPING, state.getValue(HOPPING)), 2);

        return InteractionResult.SUCCESS;
    }

    protected List<ItemStack> getCropDrops(ServerLevel level, BlockState state, BlockPos pos) {
        BlockState equivalentCrop = this.getEquivalentCrop(state);
        ResourceKey<LootTable> lootTable = equivalentCrop.getBlock().getLootTable();
        if (lootTable == BuiltInLootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            LootParams params = new LootParams.Builder(level)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                    .withParameter(LootContextParams.BLOCK_STATE, equivalentCrop)
                    .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                    .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(pos))
                    .create(LootContextParamSets.BLOCK);

            return level.getServer().reloadableRegistries().getLootTable(lootTable).getRandomItems(params);
        }
    }

    @Override
    protected InteractionResult removeSeed(Level level, BlockState state, BlockPos pos) {
        BlockState equivalentCrop = this.getEquivalentCrop(state);
        SoundType type = equivalentCrop.getSoundType(level, pos, null);
        Block.popResource(level, pos, this.getSeedItem().asItem().getDefaultInstance());
        level.playLocalSound(pos, type.getBreakSound(), SoundSource.BLOCKS, (type.getVolume() + 1) / 2, type.getPitch() * 0.8F, false);
        level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
        level.addDestroyBlockEffect(pos, equivalentCrop);

        super.removeSeed(level, state, pos);
        return InteractionResult.SUCCESS;
    }

    protected int getBoneMealAgeIncrease(RandomSource random) {
        return Mth.nextInt(random, 2, 5);
    }

    protected BlockState getEquivalentCrop(BlockState state) {
        return Blocks.WHEAT.defaultBlockState().setValue(this.getAgeProperty(), this.getAge(state));
    }

    protected ItemLike getSeedItem() {
        return Items.WHEAT_SEEDS;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return new ItemStack(this.getSeedItem());
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return !this.isMaxAge(state);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int newAge = this.getAge(state) + this.getBoneMealAgeIncrease(random);
        int maxAge = this.getMaxAge();
        if (newAge > maxAge) newAge = maxAge;

        this.growAndHarvestCrop(level, state, pos, newAge);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HOPPING);
    }
}
