package melonystudios.stancements.data.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class STLootTableProvider extends LootTableProvider {
    public STLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(new SubProviderEntry(STBlockLootSubProvider::new, LootContextParamSets.BLOCK)), registries);
    }

    @Override
    protected void validate(WritableRegistry<LootTable> lootTables, ValidationContext context, ProblemReporter.Collector problemCollector) {
        // who needs loot table validation anyway? it only makes my life worse
    }
}
