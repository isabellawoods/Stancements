package melonystudios.stancements.util;

import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.blockentity.custom.DyedWaterCauldronBlockEntity;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.custom.DyedWaterBucketItem;
import melonystudios.stancements.sound.STSounds;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.Map;

public class STCauldronInteractions {
    public static final CauldronInteraction.InteractionMap DYED_WATER = CauldronInteraction.newInteractionMap("dyed_water");
    public static final CauldronInteraction.InteractionMap MILK = CauldronInteraction.newInteractionMap("milk");

    public static final CauldronInteraction FILL_DYED_WATER = (state, level, pos, player, hand, stack) -> {
        BlockState cauldronState = STBlocks.DYED_WATER_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
        ItemInteractionResult result = CauldronInteraction.emptyBucket(level, pos, player, hand, stack, cauldronState, SoundEvents.BUCKET_EMPTY);
        level.setBlockAndUpdate(pos, cauldronState);
        DyedWaterCauldronBlockEntity cauldron = new DyedWaterCauldronBlockEntity(pos, cauldronState);
        cauldron.setWaterColor(DyedWaterBucketItem.getColor(stack));
        level.setBlockEntity(cauldron);
        return result;
    };
    public static final CauldronInteraction FILL_MILK = (state, level, pos, player, hand, stack) ->
            CauldronInteraction.emptyBucket(level, pos, player, hand, stack, STBlocks.MILK_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), NeoForgeMod.BUCKET_EMPTY_MILK.get());
    public static final CauldronInteraction DYE_ITEM = (state, level, pos, player, hand, stack) -> {
        if (!stack.is(ItemTags.DYEABLE) || stack.has(DataComponents.DYED_COLOR)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (!level.isClientSide() && blockEntity instanceof DyedWaterCauldronBlockEntity cauldron) {
                stack.set(DataComponents.DYED_COLOR, new DyedItemColor(cauldron.getWaterColor(), true));
                LayeredCauldronBlock.lowerFillLevel(state, level, pos);
            }
            level.playSound(player, pos, STSounds.DYE_ITEM.get(), SoundSource.BLOCKS);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }
    };

    public static void registerInteractions() {
        Map<Item, CauldronInteraction> dyedWater = DYED_WATER.map();
        dyedWater.put(Items.BUCKET, (state, level, pos, player, hand, stack) -> {
            ItemStack bucketStack = new ItemStack(STItems.DYED_WATER_BUCKET.get());
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof DyedWaterCauldronBlockEntity cauldron) {
                DyedWaterBucketItem.setColor(bucketStack, cauldron.getWaterColor());
            }
            return CauldronInteraction.fillBucket(state, level, pos, player, hand, stack, bucketStack,
                    state1 -> state1.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BUCKET_FILL);
        });
        dyedWater.put(Items.LEATHER_HELMET, DYE_ITEM);
        dyedWater.put(Items.LEATHER_CHESTPLATE, DYE_ITEM);
        dyedWater.put(Items.LEATHER_LEGGINGS, DYE_ITEM);
        dyedWater.put(Items.LEATHER_BOOTS, DYE_ITEM);
        dyedWater.put(Items.LEATHER_HORSE_ARMOR, DYE_ITEM);
        dyedWater.put(Items.WOLF_ARMOR, DYE_ITEM);
        // todo: make cauldrons dyeable using dyes
        //  make dyed water cauldrons show the color
        //  add dyed water buckets for all biomes

        Map<Item, CauldronInteraction> milk = MILK.map();
        milk.put(Items.BUCKET, (state, level, pos, player, hand, stack) ->
                CauldronInteraction.fillBucket(state, level, pos, player, hand, stack, new ItemStack(Items.MILK_BUCKET),
                        state1 -> state1.getValue(LayeredCauldronBlock.LEVEL) == 3, NeoForgeMod.BUCKET_FILL_MILK.get()));

        Map<Item, CauldronInteraction> empty = CauldronInteraction.EMPTY.map();
        empty.put(STItems.DYED_WATER_BUCKET.get(), FILL_DYED_WATER);
        empty.put(Items.MILK_BUCKET, FILL_MILK);
    }
}
