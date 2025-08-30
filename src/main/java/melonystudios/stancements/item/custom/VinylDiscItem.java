package melonystudios.stancements.item.custom;

import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.block.custom.MusicRecorderBlock;
import melonystudios.stancements.mixin.CurrentMusicAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class VinylDiscItem extends Item {
    public VinylDiscItem(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);

        if (state.is(STBlocks.MUSIC_RECORDER.get()) && !state.getValue(MusicRecorderBlock.RECORDING)) {
            ItemStack handStack = context.getItemInHand();
            SoundInstance currentMusic = ((CurrentMusicAccessor) Minecraft.getInstance().getMusicManager()).stancements$getCurrentMusic();
            ((MusicRecorderBlock) state.getBlock()).startRecording(world, state, pos, context.getPlayer(), handStack.split(1), currentMusic);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
