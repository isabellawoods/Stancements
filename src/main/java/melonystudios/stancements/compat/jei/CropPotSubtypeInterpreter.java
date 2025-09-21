package melonystudios.stancements.compat.jei;

import melonystudios.stancements.block.STBlockStateProperties;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CropPotSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
    public static final CropPotSubtypeInterpreter INSTANCE = new CropPotSubtypeInterpreter();

    @Override
    @Nullable
    public Object getSubtypeData(ItemStack stack, UidContext context) {
        BlockItemStateProperties blockState = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
        Boolean hopping = blockState.get(STBlockStateProperties.HOPPING);
        return hopping != null ? (hopping ? "hopping" : "") : "";
    }

    @Override
    @NotNull
    public String getLegacyStringSubtypeInfo(ItemStack stack, UidContext context) {
        BlockItemStateProperties blockState = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
        Boolean hopping = blockState.get(STBlockStateProperties.HOPPING);
        return hopping != null ? (hopping ? "hopping" : "") : "";
    }
}
