package melonystudios.stancements.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.function.Function;

public class STCodecs {
    public static final Codec<ItemStack> OPTIONAL_SINGLE_ITEM = ExtraCodecs.optionalEmptyMap(ItemStack.SINGLE_ITEM_CODEC).xmap(
            stack -> stack.orElse(ItemStack.EMPTY),
            stack -> stack.isEmpty() ? Optional.empty() : Optional.of(stack)
    );

    /// Creates a {@linkplain Codec#LONG long codec} that has a specified range.
    /// @param min The minimum bound for this codec.
    /// @param max The maximum bound for this codec.
    public static Codec<Long> longRange(long min, long max) {
        return longRange(min, max, value -> I18n.get("logger.reutilities.outside_bounds", min, max, value));
    }

    /// Creates a {@linkplain Codec#LONG long codec} that has a specified range.
    /// @param min The minimum bound for this codec.
    /// @param max The maximum bound for this codec.
    /// @param errorMessage A function to get the error message for when the codec gets a value outside its bounds.
    public static Codec<Long> longRange(long min, long max, Function<Long, String> errorMessage) {
        return Codec.LONG.validate(value -> value.compareTo(min) >= 0 && value.compareTo(max) <= 0 ? DataResult.success(value) : DataResult.error(() -> errorMessage.apply(value)));
    }
}
