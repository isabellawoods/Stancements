package melonystudios.stancements.client.element;

import melonystudios.stancements.client.STClient;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractStringWidget;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.*;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;

public class BackdropStringWidget extends AbstractStringWidget {
    private float alignX = 0.5F;

    public BackdropStringWidget(Component message, Font font) {
        this(0, 0, font.width(message.getVisualOrderText()), 9, message, font);
    }

    public BackdropStringWidget(int width, int height, Component message, Font font) {
        this(0, 0, width, height, message, font);
    }

    public BackdropStringWidget(int x, int y, int width, int height, Component message, Font font) {
        super(x, y, width, height, message, font);
        this.active = false;
    }

    @NotNull
    public BackdropStringWidget setColor(int color) {
        super.setColor(color);
        return this;
    }

    private BackdropStringWidget horizontalAlignment(float horizontalAlignment) {
        this.alignX = horizontalAlignment;
        return this;
    }

    public BackdropStringWidget alignLeft() {
        return this.horizontalAlignment(0);
    }

    public BackdropStringWidget alignCenter() {
        return this.horizontalAlignment(0.5F);
    }

    public BackdropStringWidget alignRight() {
        return this.horizontalAlignment(1);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        Component message = this.getMessage();
        Font font = this.getFont();
        int width = this.getWidth();
        int messageWidth = font.width(message);
        int textX = this.getX() + Math.round(this.alignX * (float) (width - messageWidth));
        int textY = this.getY() + (this.getHeight() - 9) / 2;

        FormattedCharSequence frontMessage = messageWidth > width ? this.clipText(message, width, false) : message.getVisualOrderText();
        FormattedCharSequence backMessage = messageWidth > width ? this.clipText(message, width, true) : StackedRenderComponents.stripTextColor(message);
        StackedRenderComponents.drawTextWithBackdrop(graphics, font, frontMessage, backMessage, textX, textY, this.getColor(), STClient.TRANSPARENT_TEXT_BACKDROP);
    }

    private FormattedCharSequence clipText(Component message, int width, boolean background) {
        Font font = this.getFont();
        Component component = background ? message.copy().withStyle(style -> style.withColor((TextColor) null)) : message;
        FormattedText formattedMessage = font.substrByWidth(component, width - font.width(CommonComponents.ELLIPSIS));
        return Language.getInstance().getVisualOrder(FormattedText.composite(formattedMessage, CommonComponents.ELLIPSIS));
    }
}
