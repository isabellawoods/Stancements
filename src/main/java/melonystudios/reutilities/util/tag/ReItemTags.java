package melonystudios.reutilities.util.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ReItemTags {
    public static final TagKey<Item> LOGOS = common("logos");

    /// Creates a new item tag with a specified name.
    /// @param name The tag's name, under the **Common** (`c`) namespace.
    public static TagKey<Item> common(String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", name));
    }
}
