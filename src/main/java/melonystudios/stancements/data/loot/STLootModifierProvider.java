package melonystudios.stancements.data.loot;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.STLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class STLootModifierProvider extends GlobalLootModifierProvider {
    public STLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Stancements.MOD_ID);
    }

    @Override
    @NotNull
    public String getName() {
        return Stancements.generatorName("Loot Modifiers");
    }

    @Override
    protected void start() {
        var dungeonTable = LootTableIdCondition.builder(BuiltInLootTables.SIMPLE_DUNGEON.location()).build();
        this.add("shattered_discs_on_dungeons", new AddTableLootModifier(new LootItemCondition[] {dungeonTable}, STLootTables.SHATTERED_DISCS_ADDITION));
    }
}
