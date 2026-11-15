package melonystudios.stancements.data.loot;

import melonystudios.stancements.item.STItems;
import melonystudios.stancements.misc.STLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record STChestLootSubProvider(HolderLookup.Provider provider) implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(STLootTables.SHATTERED_DISCS_ADDITION, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(STItems.SHATTERED_DISC)
                                .when(LootItemRandomChanceCondition.randomChance(0.2F))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                        )
                ));
    }
}
