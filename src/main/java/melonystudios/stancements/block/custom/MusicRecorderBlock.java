package melonystudios.stancements.block.custom;

import com.mojang.serialization.MapCodec;
import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import melonystudios.stancements.misc.STStatistics;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MusicRecorderBlock extends BaseEntityBlock {
    public static final BooleanProperty RECORDING = BooleanProperty.create("recording");

    public MusicRecorderBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(RECORDING, false));
    }

    @Override
    @NotNull
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(MusicRecorderBlock::new);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(world, pos, state, placer, stack);
        CustomData data = stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
        if (data.contains("recording")) world.setBlock(pos, state.setValue(RECORDING, true), 3);
    }

    @Override
    @NotNull
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder) {
            if (!recorder.getDiscStack().isEmpty()) {
                this.stopRecording(world, pos, true);
                world.setBlock(pos, state.setValue(RECORDING, false), 3);
                return InteractionResult.sidedSuccess(!world.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    public void startRecording(Level world, BlockState state, BlockPos pos, @Nullable Player player, ItemStack discStack, @Nullable SoundInstance currentMusic) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder) {
            recorder.insertDisc(discStack.copy());
            if (currentMusic != null && currentMusic.getSound() != null) {
                this.sendMessage(Component.translatable("tooltip.stancements.recording_music"), world, recorder.startRecording(currentMusic.getSound().getPath(), player));
                world.setBlock(pos, state.setValue(RECORDING, true), 3);
                 if (player != null) player.awardStat(STStatistics.SONGS_RECORDED.get());
            } else {
                this.sendMessage(Component.translatable("tooltip.stancements.no_music_playing").withStyle(ChatFormatting.GRAY), world, recorder.startRecording(null, player));
            }
        }
    }

    public void stopRecording(Level world, BlockPos pos, boolean fromTop) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder) {
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

    public void sendMessage(Component component, Level world, boolean recording) {
        if (world.isClientSide) Minecraft.getInstance().gui.setOverlayMessage(component, recording);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            this.stopRecording(world, pos, false);
            super.onRemove(state, world, pos, newState, movedByPiston);
        }
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MusicRecorderBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return world.isClientSide ? null : createTickerHelper(type, STBlockEntities.MUSIC_RECORDER.get(), MusicRecorderBlockEntity::tick);
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
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MusicRecorderBlockEntity recorder) {
            if (!recorder.recording()) return 0;
            int minimum = state.getValue(RECORDING) ? 1 : 0;
            return Math.max((int) ((recorder.ticksUntilFinishedRecording() * 15) / 600F), minimum);
        }
        return 0;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return state.getValue(RECORDING) ? 15 : 0;
    }

    @Override
    @NotNull
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RECORDING);
    }
}
