package melonystudios.stancements.network;

import io.netty.buffer.ByteBuf;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.recording.Track;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

/// ### This is a payload directed towards the *server*.
/// Sends a client's {@linkplain melonystudios.stancements.mixin.recorder.CurrentMusicAccessor music track} to the server to be recorder by an {@linkplain melonystudios.stancements.component.custom.InventoryRecorder inventory recorder}.
/// @param clientTrack A music {@linkplain Track track}.
/// @param slotIndex Which slot the recorder has been found in (according to the client's `Inventory` at least).
public record SendClientTrack(Track clientTrack, short slotIndex) implements CustomPacketPayload {
    public static final StreamCodec<ByteBuf, SendClientTrack> STREAM_CODEC = StreamCodec.composite(
            Track.STREAM_CODEC,
            SendClientTrack::clientTrack,
            ByteBufCodecs.SHORT,
            SendClientTrack::slotIndex,
            SendClientTrack::new
    );
    public static final CustomPacketPayload.Type<SendClientTrack> TYPE = new Type<>(Stancements.stancements("send_client_track"));

    @Override
    @NotNull
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
