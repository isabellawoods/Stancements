package melonystudios.stancements.data.tag;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.util.tag.STBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static melonystudios.stancements.block.STBlocks.*;

public class STBlockTagsProvider extends BlockTagsProvider {
    public STBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper fileHelper) {
        super(output, registries, Stancements.MOD_ID, fileHelper);
    }

    @Override
    @NotNull
    public String getName() {
        return "Stancements - Block Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Stancements' tags
        this.tag(STBlockTags.SHELVES).add(OAK_SHELF.get(), SPRUCE_SHELF.get(), BIRCH_SHELF.get(), JUNGLE_SHELF.get(), ACACIA_SHELF.get(), DARK_OAK_SHELF.get(), MANGROVE_SHELF.get(), CHERRY_SHELF.get(),
                BAMBOO_SHELF.get(), CRIMSON_SHELF.get(), WARPED_SHELF.get());
        this.tag(STBlockTags.CRAFTING_TABLE_CLOTHS).add(WHITE_CRAFTING_TABLE_CLOTH.get(), LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get(), GRAY_CRAFTING_TABLE_CLOTH.get(), BLACK_CRAFTING_TABLE_CLOTH.get(),
                BROWN_CRAFTING_TABLE_CLOTH.get(), RED_CRAFTING_TABLE_CLOTH.get(), ORANGE_CRAFTING_TABLE_CLOTH.get(), YELLOW_CRAFTING_TABLE_CLOTH.get(), LIME_CRAFTING_TABLE_CLOTH.get(),
                GREEN_CRAFTING_TABLE_CLOTH.get(), CYAN_CRAFTING_TABLE_CLOTH.get(), LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get(), BLUE_CRAFTING_TABLE_CLOTH.get(), PURPLE_CRAFTING_TABLE_CLOTH.get(),
                MAGENTA_CRAFTING_TABLE_CLOTH.get(), PINK_CRAFTING_TABLE_CLOTH.get());

        // Common tags
        this.tag(STBlockTags.MINEABLE_WITH_SHEARS).addTag(BlockTags.LEAVES).addTag(BlockTags.WOOL).add(Blocks.COBWEB, Blocks.SHORT_GRASS, Blocks.FERN,  Blocks.DEAD_BUSH, Blocks.HANGING_ROOTS,
                Blocks.VINE, Blocks.GLOW_LICHEN, Blocks.TRIPWIRE);

        // Minecraft tags
        this.tag(BlockTags.MINEABLE_WITH_AXE).addTag(STBlockTags.SHELVES).add(MUSIC_RECORDER.get());
        this.tag(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).addTag(STBlockTags.CRAFTING_TABLE_CLOTHS);
        this.tag(BlockTags.DAMPENS_VIBRATIONS).addTag(STBlockTags.CRAFTING_TABLE_CLOTHS);
    }
}
