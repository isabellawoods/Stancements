package melonystudios.stancements.component.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public record MinecartTagColor(DyeColor color) implements TooltipProvider {
    public static final Codec<MinecartTagColor> CODEC = DyeColor.CODEC.comapFlatMap(color -> DataResult.success(new MinecartTagColor(color)), MinecartTagColor::color);
    public static final StreamCodec<ByteBuf, MinecartTagColor> STREAM_CODEC = DyeColor.STREAM_CODEC.map(MinecartTagColor::new, MinecartTagColor::color);

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> adder, TooltipFlag flag) {
        adder.accept(Component.translatable("tooltip.stancements.minecart_tag").withStyle(ChatFormatting.GRAY));
    }

    public static MinecartTagColor of(DyeColor color) {
        return new MinecartTagColor(color);
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("MinecartTagColor[%s]", this.color());
    }
}
