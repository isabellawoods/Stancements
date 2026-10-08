package melonystudios.stancements.client.screen.list;

import melonystudios.stancements.client.STClient;
import melonystudios.stancements.client.screen.CommandHelpScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.StringUtil;
import org.lwjgl.glfw.GLFW;

public class CommandHelpList extends ObjectSelectionList<CommandHelpList.Entry> {
    private final CommandHelpScreen screen;

    public CommandHelpList(Minecraft minecraft, CommandHelpScreen screen) {
        super(minecraft, screen.width, screen.height - 33 * 2, 33, 16);
        this.screen = screen;
        var font = screen.getMinecraft().font;
        int accentColor = STClient.accentColorOrDefault(screen.modID(), 0xFFFFA0);
        int maxTextWidth = this.getRowWidth() - 8;
        String translationKey = "commands.mstudios." + screen.commandID().replace('/', '.');

        // HEADER
        // Added by: <Mod Name>
        font.split(Component.translatable("commands.mstudios.help.added_by", Component.literal(screen.getModName()).withStyle(style -> style.withBold(false).withColor(0xFFFFFF)))
                        .withStyle(style -> style.withBold(true).withColor(accentColor)), maxTextWidth)
                .forEach(line -> this.addEntry(new TextEntry(line, false)));

        // Since: <Version>
        font.split(Component.translatable("commands.mstudios.help.since", Component.translatable(translationKey + ".since").withStyle(style -> style.withBold(false).withColor(0xFFFFFF)))
                        .withStyle(style -> style.withBold(true).withColor(accentColor)), maxTextWidth)
                .forEach(line -> this.addEntry(new TextEntry(line, false)));

        if (I18n.exists(translationKey + ".enabled_by")) {
            // Enabled by: -D<debug flag>
            font.split(Component.translatable("commands.mstudios.help.enabled_by", Component.translatable(translationKey + ".enabled_by").withStyle(style -> style.withBold(false).withColor(0xFFFFFF)))
                            .withStyle(style -> style.withBold(true).withColor(accentColor)), maxTextWidth)
                    .forEach(line -> this.addEntry(new TextEntry(line, false)));
        }

        // Syntax: /mstudios <category>/<name> [<argument>]
        font.split(Component.translatable("commands.mstudios.help.syntax", Component.translatable(translationKey + ".syntax").withStyle(style -> style.withBold(false).withColor(0xFFFFFF)))
                        .withStyle(style -> style.withBold(true).withColor(accentColor)), maxTextWidth)
                .forEach(line -> this.addEntry(new TextEntry(line, false)));

        this.addEntry(new SeparatorEntry(accentColor, STClient.TRANSPARENT_TEXT_BACKDROP));

        // CONTENTS
        font.split(Component.translatable(translationKey + ".description"), maxTextWidth)
                .forEach(line -> this.addEntry(new TextEntry(line, false)));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_C && (modifiers & GLFW.GLFW_MOD_CONTROL) == 2) {
            this.minecraft.keyboardHandler.setClipboard(StringUtil.stripColor(I18n.get("commands.mstudios." + this.screen.commandID().replace('/', '.') + ".description")));
            this.screen.resetCopyNotificationTicks();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public abstract static class Entry extends ObjectSelectionList.Entry<CommandHelpList.Entry> {
        @Override
        public Component getNarration() {
            return Component.empty();
        }
    }

    public class TextEntry extends Entry {
        private final FormattedCharSequence text;
        private final boolean centered;

        public TextEntry(FormattedCharSequence text, boolean centered) {
            this.text = text;
            this.centered = centered;
        }

        @Override
        public void render(GuiGraphics graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTicks) {
            if (this.centered) {
                graphics.drawCenteredString(CommandHelpList.this.minecraft.font, this.text, CommandHelpList.this.getWidth() / 2, top + height / 2 - 9 / 2, 0xFFFFFF);
            } else {
                graphics.drawString(CommandHelpList.this.minecraft.font, this.text, left + 2, top + height / 2 - 9 / 2, 0xFFFFFF);
            }
        }
    }

    public static class SeparatorEntry extends Entry {
        private final int frontColor;
        private final int backColor;

        public SeparatorEntry(int frontColor, int backColor) {
            this.frontColor = frontColor <= 0xFFFFFF ? FastColor.ARGB32.opaque(frontColor) : frontColor;
            this.backColor = backColor;
        }

        @Override
        public void render(GuiGraphics graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTicks) {
            graphics.fill(left + 2, top + height / 2, left + width - 5, top + height / 2 + 1, this.frontColor);
            graphics.fill(left + 3, top + height / 2 + 1, left + width - 4, top + height / 2 + 2, this.backColor);
        }
    }
}
