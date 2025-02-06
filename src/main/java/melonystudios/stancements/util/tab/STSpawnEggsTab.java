package melonystudios.stancements.util.tab;

import melonystudios.stancements.Stancements;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class STSpawnEggsTab extends ItemGroup {
    public static final STSpawnEggsTab TAB = new STSpawnEggsTab(Stancements.MOD_ID + ".spawn_eggs");

    public STSpawnEggsTab(String label) {
        super(label);
    }

    @Nonnull
    public ItemStack makeIcon() {
        return new ItemStack(Items.PIG_SPAWN_EGG);
    }

    @Override
    public void fillItemList(NonNullList<ItemStack> list) {
        list.add(new ItemStack(Items.SPAWNER));
        super.fillItemList(list);
    }
}
