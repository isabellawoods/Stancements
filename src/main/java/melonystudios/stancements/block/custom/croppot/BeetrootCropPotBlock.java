package melonystudios.stancements.block.custom.croppot;

import melonystudios.stancements.block.PotPlantable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Function;

public class BeetrootCropPotBlock extends WheatCropPotBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public BeetrootCropPotBlock(Item seed, Function<Block, PotPlantable> plantable, Properties properties) {
        super(seed, plantable, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(HOPPING, false));
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected ItemLike getSeedItem() {
        return Items.BEETROOT_SEEDS;
    }

    @Override
    protected BlockState getEquivalentCrop(BlockState state) {
        return Blocks.BEETROOTS.defaultBlockState().setValue(AGE, state.getValue(AGE));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(3) != 0) super.randomTick(state, level, pos, random);
    }

    @Override
    protected int getBoneMealAgeIncrease(RandomSource random) {
        return super.getBoneMealAgeIncrease(random) / 3;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HOPPING);
    }
}
