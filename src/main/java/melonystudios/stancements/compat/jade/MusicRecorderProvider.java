package melonystudios.stancements.compat.jade;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IDisplayHelper;

import java.util.Optional;

public class MusicRecorderProvider implements StreamServerDataProvider<BlockAccessor, MusicRecorderProvider.RecorderData> {
    public static final MusicRecorderProvider INSTANCE = new MusicRecorderProvider();
    public static final Identifier ID = Stancements.stancements("music_recorder");

    @Override
    @Nullable
    public RecorderData streamData(BlockAccessor accessor) {
        MusicRecorderBlockEntity recorder = (MusicRecorderBlockEntity) accessor.getBlockEntity();
        return new RecorderData(
                Optional.ofNullable(recorder.musicID()),
                recorder.copyingSong(),
                !recorder.getTheItem().has(STDataComponents.RECORDING_TURNS_INTO) && !recorder.getTheItem().isEmpty(),
                recorder.ticksUntilFinishedRecording()
        );
    }

    @Override
    @NotNull
    public StreamCodec<RegistryFriendlyByteBuf, RecorderData> streamCodec() {
        return RecorderData.STREAM_CODEC;
    }

    @Override
    @NotNull
    public Identifier getUid() {
        return ID;
    }

    public static class Client implements IBlockComponentProvider {
        public static final Client INSTANCE = new Client();

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
            Optional<RecorderData> data = MusicRecorderProvider.INSTANCE.decodeFromData(accessor);
            if (data.isEmpty()) return;

            if (data.get().hasRecorded) {
                tooltip.add(Component.translatable("tooltip.stancements.finished_recording").withColor(Stancements.ACCENT_COLOR));
            } else if (data.get().ticksUntilFinishedRecording() <= -1) {
                tooltip.add(Component.translatable("tooltip.stancements.no_music_playing"));
            } else {
                var jukeboxSongs = accessor.getLevel().registryAccess().lookup(Registries.JUKEBOX_SONG);
                if (jukeboxSongs.isEmpty() || data.get().musicID.isEmpty()) return;

                var song = jukeboxSongs.get().get(data.get().copyingSong ? data.get().musicID.get() : RecordedDiscItem.getJukeboxSongLocation(data.get().musicID.get()));
                song.ifPresent(jukeboxSongReference -> tooltip.add(Component.translatable("tooltip.stancements.recording",
                        IDisplayHelper.get().stripColor(jukeboxSongReference.value().description()),
                        StringUtil.formatTickDuration(data.get().ticksUntilFinishedRecording(), accessor.tickRate())
                )));
            }
        }

        @Override
        @NotNull
        public Identifier getUid() {
            return ID;
        }
    }

    public record RecorderData(Optional<Identifier> musicID, boolean copyingSong, boolean hasRecorded, int ticksUntilFinishedRecording) {
        public static final StreamCodec<RegistryFriendlyByteBuf, RecorderData> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.optional(Identifier.STREAM_CODEC), RecorderData::musicID,
                ByteBufCodecs.BOOL, RecorderData::copyingSong,
                ByteBufCodecs.BOOL, RecorderData::hasRecorded,
                ByteBufCodecs.INT, RecorderData::ticksUntilFinishedRecording,
                RecorderData::new
        );
    }
}
