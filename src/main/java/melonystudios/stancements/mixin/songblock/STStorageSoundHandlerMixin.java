package melonystudios.stancements.mixin.songblock;

import melonystudios.stancements.client.STClient;
import melonystudios.stancements.client.option.STClientOptions;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.tag.STJukeboxSongTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.StorageSoundHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Pseudo
@Mixin(value = StorageSoundHandler.class, remap = false)
public class STStorageSoundHandlerMixin {
    @Inject(method = "playStorageSound(Ljava/util/UUID;Lnet/minecraft/client/resources/sounds/SoundInstance;)V", at = @At("TAIL"))
    private static void saveToBlockingDiscs(UUID storageUuid, SoundInstance sound, CallbackInfo callback) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null || !STClientOptions.MUSIC_DISCS_BLOCK_AMBIENT_MUSIC.get()) return;

        var jukeboxSongs = level.registryAccess().lookupOrThrow(Registries.JUKEBOX_SONG);
        Identifier songID = RecordedDiscItem.getJukeboxSongLocation(sound.getSound().getLocation());
        var jukeboxSong = jukeboxSongs.get(songID);

        if (jukeboxSong.isPresent() && jukeboxSong.get().is(STJukeboxSongTags.CANCELS_AMBIENT_MUSIC)) {
            STClient.DISCS_BLOCKING_MUSIC.add(sound);
        }
    }
}
