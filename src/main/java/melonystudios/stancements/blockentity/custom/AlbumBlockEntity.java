package melonystudios.stancements.blockentity.custom;

import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.container.custom.AlbumMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AlbumBlockEntity extends BaseContainerBlockEntity {
    public AlbumBlockEntity(BlockPos pos, BlockState state) {
        super(STBlockEntities.ALBUM.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.stancements.album");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return NonNullList.create();
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {

    }

    @Override
    protected AbstractContainerMenu createMenu(int containerID, Inventory inventory) {
        return new AlbumMenu(containerID, inventory);
    }

    @Override
    public int getContainerSize() {
        return 0; // the album inventory is dependent on the amount of listings, so how do i make this dynamic? ~isa 08-08-26
    }
}
