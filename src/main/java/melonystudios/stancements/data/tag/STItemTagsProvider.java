package melonystudios.stancements.data.tag;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.util.tag.STItemTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

import static melonystudios.stancements.item.STItems.*;

public class STItemTagsProvider extends ItemTagsProvider {
    public STItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper fileHelper) {
        super(generator, blockTagsProvider, Stancements.MOD_ID, fileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(STItemTags.SHELVES).add(OAK_SHELF.get(), SPRUCE_SHELF.get(), BIRCH_SHELF.get(), JUNGLE_SHELF.get(), ACACIA_SHELF.get(), DARK_OAK_SHELF.get(), CRIMSON_SHELF.get(), WARPED_SHELF.get());
        this.tag(STItemTags.WITH_COMMON_RARITY).add(Items.END_CRYSTAL, Items.GOLDEN_APPLE);
        this.tag(STItemTags.WITH_UNCOMMON_RARITY).add(Items.PIGLIN_BANNER_PATTERN, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS, Items.NAUTILUS_SHELL,
                Items.CONDUIT, Items.MUSIC_DISC_13, Items.MUSIC_DISC_CAT, Items.MUSIC_DISC_BLOCKS, Items.MUSIC_DISC_CHIRP, Items.MUSIC_DISC_FAR, Items.MUSIC_DISC_MALL, Items.MUSIC_DISC_MELLOHI, Items.MUSIC_DISC_STAL, Items.MUSIC_DISC_STRAD,
                Items.MUSIC_DISC_WARD, Items.MUSIC_DISC_11, Items.MUSIC_DISC_WAIT);
        this.tag(STItemTags.WITH_RARE_RARITY).add(Items.ENCHANTED_GOLDEN_APPLE, Items.TRIDENT, Items.NETHER_STAR, Items.WITHER_SKELETON_SKULL, Items.SKULL_BANNER_PATTERN, Items.MOJANG_BANNER_PATTERN);
        this.tag(STItemTags.WITH_EPIC_RARITY).add(Items.ELYTRA, Items.DRAGON_HEAD, Items.BARRIER, Items.STRUCTURE_VOID, Items.COMMAND_BLOCK_MINECART, Items.DEBUG_STICK, Items.KNOWLEDGE_BOOK).addOptional(
                revaried("debug_bow")).addOptional(revaried("debug_arrow")).addOptional(revaried("enchanted_knowledge_book"));
        this.tag(STItemTags.WITH_POTATO_RARITY);
    }

    public static ResourceLocation revaried(String name) {
        return new ResourceLocation("variants", name);
    }
}
