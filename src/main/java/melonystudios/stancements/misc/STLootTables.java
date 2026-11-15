package melonystudios.stancements.misc;

import melonystudios.stancements.Stancements;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class STLootTables {
    public static final ResourceKey<LootTable> SHATTERED_DISCS_ADDITION = register("chests/shattered_discs_addition");

    private static ResourceKey<LootTable> register(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, Stancements.stancements(name));
    }
}
