package melonystudios.stancements.misc.modifier;

import melonystudios.stancements.blockentity.BlockBasedMusicPlayer;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import melonystudios.stancements.misc.recording.Track;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ModificationContext {
    private ItemStack transientModifierStack = ItemStack.EMPTY;
    protected final ItemStack recordableDiscImmutable;
    private final ServerLevel level;
    private final Vec3 position;
    private final ItemStack recordableDisc;
    @Nullable
    private final Track track;
    private final boolean copying;
    private final Consumer<Integer> ejectionTicksCallback;

    public ModificationContext(ServerLevel level, Vec3 position, ItemStack recordableDisc, @Nullable Track track, boolean copying, Consumer<Integer> ejectionTicksCallback) {
        this.level = level;
        this.position = position;
        this.recordableDisc = recordableDisc;
        this.recordableDiscImmutable = recordableDisc.copy();
        this.track = track;
        this.copying = copying;
        this.ejectionTicksCallback = ejectionTicksCallback;
    }

    public static ModificationContext fromMusicRecorder(MusicRecorderBlockEntity recorder) {
        return new ModificationContext((ServerLevel) recorder.getLevel(), recorder.getBlockPos().getCenter(), recorder.getTheItem(), recorder.track(), recorder.copying(), recorder::setEjectionTicks);
    }

    public static ModificationContext fromBlockEntity(BlockEntity block) {
        ItemStack musicDisc = ItemStack.EMPTY;
        if (block instanceof Container container) musicDisc = container.getItem(0);

        Track track = null;
        if (block instanceof BlockBasedMusicPlayer player) track = Track.forJukeboxSong(block.getLevel(), player.song());

        return new ModificationContext((ServerLevel) block.getLevel(), block.getBlockPos().getCenter(), musicDisc, track, false, ticks -> {});
    }

    public ServerLevel level() {
        return this.level;
    }

    public BlockState blockState() {
        return this.level().getBlockState(this.blockPosition());
    }

    public BlockPos blockPosition() {
        return BlockPos.containing(this.position());
    }

    public Vec3 position() {
        return this.position;
    }

    /// The recordable disc [ItemStack] inside the music recorder.
    /// This stack **must NOT** be modified — use [#withTransientStack(ItemStack)] instead;
    protected ItemStack recordableDisc() {
        return this.recordableDisc;
    }

    @Nullable
    public Track track() {
        return this.track;
    }

    public boolean copying() {
        return this.copying;
    }

    public Consumer<Integer> ejectionTicksCallback() {
        return this.ejectionTicksCallback;
    }

    /// The [ItemStack] that vinyl modifiers apply their modifications on.
    public ItemStack transientStack() {
        return this.transientModifierStack;
    }

    /// @param stack The item stack to substitute the current transient stack.
    public void withTransientStack(ItemStack stack) {
        this.transientModifierStack = stack;
    }

    public MusicRecorderBlockEntity recorderOrThrow() {
        if (this.level().getBlockEntity(this.blockPosition()) instanceof MusicRecorderBlockEntity recorder) return recorder;
        throw new IllegalStateException("Invalid music recorder block entity at " + this.blockPosition());
    }

    @Nullable
    public MusicRecorderBlockEntity recorderOrNull() {
        try {
            return this.recorderOrThrow();
        } catch (IllegalStateException ignored) {
            return null;
        }
    }

    @Nullable
    public Player playerOrNull() {
        try {
            return this.recorderOrThrow().getPlayerFromRecorderUUID();
        } catch (Exception ignored) {
            return null;
        }
    }
}
