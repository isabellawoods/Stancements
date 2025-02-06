package melonystudios.stancements.mixin.item;

import melonystudios.stancements.config.STConfig;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.raid.Raid;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BannerItem.class)
public class STBannerItemMixin extends Item {
    public STBannerItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void fillItemCategory(ItemGroup tab, NonNullList<ItemStack> list) {
        if (this.allowdedIn(tab)) {
            list.add(new ItemStack(this));
            ItemStack bannerStack = new ItemStack(this);
            if (bannerStack.getItem() == Items.BLACK_BANNER) {
                if (STConfig.COMMON_CONFIGS.addOminousBannerToTab.get()) list.add(Raid.getLeaderBannerInstance().setHoverName(new TranslationTextComponent("block.minecraft.ominous_banner").withStyle(style -> style.withColor(Rarity.UNCOMMON.color).withItalic(false))));
                if (STConfig.COMMON_CONFIGS.addTermianEmpireBannerToTab.get() && ModList.get().isLoaded("backmath")) {
                    list.add(getTermianEmpireBanner());
                }
            }
        }
    }

    @Unique
    private static ItemStack getTermianEmpireBanner() {
        ItemStack lightBlueBanner = new ItemStack(Items.LIGHT_BLUE_BANNER);
        CompoundNBT blockEntityTag = lightBlueBanner.getOrCreateTagElement("BlockEntityTag");
        ListNBT patterns = new BannerPattern.Builder().addPattern(BannerPattern.GRADIENT_UP, DyeColor.PURPLE).addPattern(BannerPattern.STRIPE_CENTER, DyeColor.LIGHT_BLUE).addPattern(
                BannerPattern.RHOMBUS_MIDDLE, DyeColor.CYAN).addPattern(BannerPattern.FLOWER, DyeColor.RED).addPattern(BannerPattern.FLOWER, DyeColor.YELLOW).toListTag();
        blockEntityTag.put("Patterns", patterns);
        lightBlueBanner.hideTooltipPart(ItemStack.TooltipDisplayFlags.ADDITIONAL);
        lightBlueBanner.setHoverName(new TranslationTextComponent("block.backmath.termian_empire_banner").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x1DC2D1)).withItalic(false)));
        return lightBlueBanner;
    }
}
