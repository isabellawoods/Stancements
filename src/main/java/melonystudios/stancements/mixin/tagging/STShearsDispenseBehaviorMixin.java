package melonystudios.stancements.mixin.tagging;

import melonystudios.stancements.dispenser.TaggingDispenseBehavior;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsDispenseItemBehavior.class)
public class STShearsDispenseBehaviorMixin extends OptionalDispenseItemBehavior {
    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/dispenser/ShearsDispenseItemBehavior;setSuccess(Z)V", shift = At.Shift.AFTER))
    protected void executeOrShearOffTags(BlockSource source, ItemStack stack, CallbackInfoReturnable<ItemStack> callback) {
        this.setSuccess(this.isSuccess() || TaggingDispenseBehavior.tryShearingOffMinecartTags(source.level(), source.pos().relative(source.state().getValue(DispenserBlock.FACING))));
    }
}
