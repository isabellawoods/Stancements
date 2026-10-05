package melonystudios.stancements.network;

import com.google.common.collect.ImmutableMap;
import io.netty.buffer.ByteBuf;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.recording.Track;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.EquipmentSlot;
import org.jspecify.annotations.NonNull;

import java.util.Map;

/// ### This is a payload directed towards the *server*.
/// Sends a client's [music track][melonystudios.stancements.mixin.recorder.CurrentMusicAccessor] to the server to be recorder by an [inventory recorder][melonystudios.stancements.component.custom.InventoryRecorder].
/// @param clientTrack A music [track][Track].
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
    public static final Map<EquipmentSlot, Short> EQUIPMENT_SLOT_INDEXES = new ImmutableMap.Builder<EquipmentSlot, Short>()
            .put(EquipmentSlot.MAINHAND, (short) 98).put(EquipmentSlot.OFFHAND, (short) 99)
            .put(EquipmentSlot.HEAD, (short) (100 + EquipmentSlot.HEAD.getIndex()))
            .put(EquipmentSlot.CHEST, (short) (100 + EquipmentSlot.CHEST.getIndex()))
            .put(EquipmentSlot.LEGS, (short) (100 + EquipmentSlot.LEGS.getIndex()))
            .put(EquipmentSlot.FEET, (short) (100 + EquipmentSlot.FEET.getIndex()))
            .put(EquipmentSlot.BODY, (short) 105)
            .put(EquipmentSlot.SADDLE, (short) 106)
            .build();

    @Override
    @NonNull
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
