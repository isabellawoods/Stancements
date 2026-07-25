package melonystudios.stancements.client.event;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.blockentity.custom.DyedWaterCauldronBlockEntity;
import melonystudios.stancements.client.STClient;
import melonystudios.stancements.client.item.RecordedDiscClientExtension;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.network.ServerPayloadHandler;
import melonystudios.stancements.network.StartRecordingAttempt;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
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
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new RecordedDiscClientExtension(), STItems.RECORDED_DISC);
    }

    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.BlockTintSources event) {
        event.register(List.of(DyedWaterCauldronBlockEntity.dyedWaterCauldron()), STBlocks.DYED_WATER_CAULDRON.get());
    }

    @SubscribeEvent
    public static void stopAmbientMusicWhilePlayingDisc(SelectMusicEvent event) {
        if (STClient.isMusicDiscPlaying()) event.setMusic(null);
    }

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(Stancements.NETWORK_VERSION);
        registrar.playToServer(StartRecordingAttempt.TYPE, StartRecordingAttempt.STREAM_CODEC, ServerPayloadHandler::startRecordingAttempt);
    }
}
