package melonystudios.stancements.misc.recording;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum RecordingSource implements StringRepresentable {
    MUSIC_RECORDER(0, "music_recorder"),
    MUSIC_REMIXER(1, "music_remixer"),
    INVENTORY_RECORDER(2, "inventory_recorder");

    public static final RecordingSource[] VALUES = RecordingSource.values();
    public static final Codec<RecordingSource> CODEC = StringRepresentable.fromEnum(() -> VALUES);
    private final int id;
    private final String name;

    RecordingSource(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int id() {
        return this.id;
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return this.name;
    }
}
