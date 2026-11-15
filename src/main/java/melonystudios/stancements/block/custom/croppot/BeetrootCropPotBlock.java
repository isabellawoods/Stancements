package melonystudios.stancements.block.custom.croppot;

import com.mojang.serialization.MapCodec;
import melonystudios.stancements.block.PotPlantable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Function;

public class BeetrootCropPotBlock extends FilledCropPotBlock {
    public static final MapCodec<BeetrootCropPotBlock> CODEC = FilledCropPotBlock.CODEC.xmap(
            filledPot -> (BeetrootCropPotBlock) filledPot,
            beetrootPot -> beetrootPot
    );
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    @Override
    protected MapCodec<? extends BeetrootCropPotBlock> codec() {
        return CODEC;
    }

    public BeetrootCropPotBlock(Item seeds, Block equivalentCrop, Function<Block, PotPlantable> plantableGetter, Properties properties) {
        super(seeds, equivalentCrop, plantableGetter, properties);
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
