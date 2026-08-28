package melonystudios.stancements.container.custom;

import melonystudios.stancements.container.STMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class AlbumMenu extends AbstractContainerMenu {
    public AlbumMenu(int containerID, Inventory playerInventory) {
        super(STMenuTypes.ALBUM.get(), containerID);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
