package melonystudios.stancements.network.s2c;

import melonystudios.stancements.block.custom.BlockBasedMusicPlayer;
import melonystudios.stancements.mixin.CurrentMusicAccessor;
import melonystudios.stancements.network.c2s.StartRecordingAttempt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/// Handles all clientbound payloads registered by *Stancements*.
/// @see RequestRecordingAttempt
public class ClientPayloadHandler {
    /// Tells the server to start a recording. It passes through the client to get the song playing (or not) for it.
    /// @param request The {@link RequestRecordingAttempt} payload, containing the position and inserted disc.
    /// @param context The payload context.
    public static void requestRecordingAttempt(RequestRecordingAttempt request, IPayloadContext context) {
        var music = ((CurrentMusicAccessor) Minecraft.getInstance().getMusicManager()).stancements$getCurrentMusic();
        context.reply(new StartRecordingAttempt(request.position(), request.recordableDisc(), Optional.ofNullable(isSongRecordable(music) ? music.getSound().getLocation() : null), BlockBasedMusicPlayer.DEFAULT_RECORDING_DURATION));
    }

    /// @param currentMusic The instance of the song currently playing (or not) in the client's `MusicManager`.
    /// @return Whether the song can be recorded (song is playing and music volume is above 0).
    public static boolean isSongRecordable(@Nullable SoundInstance currentMusic) {
        var options = Minecraft.getInstance().options;
        return currentMusic != null && options.getSoundSourceVolume(SoundSource.MUSIC) > 0 && options.getSoundSourceVolume(SoundSource.MASTER) > 0;
    }
}
