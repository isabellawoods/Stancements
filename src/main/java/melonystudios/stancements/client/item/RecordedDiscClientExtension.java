package melonystudios.stancements.client.item;

import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class RecordedDiscClientExtension implements IClientItemExtensions {
    @Override
    public int getDefaultDyeColor(ItemStack stack) {
        return DyedItemColor.getOrDefault(stack, RecordedDiscItem.DEFAULT_DISC_COLOR);
    }
}
