package melonystudios.stancements.tag;

import melonystudios.stancements.Stancements;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class STItemTags {
    // Stancements' tags
    public static final TagKey<Item> SHELVES = stancements("shelves");
    public static final TagKey<Item> CRAFTING_TABLE_CLOTHS = stancements("crafting_table_cloths");
    public static final TagKey<Item> TAGGING_RAILS = stancements("tagging_rails");
    public static final TagKey<Item> VINYL_DISC_DYES = stancements("vinyl_disc_dyes");
    public static final TagKey<Item> MINECART_TAGS = stancements("minecart_tags");
    public static final TagKey<Item> JADE_CONSIDERS_AS_RECORDING = stancements("jade_considers_as_recording");
    public static final TagKey<Item> CASSETTE_TAPES = stancements("cassette_tapes");

    // Common tags
    public static final TagKey<Item> DYED_WATER_BUCKETS = common("buckets/dyed_water");

    public static TagKey<Item> stancements(String name) {
        return TagKey.create(Registries.ITEM, Stancements.stancements(name));
    }

    public static TagKey<Item> common(String name) {
        return TagKey.create(Registries.ITEM, Stancements.common(name));
    }
}
