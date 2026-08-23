package melonystudios.stancements.misc.discstyle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Rarity;

/// Style used when **recording songs**, allowing mods to register custom looks for their discs when copied using the music recorder.
/// @param color The color used for the disc's label color. Can be either in **decimal** or **hexadecimal**.
/// @param label The label used for the disc's label. Can be any value from {@link RecordedDiscItem#DISC_LABEL_MIN 1} to {@link RecordedDiscItem#DISC_LABEL_MAX 14} for the existing record labels.
/// @param rarity The rarity of the recorded disc item. Defaults to {@linkplain Rarity#UNCOMMON uncommon} (yellow).
// unfortunately had to move this to a registry as data maps don't accept conditions field within the values themselves ~isa 19-05-26
public record RecordedDiscStyle(int color, float label, Rarity rarity) {
    public static final Codec<RecordedDiscStyle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.STRING_RGB_COLOR.fieldOf("color").forGetter(RecordedDiscStyle::color),
            ExtraCodecs.floatRange(RecordedDiscItem.DISC_LABEL_MIN, RecordedDiscItem.DISC_LABEL_MAX).fieldOf("label").forGetter(RecordedDiscStyle::label),
            Rarity.CODEC.optionalFieldOf("rarity", Rarity.UNCOMMON).forGetter(RecordedDiscStyle::rarity)
    ).apply(instance, RecordedDiscStyle::new));

    /// Style used when **recording songs**, allowing mods to register custom looks for their discs when copied using the music recorder.
    /// @param color The color used for the disc's label color. Can be either in **decimal** or **hexadecimal**.
    /// @param label The label used for the disc's label. Can be any value from {@link RecordedDiscItem#DISC_LABEL_MIN 1} to {@link RecordedDiscItem#DISC_LABEL_MAX 14} for the existing record labels.
    public RecordedDiscStyle(int color, float label) {
        this(color, label, Rarity.UNCOMMON);
    }
}
