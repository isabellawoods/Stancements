package melonystudios.stancements.misc.discstyle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Rarity;

import java.util.Optional;

/// Style used when **recording songs**, allowing mods to register custom looks for their discs when copied using the music recorder.
///
/// `color` or `label` may be omitted to make a disc have the same color but different label, or same label with different colors, **but these fields cannot be omitted simultaneously!**
/// @param color *(optional)* The color used for the disc's label color. Can be either in **decimal** or **hexadecimal**.
/// @param label *(optional)* The label used for the disc's label. Can be any value from [`1`][RecordedDiscItem#DISC_LABEL_MIN] to [`14`][RecordedDiscItem#DISC_LABEL_MAX] for the existing record labels.
/// @param rarity *(optional)* The rarity of the recorded disc item. Defaults to [uncommon][Rarity#UNCOMMON] (yellow).
// unfortunately had to move this to a registry as data maps don't accept conditions field within the values themselves ~isa 19-05-26
public record RecordedDiscStyle(Optional<Integer> color, float label, Rarity rarity) {
    public static final Codec<RecordedDiscStyle> CODEC = RecordCodecBuilder.<RecordedDiscStyle>create(instance -> instance.group(
            ExtraCodecs.STRING_RGB_COLOR.optionalFieldOf("color").forGetter(RecordedDiscStyle::color),
            ExtraCodecs.floatRange(RecordedDiscItem.DISC_LABEL_MIN - 1F, RecordedDiscItem.DISC_LABEL_MAX).optionalFieldOf("label", 0F).forGetter(RecordedDiscStyle::label),
            Rarity.CODEC.optionalFieldOf("rarity", Rarity.UNCOMMON).forGetter(RecordedDiscStyle::rarity)
    ).apply(instance, RecordedDiscStyle::new)).validate(discStyle -> {
        if (discStyle.color().isEmpty() && discStyle.label() < RecordedDiscItem.DISC_LABEL_MIN) {
            return DataResult.error(() -> "Recorded disc style must have 'color' and/or 'label', but not neither!");
        }
        return DataResult.success(discStyle);
    });

    /// Style used when **recording songs**, allowing mods to register custom looks for their discs when copied using the music recorder.
    /// @param color *(optional)* The color used for the disc's label color. Can be either in **decimal** or **hexadecimal**.
    /// @param label *(optional)* The label used for the disc's label. Can be any value from [`1`][RecordedDiscItem#DISC_LABEL_MIN] to [`14`][RecordedDiscItem#DISC_LABEL_MAX] for the existing record labels.
    /// @param rarity *(optional)* The rarity of the recorded disc item. Defaults to [uncommon][Rarity#UNCOMMON] (yellow).
    public RecordedDiscStyle(Integer color, float label, Rarity rarity) {
        this(Optional.ofNullable(color), label, rarity);
    }

    /// Style used when **recording songs**, allowing mods to register custom looks for their discs when copied using the music recorder.
    /// @param color *(optional)* The color used for the disc's label color. Can be either in **decimal** or **hexadecimal**.
    /// @param label *(optional)* The label used for the disc's label. Can be any value from [`1`][RecordedDiscItem#DISC_LABEL_MIN] to [`14`][RecordedDiscItem#DISC_LABEL_MAX] for the existing record labels.
    public RecordedDiscStyle(Integer color, float label) {
        this(Optional.ofNullable(color), label, Rarity.UNCOMMON);
    }

    /// Style used when **recording songs**, allowing mods to register custom looks for their discs when copied using the music recorder.
    /// @param color *(optional)* The color used for the disc's label color. Can be either in **decimal** or **hexadecimal** (prefixed with `#`).
    public RecordedDiscStyle(Integer color) {
        this(Optional.ofNullable(color), 0, Rarity.UNCOMMON);
    }

    /// Style used when **recording songs**, allowing mods to register custom looks for their discs when copied using the music recorder.
    /// @param label *(optional)* The label used for the disc's label. Can be any value from [`1`][RecordedDiscItem#DISC_LABEL_MIN] to [`14`][RecordedDiscItem#DISC_LABEL_MAX] for the existing record labels.
    public RecordedDiscStyle(float label) {
        this(Optional.empty(), label, Rarity.UNCOMMON);
    }
}
