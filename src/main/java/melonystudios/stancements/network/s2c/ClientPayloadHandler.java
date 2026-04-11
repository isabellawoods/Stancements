package melonystudios.stancements.network.s2c;

import melonystudios.stancements.mixin.CurrentMusicAccessor;
import melonystudios.stancements.network.c2s.StartRecordingAttempt;
import net.minecraft.client.Minecraft;
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
        context.reply(new StartRecordingAttempt(request.position(), request.recordableDisc(), Optional.ofNullable(music == null ? null : music.getSound().getLocation())));
    }
}
