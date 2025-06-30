package melonystudios.stancements.item.custom;

import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.block.custom.MusicRecorderBlock;
import melonystudios.stancements.misc.STStats;
import melonystudios.stancements.util.InterfaceMethods;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class VinylDiscItem extends Item {
    public VinylDiscItem(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);

        if (state.is(STBlocks.MUSIC_RECORDER.get()) && !state.getValue(MusicRecorderBlock.RECORDING)) {
            ItemStack handStack = context.getItemInHand();
            ISound currentMusic = ((InterfaceMethods.MusicTicker) Minecraft.getInstance().getMusicManager()).getCurrentMusic();
            ((MusicRecorderBlock) state.getBlock()).startRecording(world, state, pos, context.getPlayer(), handStack.split(1), currentMusic);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }
}
