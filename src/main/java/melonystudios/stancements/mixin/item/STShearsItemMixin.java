package melonystudios.stancements.mixin.item;

import melonystudios.stancements.util.tag.STBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public class STShearsItemMixin extends Item {
    public STShearsItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "mineBlock", at = @At("TAIL"), cancellable = true)
    public void mineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity livEntity, CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(state.is(STBlockTags.MINEABLE_SHEARS) || super.mineBlock(stack, world, state, pos, livEntity));
    }

    @Inject(method = "isCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    public void isCorrectToolForDrops(BlockState state, CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(state.is(STBlockTags.MINEABLE_SHEARS));
    }
}
