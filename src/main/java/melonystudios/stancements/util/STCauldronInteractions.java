package melonystudios.stancements.util;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.blockentity.custom.DyedWaterCauldronBlockEntity;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.custom.DyedWaterBucketItem;
import melonystudios.stancements.sound.STSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.RegisterCauldronInteractionEvent;

@EventBusSubscriber(modid = Stancements.MOD_ID)
public class STCauldronInteractions {
    public static final CauldronInteraction.Dispatcher DYED_WATER = new CauldronInteraction.Dispatcher();
    public static final CauldronInteraction.Dispatcher MILK = new CauldronInteraction.Dispatcher();

    public static final Identifier DYED_WATER_ID = Stancements.stancements("dyed_water");
    public static final Identifier MILK_ID = Stancements.stancements("milk");

    @SubscribeEvent
    public static void registerDispatchers(RegisterCauldronInteractionEvent.Dispatcher event) {
        event.register(DYED_WATER_ID, new CauldronInteraction.Dispatcher());
        event.register(MILK_ID, new CauldronInteraction.Dispatcher());
    }

    @SubscribeEvent
    public static void registerInteractions(RegisterCauldronInteractionEvent.Interaction event) {
        // dyed water
        event.register(DYED_WATER_ID, Items.BUCKET, (state, level, pos, player, hand, stack) -> {
            ItemStack bucketStack = new ItemStack(STItems.DYED_WATER_BUCKET.get());
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof DyedWaterCauldronBlockEntity cauldron) {
                DyedWaterBucketItem.setColor(bucketStack, cauldron.getWaterColor());
            }
            return CauldronInteractions.fillBucket(state, level, pos, player, hand, stack, bucketStack,
                    state1 -> state1.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BUCKET_FILL);
        });
        event.register(DYED_WATER_ID, ItemTags.CAULDRON_CAN_REMOVE_DYE, STCauldronInteractions::dyeItem);

        // todo: add ability to dye banners and shulker boxes

        // milk
        event.register(MILK_ID, Items.BUCKET, (state, level, pos, player, hand, stack) ->
                CauldronInteractions.fillBucket(state, level, pos, player, hand, stack, new ItemStack(Items.MILK_BUCKET),
                        state1 -> state1.getValue(LayeredCauldronBlock.LEVEL) == 3, NeoForgeMod.BUCKET_FILL_MILK.get()));

        // all interactions
        event.registerToAll(STItems.DYED_WATER_BUCKET.get(), STCauldronInteractions::fillDyedWater);
        event.registerToAll(Items.MILK_BUCKET, STCauldronInteractions::fillMilk);
        addDefaultInteractions(DYED_WATER);
        addDefaultInteractions(MILK);
    }

    public static void addDefaultInteractions(CauldronInteraction.Dispatcher dispatcher) {
        dispatcher.put(Items.LAVA_BUCKET, CauldronInteractions::fillLavaInteraction);
        dispatcher.put(Items.WATER_BUCKET, CauldronInteractions::fillWaterInteraction);
        dispatcher.put(Items.POWDER_SNOW_BUCKET, CauldronInteractions::fillPowderSnowInteraction);
        dispatcher.put(Items.MILK_BUCKET, STCauldronInteractions::fillMilk);
        dispatcher.put(STItems.DYED_WATER_BUCKET.get(), STCauldronInteractions::fillDyedWater);
    }

    public static InteractionResult dyeItem(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        if (!stack.is(ItemTags.CAULDRON_CAN_REMOVE_DYE) || stack.has(DataComponents.DYED_COLOR)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (!level.isClientSide() && blockEntity instanceof DyedWaterCauldronBlockEntity cauldron) {
                stack.set(DataComponents.DYED_COLOR, new DyedItemColor(cauldron.getWaterColor()));
                LayeredCauldronBlock.lowerFillLevel(state, level, pos);
            }
            level.playSound(player, pos, STSounds.DYE_ITEM.get(), SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }
    }

    public static InteractionResult fillDyedWater(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        BlockState cauldronState = STBlocks.DYED_WATER_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
        InteractionResult result = CauldronInteractions.emptyBucket(level, pos, player, hand, stack, cauldronState, SoundEvents.BUCKET_EMPTY);
        level.setBlockAndUpdate(pos, cauldronState);
        DyedWaterCauldronBlockEntity cauldron = new DyedWaterCauldronBlockEntity(pos, cauldronState);
        cauldron.setWaterColor(DyedWaterBucketItem.getColor(stack));
        level.setBlockEntity(cauldron);
        return result;
    }

    public static InteractionResult fillMilk(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        return CauldronInteractions.emptyBucket(level, pos, player, hand, stack, STBlocks.MILK_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), NeoForgeMod.BUCKET_EMPTY_MILK.get());
    }
}
