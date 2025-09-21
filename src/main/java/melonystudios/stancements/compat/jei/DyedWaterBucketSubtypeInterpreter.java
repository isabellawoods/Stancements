package melonystudios.stancements.compat.jei;

import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DyedWaterBucketSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
    public static final DyedWaterBucketSubtypeInterpreter INSTANCE = new DyedWaterBucketSubtypeInterpreter();

    @Override
    @Nullable
    public Object getSubtypeData(ItemStack stack, UidContext context) {
        if (stack.has(DataComponents.DYED_COLOR)) return "color_" + stack.get(DataComponents.DYED_COLOR).rgb();
        return "";
    }

    @Override
    @NotNull
    public String getLegacyStringSubtypeInfo(ItemStack stack, UidContext context) {
        if (stack.has(DataComponents.DYED_COLOR)) return "color_" + stack.get(DataComponents.DYED_COLOR).rgb();
        return "";
    }
}
