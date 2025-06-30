package melonystudios.stancements.util;

import net.minecraft.client.audio.ISound;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class InterfaceMethods {
    public interface MusicTicker {
        @Nullable
        default ISound getCurrentMusic() {
            return null;
        }
    }

    public interface WorldRenderer {
        default void playRecordedDisc(ResourceLocation musicID, World world, BlockPos pos, ItemStack discStack) {}
    }
}
