package melonystudios.stancements.mixin.item;

import melonystudios.stancements.config.STConfig;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FireworkRocketItem.class)
public class STFireworkRocketItemMixin extends Item {
    public STFireworkRocketItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void fillItemCategory(ItemGroup tab, NonNullList<ItemStack> list) {
        if (this.allowdedIn(tab) && STConfig.COMMON_CONFIGS.populateFireworkRocketDurations.get()) {
            for (int flight = 1; flight <= 3; ++flight) {
                ItemStack rocketStack = new ItemStack(this);
                CompoundNBT fireworksTag = rocketStack.getOrCreateTagElement("Fireworks");
                fireworksTag.putInt("Flight", flight);
                list.add(rocketStack);
            }
        } else super.fillItemCategory(tab, list);
    }
}
