package melonystudios.stancements.mixin.logo;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.tab.STCreativeTabs;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
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

    @Inject(method = "extractLabels", at = @At("HEAD"), cancellable = true)
    protected void extractTiledStancementsLogo(GuiGraphicsExtractor graphics, int mouseX, int mouseY, CallbackInfo callback) {
        if (selectedTab == STCreativeTabs.MAIN.get() && selectedTab.showTitle()) {
            callback.cancel();

            Component title = Component.translatable("tab.stancements.main.menu");
            int width = this.font.width(title);
            // logo background
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Stancements.stancements("container/creative_inventory/logo_background"), 7, 5, width + 4, this.font.lineHeight + 2);

            // logo text
            graphics.text(this.font, title, 10, 7, ARGB.opaque(Stancements.ACCENT_COLOR_BACKDROP), false);
            graphics.text(this.font, title, 9, 6, ARGB.opaque(0xFFFFFF), false);
        }
    }
}
