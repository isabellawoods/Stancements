package melonystudios.stancements.mixin.block;

import melonystudios.stancements.sound.STSoundTypes;
import melonystudios.stancements.util.tag.STBlockTags;
import net.minecraft.block.*;
import net.minecraft.tags.BlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class STBlockMixin {
    @Inject(method = "getSoundType", at = @At("HEAD"), cancellable = true)
    public void getSoundType(BlockState state, CallbackInfoReturnable<SoundType> callback) {
        if (!BlockTags.getAllTags().getAllTags().isEmpty()) {
            if (state.is(Blocks.LEVER) || state.getBlock() instanceof RedstoneDiodeBlock) {
                callback.setReturnValue(SoundType.STONE);
            } else if (state.is(Blocks.JUKEBOX) || state.is(Blocks.TRIPWIRE_HOOK)) {
                callback.setReturnValue(SoundType.WOOD);
            } else if (state.is(STBlockTags.USES_IRON_SOUNDS)) {
                callback.setReturnValue(STSoundTypes.IRON);
            } else if (state.is(Blocks.SPONGE)) {
                callback.setReturnValue(STSoundTypes.SPONGE);
            } else if (state.is(Blocks.WET_SPONGE)) {
                callback.setReturnValue(STSoundTypes.WET_SPONGE);
            } else if (state.is(Blocks.COBWEB)) {
                callback.setReturnValue(STSoundTypes.COBWEB);
            } else if (state.is(Blocks.SPAWNER)) {
                callback.setReturnValue(STSoundTypes.SPAWNER);
            } else if (state.is(Blocks.VINE)) {
                callback.setReturnValue(STSoundTypes.VINE);
            } else if (state.is(Blocks.LILY_PAD)) {
                callback.setReturnValue(STSoundTypes.LILY_PAD);
            } else if (state.getBlock() instanceof AnvilBlock) {
                callback.setReturnValue(STSoundTypes.ANVIL);
            }
        }
    }
}
