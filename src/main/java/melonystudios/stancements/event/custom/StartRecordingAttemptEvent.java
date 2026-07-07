package melonystudios.stancements.event.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Optional;

/// This event is fired when the player attempts to record any music using the **music recorder**, regardless of what type of recording it may be (client music or adjacent).
///
/// This event is fired on the {@linkplain NeoForge#EVENT_BUS main *NeoForge* event bus}, and is {@linkplain ICancellableEvent cancelable}.
/// @see melonystudios.stancements.block.custom.MusicRecorderBlock#tryRecordingFromPlayer MusicRecorderBlock.tryRecordingFromPlayer()
/// @see melonystudios.stancements.block.custom.MusicRecorderBlock#tryRecordingFromAdjacentBlock MusicRecorderBlock.tryRecordingFromAdjacentBlock()
public class StartRecordingAttemptEvent extends PlayerEvent implements ICancellableEvent {
    private final BlockPos recorderPosition;
    private final ItemStack recordableDisc;
    private Optional<ResourceLocation> clientMusicID;

    /// @param player The player recording the music.
    /// @param recorderPosition The block position of the music recorder.
    /// @param recordableDisc The item stack of the item being used to record (should always have the `stancements:recording_turns_into` component).
    /// @param clientMusicID An *optional* resource location of the client's currently playing music from their {@link net.minecraft.client.sounds.MusicManager MusicManager}.
    public StartRecordingAttemptEvent(Player player, BlockPos recorderPosition, ItemStack recordableDisc, Optional<ResourceLocation> clientMusicID) {
        super(player);
        this.recorderPosition = recorderPosition;
        this.recordableDisc = recordableDisc;
        this.clientMusicID = clientMusicID;
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

    /// @return An *optional* resource location of the client's currently playing music from their {@link net.minecraft.client.sounds.MusicManager MusicManager}.
    public Optional<ResourceLocation> clientMusicID() {
        return this.clientMusicID;
    }

    /// Sets the client's music to the provided resource location.
    /// @param clientMusicID The {@linkplain ResourceLocation music ID} to use for the recording.
    public void withClientMusic(ResourceLocation clientMusicID) {
        this.clientMusicID = Optional.of(clientMusicID);
    }

    public static StartRecordingAttemptEvent recordMusic(Player player, BlockPos recorderPosition, ItemStack recordableDisc, Optional<ResourceLocation> clientMusicID) {
        StartRecordingAttemptEvent event = new StartRecordingAttemptEvent(player, recorderPosition, recordableDisc, clientMusicID);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }
}
