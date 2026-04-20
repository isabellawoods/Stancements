package melonystudios.stancements.blockentity.custom;

import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.item.custom.DyedWaterBucketItem;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class DyedWaterCauldronBlockEntity extends BlockEntity {
    private int dyedWaterColor = DyedWaterBucketItem.DEFAULT_WATER_COLOR;

    public DyedWaterCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(STBlockEntities.DYED_WATER_CAULDRON.get(), pos, state);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("dyed_water_color", this.getWaterColor());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.setWaterColor(input.getIntOr("dyed_water_color", DyedWaterBucketItem.DEFAULT_WATER_COLOR));
    }

    public int getWaterColor() {
        return this.dyedWaterColor;
    }

    public void setWaterColor(int waterColor) {
        this.dyedWaterColor = waterColor;
    }

    public static BlockTintSource dyedWaterCauldron() {
        return new BlockTintSource() {
            @Override
            public int color(BlockState state) {
                return DyedWaterBucketItem.DEFAULT_WATER_COLOR;
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof DyedWaterCauldronBlockEntity cauldron) {
                    return cauldron.getWaterColor();
                } else {
                    return 0x5DB7EF; // Cherry grove water color (for testing)
                }
            }
        };
    }
}
