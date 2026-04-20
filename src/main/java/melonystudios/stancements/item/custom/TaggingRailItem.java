package melonystudios.stancements.item.custom;

import melonystudios.reutilities.api.ReAPI;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.TagMatcherType;
import melonystudios.stancements.block.custom.TaggingRailBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Consumer;

public class TaggingRailItem extends BlockItem {
    private final TaggingRailBlock railBlock;

    public TaggingRailItem(Block block, Properties properties) {
        super(block, properties);
        this.railBlock = (TaggingRailBlock) block;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        if (ReAPI.shouldDisplay(stack, Stancements.stancements("tagging_rail/tooltip")) && !this.railBlock.detectsColors().isEmpty()) {
            MutableComponent colors = prettyPrintTagColors(this.railBlock.matcherType(), this.railBlock.detectsColors(), true);
            tooltip.accept(Component.translatable("tooltip.stancements.tagging_rail." + (this.railBlock.detectsColors().size() == 1 ? "single" : "multiple"), colors)
                    .withStyle(ChatFormatting.GRAY));

            tooltip.accept(Component.translatable("tooltip.stancements.tagging_rail.tagless").withStyle(ChatFormatting.GRAY));
        }
    }

    /// Takes a list of {@link DyeColor DyeColors} and puts them into a single text component.
    /// @param matcher A {@linkplain TagMatcherType tag matcher} used for the list. Can be `all` for "and" and `any_of` for "or".
    /// @param colors The list of dye colors to print.
    /// @param italicize Whether the "and" or "or" at the end should be italicized.
    public static MutableComponent prettyPrintTagColors(TagMatcherType matcher, List<DyeColor> colors, boolean italicize) {
        MutableComponent component = Component.empty();
        for (int i = 0; i < colors.size(); ++i) {
            DyeColor color = colors.get(i);
            component.append(Component.translatable("color.minecraft." + color).withColor(color.getTextureDiffuseColor()));

            if (i == colors.size() - 2) {
                component.append(Component.translatable("tooltip.stancements.delimiter." + matcher.getSerializedName()).withStyle(style -> style.withItalic(italicize)));
            } else if (i != colors.size() - 1) {
                component.append(Component.translatable("tooltip.stancements.delimiter"));
            }
        }
        return component;
    }
}
