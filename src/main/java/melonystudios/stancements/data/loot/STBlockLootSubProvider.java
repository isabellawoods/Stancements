package melonystudios.stancements.data.loot;

import melonystudios.stancements.Stancements;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

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
    }

    @Override
    @Nonnull
    protected Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.stream().filter(block -> Stancements.MOD_ID.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace())).collect(Collectors.toSet());
    }
}
