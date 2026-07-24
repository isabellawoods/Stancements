package melonystudios.stancements.client.element;

import melonystudios.stancements.util.Alignment;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

// i'll move this to renderslice later when i actually make the mod
public class StackedRenderComponents {
    public static void drawTextWithBackdrop(GuiGraphics graphics, Font font, Component text, int x, int y, int frontTextColor, int backTextColor) {
        graphics.drawString(font, text.copy().withStyle(Style.EMPTY), x + 1, y + 1, backTextColor, false);
        graphics.drawString(font, text, x, y, frontTextColor, false);
    }

    public static void drawCenteredTextWithBackdrop(GuiGraphics graphics, Font font, Component text, int x, int y, int frontTextColor, int backTextColor) {
        FormattedCharSequence sequence = text.getVisualOrderText();
        graphics.drawString(font, text.copy().withStyle(Style.EMPTY).getVisualOrderText(), x - font.width(sequence) / 2 + 1, y + 1, backTextColor, false);
        graphics.drawString(font, sequence, x - font.width(sequence) / 2, y, frontTextColor, false);
    }

    public static void drawCenteredWordWrap(GuiGraphics graphics, Font font, FormattedText text, int x, int y, int lineWidth, int color) {
        for (FormattedCharSequence sequence : font.split(text, lineWidth)) {
            graphics.drawCenteredString(font, sequence, x, y, color);
            y += 9;
        }
    }

    /// @author isabellawoods, [*Mellow UI* `5.0.0-beta.4`](https://github.com/isabellawoods/Mellow-UI/blob/536a0b4e4dc3c3b472d1e2c6df149f4324f83740/src/main/java/melonystudios/mellowui/element/text/ScrollingText.java#L22-L24)
    public static void renderAlignedScrollingText(GuiGraphics graphics, Font font, Component text, Alignment alignment, int minX, int minY, int maxX, int maxY, int frontColor, int backColor) {
        renderAlignedScrollingText(graphics, font, text, alignment, (minX + maxX) / 2, minX, minY, maxX, maxY, frontColor, backColor);
    }

    /// @author isabellawoods, [*Mellow UI* `5.0.0-beta.4`](https://github.com/isabellawoods/Mellow-UI/blob/536a0b4e4dc3c3b472d1e2c6df149f4324f83740/src/main/java/melonystudios/mellowui/element/text/ScrollingText.java#L26-L50)
    public static void renderAlignedScrollingText(GuiGraphics graphics, Font font, Component text, Alignment alignment, int centerX, int minX, int minY, int maxX, int maxY, int frontColor, int backColor) {
        int textWidth = font.width(text);
        int textY = (minY + maxY - 9) / 2 + 1;
        int buttonWidth = maxX - minX;
        if (textWidth > buttonWidth) {
            renderScrollingText(graphics, font, text, minX, minY, maxX, maxY, frontColor, backColor, textWidth, buttonWidth, textY);
        } else {
            switch (alignment) {
                case LEFT: {
                    drawTextWithBackdrop(graphics, font, text, minX, textY, frontColor, backColor);
                    break;
                }
                case RIGHT: {
                    int textX = maxX - font.width(text);
                    drawTextWithBackdrop(graphics, font, text, textX, textY, frontColor, backColor);
                    break;
                }
                case CENTER: default: {
                    int textX = Mth.clamp(centerX, minX + textWidth / 2, maxX - textWidth / 2);
                    drawCenteredTextWithBackdrop(graphics, font, text, textX, textY, frontColor, backColor);
                    break;
                }
            }
        }
    }

    /// @author isabellawoods, [*Mellow UI* `5.0.0-beta.4`](https://github.com/isabellawoods/Mellow-UI/blob/536a0b4e4dc3c3b472d1e2c6df149f4324f83740/src/main/java/melonystudios/mellowui/element/text/ScrollingText.java#L52-L61)
    public static void renderScrollingText(GuiGraphics graphics, Font font, Component text, int minX, int minY, int maxX, int maxY, int frontColor, int backColor, int textWidth, int buttonWidth, int textY) {
        int widthDiff = textWidth - buttonWidth;
        double time = (double) Util.getMillis() / 1000;
        double i3 = Math.max((double) widthDiff * 0.5, 3);
        double i4 = Math.sin(Math.PI / 2 * Math.cos(Math.PI * 2 * time / i3)) / 2 + 0.5;
        double xOffset = Mth.lerp(i4, 0, widthDiff);
        graphics.enableScissor(minX, minY, maxX, maxY);
        graphics.drawString(font, text.copy().withStyle(Style.EMPTY), minX - (int) xOffset + 1, textY + 1, backColor, false);
        graphics.drawString(font, text, minX - (int) xOffset, textY, frontColor, false);
        graphics.disableScissor();
    }
}
