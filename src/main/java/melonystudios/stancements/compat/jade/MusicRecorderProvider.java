package melonystudios.stancements.compat.jade;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.blockentity.BlockBasedMusicPlayer;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import melonystudios.stancements.misc.recording.Track;
import melonystudios.stancements.tag.STItemTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
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
    public static final ResourceLocation ID = Stancements.stancements("music_recorder");

    @Override
    @Nullable
    public RecorderData streamData(BlockAccessor accessor) {
        MusicRecorderBlockEntity recorder = (MusicRecorderBlockEntity) accessor.getBlockEntity();
        return new RecorderData(
                Optional.ofNullable(recorder.musicID()),
                recorder.copyingSong(),
                !recorder.getTheItem().is(STItemTags.JADE_CONSIDERS_AS_RECORDING) && !recorder.getTheItem().isEmpty(),
                recorder.ticksUntilFinishedRecording()
        );
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, RecorderData> streamCodec() {
        return RecorderData.STREAM_CODEC;
    }

    @Override
    public ResourceLocation getUid() {
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
            } else if (data.get().ticksUntilFinishedRecording() <= BlockBasedMusicPlayer.DEFAULT_TICKS_UNTIL_FINISHED) {
                tooltip.add(Component.translatable("tooltip.stancements.no_music_playing"));
            } else {
                if (data.get().musicID().isEmpty()) return;
                Component songName = IDisplayHelper.get().stripColor(new Track(
                        data.get().musicID().get(),
                        data.get().copyingSong()
                ).displayName(accessor.getLevel().registryAccess().registryOrThrow(Registries.JUKEBOX_SONG)));

                // todo: not 100% accurate -- having a "music." as the start of the translation will also make this return the sound id
                if (songName.getString().startsWith("music.")) {
                    songName = Component.translatable("tooltip.stancements.sound_id", data.get().musicID().get());
                }

                tooltip.add(Component.translatable("tooltip.stancements.recording", songName,
                        StringUtil.formatTickDuration(data.get().ticksUntilFinishedRecording(), accessor.tickRate())
                ));
            }
        }

        @Override
        public ResourceLocation getUid() {
            return ID;
        }
    }

    public record RecorderData(Optional<ResourceLocation> musicID, boolean copyingSong, boolean hasRecorded, int ticksUntilFinishedRecording) {
        public static final StreamCodec<RegistryFriendlyByteBuf, RecorderData> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), RecorderData::musicID,
                ByteBufCodecs.BOOL, RecorderData::copyingSong,
                ByteBufCodecs.BOOL, RecorderData::hasRecorded,
                ByteBufCodecs.INT, RecorderData::ticksUntilFinishedRecording,
                RecorderData::new
        );
    }
}
