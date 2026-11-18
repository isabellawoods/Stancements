package melonystudios.stancements.block.custom.croppot;

import com.mojang.serialization.MapCodec;
import melonystudios.stancements.block.PotPlantable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Function;

public class NetherWartCropPotBlock extends FilledCropPotBlock {
    public static final MapCodec<NetherWartCropPotBlock> CODEC = FilledCropPotBlock.CODEC.xmap(
            filledPot -> (NetherWartCropPotBlock) filledPot,
            netherWartPot -> netherWartPot
    );
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    @Override
    protected MapCodec<? extends NetherWartCropPotBlock> codec() {
        return CODEC;
    }

    public NetherWartCropPotBlock(Item seeds, Block equivalentCrop, Function<Block, PotPlantable> plantableGetter, Properties properties) {
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
    protected int getBoneMealAgeIncrease(RandomSource random) {
        return super.getBoneMealAgeIncrease(random) / 2;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HOPPING);
    }
}
