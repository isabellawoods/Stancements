package melonystudios.stancements.mixin.client;

import melonystudios.stancements.util.InterfaceMethods;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(MusicTicker.class)
public class STMusicTickerMixin implements InterfaceMethods.MusicTicker {
    @Shadow
    @Nullable
    private ISound currentMusic;

    @Override
    @Nullable
    public ISound getCurrentMusic() {
        return this.currentMusic;
    }
}
