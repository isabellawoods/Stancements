package melonystudios.stancements.blockentity.custom;

import melonystudios.stancements.blockentity.STBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MusicRemixerBlockEntity extends BlockEntity {
    public MusicRemixerBlockEntity(BlockPos pos, BlockState state) {
        super(STBlockEntities.MUSIC_REMIXER.get(), pos, state);
    }
}
