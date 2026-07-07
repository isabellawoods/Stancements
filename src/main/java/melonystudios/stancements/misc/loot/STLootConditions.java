package melonystudios.stancements.misc.loot;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.loot.condition.ItemRecorderStateCondition;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STLootConditions {
    public static final DeferredRegister<LootItemConditionType> CONDITIONS = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, Stancements.MOD_ID);

    public static final DeferredHolder<LootItemConditionType, LootItemConditionType> ITEM_RECORDER_STATE = CONDITIONS.register("recorder_state/item",
            () -> new LootItemConditionType(ItemRecorderStateCondition.CODEC));
}
