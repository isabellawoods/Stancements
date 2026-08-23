package melonystudios.stancements.mixin.recorder;

import com.mojang.authlib.GameProfile;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.misc.advancement.STCriteriaTriggers;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.ModificationStrategy;
import melonystudios.stancements.misc.modifier.VinylModifier;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
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

        ModificationContext context = new ModificationContext(
                (ServerLevel) this.level(),
                this.blockPosition(),
                STItems.VINYL_DISC.toStack(),
                Stancements.stancements("game/end/alpha"),
                true,
                _ -> {}
        );
        var result = VinylModifier.recordingPipeline(context, ModificationStrategy.FINISH);
        RecordedDiscItem.setJukeboxSong(result.stack(), this.level(), context.musicID(), context.copyingSong(), false);

        STCriteriaTriggers.RECORD_SONG.trigger(
                context.musicID(),
                context.copyingSong(),
                List.of(Identifier.withDefaultNamespace("game/end/alpha")),
                (ServerPlayer) this.self()
        );
        if (!this.addItem(result.stack())) this.drop(result.stack(), false);
    }
}
