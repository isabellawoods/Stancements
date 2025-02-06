package melonystudios.stancements.util.tag;

import melonystudios.stancements.Stancements;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class STBlockTags {
    public static final ITag.INamedTag<Block> SHELVES = stancements("shelves");
    public static final ITag.INamedTag<Block> MAKES_IRON_SOUNDS = melony("makes_iron_sounds");
    public static final ITag.INamedTag<Block> MINEABLE_SHEARS = melony("mineable/shears");

    private static ITag.INamedTag<Block> stancements(String name) {
        return BlockTags.bind(Stancements.stancements(name).toString());
    }

    private static ITag.INamedTag<Block> melony(String name) {
        return BlockTags.bind(new ResourceLocation("melony", name).toString());
    }
}
