package melonystudios.stancements.mixin.recorder.inventory;

import melonystudios.stancements.client.option.STClientOptions;
import melonystudios.stancements.component.custom.InventoryRecorder;
import melonystudios.stancements.misc.recording.Track;
import melonystudios.stancements.network.SendClientTrack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(MusicManager.class)
public class STMusicManagerMixin {
    @Shadow @Final private Minecraft minecraft;
    @Shadow @Nullable private SoundInstance currentMusic;

    @Inject(method = "startPlaying", at = @At("TAIL"))
    public void recordOnPlay(Music selector, CallbackInfo callback) {
        LocalPlayer player = this.minecraft.player;
        if (player == null || this.currentMusic == null) return;

        // intentionally blocks the credits screen music ("C418 - Alpha") from being recorded
        if ((this.minecraft.screen != null && STClientOptions.SCREEN_MUSIC_BLACKLIST.get().contains(this.minecraft.screen.getClass().getName())) || Minecraft.getInstance().isPaused()) return;

        if (this.minecraft.options.getSoundSourceVolume(SoundSource.MASTER) == 0.0 || this.minecraft.options.getSoundSourceVolume(SoundSource.MUSIC) == 0.0) return;

        Track track = new Track(this.currentMusic.getSound().getLocation());
        for (int i = 0; i < player.getInventory().offhand.size(); i++) {
            if (InventoryRecorder.canRecord(player.getInventory().offhand.get(i), track)) {
                this.sendMusicTrack(track, (short) (i + 150));
                return;
            }
        }

        for (int i = 0; i < player.getInventory().items.size(); i++) {
            if (InventoryRecorder.canRecord(player.getInventory().items.get(i), track)) {
                this.sendMusicTrack(track, (short) i);
                return;
            }
        }

        for (int i = 0; i < player.getInventory().armor.size(); i++) {
            if (InventoryRecorder.canRecord(player.getInventory().armor.get(i), track)) {
                this.sendMusicTrack(track, (short) (i + 100));
                break;
            }
        }
    }

    @Unique
    private void sendMusicTrack(Track track, short slotID) {
        PacketDistributor.sendToServer(new SendClientTrack(track, slotID));
    }
}
