package melonystudios.stancements;

import com.mojang.logging.LogUtils;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.tab.STCreativeTabs;
import melonystudios.stancements.misc.STStats;
import melonystudios.stancements.misc.STVanillaCompatibility;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(Stancements.MOD_ID)
public class Stancements {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "stancements";

    public Stancements(IEventBus eventBus, ModContainer container) {
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);

        STBlocks.BLOCKS.register(eventBus);
        STBlockEntities.BLOCK_ENTITIES.register(eventBus);
        STItems.ITEMS.register(eventBus);
        STDataComponents.COMPONENTS.register(eventBus);
        STCreativeTabs.TABS.register(eventBus);
        STStats.STATS.register(eventBus);
    }

    public static ResourceLocation stancements(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static ResourceLocation common(String name) {
        return ResourceLocation.fromNamespaceAndPath("c", name);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Miscellaneous
        STVanillaCompatibility.flammables();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Item Overrides
        ItemProperties.register(STItems.RECORDED_DISC.get(), stancements("label"), (stack, world, entity, seed) -> {
            Float label = stack.get(STDataComponents.LABEL);
            return label == null ? 1 : label;
        });
    }
}
