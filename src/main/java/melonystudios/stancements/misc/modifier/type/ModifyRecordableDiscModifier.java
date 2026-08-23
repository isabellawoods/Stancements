package melonystudios.stancements.misc.modifier.type;

import com.mojang.serialization.Codec;
import melonystudios.stancements.misc.loot.ModificationContextAware;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.ModifierComponentType;
import melonystudios.stancements.misc.modifier.VinylModifier;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;

import java.util.List;

public record ModifyRecordableDiscModifier(List<LootItemFunction> functions) implements ModifierComponentType {
    public static final Codec<ModifyRecordableDiscModifier> CODEC = LootItemFunctions.ROOT_CODEC.listOf().xmap(ModifyRecordableDiscModifier::new, ModifyRecordableDiscModifier::functions);

    @Override
    public void onApplyModifiers(ModificationContext context, Holder<VinylModifier> modifier) {
        ItemStack funcStack = context.transientStack().copy();
        for (LootItemFunction function : this.functions()) {
            if (function instanceof ModificationContextAware condition) condition.withContext(context);
            funcStack = function.apply(funcStack, VinylModifier.modifierContext(context));
        }

        // using a separate transient stack as the ItemStack parameter couldn't be updated by this component
        // ~isa 01-07-26
        context.withTransientStack(funcStack);
    }
}
