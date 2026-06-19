package melonystudios.stancements.event.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;

/// This event is fired when the player attempts to record any music using the **music recorder**.
///
/// Depending on what kind of recording is being done, one of two subevents are fired:
/// - **{@link ClientMusicRecording ClientMusicRecording}**: fired when recording the song being played in the client's {@link net.minecraft.client.sounds.MusicManager MusicManager}.
/// - **{@link AdjacentRecording AdjacentRecording}**: fired when recording from an adjacent block. Which direction is the block hasn't been decided when this event fires.
///
/// This event is fired on the {@linkplain NeoForge#EVENT_BUS main *NeoForge* event bus}, and is {@linkplain ICancellableEvent cancelable}.
/// @see melonystudios.stancements.block.custom.MusicRecorderBlock#tryRecordingFromPlayer MusicRecorderBlock.tryRecordingFromPlayer()
/// @see melonystudios.stancements.block.custom.MusicRecorderBlock#tryRecordingFromAdjacentBlock MusicRecorderBlock.tryRecordingFromAdjacentBlock()
public abstract class StartRecordingAttemptEvent extends PlayerEvent implements ICancellableEvent {
    private final BlockPos recorderPosition;
    private final ItemStack recordableDisc;

    /// @param player The player recording the music.
    /// @param recorderPosition The block position of the music recorder.
    /// @param recordableDisc The item stack of the item being used to record (should always have the `stancements:recording_turns_into` component).
    public StartRecordingAttemptEvent(Player player, BlockPos recorderPosition, ItemStack recordableDisc) {
        super(player);
        this.recorderPosition = recorderPosition;
        this.recordableDisc = recordableDisc;
    }

    /// @return The block position of the music recorder.
    public BlockPos recorderPosition() {
        return this.recorderPosition;
    }

    /// @return The item stack of the item being used to record. This item should *always* have the
    /// `stancements:recording_turns_into` component.
    public ItemStack recordableDisc() {
        return this.recordableDisc;
    }

    @ApiStatus.Internal
    public static ClientMusicRecording recordClientMusic(Player player, BlockPos recorderPosition, ItemStack recordableDisc, Optional<ResourceLocation> clientMusicID) {
        ClientMusicRecording event = new ClientMusicRecording(player, recorderPosition, recordableDisc, clientMusicID);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }

    @ApiStatus.Internal
    public static AdjacentRecording recordFromAdjacentBlock(Player player, BlockPos recorderPosition, ItemStack recordableDisc) {
        AdjacentRecording event = new AdjacentRecording(player, recorderPosition, recordableDisc);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }

    public static class ClientMusicRecording extends StartRecordingAttemptEvent {
        private Optional<ResourceLocation> clientMusicID;

        /// @param player The player recording the music.
        /// @param recorderPosition The block position of the music recorder.
        /// @param recordableDisc The item stack of the item being used to record (should always have the `stancements:recording_turns_into` component).
        /// @param clientMusicID An *optional* resource location of the client's currently playing music from their {@link net.minecraft.client.sounds.MusicManager MusicManager}.
        public ClientMusicRecording(Player player, BlockPos recorderPosition, ItemStack recordableDisc, Optional<ResourceLocation> clientMusicID) {
            super(player, recorderPosition, recordableDisc);
            this.clientMusicID = clientMusicID;
        }

        /// @return An *optional* resource location of the client's currently playing music from their {@link net.minecraft.client.sounds.MusicManager MusicManager}.
        public Optional<ResourceLocation> clientMusicID() {
            return this.clientMusicID;
        }

        /// Sets the client's music to the provided resource location.
        /// @param clientMusicID The {@linkplain ResourceLocation music ID} to use for the recording.
        public void withClientMusic(ResourceLocation clientMusicID) {
            this.clientMusicID = Optional.of(clientMusicID);
        }
    }

    public static class AdjacentRecording extends StartRecordingAttemptEvent {
        /// @param player The player recording the music.
        /// @param recorderPosition The block position of the music recorder.
        /// @param recordableDisc The item stack of the item being used to record (should always have the `stancements:recording_turns_into` component).
        public AdjacentRecording(Player player, BlockPos recorderPosition, ItemStack recordableDisc) {
            super(player, recorderPosition, recordableDisc);
        }
    }
}
