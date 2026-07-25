package melonystudios.stancements.item.custom;

import melonystudios.stancements.block.STBlockStateProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

public class CropPotBlockItem extends BlockItem {
    public CropPotBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    @NonNull
    public Component getName(ItemStack stack) {
        String descriptionID = this.getBlock().getDescriptionId();
        BlockItemStateProperties blockState = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
        Boolean hopping = blockState.get(STBlockStateProperties.HOPPING);
        if (hopping != null && hopping) {
            Identifier identifier = BuiltInRegistries.BLOCK.getKey(this.getBlock());
            return Component.translatable("block." + identifier.getNamespace() + ".hopping_" + identifier.getPath());
        }
        return Component.translatable(descriptionID);
    }
}
