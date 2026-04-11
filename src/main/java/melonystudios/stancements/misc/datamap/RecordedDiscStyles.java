package melonystudios.stancements.misc.datamap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.reutilities.api.ReCodecs;
import melonystudios.stancements.item.custom.RecordedDiscItem;

/// Data map value for the style used when {@linkplain STDataMaps#RECORDED_DISC_STYLES recording songs}, allowing mods to register
/// custom looks for recording discs with existing jukebox songs.
/// @param color The color used for the disc's label color. Can be either in **decimal** or **hexadecimal**.
/// @param label The label used for the disc's label. Can be any value from {@link RecordedDiscItem#DISC_LABEL_MIN 1} to {@link RecordedDiscItem#DISC_LABEL_MAX 13} for the existing record labels.
public record RecordedDiscStyles(int color, float label) {
    public static final Codec<RecordedDiscStyles> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ReCodecs.HEX_INT_CODEC.fieldOf("color").forGetter(RecordedDiscStyles::color),
            ReCodecs.floatRange(RecordedDiscItem.DISC_LABEL_MIN, RecordedDiscItem.DISC_LABEL_MAX).fieldOf("label").forGetter(RecordedDiscStyles::label)
    ).apply(instance, RecordedDiscStyles::new));
}
