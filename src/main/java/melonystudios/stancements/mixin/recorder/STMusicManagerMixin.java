package melonystudios.stancements.mixin.recorder;

import melonystudios.stancements.client.option.STClientOptions;
import melonystudios.stancements.component.custom.InventoryRecorder;
import melonystudios.stancements.misc.recording.Track;
import melonystudios.stancements.network.SendClientTrack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public class STMusicManagerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow @Nullable
    private SoundInstance currentMusic;

    @Inject(method = "startPlaying", at = @At("TAIL"))
    public void recordOnPlay(Music selector, CallbackInfo callback) {
        LocalPlayer player = this.minecraft.player;
        // backport "getSound()" crash fix from 5.0.0-beta.3: mojang finally made getSound() nullable ~isa 04-10-26
        if (player == null || this.currentMusic == null || this.currentMusic.getSound() == null) return;

        // intentionally blocks the credits screen music ("C418 - Alpha") from being recorded
        if ((this.minecraft.screen != null && STClientOptions.SCREEN_MUSIC_BLACKLIST.get().contains(this.minecraft.screen.getClass().getName())) || this.minecraft.isPaused()) return;

        if (this.minecraft.options.getSoundSourceVolume(SoundSource.MASTER) == 0.0 || this.minecraft.options.getSoundSourceVolume(SoundSource.MUSIC) == 0.0) return;

        Track track = new Track(this.currentMusic.getSound().getLocation());
        if (InventoryRecorder.canRecord(player.getOffhandItem(), track)) {
            this.sendMusicTrack(track, (short) 150);
            return;
        }

        for (int i = 0; i < player.getInventory().getNonEquipmentItems().size(); i++) {
            if (InventoryRecorder.canRecord(player.getInventory().getNonEquipmentItems().get(i), track)) {
                this.sendMusicTrack(track, (short) i);
                return;
            }
        }

        for (EquipmentSlot slot : EquipmentSlot.VALUES) {
            if (InventoryRecorder.canRecord(player.getItemBySlot(slot), track)) {
                this.sendMusicTrack(track, SendClientTrack.EQUIPMENT_SLOT_INDEXES.get(slot));
                break;
            }
        }
    }

    @Unique
    private void sendMusicTrack(Track track, short slotID) {
        ClientPacketDistributor.sendToServer(new SendClientTrack(track, slotID));
    }
}
