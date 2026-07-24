package melonystudios.stancements.client.item;

import com.mojang.blaze3d.systems.RenderSystem;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.client.element.StackedRenderComponents;
import melonystudios.stancements.component.custom.TrackStorage;
import melonystudios.stancements.util.Alignment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ClientTrackStorageTooltip implements ClientTooltipComponent {
    private static final int TRANSPARENT_TEXT_BACKDROP = 0x7F000000;
    private static final int FULLNESS_BAR_HEIGHT = 20;
    private static final Component DESCRIPTION_TEXT = Component.translatable("tooltip.stancements.cassette_tape").withStyle(ChatFormatting.GRAY);
    private final TrackStorage storage;

    public ClientTrackStorageTooltip(TrackStorage storage) {
        this.storage = storage;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        int width = this.getWidth(font);

        List<MutableComponent> lines = this.storage.getLinesToDisplay(level, Screen.hasShiftDown(), 3);

        if (!lines.isEmpty()) {
            RenderSystem.enableBlend();
            graphics.blitSprite(Stancements.stancements("container/inventory_recorder/track_storage_background"), x, y - 2, width, this.getHeight() - FULLNESS_BAR_HEIGHT + 3);

            for (MutableComponent line : lines) {
                StackedRenderComponents.renderAlignedScrollingText(graphics, font, line, Alignment.LEFT, x + 3, y, x + width - 3, y + font.lineHeight, TrackStorage.TEXT_COLOR, TRANSPARENT_TEXT_BACKDROP);
                y += font.lineHeight + 2;
            }
            RenderSystem.disableBlend();
            y += 3;
        } else {
            StackedRenderComponents.drawCenteredWordWrap(graphics, font, DESCRIPTION_TEXT, x + this.getWidth() / 2, y, width, 0xFFFFFF);
            y += font.split(DESCRIPTION_TEXT, width).size() * font.lineHeight + 2;
        }

        this.renderFullnessBar(graphics, font, x, y);
    }

    private void renderFullnessBar(GuiGraphics graphics, Font font, int x, int y) {
        float fullnessFraction = (float) this.storage.tracklist().size() / this.storage.capacity();
        int width = this.getWidth(font);

        RenderSystem.enableBlend();
        graphics.blitSprite(this.storage.backgroundSprite(), x, y, width, 14);
        graphics.blitSprite(this.storage.fillSprite(), x, y, (int) (width * fullnessFraction), 14);
        RenderSystem.disableBlend();
        StackedRenderComponents.drawCenteredTextWithBackdrop(graphics, font, this.storage.getFullnessText(), x + 75, y + 3, 0xFFFFFF, TRANSPARENT_TEXT_BACKDROP);
    }

    @Override
    public int getWidth(Font font) {
        return this.getWidth();
    }

    public int getWidth() {
        return 150;
    }

    @Override
    public int getHeight() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return FULLNESS_BAR_HEIGHT;
        int lineCount = this.storage.getLineCountForHeight(Screen.hasShiftDown(), 3);
        Font font = Minecraft.getInstance().font;
        int descriptionLines = Minecraft.getInstance().font.split(DESCRIPTION_TEXT, this.getWidth()).size();

        if (lineCount <= 0) {
            return descriptionLines * font.lineHeight + FULLNESS_BAR_HEIGHT;
        } else {
            return lineCount * (font.lineHeight + 2) + FULLNESS_BAR_HEIGHT;
        }
    }
}
