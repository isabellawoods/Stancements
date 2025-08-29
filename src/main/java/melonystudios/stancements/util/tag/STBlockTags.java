package melonystudios.stancements.util.tag;

import melonystudios.stancements.Stancements;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class STBlockTags {
    // Stancements' tags
    public static final TagKey<Block> SHELVES = stancements("shelves");

    public static TagKey<Block> stancements(String name) {
        return BlockTags.create(Stancements.stancements(name));
    }
}
