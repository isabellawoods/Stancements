package melonystudios.stancements.component;

import melonystudios.reutilities.api.ReCodecs;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.component.custom.*;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STDataComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Stancements.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> MUSIC_ID = COMPONENTS.registerComponentType("music_id",
            builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MusicData>> MUSIC_DATA = COMPONENTS.registerComponentType("music_data",
            builder -> builder.persistent(MusicData.CODEC).networkSynchronized(MusicData.STREAM_CODEC).cacheEncoding());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> LABEL = COMPONENTS.registerComponentType("label",
            builder -> builder.persistent(ReCodecs.floatRange(RecordedDiscItem.DISC_LABEL_MIN, RecordedDiscItem.DISC_LABEL_MAX)).networkSynchronized(ByteBufCodecs.FLOAT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MinecartTagColor>> MINECART_TAG_COLOR = COMPONENTS.registerComponentType("minecart_tag_color",
            builder -> builder.persistent(MinecartTagColor.CODEC).networkSynchronized(MinecartTagColor.STREAM_CODEC).cacheEncoding());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RecordingTurnsInto>> RECORDING_TURNS_INTO = COMPONENTS.registerComponentType("recording_turns_into",
            builder -> builder.persistent(RecordingTurnsInto.CODEC).networkSynchronized(RecordingTurnsInto.STREAM_CODEC).cacheEncoding());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<TrackStorage>> TRACK_STORAGE = COMPONENTS.registerComponentType("track_storage",
            builder -> builder.persistent(TrackStorage.CODEC).networkSynchronized(TrackStorage.STREAM_CODEC).cacheEncoding());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<InventoryRecorder>> INVENTORY_RECORDER = COMPONENTS.registerComponentType("inventory_recorder",
            builder -> builder.persistent(InventoryRecorder.CODEC).networkSynchronized(InventoryRecorder.STREAM_CODEC).cacheEncoding());
}
