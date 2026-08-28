package melonystudios.stancements.blockentity;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.item.JukeboxSong;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/// Necessary data about the adjacent block for music recording.
/// @see melonystudios.stancements.block.custom.MusicRecorderBlock#tryRecordingFromAdjacentBlock MusicRecorderBlock.tryRecordingFromAdjacentBlock()
public interface BlockBasedMusicPlayer {
    int DEFAULT_TICKS_UNTIL_FINISHED = -1;
    int JUKEBOX_PADDING_TICKS = 20;

    /// The jukebox song being played by this block entity. May be `null`.
    @Nullable JukeboxSong song();

    /// The time it takes to finish recording this song, in ticks. For jukeboxes,
    /// an extra {@linkplain #JUKEBOX_PADDING_TICKS `20` ticks} is added to the end.
    int recordingDuration();

    /// The item stack of the disc playing the song.
    /// Recording is blocked if this disc is marked as a {@linkplain melonystudios.stancements.component.custom.MusicData copy}.
    @NotNull ItemStack musicDisc();

    static Optional<JukeboxSong> sourceSongFromItem(RegistryAccess registries, ItemStack stack) {
        JukeboxPlayable playable = stack.get(DataComponents.JUKEBOX_PLAYABLE);
        var jukeboxSongs = registries.registryOrThrow(Registries.JUKEBOX_SONG);

        if (playable != null) return playable.song().unwrap(jukeboxSongs);
        return Optional.empty();
    }
}
