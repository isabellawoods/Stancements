package melonystudios.stancements.mixin.recorder;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MusicManager.class)
public interface CurrentMusicAccessor {
    @Accessor("currentMusic")
    @Nullable SoundInstance stancements$getCurrentMusic();
}
