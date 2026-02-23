package melonystudios.stancements.data.loot;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.item.STItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.stream.Collectors;

import static melonystudios.stancements.block.STBlocks.*;

public class STBlockLootSubProvider extends BlockLootSubProvider {
    public STBlockLootSubProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        // Decorative
        // Shelves
        this.dropSelf(OAK_SHELF.get());
        this.dropSelf(SPRUCE_SHELF.get());
        this.dropSelf(BIRCH_SHELF.get());
        this.dropSelf(JUNGLE_SHELF.get());
        this.dropSelf(ACACIA_SHELF.get());
        this.dropSelf(DARK_OAK_SHELF.get());
        this.dropSelf(MANGROVE_SHELF.get());
        this.dropSelf(CHERRY_SHELF.get());
        this.dropSelf(BAMBOO_SHELF.get());
        this.dropSelf(CRIMSON_SHELF.get());
        this.dropSelf(WARPED_SHELF.get());

        // Crafting Table Cloths
        this.dropSelf(WHITE_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(GRAY_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(BLACK_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(BROWN_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(RED_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(ORANGE_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(YELLOW_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(LIME_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(GREEN_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(CYAN_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(BLUE_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(PURPLE_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(MAGENTA_CRAFTING_TABLE_CLOTH.get());
        this.dropSelf(PINK_CRAFTING_TABLE_CLOTH.get());

        // Functional
        this.dropSelf(MUSIC_RECORDER.get());
        this.dropOther(DYED_WATER_CAULDRON.get(), Items.CAULDRON);
        this.dropOther(MILK_CAULDRON.get(), Items.CAULDRON);
        this.cropPot(CROP_POT.get());
        this.cropPot(WHEAT_CROP_POT.get(), Items.WHEAT_SEEDS);
        this.cropPot(CARROT_CROP_POT.get(), Items.CARROT);
        this.cropPot(POTATO_CROP_POT.get(), Items.POTATO);
        this.cropPot(BEETROOT_CROP_POT.get(), Items.BEETROOT_SEEDS);
        this.cropPot(NETHER_WART_CROP_POT.get(), Items.NETHER_WART);

        // Rails
        this.dropSelf(GILDED_RAIL.get());
        this.dropSelf(WHITE_TAGGING_RAIL.get());
        this.dropSelf(LIGHT_GRAY_TAGGING_RAIL.get());
        this.dropSelf(GRAY_TAGGING_RAIL.get());
        this.dropSelf(BLACK_TAGGING_RAIL.get());
        this.dropSelf(BROWN_TAGGING_RAIL.get());
        this.dropSelf(RED_TAGGING_RAIL.get());
        this.dropSelf(ORANGE_TAGGING_RAIL.get());
        this.dropSelf(YELLOW_TAGGING_RAIL.get());
        this.dropSelf(LIME_TAGGING_RAIL.get());
        this.dropSelf(GREEN_TAGGING_RAIL.get());
        this.dropSelf(CYAN_TAGGING_RAIL.get());
        this.dropSelf(LIGHT_BLUE_TAGGING_RAIL.get());
        this.dropSelf(BLUE_TAGGING_RAIL.get());
        this.dropSelf(PURPLE_TAGGING_RAIL.get());
        this.dropSelf(MAGENTA_TAGGING_RAIL.get());
        this.dropSelf(PINK_TAGGING_RAIL.get());
    }

    /// Creates a loot table for an empty {@linkplain melonystudios.stancements.block.custom.croppot.CropPotBlock crop pot block}.
    /// @param cropPot The crop pot block.
    public void cropPot(Block cropPot) {
        this.add(cropPot, LootTable.lootTable()
                .withPool(this.applyExplosionCondition(cropPot, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(STItems.CROP_POT))
                        .apply(CopyBlockState.copyState(cropPot).copy(STBlockStateProperties.HOPPING)))));
    }

    /// Creates a loot table for a full {@linkplain melonystudios.stancements.block.custom.croppot.WheatCropPotBlock crop pot block}.
    /// @param cropPot The crop pot block.
    /// @param seeds The seeds item that drops when breaking this block.
    public void cropPot(Block cropPot, ItemLike seeds) {
        this.add(cropPot, LootTable.lootTable()
                .withPool(this.applyExplosionCondition(cropPot, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(STItems.CROP_POT))
                        .apply(CopyBlockState.copyState(cropPot).copy(STBlockStateProperties.HOPPING))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(seeds))));
    }

    @Override
    @Nonnull
    protected Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.stream().filter(block -> Stancements.MOD_ID.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace())).collect(Collectors.toSet());
    }
}
