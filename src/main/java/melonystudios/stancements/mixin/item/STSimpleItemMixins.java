package melonystudios.stancements.mixin.item;

import melonystudios.stancements.config.STConfig;
import melonystudios.stancements.util.STUtils;
import melonystudios.stancements.util.tag.STItemTags;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

public class STSimpleItemMixins {
    @Mixin(Item.class)
    public static class ItemMixin {
        // Backports the new item rarities from 1.21.2.
        @Inject(method = "getRarity", at = @At("HEAD"), cancellable = true)
        private void getRarity(ItemStack stack, CallbackInfoReturnable<Rarity> callback) {
            if (!ItemTags.getAllTags().getAllTags().isEmpty()) {
                if (stack.getItem().is(STItemTags.WITH_COMMON_RARITY)) {
                    callback.setReturnValue(stack.isEnchanted() ? Rarity.RARE : Rarity.COMMON);
                } else if (stack.getItem().is(STItemTags.WITH_UNCOMMON_RARITY)) {
                    callback.setReturnValue(stack.isEnchanted() ? Rarity.RARE : Rarity.UNCOMMON);
                } else if (stack.getItem().is(STItemTags.WITH_RARE_RARITY)) {
                    callback.setReturnValue(stack.isEnchanted() ? Rarity.EPIC : Rarity.RARE);
                } else if (stack.getItem().is(STItemTags.WITH_EPIC_RARITY)) {
                    callback.setReturnValue(Rarity.EPIC);
                } else if (stack.getItem().is(STItemTags.WITH_POTATO_RARITY)) {
                    callback.setReturnValue(STUtils.potatoRarity());
                }
            }
        }

        // Adds the "Honey" and "Bees" tooltips to bee nests and beehives.
        @Inject(method = "appendHoverText", at = @At("HEAD"))
        public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag, CallbackInfo callback) {
            if ((stack.getItem() == Items.BEE_NEST || stack.getItem() == Items.BEEHIVE) && STConfig.COMMON_CONFIGS.addBeehiveTooltips.get()) {
                CompoundNBT tag = stack.getTag();
                int honeyLevel = 0;
                int bees = 0;

                if (tag != null && tag.contains("BlockStateTag", Constants.NBT.TAG_COMPOUND)) {
                    CompoundNBT blockStateTag = tag.getCompound("BlockStateTag");
                    honeyLevel = blockStateTag.getInt("honey_level");
                }
                if (tag != null && tag.contains("BlockEntityTag", Constants.NBT.TAG_COMPOUND)) {
                    CompoundNBT blockEntityTag = tag.getCompound("BlockEntityTag");
                    bees = blockEntityTag.contains("Bees", Constants.NBT.TAG_LIST) ? blockEntityTag.getList("Bees",
                            Constants.NBT.TAG_COMPOUND).size() : 0;
                }

                tooltip.add(new TranslationTextComponent("tooltip.stancements.honey_level", honeyLevel, 5).withStyle(TextFormatting.GRAY));
                tooltip.add(new TranslationTextComponent("tooltip.stancements.bees_in_hive", bees, 3).withStyle(TextFormatting.GRAY));
            }
        }
    }

    @Mixin(PotionItem.class)
    public static class PotionItemMixin extends Item {
        public PotionItemMixin(Properties properties) {
            super(properties);
        }

        // Removes the enchantment glint from all potion types.
        @Inject(method = "isFoil", at = @At("HEAD"), cancellable = true)
        private void isFoil(ItemStack stack, CallbackInfoReturnable<Boolean> callback) {
            if (STConfig.COMMON_CONFIGS.removePotionGlint.get()) callback.setReturnValue(false);
        }
    }
}
