package melonystudios.stancements.item.custom;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.InventoryRecorder;
import melonystudios.stancements.component.custom.MusicData;
import melonystudios.stancements.misc.recording.Track;
import melonystudios.stancements.option.STCommonOptions;
import melonystudios.stancements.util.ReAPI;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class RecordedDiscItem extends Item {
    public static final int DEFAULT_DISC_COLOR = 0xFFF9FFFE;
    public static final int DISC_LABEL_MIN = 1;
    public static final int DISC_LABEL_MAX = 14;

    public RecordedDiscItem(Properties properties) {
        super(properties
                .component(STDataComponents.LABEL, (float) DISC_LABEL_MIN)
                .component(DataComponents.DYED_COLOR, new DyedItemColor(DEFAULT_DISC_COLOR))
                .component(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT.withHidden(DataComponents.DYED_COLOR, true))
        );
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        MusicData data = stack.get(STDataComponents.MUSIC_DATA);
        if (data != null && data.copied() && ReAPI.shouldDisplay(stack, Stancements.stancements("recorded_disc/copied"))) {
            tooltip.accept(Component.translatable("tooltip.stancements.recorded_disc.copied").withStyle(ChatFormatting.DARK_GRAY));
        }

        if (stack.has(DataComponents.JUKEBOX_PLAYABLE)) return;

        if (data != null && data.id().isPresent() && ReAPI.shouldDisplay(stack, Stancements.stancements("recorded_disc/sound_id"))) {
            tooltip.accept(Component.translatable("tooltip.stancements.music_id_present.warning").withColor(InventoryRecorder.State.PAUSED.color()));
            tooltip.accept(Component.empty());

            tooltip.accept(Component.translatable("tooltip.stancements.recorded_disc.sound_id", data.id().get().toString()).withStyle(ChatFormatting.GRAY));
        } else if (ReAPI.shouldDisplay(stack, Stancements.stancements("recorded_disc/blank"))) {
            tooltip.accept(Component.translatable("tooltip.stancements.recorded_disc.blank").withStyle(ChatFormatting.GRAY));
        }
    }

    /// Returns the identifier of a {@linkplain melonystudios.stancements.misc.STJukeboxSongs jukebox song} based on the recorded `track`.
    /// @param trackID An identifier of the song's location within the game's files.
    public static Identifier getJukeboxSongLocation(Identifier trackID) {
        return Identifier.parse(trackID.toString()
                .replace("sounds/", "")
                .replace("music/", "")
                .replace("music_disc/", "") // "music_disc" for Project Alcook's dead forest biome ~isa 19-05-26
                .replace("records/", "")
                .replace(".ogg", ""));
    }

    public static boolean setJukeboxSong(Level level, ItemStack stack, Track track, boolean copying, boolean reduceWrites) {
        var jukeboxSongs = level.registryAccess().lookupOrThrow(Registries.JUKEBOX_SONG);
        var song = jukeboxSongs.get(track.jukeboxSongID());

        if (song.isPresent()) {
            MusicData data = stack.getOrDefault(STDataComponents.MUSIC_DATA, MusicData.data()).withTrack(track);
            if (!data.copied() && copying) data.markAsCopy(true);

            stack.set(STDataComponents.MUSIC_DATA, data);
            stack.set(DataComponents.JUKEBOX_PLAYABLE, new JukeboxPlayable(song.get()));
            return true;
        } else if (!reduceWrites) {
            stack.set(STDataComponents.MUSIC_DATA, MusicData.of(track, copying));
            return false;
        }

        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, level, owner, slot);
        if (level.isClientSide() || !STCommonOptions.RECORDED_DISC_AUTO_CONVERSION.get()) return;

        // remove old "music_id" component from 1.16
        Identifier id = stack.get(STDataComponents.MUSIC_ID);
        if (!stack.has(DataComponents.JUKEBOX_PLAYABLE) && id != null) {
            setJukeboxSong(level, stack, new Track(id, false), false, true);
            stack.remove(STDataComponents.MUSIC_ID);
        }

        // add the jukebox_playable component if it doesn't exist (if its source data pack gets disabled,
        // for example)
        MusicData data = stack.getOrDefault(STDataComponents.MUSIC_DATA, MusicData.data());
        if (!stack.has(DataComponents.JUKEBOX_PLAYABLE) && data.id().isPresent()) {
            setJukeboxSong(level, stack, new Track(data.id().get(), false), false, true);
        }
    }
}
