package melonystudios.stancements.mixin.item;

import melonystudios.stancements.config.STConfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(HangingEntityItem.class)
public class STHangingEntityItemMixin extends Item {
    public STHangingEntityItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void fillItemCategory(ItemGroup tab, NonNullList<ItemStack> list) {
        if (this.allowdedIn(tab) && STConfig.COMMON_CONFIGS.populatePaintingVariants.get()) {
            list.add(new ItemStack(this));
            if (this == Items.PAINTING) {
                for (PaintingType painting : ForgeRegistries.PAINTING_TYPES) {
                    ItemStack paintingStack = new ItemStack(this);
                    CompoundNBT entityTag = paintingStack.getOrCreateTagElement("EntityTag");
                    entityTag.putString("Motive", painting.getRegistryName().toString());
                    list.add(paintingStack);
                }
            }
        } else super.fillItemCategory(tab, list);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        CompoundNBT entityTag = stack.getTagElement("EntityTag");
        if (stack.getItem() == Items.PAINTING) {
            if (entityTag != null && entityTag.contains("Motive")) {
                PaintingType painting = ForgeRegistries.PAINTING_TYPES.getValue(ResourceLocation.tryParse(entityTag.getString("Motive")));
                String translation = Util.makeDescriptionId("painting", painting.getRegistryName());
                String titleTranslation = translation + ".title";
                String authorTranslation = translation + ".author";

                if (I18n.exists(titleTranslation)) tooltip.add(new TranslationTextComponent(titleTranslation).withStyle(TextFormatting.YELLOW));
                if (I18n.exists(authorTranslation)) tooltip.add(new TranslationTextComponent(authorTranslation).withStyle(TextFormatting.GRAY));
                tooltip.add(new TranslationTextComponent("tooltip.stancements.painting_dimensions", painting.getWidth() / 16, painting.getHeight() / 16).withStyle(TextFormatting.WHITE));
            } else {
                tooltip.add(new TranslationTextComponent("tooltip.stancements.random_painting").withStyle(TextFormatting.GRAY));
            }
        }
    }
}
