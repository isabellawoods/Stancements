package melonystudios.stancements.util.tag;

import melonystudios.stancements.Stancements;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class STItemTags {
    // Stancements' tags
    public static final TagKey<Item> SHELVES = stancements("shelves");
    public static final TagKey<Item> CRAFTING_TABLE_CLOTHS = stancements("crafting_table_cloths");
    public static final TagKey<Item> VINYL_DISC_DYES = stancements("vinyl_disc_dyes");

    public static TagKey<Item> stancements(String name) {
        return ItemTags.create(Stancements.stancements(name));
    }
}
