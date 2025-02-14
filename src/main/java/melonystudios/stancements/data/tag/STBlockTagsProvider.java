package melonystudios.stancements.data.tag;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.util.tag.STBlockTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

import static melonystudios.stancements.block.STBlocks.*;

public class STBlockTagsProvider extends BlockTagsProvider {
    public STBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper fileHelper) {
        super(generator, Stancements.MOD_ID, fileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(STBlockTags.SHELVES).add(OAK_SHELF.get(), SPRUCE_SHELF.get(), BIRCH_SHELF.get(), JUNGLE_SHELF.get(), ACACIA_SHELF.get(), DARK_OAK_SHELF.get(), CRIMSON_SHELF.get(), WARPED_SHELF.get());

        // Backport related tags
        this.tag(STBlockTags.MINEABLE_SHEARS).addTag(BlockTags.LEAVES).addTag(BlockTags.WOOL).add(Blocks.COBWEB, Blocks.GRASS, Blocks.FERN, Blocks.DEAD_BUSH, Blocks.VINE, Blocks.TRIPWIRE, Blocks.REDSTONE_WIRE);
        this.tag(STBlockTags.MAKES_IRON_SOUNDS).addTag(Tags.Blocks.STORAGE_BLOCKS_IRON).add(Blocks.IRON_BARS, Blocks.IRON_DOOR, Blocks.IRON_TRAPDOOR, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.CAULDRON, Blocks.HOPPER);
    }
}
