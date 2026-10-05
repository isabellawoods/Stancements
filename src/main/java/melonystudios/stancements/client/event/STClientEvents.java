package melonystudios.stancements.client.event;

import com.mojang.datafixers.util.Either;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.blockentity.custom.DyedWaterCauldronBlockEntity;
import melonystudios.stancements.client.STClient;
import melonystudios.stancements.client.item.ClientTrackStorageTooltip;
import melonystudios.stancements.client.item.RecordedDiscClientExtension;
import melonystudios.stancements.client.item.properties.InventoryRecorderState;
import melonystudios.stancements.client.item.properties.StorageInserted;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.InventoryRecorder;
import melonystudios.stancements.component.custom.TrackStorage;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.network.SendClientTrack;
import melonystudios.stancements.network.ServerPayloadHandler;
import melonystudios.stancements.network.StartRecordingAttempt;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.List;

@EventBusSubscriber(modid = Stancements.MOD_ID, value = Dist.CLIENT)
public class STClientEvents {
    @SubscribeEvent
    public static void registerSelectModelProperties(RegisterSelectItemModelPropertyEvent event) {
        event.register(Stancements.stancements("inventory_recorder/state"), InventoryRecorderState.TYPE);
    }

    @SubscribeEvent
    public static void registerConditionalModelProperties(RegisterConditionalItemModelPropertyEvent event) {
        event.register(Stancements.stancements("inventory_recorder/storage_inserted"), StorageInserted.CODEC);
    }

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
        event.registerItem(new RecordedDiscClientExtension(), STItems.RECORDED_DISC, STItems.SCULK_INFESTED_RECORDED_DISC);
    }

    @SubscribeEvent
    public static void registerClientTooltips(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(TrackStorage.class, ClientTrackStorageTooltip::new);
    }

    @SubscribeEvent
    public static void gatherClientTooltips(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();
        if (stack.has(STDataComponents.TRACK_STORAGE)) {
            event.getTooltipElements().add(1, Either.right(stack.get(STDataComponents.TRACK_STORAGE)));
        } else if (stack.has(STDataComponents.INVENTORY_RECORDER)) {
            InventoryRecorder recorder = stack.getOrDefault(STDataComponents.INVENTORY_RECORDER, InventoryRecorder.EMPTY);
            if (recorder.item().isPresent()) event.getTooltipElements().add(1, Either.right(recorder.item().get().get(STDataComponents.TRACK_STORAGE)));
        }
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
        registrar.playToServer(StartRecordingAttempt.TYPE, StartRecordingAttempt.STREAM_CODEC, (payload, context) -> context.enqueueWork(
                () -> ServerPayloadHandler.startRecordingAttempt(payload, context)
        ).exceptionally(throwable -> logExceptionMessage(context.player(), throwable)));

        registrar.playToServer(SendClientTrack.TYPE, SendClientTrack.STREAM_CODEC, (payload, context) -> context.enqueueWork(
                () -> ServerPayloadHandler.receiveClientTrack(payload, context)
        ).exceptionally(throwable -> logExceptionMessage(context.player(), throwable)));
    }

    private static Void logExceptionMessage(Player player, Throwable throwable) {
        Stancements.LOGGER.error("Exception while processing music data send by {}'s client", player.getDisplayName().getString(), throwable);
        return null;
    }
}
