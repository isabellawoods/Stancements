package melonystudios.stancements;

import com.mojang.logging.LogUtils;
import melonystudios.stancements.block.STBlockStateProperties;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.tab.STCreativeTabs;
import melonystudios.stancements.misc.STStatistics;
import melonystudios.stancements.sound.STSounds;
import melonystudios.stancements.util.STCauldronInteractions;
import melonystudios.stancements.util.STVanillaCompatibility;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.slf4j.Logger;

@Mod(Stancements.MOD_ID)
public class Stancements {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "stancements"; // Portmanteau of "stacked" and "enhancements".

    public Stancements(IEventBus eventBus, ModContainer container) {
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);

        STBlocks.BLOCKS.register(eventBus);
        STBlockEntities.BLOCK_ENTITIES.register(eventBus);
        STItems.ITEMS.register(eventBus);
        STDataComponents.COMPONENTS.register(eventBus);
        STCreativeTabs.TABS.register(eventBus);
        STSounds.SOUNDS.register(eventBus);
        STStatistics.STATS.register(eventBus);

        NeoForgeMod.enableMilkFluid();
        container.registerConfig(ModConfig.Type.COMMON, STConfigs.SPEC, "melonystudios/stancements-common.toml");
        container.registerExtensionPoint(IConfigScreenFactory.class, (minecraft, lastScreen) -> new ConfigurationScreen(container, lastScreen));
    }

    /// Creates a new resource location under ***Stancements***' namespace.
    /// @param name The path of this resource location.
    public static ResourceLocation stancements(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    /// Creates a new resource location using the **Common** (`c`) namespace.
    /// @param name The path of this resource location.
    public static ResourceLocation common(String name) {
        return ResourceLocation.fromNamespaceAndPath("c", name);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Miscellaneous
        STVanillaCompatibility.flammables();
        STCauldronInteractions.registerInteractions();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Item overrides
        ItemProperties.register(STItems.RECORDED_DISC.get(), stancements("label"), (stack, world, livEntity, seed) -> {
            Float label = stack.get(STDataComponents.LABEL);
            return label == null ? 1 : label;
        });
        ItemProperties.register(STItems.CROP_POT.get(), stancements("hopping"), (stack, world, livEntity, seed) -> {
            BlockItemStateProperties blockState = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
            Boolean hopping = blockState.get(STBlockStateProperties.HOPPING);
            return hopping != null && hopping ? 1 : 0;
        });
    }
}
