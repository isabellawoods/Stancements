package melonystudios.stancements.mixin;

import melonystudios.stancements.util.tag.STBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public class STShearsItemMixin extends Item {
    public STShearsItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "mineBlock", at = @At("HEAD"), cancellable = true)
    public void mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livEntity, CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(state.is(STBlockTags.MINEABLE_WITH_SHEARS));
    }
}
