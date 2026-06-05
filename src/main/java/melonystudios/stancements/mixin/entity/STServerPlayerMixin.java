package melonystudios.stancements.mixin.entity;

import com.mojang.authlib.GameProfile;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.misc.advancement.STCriteriaTriggers;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerPlayer.class)
public abstract class STServerPlayerMixin extends Player {
    @Shadow
    public boolean wonGame;

    public STServerPlayerMixin(Level level, GameProfile profile) {
        super(level, profile);
    }

    @Inject(method = "showEndCredits", at = @At("HEAD"))
    public void giveAlphaMusicDisc(CallbackInfo callback) {
        if (this.wonGame) return;

        ItemStack stack = RecordedDiscItem.getRecordedDisc(this.level(), Identifier.withDefaultNamespace("game/end/alpha"), true, new ItemStack(STItems.VINYL_DISC.get()));
        STCriteriaTriggers.RECORD_SONG.trigger(
                Identifier.withDefaultNamespace("game/end/alpha"),
                true,
                List.of(Identifier.withDefaultNamespace("game/end/alpha")),
                (ServerPlayer) this.self()
        );
        if (!this.addItem(stack)) this.drop(stack, false);
    }
}
