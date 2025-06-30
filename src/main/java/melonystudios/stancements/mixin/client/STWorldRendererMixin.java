package melonystudios.stancements.mixin.client;

import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.sound.disc.RecordedDiscSound;
import melonystudios.stancements.util.InterfaceMethods;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

@Mixin(WorldRenderer.class)
public abstract class STWorldRendererMixin implements InterfaceMethods.WorldRenderer {
    @Shadow
    @Final
    private Map<BlockPos, ISound> playingRecords;
    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    protected abstract void notifyNearbyEntities(World world, BlockPos pos, boolean isPartying);

    @Unique
    @Override
    public void playRecordedDisc(ResourceLocation musicID, World world, BlockPos pos, ItemStack discStack) {
        ISound sound = this.playingRecords.get(pos);
        if (sound != null) {
            this.minecraft.getSoundManager().stop(sound);
            this.playingRecords.remove(pos);
        }

        if (musicID != null && !discStack.isEmpty() && discStack.getItem() instanceof RecordedDiscItem) {
            RecordedDiscItem discItem = (RecordedDiscItem) discStack.getItem();
            IFormattableTextComponent component = discItem.getMusicName(discStack);
            if (component != null) this.minecraft.gui.setNowPlaying(component);

            ISound discSound = new RecordedDiscSound(musicID, pos.getX(), pos.getY(), pos.getZ());
            this.playingRecords.put(pos, discSound);
            this.minecraft.getSoundManager().play(discSound);
        }

        this.notifyNearbyEntities(world, pos, musicID != null);
    }
}
