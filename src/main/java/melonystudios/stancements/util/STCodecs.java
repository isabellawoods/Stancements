package melonystudios.stancements.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import java.util.function.Function;

public class STCodecs {
    /// Creates a {@linkplain Codec#LONG long codec} that has a specified range.
    /// @param min The minimum bound for this codec.
    /// @param max The maximum bound for this codec.
    public static Codec<Long> longRange(long min, long max) {
        return longRange(min, max, value -> String.format("Value must be within %s and %s; is %s", min, max, value));
    }

    /// Creates a {@linkplain Codec#LONG long codec} that has a specified range.
    /// @param min The minimum bound for this codec.
    /// @param max The maximum bound for this codec.
    /// @param errorMessage A function to get the error message for when the codec gets a value outside its bounds.
    public static Codec<Long> longRange(long min, long max, Function<Long, String> errorMessage) {
        return Codec.LONG.validate(value -> value.compareTo(min) >= 0 && value.compareTo(max) <= 0 ? DataResult.success(value) : DataResult.error(() -> errorMessage.apply(value)));
    }
}
