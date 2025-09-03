package melonystudios.stancements.item.tab;

import melonystudios.stancements.Stancements;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static melonystudios.stancements.item.STItems.*;

public class STCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Stancements.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(MUSIC_RECORDER.get())).title(Component.translatable("tab.stancements.main")).displayItems(((parameters, output) -> {
                // Decorative blocks
                // Shelves
                output.accept(OAK_SHELF);
                output.accept(SPRUCE_SHELF);
                output.accept(BIRCH_SHELF);
                output.accept(JUNGLE_SHELF);
                output.accept(ACACIA_SHELF);
                output.accept(DARK_OAK_SHELF);
                output.accept(MANGROVE_SHELF);
                output.accept(CHERRY_SHELF);
                output.accept(BAMBOO_SHELF);
                output.accept(CRIMSON_SHELF);
                output.accept(WARPED_SHELF);

                // Crafting Table Cloths
                output.accept(WHITE_CRAFTING_TABLE_CLOTH);
                output.accept(LIGHT_GRAY_CRAFTING_TABLE_CLOTH);
                output.accept(GRAY_CRAFTING_TABLE_CLOTH);
                output.accept(BLACK_CRAFTING_TABLE_CLOTH);
                output.accept(BROWN_CRAFTING_TABLE_CLOTH);
                output.accept(RED_CRAFTING_TABLE_CLOTH);
                output.accept(ORANGE_CRAFTING_TABLE_CLOTH);
                output.accept(YELLOW_CRAFTING_TABLE_CLOTH);
                output.accept(LIME_CRAFTING_TABLE_CLOTH);
                output.accept(GREEN_CRAFTING_TABLE_CLOTH);
                output.accept(CYAN_CRAFTING_TABLE_CLOTH);
                output.accept(LIGHT_BLUE_CRAFTING_TABLE_CLOTH);
                output.accept(BLUE_CRAFTING_TABLE_CLOTH);
                output.accept(PURPLE_CRAFTING_TABLE_CLOTH);
                output.accept(MAGENTA_CRAFTING_TABLE_CLOTH);
                output.accept(PINK_CRAFTING_TABLE_CLOTH);

                // Functional blocks
                output.accept(MUSIC_RECORDER);

                // Items
                output.accept(VINYL_DISC);
                output.accept(RECORDED_DISC);
            })).build());
}
