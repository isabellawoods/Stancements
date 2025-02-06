package melonystudios.stancements.util.tab;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.item.STItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class STTab extends ItemGroup {
    public static final STTab TAB = new STTab(Stancements.MOD_ID + ".tab");

    public STTab(String label) {
        super(label);
    }

    @Nonnull
    public ItemStack makeIcon() {
        return new ItemStack(STItems.OAK_SHELF.get());
    }
}
