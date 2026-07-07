package melonystudios.stancements.misc.modifier;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ModificationStrategy implements StringRepresentable {
    /// Makes the modifier run right as the recording process starts.
    START(0, "start"),
    /// Makes the modifier run right as the recording process ends.
    FINISH(1, "finish");

    public static final ModificationStrategy[] VALUES = ModificationStrategy.values();
    public static final Codec<ModificationStrategy> CODEC = StringRepresentable.fromEnum(() -> VALUES);
    private final int id;
    private final String name;

    ModificationStrategy(int id, String name) {
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
