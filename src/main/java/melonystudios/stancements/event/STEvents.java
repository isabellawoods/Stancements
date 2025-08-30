package melonystudios.stancements.event;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.data.loot.STLootTableProvider;
import melonystudios.stancements.data.misc.STDataMapsProvider;
import melonystudios.stancements.data.misc.STDataPackRegistriesProvider;
import melonystudios.stancements.data.misc.STRecipeProvider;
import melonystudios.stancements.data.model.STBlockStateProvider;
import melonystudios.stancements.data.model.STItemModelProvider;
import melonystudios.stancements.data.tag.STBlockTagsProvider;
import melonystudios.stancements.data.tag.STItemTagsProvider;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Stancements.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class STEvents {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();
        PackOutput output = generator.getPackOutput();

        if (event.includeClient()) {
            // Models
            generator.addProvider(true, new STBlockStateProvider(output, fileHelper));
            generator.addProvider(true, new STItemModelProvider(output, fileHelper));
        }

        if (event.includeServer()) {
            // Tags
            STBlockTagsProvider blockTags = new STBlockTagsProvider(output, registries, fileHelper);
            generator.addProvider(true, blockTags);
            generator.addProvider(true, new STItemTagsProvider(output, registries, blockTags.contentsGetter(), fileHelper));

            // Miscellaneous
            generator.addProvider(true, new STDataPackRegistriesProvider(output, registries));
            generator.addProvider(true, new STRecipeProvider(output, registries));
            generator.addProvider(true, new STLootTableProvider(output, registries));
            generator.addProvider(true, new STDataMapsProvider(output, registries));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> tintIndex == 0 ? -1 : DyedItemColor.getOrDefault(stack, RecordedDiscItem.DEFAULT_DISC_COLOR), STItems.RECORDED_DISC.get());
    }
}
