package melonystudios.stancements.command;

import com.mojang.brigadier.CommandDispatcher;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.MusicData;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.misc.recording.Track;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ConvertDiscToJukeboxSongCommand {
    public static final String IDENTIFIER = "gameplay/convert_disc_to_jukebox_song";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mstudios")
                .then(Commands.literal(IDENTIFIER)
                        .executes(context -> convertDiscToJukeboxSong(
                                context.getSource(),
                                context.getSource().getPlayer(),
                                98 // weapon.mainhand
                        ))
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> convertDiscToJukeboxSong(
                                        context.getSource(),
                                        EntityArgument.getPlayer(context, "player"),
                                        98 // weapon.mainhand
                                ))
                                .then(Commands.argument("slot", SlotArgument.slot())
                                        .executes(context -> convertDiscToJukeboxSong(
                                                context.getSource(),
                                                EntityArgument.getPlayer(context, "player"),
                                                SlotArgument.getSlot(context, "slot")
                                        ))))));
    }

    private static int convertDiscToJukeboxSong(CommandSourceStack source, @Nullable ServerPlayer player, int slotID) {
        if (player == null) {
            source.sendFailure(Component.translatable("commands.mstudios.convert_disc_to_jukebox_song.no_player"));
            return 0;
        }

        ItemStack slotStack = player.getSlot(slotID).get();
        MusicData data = slotStack.get(STDataComponents.MUSIC_DATA);
        MutableComponent stackDisplay = slotStack.getDisplayName().copy();
        if (slotStack.isEmpty() || data == null || data.id().isEmpty()) {
            source.sendFailure(Component.translatable("commands.mstudios.convert_disc_to_jukebox_song.fail", stackDisplay.withStyle(ChatFormatting.RED)));
            return 0;
        }

        boolean converted = RecordedDiscItem.setJukeboxSong(source.getLevel(), slotStack, new Track(data.id().get(), false), false, true);
        if (converted) {
            if (data.copied()) {
                slotStack.set(STDataComponents.MUSIC_DATA, new MusicData(Optional.empty(), true));
            } else {
                slotStack.remove(STDataComponents.MUSIC_DATA);
            }
            source.sendSuccess(() -> Component.translatable("commands.mstudios.convert_disc_to_jukebox_song.success", stackDisplay), true);
            return 1;
        } else {
            source.sendFailure(Component.translatable("commands.mstudios.convert_disc_to_jukebox_song.fail", stackDisplay.withStyle(ChatFormatting.RED)));
            return 0;
        }
    }
}
