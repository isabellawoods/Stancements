package melonystudios.stancements.mixin.item;

import melonystudios.stancements.util.tab.STSpawnEggsTab;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpawnEggItem.class)
public class STSpawnEggItemMixin extends Item {
    public STSpawnEggItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void fillItemCategory(ItemGroup tab, NonNullList<ItemStack> list) {
        if ((this.allowdedIn(tab) || tab == STSpawnEggsTab.TAB) && tab != ItemGroup.TAB_MISC) {
            list.add(new ItemStack(this));
        }
    }
}
