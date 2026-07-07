package melonystudios.stancements.network.c2s;

import melonystudios.stancements.block.custom.MusicRecorderBlock;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import melonystudios.stancements.event.custom.StartRecordingAttemptEvent;
import melonystudios.stancements.mixin.recorder.CurrentMusicAccessor;
import melonystudios.stancements.option.STCommonOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;

/// Handles all serverbound payloads registered by *Stancements*.
/// @see StartRecordingAttempt
public class ServerPayloadHandler {
    /// Attempts to start a recording of music from the client's {@link CurrentMusicAccessor MusicManager} or from adjacent jukeboxes.
    /// @param receival The {@link StartRecordingAttempt} payload, containing the position, inserted disc, the client's music and the volumes of the client's sound-related sliders.
    /// @param context The payload context.
    @SuppressWarnings("deprecation")
    public static void startRecordingAttempt(StartRecordingAttempt receival, IPayloadContext context) {
        if (!(context.player().level() instanceof ServerLevel level) || !level.hasChunkAt(receival.position())) return;
        BlockState state = level.getBlockState(receival.position());

        if (!(state.getBlock() instanceof MusicRecorderBlock recorder)) return;
        if (!(level.getBlockEntity(receival.position()) instanceof MusicRecorderBlockEntity blockEntity)) return;

        // block any type of recording if their "Master Volume" is 0
        var volumes = receival.volumes();
        if (!volumes.master()) {
            // at least put the disc in the recorder if it can't be recorded
            blockEntity.insertDisc(receival.recordableDisc());
            return;
        }

        // fire recording event ~isa 26-06-26
        StartRecordingAttemptEvent event = StartRecordingAttemptEvent.recordMusic(context.player(), receival.position(), receival.recordableDisc(), receival.clientMusicID());
        if (event.isCanceled()) {
            // at least put the disc in the recorder if it can't be recorded
            blockEntity.insertDisc(receival.recordableDisc());
            return;
        }

        Optional<ResourceLocation> clientMusicID = receival.clientMusicID();
        if (event.clientMusicID().isPresent()) clientMusicID = event.clientMusicID();

        if (clientMusicID.isPresent() && volumes.music()) {
            // always record current song first
            recorder.tryRecordingFromPlayer(level, state, receival.position(), context.player(), receival.recordableDisc(), clientMusicID.get(), STCommonOptions.DEFAULT_RECORDING_DURATION.get());
        } else if (volumes.records()) {
            // if none is playing, try recording from an adjacent jukebox
            recorder.tryRecordingFromAdjacentBlock(level, state, receival.position(), context.player(), receival.recordableDisc());
        } else {
            // at least put the disc in the recorder if it can't be recorded
            blockEntity.insertDisc(receival.recordableDisc());
        }

        context.player().awardStat(Stats.ITEM_USED.get(receival.recordableDisc().getItem()));
    }
}
