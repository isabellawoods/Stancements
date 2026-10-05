package melonystudios.stancements.client.item;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.client.element.StackedRenderComponents;
import melonystudios.stancements.component.custom.TrackStorage;
import melonystudios.stancements.util.Alignment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class ClientTrackStorageTooltip implements ClientTooltipComponent {
    private static final int TRANSPARENT_TEXT_BACKDROP = 0x7F000000;
    private static final int FULLNESS_BAR_HEIGHT = 20;
    private static final Component DESCRIPTION_TEXT = Component.translatable("tooltip.stancements.cassette_tape").withStyle(ChatFormatting.GRAY);
    private final TrackStorage storage;

    public ClientTrackStorageTooltip(TrackStorage storage) {
        this.storage = storage;
    }

    @Override
    public void extractImage(Font font, int x, int y, int width, int height, GuiGraphicsExtractor graphics) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) return;
        int contentWidth = this.getWidth(font);

        List<MutableComponent> lines = this.storage.getLinesToDisplay(level, minecraft.hasShiftDown(), 3);

        if (!lines.isEmpty()) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Stancements.stancements("container/inventory_recorder/track_storage_background"), x, y - 2, contentWidth, this.getHeight(font) - FULLNESS_BAR_HEIGHT + 3);

            for (MutableComponent line : lines) {
                MutableComponent backdroppedLine = line.copy().withStyle(style -> style.withShadowColor(TRANSPARENT_TEXT_BACKDROP));
                StackedRenderComponents.alignedScrollingText(graphics, font, backdroppedLine, Alignment.LEFT, x + 3, y, x + contentWidth - 3, y + font.lineHeight, 0xFFFFFFFF);
                y += font.lineHeight + 2;
            }
            y += 3;
        } else {
            StackedRenderComponents.centeredTextWithWordWrap(graphics, font, DESCRIPTION_TEXT, x + contentWidth / 2, y, contentWidth, 0xFFFFFFFF);
            y += font.split(DESCRIPTION_TEXT, contentWidth).size() * font.lineHeight + 2;
        }

        this.extractFullnessBar(graphics, font, x, y);
    }

    private void extractFullnessBar(GuiGraphicsExtractor graphics, Font font, int x, int y) {
        float fullnessFraction = (float) this.storage.tracklist().size() / this.storage.capacity();
        int width = this.getWidth(font);
        MutableComponent fullnessText = this.storage.getFullnessText().copy().withStyle(style -> style.withShadowColor(TRANSPARENT_TEXT_BACKDROP));

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.storage.backgroundSprite(), x, y, width, 14);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.storage.fillSprite(), x, y, (int) (width * fullnessFraction), 14);
        graphics.centeredText(font, fullnessText, x + 75, y + 3, 0xFFFFFFFF);
    }

    @Override
    public int getWidth(Font font) {
        return 150;
    }

    @Override
    public int getHeight(Font font) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) return FULLNESS_BAR_HEIGHT;
        int lineCount = this.storage.getLineCountForHeight(minecraft.hasShiftDown(), 3);
        int descriptionLines = font.split(DESCRIPTION_TEXT, this.getWidth(font)).size();

        if (lineCount <= 0) {
            return descriptionLines * font.lineHeight + FULLNESS_BAR_HEIGHT;
        } else {
            return lineCount * (font.lineHeight + 2) + FULLNESS_BAR_HEIGHT;
        }
    }
}
