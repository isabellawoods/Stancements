package melonystudios.stancements.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nullable;

public class STUtils {
    @Nullable
    private static final Rarity POTATO = Rarity.create("POTATO", TextFormatting.GREEN);

    public static Rarity potatoRarity() {
        return POTATO != null ? POTATO : Rarity.COMMON;
    }

    // Stack loading methods copied from Revaried (1.8.0.10)
    public static CompoundNBT saveStack(ItemStack stack, CompoundNBT tag) {
        if (stack == ItemStack.EMPTY) {
            tag.putString("id", "minecraft:air");
            tag.putInt("count", 0);
        } else {
            tag.putString("id", stack.getItem().getRegistryName().toString());
            if (stack.getCount() != 1) tag.putInt("count", stack.getCount());
            if (stack.getTag() != null) tag.put("tags", stack.getTag().copy());
        }
        return tag;
    }

    public static ItemStack loadStack(CompoundNBT tag) {
        Item item = Items.AIR;
        int count = 1;

        if (tag.contains("id", Constants.NBT.TAG_STRING)) {
            Item item1 = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(tag.getString("id")));
            if (item1 != null) item = item1;
        }

        if (tag.contains("count", Constants.NBT.TAG_ANY_NUMERIC)) {
            count = tag.getInt("count");
        }

        ItemStack stack = new ItemStack(item, count);

        if (tag.contains("tags", Constants.NBT.TAG_STRING)) {
            try {
                CompoundNBT tagsTag = JsonToNBT.parseTag(tag.getString("tags"));
                tag.put("tags", tagsTag);
            } catch (CommandSyntaxException exception) {
                LogManager.getLogger().error(new TranslationTextComponent("error.stancements.stack_loading.tag", tag.getString("tags")).getString(), exception.getMessage());
            }
        }

        if (tag.contains("tags", Constants.NBT.TAG_COMPOUND)) {
            stack.setTag(tag.getCompound("tags"));
        } else if (tag.contains("tag", Constants.NBT.TAG_COMPOUND)) {
            stack.setTag(tag.getCompound("tag"));
        }
        stack.getItem().verifyTagAfterLoad(tag);

        if (stack.getItem().isDamageable(stack)) stack.setDamageValue(stack.getDamageValue());
        return stack;
    }
}
