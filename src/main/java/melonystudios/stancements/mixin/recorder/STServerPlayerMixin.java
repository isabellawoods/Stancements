package melonystudios.stancements.mixin.recorder;

import com.mojang.authlib.GameProfile;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.misc.advancement.STCriteriaTriggers;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.ModificationStrategy;
import melonystudios.stancements.misc.modifier.VinylModifier;
import melonystudios.stancements.misc.recording.Tracks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
                Tracks.C418_ALPHA,
                true,
                _ -> {}
        );
        var result = VinylModifier.recordingPipeline(context, ModificationStrategy.FINISH);
        ItemStack resultStack = result.stack();
        RecordedDiscItem.setJukeboxSong(resultStack, this.level(), context.track().jukeboxSongID(), context.copyingSong(), false);

        STCriteriaTriggers.RECORD_SONG.trigger(
                context.track(),
                null,
                context.copyingSong(),
                Tracks.C418_ALPHA.listOf(),
                (ServerPlayer) this.self()
        );
        if (!this.addItem(resultStack)) this.drop(resultStack, false);
    }
}
