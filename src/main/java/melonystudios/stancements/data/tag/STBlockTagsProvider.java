package melonystudios.stancements.data.tag;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.util.tag.STBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
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
        this.tag(STBlockTags.CROP_POTS).add(CROP_POT.get(), WHEAT_CROP_POT.get(), CARROT_CROP_POT.get(), POTATO_CROP_POT.get(), BEETROOT_CROP_POT.get(), NETHER_WART_CROP_POT.get());

        // Common tags
        this.tag(Tags.Blocks.VILLAGER_JOB_SITES).add(DYED_WATER_CAULDRON.get(), MILK_CAULDRON.get());
        this.tag(STBlockTags.MINEABLE_WITH_SHEARS).addTag(BlockTags.LEAVES).addTag(BlockTags.WOOL).add(Blocks.COBWEB, Blocks.SHORT_GRASS, Blocks.FERN,  Blocks.DEAD_BUSH, Blocks.HANGING_ROOTS,
                Blocks.VINE, Blocks.GLOW_LICHEN, Blocks.TRIPWIRE);

        this.tag(Tags.Blocks.DYED_WHITE).add(WHITE_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_LIGHT_GRAY).add(LIGHT_GRAY_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_GRAY).add(GRAY_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_BLACK).add(BLACK_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_BROWN).add(BROWN_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_RED).add(RED_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_ORANGE).add(ORANGE_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_YELLOW).add(YELLOW_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_LIME).add(LIME_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_GREEN).add(GREEN_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_CYAN).add(CYAN_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_LIGHT_BLUE).add(LIGHT_BLUE_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_BLUE).add(BLUE_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_PURPLE).add(PURPLE_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_MAGENTA).add(MAGENTA_CRAFTING_TABLE_CLOTH.get());
        this.tag(Tags.Blocks.DYED_PINK).add(PINK_CRAFTING_TABLE_CLOTH.get());

        // Minecraft tags
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(STBlockTags.CROP_POTS);
        this.tag(BlockTags.MINEABLE_WITH_AXE).addTag(STBlockTags.SHELVES).add(MUSIC_RECORDER.get());
        this.tag(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).addTag(STBlockTags.CRAFTING_TABLE_CLOTHS);
        this.tag(BlockTags.DAMPENS_VIBRATIONS).addTag(STBlockTags.CRAFTING_TABLE_CLOTHS);
        this.tag(BlockTags.CAULDRONS).add(DYED_WATER_CAULDRON.get(), MILK_CAULDRON.get());
    }
}
