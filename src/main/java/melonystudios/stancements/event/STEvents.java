package melonystudios.stancements.event;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.command.ConvertDiscToJukeboxSongCommand;
import melonystudios.stancements.data.misc.*;
import melonystudios.stancements.misc.attachment.STAttachmentTypes;
import melonystudios.stancements.misc.attachment.STCapabilities;
import melonystudios.stancements.command.UpdateRecordedDiscCommand;
import melonystudios.stancements.data.loot.STLootTableProvider;
import melonystudios.stancements.data.model.STBlockStateProvider;
import melonystudios.stancements.data.model.STItemModelProvider;
import melonystudios.stancements.data.tag.STBlockTagsProvider;
import melonystudios.stancements.data.tag.STItemTagsProvider;
import melonystudios.stancements.misc.datamap.STDataMaps;
import melonystudios.stancements.network.s2c.ClientPayloadHandler;
import melonystudios.stancements.network.s2c.RequestRecordingAttempt;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.RegisterCauldronFluidContentEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Stancements.MOD_ID)
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
            // Miscellaneous
            generator.addProvider(true, new STDataPackRegistriesProvider(output, registries));
            generator.addProvider(true, new AdvancementProvider(output, registries, fileHelper, List.of(new Stadvancements())));
            generator.addProvider(true, new STRecipeProvider(output, registries));
            generator.addProvider(true, new STLootTableProvider(output, registries));
            generator.addProvider(true, new STDataMapsProvider(output, registries));

            // Tags
            STBlockTagsProvider blockTags = new STBlockTagsProvider(output, registries, fileHelper);
            generator.addProvider(true, blockTags);
            generator.addProvider(true, new STItemTagsProvider(output, registries, blockTags.contentsGetter(), fileHelper));
        }
    }

    @SubscribeEvent
    public static void registerBuiltInDataPacks(AddPackFindersEvent event) {
        event.addPackFinders(
                Stancements.stancements("datapacks/" + Stancements.RMS_ID),
                PackType.SERVER_DATA,
                Component.translatable("pack.stancements." + Stancements.RMS_ID, Component.translatable("pack.stancements.prefix").withColor(Stancements.ACCENT_COLOR)),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP
        );
    }

    @SubscribeEvent
    public static void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(STDataMaps.POT_PLANTABLES);
        event.register(STDataMaps.RECORDED_DISC_STYLES);
    }

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(Stancements.NETWORK_VERSION);
        registrar.playToClient(RequestRecordingAttempt.TYPE, RequestRecordingAttempt.STREAM_CODEC, ClientPayloadHandler::requestRecordingAttempt);
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        UpdateRecordedDiscCommand.register(event.getDispatcher());
        ConvertDiscToJukeboxSongCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void registerCauldrons(RegisterCauldronFluidContentEvent event) {
        event.register(STBlocks.MILK_CAULDRON.get(), NeoForgeMod.MILK.get(), FluidType.BUCKET_VOLUME, BlockStateProperties.LEVEL_CAULDRON);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
            if (type.getBaseClass().isAssignableFrom(AbstractMinecart.class)) {
                event.registerEntity(STCapabilities.MINECART_TAGS, type, (entity, data) -> entity.getData(STAttachmentTypes.MINECART_TAGS.get()));
            }
        }
    }
}
