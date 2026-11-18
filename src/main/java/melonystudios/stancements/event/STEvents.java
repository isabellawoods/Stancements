package melonystudios.stancements.event;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.block.custom.GildedRailBlock;
import melonystudios.stancements.block.custom.MusicRecorderBlock;
import melonystudios.stancements.block.custom.MusicRemixerBlock;
import melonystudios.stancements.block.custom.TaggingRailBlock;
import melonystudios.stancements.client.network.ClientPayloadHandler;
import melonystudios.stancements.client.network.RequestRecordingAttempt;
import melonystudios.stancements.command.ApplyDiscStyleCommand;
import melonystudios.stancements.command.ConvertDiscToJukeboxSongCommand;
import melonystudios.stancements.command.RunVinylModifiersCommand;
import melonystudios.stancements.command.UpdateRecordedDiscCommand;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.data.loot.STLootModifierProvider;
import melonystudios.stancements.data.loot.STLootTableProvider;
import melonystudios.stancements.data.misc.STDataMapsProvider;
import melonystudios.stancements.data.misc.STDataPackRegistriesProvider;
import melonystudios.stancements.data.misc.STRecipeProvider;
import melonystudios.stancements.data.misc.Stadvancements;
import melonystudios.stancements.data.model.STModelProvider;
import melonystudios.stancements.data.tag.STBlockTagsProvider;
import melonystudios.stancements.data.tag.STItemTagsProvider;
import melonystudios.stancements.data.tag.STJukeboxSongTagsProvider;
import melonystudios.stancements.data.tag.STVinylModifierTagsProvider;
import melonystudios.stancements.misc.STRegistries;
import melonystudios.stancements.misc.album.Album;
import melonystudios.stancements.misc.attachment.STAttachmentTypes;
import melonystudios.stancements.misc.attachment.STCapabilities;
import melonystudios.stancements.misc.discstyle.RecordedDiscStyle;
import melonystudios.stancements.misc.modifier.VinylModifier;
import melonystudios.stancements.option.STCommonOptions;
import melonystudios.stancements.util.ReAPI;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.tooltip.TooltipLocation;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.RegisterTooltipAppendersEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.RegisterCauldronFluidContentEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Stancements.MOD_ID)
public class STEvents {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createDatapackRegistryObjects(STDataPackRegistriesProvider.BUILDER);
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();
        PackOutput output = generator.getPackOutput();

        // Models
        generator.addProvider(true, new STModelProvider(output));

        // Miscellaneous
        generator.addProvider(true, new STDataPackRegistriesProvider(output, registries));
        generator.addProvider(true, new AdvancementProvider(output, registries, List.of(new Stadvancements())));
        generator.addProvider(true, new STRecipeProvider.Runner(output, registries));
        generator.addProvider(true, new STLootTableProvider(output, registries));
        generator.addProvider(true, new STLootModifierProvider(output, registries));
        generator.addProvider(true, new STDataMapsProvider(output, registries));

