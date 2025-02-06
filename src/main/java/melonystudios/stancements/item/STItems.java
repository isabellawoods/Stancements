package melonystudios.stancements.item;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.util.tab.STTab;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class STItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Stancements.MOD_ID);

    public static final RegistryObject<Item> OAK_SHELF = ITEMS.register("oak_shelf", () -> new BlockItem(STBlocks.OAK_SHELF.get(), new Item.Properties().tab(STTab.TAB)));
    public static final RegistryObject<Item> SPRUCE_SHELF = ITEMS.register("spruce_shelf", () -> new BlockItem(STBlocks.SPRUCE_SHELF.get(), new Item.Properties().tab(STTab.TAB)));
    public static final RegistryObject<Item> BIRCH_SHELF = ITEMS.register("birch_shelf", () -> new BlockItem(STBlocks.BIRCH_SHELF.get(), new Item.Properties().tab(STTab.TAB)));
    public static final RegistryObject<Item> JUNGLE_SHELF = ITEMS.register("jungle_shelf", () -> new BlockItem(STBlocks.JUNGLE_SHELF.get(), new Item.Properties().tab(STTab.TAB)));
    public static final RegistryObject<Item> ACACIA_SHELF = ITEMS.register("acacia_shelf", () -> new BlockItem(STBlocks.ACACIA_SHELF.get(), new Item.Properties().tab(STTab.TAB)));
    public static final RegistryObject<Item> DARK_OAK_SHELF = ITEMS.register("dark_oak_shelf", () -> new BlockItem(STBlocks.DARK_OAK_SHELF.get(), new Item.Properties().tab(STTab.TAB)));
    public static final RegistryObject<Item> CRIMSON_SHELF = ITEMS.register("crimson_shelf", () -> new BlockItem(STBlocks.CRIMSON_SHELF.get(), new Item.Properties().tab(STTab.TAB)));
    public static final RegistryObject<Item> WARPED_SHELF = ITEMS.register("warped_shelf", () -> new BlockItem(STBlocks.WARPED_SHELF.get(), new Item.Properties().tab(STTab.TAB)));
}
