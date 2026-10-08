package melonystudios.stancements.client.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import melonystudios.stancements.component.custom.InventoryRecorder;
import melonystudios.stancements.item.STItems;
import melonystudios.stancements.misc.recording.Track;
import melonystudios.stancements.mixin.recorder.CurrentMusicAccessor;
import melonystudios.stancements.network.StartRecordingAttempt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Optional;

import static melonystudios.stancements.client.command.SendTrackToServerCommand.sendMusicTrack;

public class SendCurrentTrackToServerCommand {
    public static final String IDENTIFIER = "develop/send_current_track_to_server";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mstudios")
                .then(Commands.literal(IDENTIFIER).requires(stack -> stack.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .executes(context -> sendCurrentTrackToServer(
                                context.getSource(),
                                -1
                        ))
                        .then(Commands.argument("slot", SlotArgument.slot())
                                .executes(context -> sendCurrentTrackToServer(
                                        context.getSource(),
                                        SlotArgument.getSlot(context, "slot")
                                )))
                        .then(Commands.argument("recorder_pos", BlockPosArgument.blockPos())
                                .executes(context -> sendCurrentTrackToBlock(
                                        context.getSource(),
                                        BlockPosArgument.getBlockPos(context, "recorder_pos")
                                )))));
    }

    private static int sendCurrentTrackToServer(CommandSourceStack source, int slotIndex) {
        Entity entity = source.getEntity();
        if (!(entity instanceof LocalPlayer player)) {
            source.sendFailure(Component.translatable("commands.mstudios.send_current_track_to_server.no_player"));
            return 0;
        }

        var music = ((CurrentMusicAccessor) Minecraft.getInstance().getMusicManager()).stancements$getCurrentMusic();
        // noinspection ConstantValue
        if (music == null || music.getSound() == null) {
            source.sendFailure(Component.translatable("commands.mstudios.send_current_track_to_server.no_music_playing"));
            return 0;
        }
        Track track = new Track(music.getSound().getLocation(), false);

        if (slotIndex > -1) {
            if (InventoryRecorder.canRecord(player.getSlot(slotIndex).get(), track)) {
                return sendMusicTrack(source, track, (short) slotIndex, "send_current_track_to_server");
            } else {
                source.sendFailure(Component.translatable("commands.mstudios.send_current_track_to_server.fail.in_slot", player.getDisplayName()));
                return 0;
            }
        }

        for (int i = 0; i < player.getInventory().offhand.size(); i++) {
            if (InventoryRecorder.canRecord(player.getInventory().offhand.get(i), track)) {
                return sendMusicTrack(source, track, (short) (i + 150), "send_current_track_to_server");
            }
        }

        for (int i = 0; i < player.getInventory().items.size(); i++) {
            if (InventoryRecorder.canRecord(player.getInventory().items.get(i), track)) {
                return sendMusicTrack(source, track, (short) i, "send_current_track_to_server");
            }
        }

        for (int i = 0; i < player.getInventory().armor.size(); i++) {
            if (InventoryRecorder.canRecord(player.getInventory().armor.get(i), track)) {
                return sendMusicTrack(source, track, (short) (i + 100), "send_current_track_to_server");
            }
        }

        source.sendFailure(Component.translatable("commands.mstudios.send_current_track_to_server.fail.in_inventory", player.getDisplayName()));
        return 0;
    }

    private static int sendCurrentTrackToBlock(CommandSourceStack source, BlockPos recorderPos) throws CommandSyntaxException {
        BlockEntity blockEntity = source.getUnsidedLevel().getBlockEntity(recorderPos);
        if (!source.getUnsidedLevel().hasChunkAt(recorderPos)) {
            throw BlockPosArgument.ERROR_NOT_LOADED.create();
        } else if (!source.getUnsidedLevel().isInWorldBounds(recorderPos)) {
            throw BlockPosArgument.ERROR_OUT_OF_WORLD.create();
        }

        if (!(blockEntity instanceof MusicRecorderBlockEntity)) {
            source.sendFailure(Component.translatable("commands.mstudios.send_current_track_to_server.fail.recorder", recorderPos.getX(), recorderPos.getY(), recorderPos.getZ()));
            return 0;
        }

        var music = ((CurrentMusicAccessor) Minecraft.getInstance().getMusicManager()).stancements$getCurrentMusic();
        // noinspection ConstantValue
        if (music == null || music.getSound() == null) {
            source.sendFailure(Component.translatable("commands.mstudios.send_current_track_to_server.no_music_playing"));
            return 0;
        }

        Track track = new Track(music.getSound().getLocation(), false);
        var options = Minecraft.getInstance().options;
        var volumes = new StartRecordingAttempt.MusicVolumes(options.getSoundSourceVolume(SoundSource.MASTER) != 0.0, options.getSoundSourceVolume(SoundSource.MUSIC) != 0.0, options.getSoundSourceVolume(SoundSource.RECORDS) != 0.0);
        PacketDistributor.sendToServer(new StartRecordingAttempt(recorderPos, STItems.VINYL_DISC.toStack(), Optional.of(track), volumes));

        source.sendSuccess(() -> Component.translatable(
                "commands.mstudios.send_current_track_to_server.success.recorder",
                Component.translatable(track.identifier().toString()).withStyle(style -> style.withBold(true)),
                recorderPos.getX(),
                recorderPos.getY(),
                recorderPos.getZ()
        ), true);
        return 1;
    }
}
