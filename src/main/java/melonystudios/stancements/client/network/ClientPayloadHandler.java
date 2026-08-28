package melonystudios.stancements.client.network;

import melonystudios.stancements.misc.recording.Track;
import melonystudios.stancements.mixin.recorder.CurrentMusicAccessor;
import melonystudios.stancements.network.StartRecordingAttempt;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;

/// Handles all clientbound payloads registered by *Stancements*.
/// @see RequestRecordingAttempt
public class ClientPayloadHandler {
    /// Tells the server to start a recording. It passes through the client to get the song playing (or not) for it.
    /// @param request The {@link RequestRecordingAttempt} payload, containing the position and inserted disc.
    /// @param context The payload context.
    public static void requestRecordingAttempt(RequestRecordingAttempt request, IPayloadContext context) {
        var music = ((CurrentMusicAccessor) Minecraft.getInstance().getMusicManager()).stancements$getCurrentMusic();
        var options = Minecraft.getInstance().options;
        var volumes = new StartRecordingAttempt.MusicVolumes(options.getSoundSourceVolume(SoundSource.MASTER) != 0.0, options.getSoundSourceVolume(SoundSource.MUSIC) != 0.0, options.getSoundSourceVolume(SoundSource.RECORDS) != 0.0);

        context.reply(new StartRecordingAttempt(request.position(), request.recordableDisc(), Optional.ofNullable(music == null ? null : new Track(music.getSound().getLocation(), false)), volumes));
    }
}
