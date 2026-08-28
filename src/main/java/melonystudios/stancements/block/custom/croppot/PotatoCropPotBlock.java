package melonystudios.stancements.block.custom.croppot;

import melonystudios.stancements.block.PotPlantable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class PotatoCropPotBlock extends WheatCropPotBlock {
    public PotatoCropPotBlock(Item seed, Function<Block, PotPlantable> plantable, Properties properties) {
        super(seed, plantable, properties);
    }

    @Override
    protected ItemLike getSeedItem() {
        return Items.POTATO;
    }

    @Override
    protected BlockState getEquivalentCrop(BlockState state) {
        return Blocks.POTATOES.defaultBlockState().setValue(AGE, state.getValue(AGE));
    }
}
