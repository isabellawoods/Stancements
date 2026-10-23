package melonystudios.stancements.client.command;

import com.mojang.brigadier.CommandDispatcher;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.client.screen.CommandHelpScreen;
import melonystudios.stancements.command.ApplyDiscStyleCommand;
import melonystudios.stancements.command.ConvertDiscToJukeboxSongCommand;
import melonystudios.stancements.command.RunVinylModifiersCommand;
import melonystudios.stancements.command.UpdateRecordedDiscCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class MelonyStudiosHelpCommands {
    public static final String IDENTIFIER = "help";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mstudios")
                .then(Commands.literal(IDENTIFIER)
                        .then(Commands.literal(ConvertDiscToJukeboxSongCommand.IDENTIFIER)
                                .executes(context -> openCommandHelpScreen(context.getSource(), Stancements.MOD_ID, ConvertDiscToJukeboxSongCommand.IDENTIFIER)))
                        .then(Commands.literal(UpdateRecordedDiscCommand.IDENTIFIER)
                                .executes(context -> openCommandHelpScreen(context.getSource(), Stancements.MOD_ID, UpdateRecordedDiscCommand.IDENTIFIER)))
                        .then(Commands.literal(ApplyDiscStyleCommand.IDENTIFIER)
                                .executes(context -> openCommandHelpScreen(context.getSource(), Stancements.MOD_ID, ApplyDiscStyleCommand.IDENTIFIER)))
                        .then(Commands.literal(SendTrackToServerCommand.IDENTIFIER)
                                .executes(context -> openCommandHelpScreen(context.getSource(), Stancements.MOD_ID, SendTrackToServerCommand.IDENTIFIER)))
                        .then(Commands.literal(SendCurrentTrackToServerCommand.IDENTIFIER)
                                .executes(context -> openCommandHelpScreen(context.getSource(), Stancements.MOD_ID, SendCurrentTrackToServerCommand.IDENTIFIER)))
                        .then(Commands.literal(RunVinylModifiersCommand.IDENTIFIER)
                                .executes(context -> openCommandHelpScreen(context.getSource(), Stancements.MOD_ID, RunVinylModifiersCommand.IDENTIFIER)))));
    }

    private static int openCommandHelpScreen(CommandSourceStack source, String modID, String commandID) {
        source.sendSuccess(() -> Component.translatable(
                "commands.mstudios.help.success",
                Component.literal(commandID).withStyle(style -> style.withBold(true)),
                Component.literal(modID).withStyle(style -> style.withBold(true))
        ), true);
        Minecraft.getInstance().setScreen(new CommandHelpScreen(modID, commandID));
        return 1;
    }
}
