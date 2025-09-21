package melonystudios.stancements.block.custom.croppot;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CarrotCropPotBlock extends WheatCropPotBlock {
    public CarrotCropPotBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getSeedItem() {
        return Items.CARROT;
    }

    @Override
    protected BlockState getEquivalentCrop(BlockState state) {
        return Blocks.CARROTS.defaultBlockState().setValue(AGE, state.getValue(AGE));
    }
}
