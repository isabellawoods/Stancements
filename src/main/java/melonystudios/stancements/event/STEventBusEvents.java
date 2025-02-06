package melonystudios.stancements.event;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.data.model.STBlockStateProvider;
import melonystudios.stancements.data.model.STItemModelProvider;
import melonystudios.stancements.data.tag.STBlockTagsProvider;
import melonystudios.stancements.data.tag.STItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Stancements.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class STEventBusEvents {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        generator.addProvider(new STBlockStateProvider(generator, fileHelper));
        generator.addProvider(new STItemModelProvider(generator, fileHelper));

        STBlockTagsProvider blockTagsProvider = new STBlockTagsProvider(generator, fileHelper);
        generator.addProvider(blockTagsProvider);
        generator.addProvider(new STItemTagsProvider(generator, blockTagsProvider, fileHelper));
    }
}