        // Tags
        STBlockTagsProvider blockTags = new STBlockTagsProvider(output, registries);
        generator.addProvider(true, blockTags);
        generator.addProvider(true, new STItemTagsProvider(output, registries));
        generator.addProvider(true, new STJukeboxSongTagsProvider(output, registries));
        generator.addProvider(true, new STVinylModifierTagsProvider(output, registries));
    }

    @SubscribeEvent
    public static void registerBuiltInDataPacks(AddPackFindersEvent event) {
        event.addPackFinders(
                Stancements.stancements("datapacks/" + Stancements.RMS_ID),
                PackType.SERVER_DATA,
                Component.translatable(Util.makeDescriptionId("pack", Stancements.stancements(Stancements.RMS_ID)), Component.translatable("pack.stancements.prefix").withColor(Stancements.ACCENT_COLOR)),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP
        );
    }

    @SubscribeEvent
    public static void addBuiltInRegistries(NewRegistryEvent event) {
        event.register(STRegistries.VINYL_MODIFIER_COMPONENT_TYPE);
    }

    @SubscribeEvent
    public static void addDataDrivenRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(STRegistries.RECORDED_DISC_STYLE, RecordedDiscStyle.CODEC, RecordedDiscStyle.CODEC);
        event.dataPackRegistry(STRegistries.VINYL_MODIFIER, VinylModifier.DIRECT_CODEC, VinylModifier.DIRECT_CODEC);
        event.dataPackRegistry(STRegistries.ALBUM, Album.DIRECT_CODEC, Album.DIRECT_CODEC);
    }

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(Stancements.NETWORK_VERSION);
        registrar.playToClient(RequestRecordingAttempt.TYPE, RequestRecordingAttempt.STREAM_CODEC, (payload, context) -> context.enqueueWork(() ->
                ClientPayloadHandler.requestRecordingAttempt(payload, context)
        ).exceptionally(throwable -> {
            Stancements.LOGGER.error("Exception while sending {}'s music data to the server", context.player().getDisplayName().getString(), throwable);
            return null;
        }));
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        UpdateRecordedDiscCommand.register(event.getDispatcher());
        ConvertDiscToJukeboxSongCommand.register(event.getDispatcher());
        ApplyDiscStyleCommand.register(event.getDispatcher(), event.getBuildContext());
        RunVinylModifiersCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void registerTooltipAppenders(RegisterTooltipAppendersEvent event) {
        // Music recorder
        event.registerAppender(TooltipLocation.HEAD, (stack, _, _, _, _, tooltip) -> {
            if (stack.getItem() instanceof BlockItem item && item.getBlock() instanceof MusicRecorderBlock && ReAPI.shouldDisplay(stack, Stancements.stancements("music_recorder/tooltip"))) {
                tooltip.accept(Component.translatable("tooltip.stancements.music_recorder").withStyle(ChatFormatting.GRAY));
            }
        });

        // Music remixers
        event.registerAppender(TooltipLocation.HEAD, (stack, _, _, _, _, tooltip) -> {
            if (!(stack.getItem() instanceof BlockItem item && item.getBlock() instanceof MusicRemixerBlock remixer)) return;
            BlockItemStateProperties blockState = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
            Boolean open = blockState.get(BlockStateProperties.OPEN);

            if (ReAPI.shouldDisplay(stack, Stancements.stancements("music_remixer/tooltip"))) {
                tooltip.accept(Component.translatable("tooltip.stancements.music_remixer").withStyle(ChatFormatting.GRAY));
            }

            if (ReAPI.shouldDisplay(stack, Stancements.stancements("music_remixer/casing")) && (open == null || !open)) {
                tooltip.accept(Component.translatable("tooltip.stancements.music_remixer.casing", ComponentUtils.mergeStyles(
                        remixer.casing().value().getName(),
                        Style.EMPTY.withColor(ChatFormatting.GRAY))
                ).withStyle(ChatFormatting.DARK_GRAY));
            }
        });

        // Rails
        event.registerAppender(TooltipLocation.HEAD, (stack, _, _, _, _, tooltip) -> {
            if (stack.getItem() instanceof BlockItem item && item.getBlock() instanceof GildedRailBlock && ReAPI.shouldDisplay(stack, Stancements.stancements("gilded_rail/tooltip"))) {
                float speedMultiplier = STCommonOptions.GILDED_RAIL_SPEED_MULTIPLIER.get().floatValue();
                tooltip.accept(Component.translatable("tooltip.stancements.gilded_rail",
                        GildedRailBlock.FORMAT.format((speedMultiplier * speedMultiplier) * 100 - 100)
                ).withStyle(ChatFormatting.GRAY));
            }
        });
        event.registerAppender(TooltipLocation.HEAD, (stack, _, _, _, _, tooltip) -> {
            if (!(stack.getItem() instanceof BlockItem item && item.getBlock() instanceof TaggingRailBlock taggingRail)) return;

            if (ReAPI.shouldDisplay(stack, Stancements.stancements("tagging_rail/tooltip")) && !taggingRail.detectsColors().isEmpty()) {
                MutableComponent colors = TaggingRailBlock.prettyPrintTagColors(taggingRail.matcherType(), taggingRail.detectsColors(), true);
                tooltip.accept(Component.translatable("tooltip.stancements.tagging_rail." + (taggingRail.detectsColors().size() == 1 ? "single" : "multiple"), colors)
                        .withStyle(ChatFormatting.GRAY));

                tooltip.accept(Component.translatable("tooltip.stancements.tagging_rail.tagless").withStyle(ChatFormatting.GRAY));
            }
        });

        // Minecart tags
        event.registerComponentAppenderAfterAll(STDataComponents.MINECART_TAG_COLOR, (stack, context, display, _, flag, tooltip) -> {
            if (display.shows(STDataComponents.MINECART_TAG_COLOR.get()) && stack.has(STDataComponents.MINECART_TAG_COLOR)) {
                stack.get(STDataComponents.MINECART_TAG_COLOR).addToTooltip(context, tooltip, flag, stack.getComponents());
            }
        });

        // Inventory recorders
        event.registerComponentAppenderAfterAll(STDataComponents.INVENTORY_RECORDER, (stack, context, display, _, flag, tooltip) -> {
            if (display.shows(STDataComponents.INVENTORY_RECORDER.get()) && stack.has(STDataComponents.INVENTORY_RECORDER)) {
                stack.get(STDataComponents.INVENTORY_RECORDER).addToTooltip(context, tooltip, flag, stack.getComponents());
            }
        });
    }

    @SubscribeEvent
    public static void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        event.modifyMatching((item, _) -> item instanceof BannerItem, (components, _, _) -> components.set(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD).build()));
    }

    @SubscribeEvent
    public static void registerCauldrons(RegisterCauldronFluidContentEvent event) {
        event.register(STBlocks.MILK_CAULDRON.get(), NeoForgeMod.MILK.get(), FluidType.BUCKET_VOLUME, BlockStateProperties.LEVEL_CAULDRON);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
            if (type.getBaseClass().isAssignableFrom(AbstractMinecart.class)) {
                event.registerEntity(STCapabilities.MINECART_TAGS, type, (entity, _) -> entity.getData(STAttachmentTypes.MINECART_TAGS.get()));
            }
        }
    }
}
