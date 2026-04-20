package melonystudios.stancements.event;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.blockentity.custom.DyedWaterCauldronBlockEntity;
import melonystudios.stancements.data.model.STModelProvider;
import melonystudios.stancements.network.c2s.ServerPayloadHandler;
import melonystudios.stancements.network.c2s.StartRecordingAttempt;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.List;

@EventBusSubscriber(modid = Stancements.MOD_ID, value = Dist.CLIENT)
public class STClientEvents {
    @SubscribeEvent
    public static void registerBuiltInResourcePacks(AddPackFindersEvent event) {
        event.addPackFinders(
                Stancements.stancements("resourcepacks/" + Stancements.RMS_ID),
                PackType.CLIENT_RESOURCES,
                Component.translatable("pack.stancements." + Stancements.RMS_ID, Component.translatable("pack.stancements.prefix").withColor(Stancements.ACCENT_COLOR)),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP
        );
    }

    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.BlockTintSources event) {
        event.register(List.of(DyedWaterCauldronBlockEntity.dyedWaterCauldron()), STBlocks.DYED_WATER_CAULDRON.get());
    }

    /*
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> tintIndex == 0 ? -1 : DyedItemColor.getOrDefault(stack, RecordedDiscItem.DEFAULT_DISC_COLOR), STItems.RECORDED_DISC.get());
        event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : DyedWaterBucketItem.getColor(stack), STItems.DYED_WATER_BUCKET.get());
    }

    @SubscribeEvent
    public static void addComponentTooltips(AddComponentTooltipsEvent event) {
        event.addComponent(0.1, STDataComponents.MINECART_TAG_COLOR.get());
    }
    */

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(Stancements.NETWORK_VERSION);
        registrar.playToServer(StartRecordingAttempt.TYPE, StartRecordingAttempt.STREAM_CODEC, ServerPayloadHandler::startRecordingAttempt);
    }
}
