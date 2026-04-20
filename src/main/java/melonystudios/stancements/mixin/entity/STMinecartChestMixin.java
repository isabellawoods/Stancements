package melonystudios.stancements.mixin.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.entity.vehicle.minecart.MinecartChest;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecartChest.class)
public abstract class STMinecartChestMixin extends AbstractMinecart {
    public STMinecartChestMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void tagOrShearMinecart(Player player, InteractionHand hand, Vec3 location, CallbackInfoReturnable<InteractionResult> callback) {
        InteractionResult superResult = super.interact(player, hand, location);
        if (superResult.consumesAction()) callback.setReturnValue(superResult);
    }
}
