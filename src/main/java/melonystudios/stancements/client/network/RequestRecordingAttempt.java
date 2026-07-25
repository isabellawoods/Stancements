package melonystudios.stancements.client.network;

import melonystudios.stancements.Stancements;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

/// ### This is a payload directed towards the *client*.
/// Asks the client to start the music recording process, as it requires the {@linkplain melonystudios.stancements.mixin.recorder.CurrentMusicAccessor currently playing song in `MusicManager`}.
/// @param position The in-world position of the recorder (assuming it is in the same dimension).
/// @param recordableDisc The item stack (any item with the {@link melonystudios.stancements.component.STDataComponents#RECORDING_TURNS_INTO recording_turns_into} component) inserted into the recorder.
public record RequestRecordingAttempt(BlockPos position, ItemStack recordableDisc) implements CustomPacketPayload {
    public static final StreamCodec<RegistryFriendlyByteBuf, RequestRecordingAttempt> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            RequestRecordingAttempt::position,
            ItemStack.OPTIONAL_STREAM_CODEC,
            RequestRecordingAttempt::recordableDisc,
            RequestRecordingAttempt::new
    );
    public static final Type<RequestRecordingAttempt> TYPE = new Type<>(Stancements.stancements("request_recording_attempt"));

    @Override
    @NonNull
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
