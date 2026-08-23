package melonystudios.stancements.mixin.recorder;

import melonystudios.stancements.blockentity.BlockBasedMusicPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.JukeboxUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.JukeboxUpgradeWrapper;
import net.p3pp3rf1y.sophisticatedstorage.block.StorageBlockEntity;
import net.p3pp3rf1y.sophisticatedstorage.block.StorageWrapper;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Pseudo
@Mixin(StorageBlockEntity.class)
public abstract class STStorageBlockEntityMixin extends BlockEntity implements BlockBasedMusicPlayer {
    @Shadow
    public abstract StorageWrapper getStorageWrapper();

    public STStorageBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public JukeboxSong song() {
        if (!this.hasLevel()) return null; // oh my fucking god... ~isa 23-08-26
        return BlockBasedMusicPlayer.findJukeboxSongFromDisc(this.musicDisc()).orElse(null);
    }

    @Override
    public int recordingDuration() {
        var upgrade = this.getJukeboxUpgrade();
        if (upgrade.isPresent() && this.hasLevel()) {
            return (int) (upgrade.get().getDiscFinishTime() - this.getLevel().getGameTime());
        }
        return DEFAULT_TICKS_UNTIL_FINISHED;
    }

    @Override
    @NonNull
    public ItemStack musicDisc() {
        if (this.getJukeboxUpgrade().isPresent()) {
            return this.getJukeboxUpgrade().get().getDisc();
        }
        return ItemStack.EMPTY;
    }

    @Unique
    private Optional<JukeboxUpgradeWrapper> getJukeboxUpgrade() {
        return this.getStorageWrapper().getUpgradeHandler().getTypeWrappers(JukeboxUpgradeItem.TYPE).stream()
                .filter(JukeboxUpgradeWrapper::isPlaying).findFirst();
    }
}
