package melonystudios.stancements;

import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.blockentity.STBlockEntities;
import melonystudios.stancements.config.STConfig;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.misc.STStats;
import melonystudios.stancements.sound.STSounds;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
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
        STBlockEntities.BLOCK_ENTITIES.register(eventBus);
        STSounds.SOUNDS.register(eventBus);
        STStats.init();

        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, STConfig.COMMON_SPEC, "melonystudios/stancements-common.toml");
    }

    public static ResourceLocation stancements(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ItemModelsProperties.register(STItems.RECORDED_DISC.get(), stancements("label"), (stack, world, livEntity) -> {
            CompoundNBT tag = stack.getTag();
            if (tag != null && tag.contains("label", Constants.NBT.TAG_ANY_NUMERIC)) return tag.getInt("label");
            return 0;
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {}
}
