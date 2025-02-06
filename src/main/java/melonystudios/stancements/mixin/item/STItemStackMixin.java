package melonystudios.stancements.mixin.item;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class STItemStackMixin {
    @Inject(method = "setDamageValue", at = @At("HEAD"), cancellable = true)
    public void setDamageValue(int damage, CallbackInfo callback) {
        if (damage == 0) callback.cancel();
    }
}
