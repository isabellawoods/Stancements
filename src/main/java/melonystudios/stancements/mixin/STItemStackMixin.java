package melonystudios.stancements.mixin;

import melonystudios.reutilities.component.ReDataComponents;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.component.STDataComponents;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class STItemStackMixin implements DataComponentHolder, IItemStackExtension {
    @Inject(method = "addDetailsToTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/item/component/TooltipDisplay;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V",
            ordinal = 19, shift = At.Shift.AFTER))
    public void addMinecartTagColorTooltip(Item.TooltipContext context, TooltipDisplay display, @Nullable Player player, TooltipFlag flag, Consumer<Component> adder, CallbackInfo callback) {
        if (this.shouldDisplay(Stancements.stancements("minecart_tag_color"))) {
            this.addToTooltip(STDataComponents.MINECART_TAG_COLOR, context, display, adder, flag);
        }
    }

    @Unique
    public boolean shouldDisplay(Identifier name) {
        List<Identifier> itemTags = this.getComponents().get(ReDataComponents.HIDE_COMPONENTS.get());
        if (itemTags == null || itemTags.isEmpty()) return true;
        return !itemTags.contains(name);
    }
}
