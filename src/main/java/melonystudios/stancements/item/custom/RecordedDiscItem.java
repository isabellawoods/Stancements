package melonystudios.stancements.item.custom;

import com.google.common.collect.Lists;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.STItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RecordedDiscItem extends Item {
    public static final int DEFAULT_DISC_COLOR = 0xFFF9FFFE;

    public RecordedDiscItem(Properties properties) {
        super(properties.component(STDataComponents.LABEL, 1F).component(DataComponents.DYED_COLOR, new DyedItemColor(DEFAULT_DISC_COLOR, true)));
    }

    @Override
    @NotNull
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        if (state.is(Blocks.JUKEBOX) && !state.getValue(JukeboxBlock.HAS_RECORD)) {
            ItemStack handStack = context.getItemInHand();
            if (world.isClientSide) {
                ResourceLocation musicID = this.getMusicID(handStack);
                // ((InterfaceMethods.WorldRenderer) Minecraft.getInstance().levelRenderer).playRecordedDisc(musicID, world, pos, handStack);
            }

            if (!world.isClientSide) {
                // ((JukeboxBlock) state.getBlock()).setRecord(world, pos, state, handStack);
                handStack.shrink(1);
                Player player = context.getPlayer();
                if (player != null) player.awardStat(Stats.PLAY_RECORD);
            }

            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        String musicID = stack.get(STDataComponents.MUSIC_ID);
        if (musicID != null) {
            MutableComponent musicName = this.getMusicName(stack);
            if (musicName == null) return;

            if (musicName.getContents() instanceof TranslatableContents) {
                tooltip.add(musicName.withStyle(ChatFormatting.GRAY));
            } else if (musicName.getContents() instanceof PlainTextContents.LiteralContents) {
                tooltip.add(Component.translatable("tooltip.stancements.recorded_disc.sound_id", musicName).withStyle(ChatFormatting.GRAY));
            }
        } else {
            tooltip.add(Component.translatable("tooltip.stancements.recorded_disc.blank").withStyle(ChatFormatting.GRAY));
        }
    }

    /// Gets the translated name for a {@linkplain STDataComponents#MUSIC_ID music id}, in the same format as the 1.21.6 music toast.
    /// @param stack The item stack.
    /// @return A translatable or literal component if the component exists, or <code>null</code> if it doesn't.
    @Nullable
    @OnlyIn(Dist.CLIENT)
    public MutableComponent getMusicName(ItemStack stack) {
        String musicID = stack.get(STDataComponents.MUSIC_ID);
        if (musicID != null) {
            String translation = "music." + musicID
                    .replace(":", ".")
                    .replace("/", ".")
                    .replace("sounds.", "")
                    .replace("music.", "")
                    .replace(".ogg", "");
            return I18n.exists(translation) ? Component.translatable(translation) : Component.literal(translation);
        }
        return null;
    }

    public static ItemStack getRecordedDisc(ResourceLocation musicID, ItemStack originalStack, RandomSource rand) {
        if (originalStack.isEmpty()) return ItemStack.EMPTY;
        ItemStack discStack = new ItemStack(STItems.RECORDED_DISC.get());
        discStack.set(STDataComponents.MUSIC_ID, musicID.toString());
        discStack.set(STDataComponents.LABEL, (float) (rand.nextInt(10) + 1));
        return getRandomLabelColor(discStack, rand);
    }

    public static ItemStack getRandomLabelColor(ItemStack stack, RandomSource rand) {
        List<DyeItem> dyes = Lists.newArrayList();
        dyes.add(getRandomDye(rand));
        if (rand.nextFloat() > 0.7F) dyes.add(getRandomDye(rand));
        if (rand.nextFloat() > 0.8F) dyes.add(getRandomDye(rand));
        ItemStack dyedStack = DyedItemColor.applyDyes(stack.copy(), dyes);
        return dyedStack.isEmpty() ? stack : dyedStack;
    }

    /// Picks a random {@link DyeColor} from the existing 16 colors.
    /// @param rand The random source of randomize the colors.
    private static DyeItem getRandomDye(RandomSource rand) {
        return DyeItem.byColor(DyeColor.byId(rand.nextInt(16)));
    }

    /// Gets the {@linkplain STDataComponents#MUSIC_ID music id} component from an item stack.
    /// @return A resource location of the stack's music id, or null if it doesn't exist.
    @Nullable
    public ResourceLocation getMusicID(ItemStack discStack) {
        String musicID = discStack.get(STDataComponents.MUSIC_ID);
        return musicID != null ? ResourceLocation.parse(musicID) : null;
    }
}
