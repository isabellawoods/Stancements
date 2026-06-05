package melonystudios.stancements;

import com.mojang.logging.LogUtils;
import melonystudios.reutilities.component.ReDataComponents;
import melonystudios.stancements.block.STBlockTypes;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.tab.STCreativeTabs;
import melonystudios.stancements.misc.STStatistics;
import melonystudios.stancements.misc.advancement.STCriteriaTriggers;
import melonystudios.stancements.misc.attachment.STAttachmentTypes;
import melonystudios.stancements.option.STOptions;
import melonystudios.stancements.sound.STSounds;
import melonystudios.stancements.util.STCompatibility;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.slf4j.Logger;

@Mod(Stancements.MOD_ID)
public class Stancements {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final int ACCENT_COLOR = 0xCF8EDA;
    public static final String MOD_ID = "stancements"; // Portmanteau of "stacked" and "enhancements".
    public static final String NETWORK_VERSION = "1";
    public static final String RMS_ID = "recorder_modded_songs";

    public Stancements(IEventBus eventBus, ModContainer container) {
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);

        STBlocks.BLOCKS.register(eventBus);
        STBlockTypes.TYPES.register(eventBus);
        STBlockEntities.BLOCK_ENTITIES.register(eventBus);
        STItems.ITEMS.register(eventBus);
        STDataComponents.COMPONENTS.register(eventBus);
        ReDataComponents.COMPONENTS.register(eventBus);
        STCreativeTabs.TABS.register(eventBus);
        STSounds.STANCEMENTS.register(eventBus);
        STSounds.MINECRAFT.register(eventBus);
        STStatistics.STATS.register(eventBus);
        STCriteriaTriggers.TRIGGERS.register(eventBus);
        STAttachmentTypes.ATTACHMENTS.register(eventBus);

        NeoForgeMod.enableMilkFluid();
        container.registerConfig(ModConfig.Type.COMMON, STOptions.SPEC, "melonystudios/stancements-common.toml");
    }

    /// Creates a name for a data generator using ***Stancements***' name.
    /// @param name The name of the generator, like *"Item Models"*.
    public static String generatorName(String name) {
        return "Stancements — " + name;
    }

    /// Creates a new identifier under ***Stancements***' namespace.
    /// @param name The path of this identifier.
    public static Identifier stancements(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }

    /// Creates a new identifier under the **Common** (`c`) namespace.
    /// @param name The path of this identifier.
    public static Identifier common(String name) {
        return Identifier.fromNamespaceAndPath("c", name);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Miscellaneous
        STCompatibility.flammables();
        STCompatibility.dispenserBehaviors();
    }

    private void clientSetup(final FMLClientSetupEvent event) {}
}
