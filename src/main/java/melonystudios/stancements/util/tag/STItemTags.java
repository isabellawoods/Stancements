package melonystudios.stancements.util.tag;

import melonystudios.stancements.Stancements;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class STItemTags {
    public static final ITag.INamedTag<Item> SHELVES = stancements("shelves");
    public static final Tags.IOptionalNamedTag<Item> WITH_COMMON_RARITY = melonyOptional("with_rarity/common");
    public static final Tags.IOptionalNamedTag<Item> WITH_UNCOMMON_RARITY = melonyOptional("with_rarity/uncommon");
    public static final Tags.IOptionalNamedTag<Item> WITH_RARE_RARITY = melonyOptional("with_rarity/rare");
    public static final Tags.IOptionalNamedTag<Item> WITH_EPIC_RARITY = melonyOptional("with_rarity/epic");
    public static final Tags.IOptionalNamedTag<Item> WITH_POTATO_RARITY = melonyOptional("with_rarity/potato");

    private static ITag.INamedTag<Item> stancements(String name) {
        return ItemTags.bind(Stancements.stancements(name).toString());
    }

    private static Tags.IOptionalNamedTag<Item> melonyOptional(String name) {
        return ItemTags.createOptional(new ResourceLocation("melony", name));
    }
}
