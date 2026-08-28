package melonystudios.stancements.mixin.recorder;

import com.mojang.authlib.GameProfile;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.misc.advancement.STCriteriaTriggers;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.ModificationStrategy;
import melonystudios.stancements.misc.modifier.VinylModifier;
import melonystudios.stancements.misc.recording.Tracks;
import net.minecraft.core.BlockPos;
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

    public STServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile profile) {
        super(level, pos, yRot, profile);
    }

    // i just deleted by only dev world for stancements while testing this... that i've had for ONE YEAR
    // ~isa 06-07-26
    @Inject(method = "showEndCredits", at = @At("HEAD"))
    public void giveAlphaMusicDisc(CallbackInfo callback) {
        if (this.wonGame) return;

        ModificationContext context = new ModificationContext(
                (ServerLevel) this.level(),
                this.blockPosition(),
                STItems.VINYL_DISC.toStack(),
                Tracks.C418_ALPHA,
                true,
                ticks -> {}
        );
        var result = VinylModifier.recordingPipeline(context, ModificationStrategy.FINISH);
        RecordedDiscItem.setJukeboxSong(this.level(), result.stack(), context.track(), context.copying(), false);

        STCriteriaTriggers.RECORD_SONG.trigger(
                context.track(),
                null,
                context.copying(),
                List.of(Tracks.C418_ALPHA),
                (ServerPlayer) this.self()
        );
        if (!this.addItem(result.stack())) this.drop(result.stack(), false);
    }
}
