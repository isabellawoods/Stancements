package melonystudios.stancements.util.tag;

import melonystudios.stancements.Stancements;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class STBlockTags {
    // Stancements' tags
    public static final TagKey<Block> SHELVES = stancements("shelves");
    public static final TagKey<Block> CRAFTING_TABLE_CLOTHS = stancements("crafting_table_cloths");
    public static final TagKey<Block> CROP_POTS = stancements("crop_pots");

    // Common tags
    public static final TagKey<Block> MINEABLE_WITH_SHEARS = common("mineable/shears");

    public static TagKey<Block> stancements(String name) {
        return BlockTags.create(Stancements.stancements(name));
    }

    public static TagKey<Block> common(String name) {
        return BlockTags.create(Stancements.common(name));
    }
}
