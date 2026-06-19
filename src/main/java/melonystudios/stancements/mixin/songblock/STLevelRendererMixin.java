package melonystudios.stancements.mixin.songblock;

import com.llamalad7.mixinextras.sugar.Local;
import melonystudios.stancements.STClient;
import melonystudios.stancements.option.STClientOptions;
import melonystudios.stancements.tag.STJukeboxSongTags;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.JukeboxSong;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class STLevelRendererMixin {
    @Inject(method = "playJukeboxSong", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;notifyNearbyEntities(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Z)V"))
    private void setCancelsClientMusic(Holder<JukeboxSong> song, BlockPos pos, CallbackInfo callback, @Local SoundInstance sound) {
        if (song.is(STJukeboxSongTags.CANCELS_AMBIENT_MUSIC) && STClientOptions.MUSIC_DISCS_BLOCK_AMBIENT_MUSIC.get()) {
            STClient.DISCS_BLOCKING_MUSIC.add(sound);
        }
    }
}
