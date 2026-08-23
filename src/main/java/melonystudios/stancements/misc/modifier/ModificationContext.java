package melonystudios.stancements.misc.modifier;

import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class ModificationContext {
    private ItemStack transientModifierStack = ItemStack.EMPTY;
    protected final ItemStackTemplate musicDiscImmutable;
    private final ServerLevel level;
    private final BlockPos blockPosition;
    private final ItemStack musicDisc;
    private final Identifier musicID;
    private final boolean copyingSong;
    private final Consumer<Integer> ejectionTicksCallback;

    public ModificationContext(ServerLevel level, BlockPos blockPosition, ItemStack musicDisc, Identifier musicID, boolean copyingSong, Consumer<Integer> ejectionTicksCallback) {
        this.level = level;
        this.blockPosition = blockPosition;
        this.musicDisc = musicDisc;
        this.musicDiscImmutable = ItemStackTemplate.fromNonEmptyStack(musicDisc);
        this.musicID = musicID;
        this.copyingSong = copyingSong;
        this.ejectionTicksCallback = ejectionTicksCallback;
    }

    public static ModificationContext fromBlockEntity(MusicRecorderBlockEntity recorder) {
        return new ModificationContext((ServerLevel) recorder.getLevel(), recorder.getBlockPos(), recorder.getTheItem(), recorder.musicID(), recorder.copyingSong(), recorder::setEjectionTicks);
    }

    public ServerLevel level() {
        return this.level;
    }

    public BlockState blockState() {
        return this.level().getBlockState(this.blockPosition());
    }

    public BlockPos blockPosition() {
        return this.blockPosition;
    }

    /// The recordable disc {@link ItemStack} inside the music recorder.
    /// This stack **must NOT** be modified — use {@link #withTransientStack(ItemStack)} instead;
    protected ItemStack musicDisc() {
        return this.musicDisc;
    }

    public Identifier musicID() {
        return this.musicID;
    }

    public boolean copyingSong() {
        return this.copyingSong;
    }

    public Consumer<Integer> ejectionTicksCallback() {
        return this.ejectionTicksCallback;
    }

    /// The {@link ItemStack} that vinyl modifiers apply their modifications on.
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
