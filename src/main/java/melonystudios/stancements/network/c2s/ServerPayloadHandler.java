package melonystudios.stancements.network.c2s;

import melonystudios.stancements.block.custom.MusicRecorderBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/// Handles all serverbound payloads registered by *Stancements*.
/// @see StartRecordingAttempt
public class ServerPayloadHandler {
    /// Attempts to start a recording of music from the client's {@link melonystudios.stancements.mixin.CurrentMusicAccessor MusicManager} or from adjacent jukeboxes.
    /// @param receival The {@link StartRecordingAttempt} payload, containing the position, inserted disc and the client's music.
    /// @param context The payload context.
    @SuppressWarnings("deprecation")
    public static void startRecordingAttempt(StartRecordingAttempt receival, IPayloadContext context) {
        if (!(context.player().level() instanceof ServerLevel level) || !level.hasChunkAt(receival.position())) return;
        BlockState state = level.getBlockState(receival.position());

        if (!(state.getBlock() instanceof MusicRecorderBlock recorder)) return;

        if (receival.clientMusicID().isPresent()) {
            // always record current song first
            recorder.tryRecordingFromPlayer(level, state, receival.position(), context.player(), receival.recordableDisc(), receival.clientMusicID().get());
        } else {
            // if none is playing, try recording from an adjacent jukebox
            recorder.tryRecordingFromAdjacentJukebox(level, state, receival.position(), context.player(), receival.recordableDisc());
        }

        context.player().awardStat(Stats.ITEM_USED.get(receival.recordableDisc().getItem()));
    }
}
