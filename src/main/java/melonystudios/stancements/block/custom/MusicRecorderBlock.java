package melonystudios.stancements.block.custom;

import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import melonystudios.stancements.misc.STStats;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class MusicRecorderBlock extends ContainerBlock {
    public static final BooleanProperty RECORDING = BooleanProperty.create("recording");

    public MusicRecorderBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(RECORDING, false));
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity livEntity, ItemStack stack) {
        super.setPlacedBy(world, pos, state, livEntity, stack);
        CompoundNBT tag = stack.getTag();
        if (tag != null && tag.contains("BlockEntityTag", Constants.NBT.TAG_COMPOUND)) {
            CompoundNBT blockEntityTag = tag.getCompound("BlockEntityTag");
            if (blockEntityTag.contains("recording", Constants.NBT.TAG_ANY_NUMERIC)) {
                world.setBlock(pos, state.setValue(RECORDING, true), 3);
            }
        }
    }

    @Override
    @Nonnull
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hitResult) {
        TileEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity) {
            MusicRecorderBlockEntity recorder = (MusicRecorderBlockEntity) blockEntity;
            if (!recorder.getDiscStack().isEmpty()) {
                this.stopRecording(world, pos, true);
                world.setBlock(pos, state.setValue(RECORDING, false), 3);
                return ActionResultType.sidedSuccess(!world.isClientSide);
            }
        }
        return ActionResultType.PASS;
    }

    public void startRecording(World world, BlockState state, BlockPos pos, @Nullable PlayerEntity player, ItemStack discStack, @Nullable ISound currentMusic) {
        TileEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity) {
            MusicRecorderBlockEntity recorder = (MusicRecorderBlockEntity) blockEntity;
            recorder.insertDisc(discStack.copy());
            if (currentMusic != null && currentMusic.getSound() != null) {
                this.sendMessage(new TranslationTextComponent("tooltip.stancements.recording_music"), world, recorder.startRecording(currentMusic.getSound().getPath(), player));
                world.setBlock(pos, state.setValue(RECORDING, true), 3);
                if (player != null) player.awardStat(STStats.SONGS_RECORDED);
            } else {
                this.sendMessage(new TranslationTextComponent("tooltip.stancements.no_music_playing").withStyle(TextFormatting.GRAY), world, recorder.startRecording(null, player));
            }
        }
    }

    public void stopRecording(World world, BlockPos pos, boolean fromTop) {
        TileEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity) {
            MusicRecorderBlockEntity recorder = (MusicRecorderBlockEntity) blockEntity;
            ItemStack discStack = recorder.getDiscStack();
            recorder.finishRecording(ItemStack.EMPTY, true);
            if (discStack.isEmpty()) return;

            if (fromTop) {
                double xOffset = (double) (world.random.nextFloat() * 0.7F) + (double) 0.15F;
                double yOffset = (double) (world.random.nextFloat() * 0.7F) + (double) 0.660000002F;
                double zOffset = (double) (world.random.nextFloat() * 0.7F) + (double) 0.15F;
                ItemEntity discEntity = new ItemEntity(world, (double) pos.getX() + xOffset, (double) pos.getY() + yOffset, (double) pos.getZ() + zOffset, discStack.copy());
                discEntity.setDefaultPickUpDelay();
                world.addFreshEntity(discEntity);
            } else {
                ItemEntity discEntity = new ItemEntity(world, pos.getX() + 0.5D, pos.getY() + 1, pos.getZ() + 0.5D, discStack);
                discEntity.setDefaultPickUpDelay();
                world.addFreshEntity(discEntity);
            }
        }
    }

    public void sendMessage(ITextComponent component, World world, boolean recording) {
        if (world.isClientSide) Minecraft.getInstance().gui.setOverlayMessage(component, recording);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            this.stopRecording(world, pos, false);
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    @Nullable
    public TileEntity newBlockEntity(IBlockReader world) {
        return new MusicRecorderBlockEntity();
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, World world, BlockPos pos) {
        TileEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity) {
            MusicRecorderBlockEntity recorder = (MusicRecorderBlockEntity) blockEntity;
            if (!recorder.recording()) return 0;
            int minimum = state.getValue(RECORDING) ? 1 : 0;
            return Math.max((int) ((recorder.ticksUntilFinishedRecording() * 15) / 600F), minimum);
        }
        return 0;
    }

    @Override
    public int getSignal(BlockState state, IBlockReader world, BlockPos pos, Direction direction) {
        return state.getValue(RECORDING) ? 15 : 0;
    }

    @Override
    @Nonnull
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(RECORDING);
    }
}
