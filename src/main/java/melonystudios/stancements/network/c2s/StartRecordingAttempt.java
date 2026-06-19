package melonystudios.stancements.network.c2s;

import io.netty.buffer.ByteBuf;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.mixin.recorder.CurrentMusicAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/// ### This is a payload directed towards the *server*.
/// Attempts to start a recording of:
/// 1. The song playing on the client's {@link CurrentMusicAccessor MusicManager};
/// 2. The music disc playing on an adjacent jukebox.
/// @param position The in-world position of the recorder (assuming it is in the same dimension).
/// @param recordableDisc The item stack (any item with the {@link melonystudios.stancements.component.STDataComponents#RECORDING_TURNS_INTO recording_turns_into} component) inserted into the recorder.
/// @param clientMusicID An *optional* {@linkplain ResourceLocation resource location} indicating the song playing to the client.
/// @param volumes Whether the "Master Volume", "Music" and "Jukebox/Note Block" sound sliders allow sounds to be played.
public record StartRecordingAttempt(BlockPos position, ItemStack recordableDisc, Optional<ResourceLocation> clientMusicID, MusicVolumes volumes) implements CustomPacketPayload {
    public static final StreamCodec<RegistryFriendlyByteBuf, StartRecordingAttempt> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            StartRecordingAttempt::position,
            ItemStack.OPTIONAL_STREAM_CODEC,
            StartRecordingAttempt::recordableDisc,
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC),
            StartRecordingAttempt::clientMusicID,
            MusicVolumes.STREAM_CODEC,
            StartRecordingAttempt::volumes,
            StartRecordingAttempt::new
    );
    public static final CustomPacketPayload.Type<StartRecordingAttempt> TYPE = new CustomPacketPayload.Type<>(Stancements.stancements("start_recording_attempt"));

    @Override
    @NotNull
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public record MusicVolumes(boolean master, boolean music, boolean records) {
        public static final StreamCodec<ByteBuf, MusicVolumes> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL,
                MusicVolumes::master,
                ByteBufCodecs.BOOL,
                MusicVolumes::music,
                ByteBufCodecs.BOOL,
                MusicVolumes::records,
                MusicVolumes::new
        );
    }
}
