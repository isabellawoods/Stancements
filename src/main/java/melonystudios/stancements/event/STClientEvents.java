package melonystudios.stancements.event;

import melonystudios.reutilities.event.custom.AddComponentTooltipsEvent;
import melonystudios.stancements.STClient;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.blockentity.custom.DyedWaterCauldronBlockEntity;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.item.custom.DyedWaterBucketItem;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.item.extension.RecordedDiscClientExtension;
import melonystudios.stancements.network.c2s.StartRecordingAttempt;
import melonystudios.stancements.network.c2s.ServerPayloadHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import static melonystudios.stancements.item.custom.DyedWaterBucketItem.DEFAULT_WATER_COLOR;

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
        event.registerItem(new RecordedDiscClientExtension(), STItems.RECORDED_DISC, STItems.SCULK_INFESTED_RECORDED_DISC);
    }

    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            if (level == null || pos == null) return DEFAULT_WATER_COLOR;
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof DyedWaterCauldronBlockEntity cauldron) {
                return cauldron.getWaterColor();
            } else {
                return 0x5DB7EF; // Cherry grove water color (for testing)
            }
        }, STBlocks.DYED_WATER_CAULDRON.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> tintIndex == 0 ? -1 : DyedItemColor.getOrDefault(stack, RecordedDiscItem.DEFAULT_DISC_COLOR), STItems.RECORDED_DISC.get());
        event.register((stack, tintIndex) -> tintIndex <= 1 ? -1 : DyedItemColor.getOrDefault(stack, RecordedDiscItem.DEFAULT_DISC_COLOR), STItems.SCULK_INFESTED_RECORDED_DISC.get());
        event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : DyedWaterBucketItem.getColor(stack), STItems.DYED_WATER_BUCKET.get());
    }

    @SubscribeEvent
    public static void addComponentTooltips(AddComponentTooltipsEvent event) {
        event.addComponent(0.1, STDataComponents.MINECART_TAG_COLOR.get());
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
