package melonystudios.stancements.misc.modifier;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ModificationStrategy implements StringRepresentable {
    /// Makes the modifier run right as the recording process starts.
    START("start"),
    /// Makes the modifier run right as the recording process ends.
    FINISH("finish"),
    /// Makes the modifier run after the recordable disc is ejected.
    EJECT("eject");

    public static final ModificationStrategy[] VALUES = ModificationStrategy.values();
    public static final Codec<ModificationStrategy> CODEC = StringRepresentable.fromEnum(() -> VALUES);
    private final String name;

    ModificationStrategy(String name) {
        this.name = name;
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return this.name;
    }
}
