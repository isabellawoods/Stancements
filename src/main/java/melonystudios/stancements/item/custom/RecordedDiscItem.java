package melonystudios.stancements.item.custom;

import com.google.common.collect.Lists;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.util.InterfaceMethods;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class RecordedDiscItem extends Item implements IDyeableArmorItem {
    public RecordedDiscItem(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        if (state.is(Blocks.JUKEBOX) && !state.getValue(JukeboxBlock.HAS_RECORD)) {
            ItemStack handStack = context.getItemInHand();
            if (world.isClientSide) {
                ResourceLocation musicID = this.getMusicID(handStack);
                ((InterfaceMethods.WorldRenderer) Minecraft.getInstance().levelRenderer).playRecordedDisc(musicID, world, pos, handStack);
            }

            if (!world.isClientSide) {
                ((JukeboxBlock) state.getBlock()).setRecord(world, pos, state, handStack);
                handStack.shrink(1);
                PlayerEntity player = context.getPlayer();
                if (player != null) player.awardStat(Stats.PLAY_RECORD);
            }

            return ActionResultType.sidedSuccess(world.isClientSide);
        }
        return ActionResultType.PASS;
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundNBT displayTag = stack.getTagElement("display");
        return displayTag != null && displayTag.contains("color", Constants.NBT.TAG_ANY_NUMERIC) ? displayTag.getInt("color") : 16383998;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        CompoundNBT tag = stack.getTag();
        if (tag != null && tag.contains("music_id", Constants.NBT.TAG_STRING)) {
            IFormattableTextComponent musicName = this.getMusicName(stack);
            if (musicName instanceof TranslationTextComponent) {
                tooltip.add(musicName.withStyle(TextFormatting.GRAY));
            } else if (musicName instanceof StringTextComponent) {
                tooltip.add(new TranslationTextComponent("tooltip.stancements.recorded_disc.sound_id", tag.getString("music_id")).withStyle(TextFormatting.GRAY));
            }
        } else {
            tooltip.add(new TranslationTextComponent("tooltip.stancements.recorded_disc.blank").withStyle(TextFormatting.GRAY));
        }
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public IFormattableTextComponent getMusicName(ItemStack stack) {
        CompoundNBT tag = stack.getTag();
        if (tag != null && tag.contains("music_id", Constants.NBT.TAG_STRING)) {
            String translation = "music." + tag.getString("music_id")
                    .replace(":", ".")
                    .replace("/", ".")
                    .replace("sounds.", "")
                    .replace("music.", "")
                    .replace(".ogg", "");
            return I18n.exists(translation) ? new TranslationTextComponent(translation) : new StringTextComponent(translation);
        }
        return null;
    }

    public static ItemStack getRecordedDisc(ResourceLocation musicID, ItemStack originalStack) {
        if (originalStack.isEmpty()) return ItemStack.EMPTY;
        ItemStack discStack = new ItemStack(STItems.RECORDED_DISC.get());
        discStack.getOrCreateTag().putString("music_id", musicID.toString());
        discStack.getOrCreateTag().putInt("label", random.nextInt(11));
        return getRandomLabelColor(discStack, random);
    }

    public static ItemStack getRandomLabelColor(ItemStack stack, Random rand) {
        List<DyeItem> dyes = Lists.newArrayList();
        dyes.add(getRandomDye(rand));
        if (rand.nextFloat() > 0.7F) dyes.add(getRandomDye(rand));
        if (rand.nextFloat() > 0.8F) dyes.add(getRandomDye(rand));
        return IDyeableArmorItem.dyeArmor(stack, dyes);
    }

    private static DyeItem getRandomDye(Random rand) {
        return DyeItem.byColor(DyeColor.byId(rand.nextInt(16)));
    }

    public ResourceLocation getMusicID(ItemStack discStack) {
        CompoundNBT tag = discStack.getTag();
        if (tag != null && tag.contains("music_id", Constants.NBT.TAG_STRING)) {
            return new ResourceLocation(tag.getString("music_id"));
        }
        return null;
    }
}
