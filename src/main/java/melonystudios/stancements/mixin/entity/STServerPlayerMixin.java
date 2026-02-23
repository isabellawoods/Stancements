package melonystudios.stancements.mixin.entity;

import com.mojang.authlib.GameProfile;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.misc.advancement.STCriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class STServerPlayerMixin extends Player {
    @Shadow
    public boolean wonGame;

    public STServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile profile) {
        super(level, pos, yRot, profile);
    }

    @Inject(method = "showEndCredits", at = @At("HEAD"))
    public void giveAlphaMusicDisc(CallbackInfo callback) {
        if (this.wonGame) return;

        ItemStack stack = RecordedDiscItem.getRecordedDisc(this.level(), Stancements.stancements("game/end/alpha"), true, new ItemStack(STItems.VINYL_DISC.get()));
        stack.set(DataComponents.RARITY, Rarity.EPIC);
        STCriteriaTriggers.RECORD_SONG.trigger(Stancements.stancements("game/end/alpha"), true, (ServerPlayer) this.self());
        if (!this.addItem(stack)) this.drop(stack, false);
    }
}
