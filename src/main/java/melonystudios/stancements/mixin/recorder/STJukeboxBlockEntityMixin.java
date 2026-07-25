package melonystudios.stancements.mixin.recorder;

import melonystudios.stancements.blockentity.BlockBasedMusicPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(JukeboxBlockEntity.class)
public abstract class STJukeboxBlockEntityMixin implements BlockBasedMusicPlayer {
    @Shadow public abstract JukeboxSongPlayer getSongPlayer();
    @Shadow public abstract ItemStack getTheItem();

    @Override
    public JukeboxSong song() {
        return this.getSongPlayer().getSong();
    }

    @Override
    public int recordingDuration() {
        // exact duration of the song, so it always finishes when the song ends
        return (int) (this.song().lengthInTicks() - this.getSongPlayer().getTicksSinceSongStarted()) + JUKEBOX_PADDING_TICKS; // 20 ticks for padding
    }

    @Override
    @NonNull
    public ItemStack musicDisc() {
        return this.getTheItem();
    }
}
