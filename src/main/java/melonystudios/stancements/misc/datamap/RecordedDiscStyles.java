package melonystudios.stancements.misc.datamap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.reutilities.api.ReAPI;

/// Data map value for the style used when {@linkplain STDataMaps#RECORDED_DISC_STYLES recording songs}, allowing mods to register
/// custom looks for recording discs with existing jukebox songs.
/// @param color The color used for the disc's label color.
/// @param label The label used for the disc's label. Can be any value from `1` to `13` for the existing record labels.
public record RecordedDiscStyles(int color, float label) {
    public static final Codec<RecordedDiscStyles> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("color").forGetter(RecordedDiscStyles::color),
            ReAPI.floatRange(1, 13).fieldOf("label").forGetter(RecordedDiscStyles::label)
    ).apply(instance, RecordedDiscStyles::new));
}
