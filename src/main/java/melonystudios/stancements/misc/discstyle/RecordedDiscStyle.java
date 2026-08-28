package melonystudios.stancements.misc.discstyle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.reutilities.api.ReCodecs;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.world.item.Rarity;

/// Style used when **recording songs**, allowing mods to register custom looks for their discs when copied using the music recorder.
///
/// `color` or `label` may be omitted to make a disc have the same color but different label, or same label with different colors, **but these fields cannot be omitted simultaneously!**
/// @param color *(optional)* The color used for the disc's label color. Can be either in **decimal** or **hexadecimal** (prefixed with `#`).
/// @param label *(optional)* The label used for the disc's label. Can be any value from {@link RecordedDiscItem#DISC_LABEL_MIN 1} to {@link RecordedDiscItem#DISC_LABEL_MAX 14} for the existing record labels.
/// @param rarity *(optional)* The rarity of the recorded disc item. Defaults to {@linkplain Rarity#UNCOMMON uncommon} (yellow).
// unfortunately had to move this to a registry as data maps don't accept conditions field within the values themselves ~isa 19-05-26
public record RecordedDiscStyle(int color, float label, Rarity rarity) {
    public static final Codec<RecordedDiscStyle> CODEC = RecordCodecBuilder.<RecordedDiscStyle>create(instance -> instance.group(
            ReCodecs.hexadecimalRange(-1, 0xFFFFFF).optionalFieldOf("color", -1).forGetter(RecordedDiscStyle::color),
            ReCodecs.floatRange(RecordedDiscItem.DISC_LABEL_MIN - 1F, RecordedDiscItem.DISC_LABEL_MAX).optionalFieldOf("label", 0F).forGetter(RecordedDiscStyle::label),
            Rarity.CODEC.optionalFieldOf("rarity", Rarity.UNCOMMON).forGetter(RecordedDiscStyle::rarity)
    ).apply(instance, RecordedDiscStyle::new)).validate(discStyle -> {
        if (discStyle.color() < 0 && discStyle.label() < RecordedDiscItem.DISC_LABEL_MIN) {
            return DataResult.error(() -> "Recorded disc style must have 'color' and/or 'label', but not neither!");
        }
        return DataResult.success(discStyle);
    });

    /// Style used when **recording songs**, allowing mods to register custom looks for their discs when copied using the music recorder.
    /// @param color *(optional)* The color used for the disc's label color. Can be either in **decimal** or **hexadecimal** (prefixed with `#`).
    /// @param label *(optional)* The label used for the disc's label. Can be any value from {@link RecordedDiscItem#DISC_LABEL_MIN 1} to {@link RecordedDiscItem#DISC_LABEL_MAX 14} for the existing record labels.
    public RecordedDiscStyle(int color, float label) {
        this(color, label, Rarity.UNCOMMON);
    }

    /// Style used when **recording songs**, allowing mods to register custom looks for their discs when copied using the music recorder.
    /// @param color *(optional)* The color used for the disc's label color. Can be either in **decimal** or **hexadecimal** (prefixed with `#`).
    public RecordedDiscStyle(int color) {
        this(color, 0, Rarity.UNCOMMON);
    }

    /// Style used when **recording songs**, allowing mods to register custom looks for their discs when copied using the music recorder.
    /// @param label *(optional)* The label used for the disc's label. Can be any value from {@link RecordedDiscItem#DISC_LABEL_MIN 1} to {@link RecordedDiscItem#DISC_LABEL_MAX 14} for the existing record labels.
    public RecordedDiscStyle(float label) {
        this(-1, label, Rarity.UNCOMMON);
    }
}
