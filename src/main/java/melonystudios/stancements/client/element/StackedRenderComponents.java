package melonystudios.stancements.client.element;

import melonystudios.stancements.util.Alignment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;

// i'll move this to renderslice later when i actually make the mod
public class StackedRenderComponents {
    public static void centeredTextWithWordWrap(GuiGraphicsExtractor graphics, Font font, FormattedText text, int x, int y, int lineWidth, int color) {
        for (FormattedCharSequence sequence : font.split(text, lineWidth)) {
            graphics.centeredText(font, sequence, x, y, color);
            y += 9;
        }
    }

    /// @author isabellawoods, [*Mellow UI* `5.0.0-beta.4`](https://github.com/isabellawoods/Mellow-UI/blob/536a0b4e4dc3c3b472d1e2c6df149f4324f83740/src/main/java/melonystudios/mellowui/element/text/ScrollingText.java#L22-L24)
    public static void alignedScrollingText(GuiGraphicsExtractor graphics, Font font, Component text, Alignment alignment, int minX, int minY, int maxX, int maxY, int color) {
        alignedScrollingText(graphics, font, text, alignment, (minX + maxX) / 2, minX, minY, maxX, maxY, color);
    }

    /// @author isabellawoods, [*Mellow UI* `5.0.0-beta.4`](https://github.com/isabellawoods/Mellow-UI/blob/536a0b4e4dc3c3b472d1e2c6df149f4324f83740/src/main/java/melonystudios/mellowui/element/text/ScrollingText.java#L26-L50)
    public static void alignedScrollingText(GuiGraphicsExtractor graphics, Font font, Component text, Alignment alignment, int centerX, int minX, int minY, int maxX, int maxY, int color) {
        int textWidth = font.width(text);
        int textY = (minY + maxY - 9) / 2 + 1;
        int buttonWidth = maxX - minX;
        if (textWidth > buttonWidth) {
            scrollingText(graphics, font, text, minX, minY, maxX, maxY, color, textWidth, buttonWidth, textY);
        } else {
            switch (alignment) {
                case LEFT: {
                    graphics.text(font, text, minX, textY, color);
                    break;
                }
                case RIGHT: {
                    int textX = maxX - font.width(text);
                    graphics.text(font, text, textX, textY, color);
                    break;
                }
                case CENTER: default: {
                    int textX = Mth.clamp(centerX, minX + textWidth / 2, maxX - textWidth / 2);
                    graphics.centeredText(font, text, textX, textY, color);
                    break;
                }
            }
        }
    }

    /// @author isabellawoods, [*Mellow UI* `5.0.0-beta.4`](https://github.com/isabellawoods/Mellow-UI/blob/536a0b4e4dc3c3b472d1e2c6df149f4324f83740/src/main/java/melonystudios/mellowui/element/text/ScrollingText.java#L52-L61)
    public static void scrollingText(GuiGraphicsExtractor graphics, Font font, Component text, int minX, int minY, int maxX, int maxY, int color, int textWidth, int buttonWidth, int textY) {
        int widthDiff = textWidth - buttonWidth;
        double time = (double) Util.getMillis() / 1000;
        double i3 = Math.max((double) widthDiff * 0.5, 3);
        double i4 = Math.sin(Math.PI / 2 * Math.cos(Math.PI * 2 * time / i3)) / 2 + 0.5;
        double xOffset = Mth.lerp(i4, 0, widthDiff);
        graphics.enableScissor(minX, minY, maxX, maxY);
        graphics.text(font, text, minX - (int) xOffset + 1, textY + 1, color, false);
        graphics.disableScissor();
    }
}
