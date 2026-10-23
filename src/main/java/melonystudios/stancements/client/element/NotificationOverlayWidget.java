package melonystudios.stancements.client.element;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.client.STClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;

public class NotificationOverlayWidget extends AbstractWidget {
    public static final Component COPIED_TO_CLIPBOARD = Component.translatable("menu.stancements.command_help.copied_text").withStyle(style -> style.withShadowColor(STClient.TRANSPARENT_TEXT_BACKDROP));
    private long notificationOverlayMillis = -1;
    private final int horizontalPadding;
    private final int verticalPadding;

    public NotificationOverlayWidget(int x, int y, int width, int height, Component message) {
        this(x, y, width, height, 9, 3, message);
    }

    public NotificationOverlayWidget(int x, int y, int width, int height, int horizontalPadding, int verticalPadding, Component message) {
        super(x, y, width, height, message);
        this.horizontalPadding = horizontalPadding;
        this.verticalPadding = verticalPadding;
    }

    public void displayNotificationOverlay() {
        this.notificationOverlayMillis = Util.getMillis();
    }

    public long notificationOverlayMillis() {
        return this.notificationOverlayMillis;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.notificationOverlayMillis() <= -1) return;
        Minecraft minecraft = Minecraft.getInstance();
        double notificationDisplayTime = minecraft.options.notificationDisplayTime().get();

        int textAlpha = Mth.floor(Mth.clampedLerp((double) (Util.getMillis() - this.notificationOverlayMillis()) / (3000.0 * notificationDisplayTime), 255, 25.5));
        if (textAlpha > 25.5) {
            var font = minecraft.font;
            int textWidth = font.width(this.getMessage());
            int k1 = textAlpha << 24;
            int textColor = this.getMessage().getStyle().getColor() != null ? this.getMessage().getStyle().getColor().getValue() : 0xFFFFFF | k1;

            graphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    Stancements.stancements("widget/notification_overlay_background"),
                    this.getX() - textWidth / 2 - this.horizontalPadding,
                    this.getY() - this.verticalPadding,
                    textWidth + this.horizontalPadding * 2,
                    font.lineHeight + this.verticalPadding + 1
            );
            graphics.centeredText(font, this.getMessage(), this.getX(), this.getY(), textColor);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        output.add(NarratedElementType.TITLE, this.getMessage());
    }
}
