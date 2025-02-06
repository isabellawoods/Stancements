package melonystudios.stancements;

import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.config.STConfig;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.sound.STSounds;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Stancements.MOD_ID)
public class Stancements {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "stancements"; // Portmanteau of "stacked" and "enhancements".

    public Stancements() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);

        STItems.ITEMS.register(eventBus);
        STBlocks.BLOCKS.register(eventBus);
        STSounds.SOUNDS.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, STConfig.COMMON_SPEC, "melonystudios/stancements-common.toml");
    }

    public static ResourceLocation stancements(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    private void clientSetup(final FMLClientSetupEvent event) {}
}
