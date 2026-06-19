package melonystudios.stancements.mixin.logo;

import com.mojang.blaze3d.systems.RenderSystem;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.tab.STCreativeTabs;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public class STCreativeModeInventoryScreenMixin extends Screen {
    @Shadow
    private static CreativeModeTab selectedTab;

    public STCreativeModeInventoryScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "renderLabels", at = @At("HEAD"), cancellable = true)
    protected void renderTiledStancementsLogo(GuiGraphics graphics, int mouseX, int mouseY, CallbackInfo callback) {
        if (selectedTab == STCreativeTabs.MAIN.get() && selectedTab.showTitle()) {
            callback.cancel();

            RenderSystem.enableBlend();
            Component title = Component.translatable("tab.stancements.main.menu");
            int width = this.font.width(title);
            // logo background
            graphics.blitSprite(Stancements.stancements("container/creative_inventory/logo_background"), 7, 5, width + 4, this.font.lineHeight + 2);

            // logo text
            graphics.drawString(this.font, title, 10, 7, Stancements.ACCENT_COLOR_BACKDROP, false);
            graphics.drawString(this.font, title, 9, 6, 0xFFFFFF, false);
            RenderSystem.disableBlend();
        }
    }
}
