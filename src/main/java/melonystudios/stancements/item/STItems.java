package melonystudios.stancements.item;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.item.custom.VinylDiscItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Stancements.MOD_ID);

    // Decorative blocks
    public static final DeferredItem<Item> OAK_SHELF = ITEMS.register("oak_shelf", () -> new BlockItem(STBlocks.OAK_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> SPRUCE_SHELF = ITEMS.register("spruce_shelf", () -> new BlockItem(STBlocks.SPRUCE_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> BIRCH_SHELF = ITEMS.register("birch_shelf", () -> new BlockItem(STBlocks.BIRCH_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> JUNGLE_SHELF = ITEMS.register("jungle_shelf", () -> new BlockItem(STBlocks.JUNGLE_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> ACACIA_SHELF = ITEMS.register("acacia_shelf", () -> new BlockItem(STBlocks.ACACIA_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> DARK_OAK_SHELF = ITEMS.register("dark_oak_shelf", () -> new BlockItem(STBlocks.DARK_OAK_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> CRIMSON_SHELF = ITEMS.register("crimson_shelf", () -> new BlockItem(STBlocks.CRIMSON_SHELF.get(), new Item.Properties()));
    public static final DeferredItem<Item> WARPED_SHELF = ITEMS.register("warped_shelf", () -> new BlockItem(STBlocks.WARPED_SHELF.get(), new Item.Properties()));

    // Functional blocks
    public static final DeferredItem<Item> MUSIC_RECORDER = ITEMS.register("music_recorder", () -> new BlockItem(STBlocks.MUSIC_RECORDER.get(), new Item.Properties()));

    // Items
    public static final DeferredItem<Item> VINYL_DISC = ITEMS.register("vinyl_disc", () -> new VinylDiscItem(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> RECORDED_DISC = ITEMS.register("recorded_disc", () -> new RecordedDiscItem(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
}
