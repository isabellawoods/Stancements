package melonystudios.stancements.event;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.data.model.STBlockStateProvider;
import melonystudios.stancements.data.model.STItemModelProvider;
import melonystudios.stancements.data.tag.STBlockTagsProvider;
import melonystudios.stancements.data.tag.STItemTagsProvider;
import melonystudios.stancements.item.STItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
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

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemColorHandlers(final ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 0 ? -1 : ((IDyeableArmorItem) stack.getItem()).getColor(stack),
                STItems.RECORDED_DISC.get());
    }
}
