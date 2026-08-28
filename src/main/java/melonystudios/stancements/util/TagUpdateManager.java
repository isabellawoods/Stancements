package melonystudios.stancements.util;

import com.mojang.logging.LogUtils;
import melonystudios.stancements.misc.recording.Track;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.function.Consumer;

public class TagUpdateManager {
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final Marker RECORDER = MarkerFactory.getMarker("MusicRecorder");

    public static void readRecorderTrack(CompoundTag tag, BlockPos pos, Consumer<Track> setter) {
        String tagName;
        if (tag.contains("music_id", Tag.TAG_STRING) && !(tag.contains("track", Tag.TAG_STRING) || tag.contains("track", Tag.TAG_COMPOUND))) {
            tagName = "music_id";
        } else {
            tagName = "track";
        }

        if (tag.contains(tagName)) Track.CODEC.parse(NbtOps.INSTANCE, tag.get(tagName))
                .resultOrPartial(error -> LOGGER.error(RECORDER, "Failed to read the '{}' tag from a Music Recorder block entity at [{}, {}, {}]: {}", tagName, pos.getX(), pos.getY(), pos.getZ(), error))
                .ifPresent(setter);
    }

    public static void saveRecorderTrack(CompoundTag tag, Track track, BlockPos pos) {
        if (tag.contains("music_id")) tag.remove("music_id");

        if (track != null) Track.CODEC.encodeStart(NbtOps.INSTANCE, track)
                .resultOrPartial(error -> LOGGER.error(RECORDER, "Failed to save the 'track' tag of a Music Recorder block entity at [{}, {}, {}]: {}", pos.getX(), pos.getY(), pos.getZ(), error))
                .ifPresent(trackTag -> tag.put("track", trackTag));
    }
}
