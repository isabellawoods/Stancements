package melonystudios.stancements.util;

import com.mojang.logging.LogUtils;
import melonystudios.stancements.misc.recording.Track;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.function.Consumer;

public class TagUpdateManager {
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final Marker RECORDER = MarkerFactory.getMarker("MusicRecorder");

    public static void readRecorderTrack(ValueInput input, BlockPos pos, Consumer<Track> setter) {
        String tagName;
        if (input.child("music_id").isPresent() && input.child("track").isEmpty()) {
            tagName = "music_id";
        } else {
            tagName = "track";
        }

        input.child(tagName).ifPresent(_ -> input.read(tagName, Track.CODEC).ifPresentOrElse(setter, () ->
                LOGGER.error(RECORDER, "Failed to read the '{}' tag from a Music Recorder block entity at [{}, {}, {}]",
                        tagName, pos.getX(), pos.getY(), pos.getZ())));
    }

    public static void saveRecorderTrack(ValueOutput output, @Nullable Track track, BlockPos pos) {
        output.discard("music_id");
        output.storeNullable("track", Track.CODEC, track);
    }
}
