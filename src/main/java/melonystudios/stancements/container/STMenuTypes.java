package melonystudios.stancements.container;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.container.custom.AlbumMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Stancements.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<AlbumMenu>> ALBUM = MENUS.register("album",
            () -> new MenuType<>(AlbumMenu::new, FeatureFlags.VANILLA_SET));
}
