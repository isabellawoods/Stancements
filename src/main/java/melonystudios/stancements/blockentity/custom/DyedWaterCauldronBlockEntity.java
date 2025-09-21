package melonystudios.stancements.blockentity.custom;

import melonystudios.stancements.blockentity.STBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DyedWaterCauldronBlockEntity extends BlockEntity {
    private int dyedWaterColor = 0x3F76E4;

    public DyedWaterCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(STBlockEntities.DYED_WATER_CAULDRON.get(), pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("dyed_water_color", this.getWaterColor());
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.setWaterColor(tag.getInt("dyed_water_color"));
    }

    public int getWaterColor() {
        return this.dyedWaterColor;
    }

    public void setWaterColor(int waterColor) {
        this.dyedWaterColor = waterColor;
    }
}
