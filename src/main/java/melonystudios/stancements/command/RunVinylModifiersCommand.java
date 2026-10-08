package melonystudios.stancements.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import melonystudios.stancements.command.argument.StringRepresentableEnumArgument;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.ModificationStrategy;
import melonystudios.stancements.misc.modifier.VinylModifier;
import melonystudios.stancements.misc.recording.Track;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.SlotAccess;
import org.jetbrains.annotations.Nullable;

public class RunVinylModifiersCommand {
    public static final String IDENTIFIER = "develop/run_vinyl_modifiers";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mstudios")
                .then(Commands.literal(IDENTIFIER).requires(stack -> stack.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .then(Commands.argument("identifier", ResourceLocationArgument.id())
                                .then(Commands.argument("resolved", BoolArgumentType.bool())
                                        .then(Commands.argument("copying", BoolArgumentType.bool())
                                                .then(Commands.argument("strategy", StringRepresentableEnumArgument.representable(ModificationStrategy.class))
                                                        .executes(context -> runVinylModifiers(
                                                                context.getSource(),
                                                                new Track(ResourceLocationArgument.getId(context, "identifier"), BoolArgumentType.getBool(context, "resolved")),
                                                                BoolArgumentType.getBool(context, "copying"),
                                                                context.getArgument("strategy", ModificationStrategy.class),
                                                                context.getSource().getPlayer(),
                                                                98 // weapon.mainhand
                                                        ))
                                                        .then(Commands.argument("player", EntityArgument.player())
                                                                .executes(context -> runVinylModifiers(
                                                                        context.getSource(),
                                                                        new Track(ResourceLocationArgument.getId(context, "identifier"), BoolArgumentType.getBool(context, "resolved")),
                                                                        BoolArgumentType.getBool(context, "copying"),
                                                                        context.getArgument("strategy", ModificationStrategy.class),
                                                                        EntityArgument.getPlayer(context, "player"),
                                                                        98 // weapon.mainhand
                                                                ))
                                                                .then(Commands.argument("slot", SlotArgument.slot())
                                                                        .executes(context -> runVinylModifiers(
                                                                                context.getSource(),
                                                                                new Track(ResourceLocationArgument.getId(context, "identifier"), BoolArgumentType.getBool(context, "resolved")),
                                                                                BoolArgumentType.getBool(context, "copying"),
                                                                                context.getArgument("strategy", ModificationStrategy.class),
                                                                                EntityArgument.getPlayer(context, "player"),
                                                                                SlotArgument.getSlot(context, "slot")
                                                                        ))))))))));
    }

    private static int runVinylModifiers(CommandSourceStack source, Track track, boolean copying, ModificationStrategy strategy, @Nullable ServerPlayer player, int slotID) {
        if (player == null) {
            source.sendFailure(Component.translatable("commands.mstudios.run_vinyl_modifiers.no_player"));
            return 0;
        }

        SlotAccess slot = player.getSlot(slotID);
        MutableComponent stackDisplayName = slot.get().getDisplayName().copy();
        ModificationContext context = new ModificationContext(
                source.getLevel(),
                source.getPosition(),
                slot.get(),
                track,
                copying,
                ticks -> {}
        );
        var result = VinylModifier.recordingPipeline(context, strategy);

        if (slot.set(result.stack())) {
            source.sendSuccess(() -> Component.translatable(
                    "commands.mstudios.run_vinyl_modifiers.success",
                    Component.literal(track.identifier().toString()).withStyle(style -> style.withBold(true))
            ), true);

            if (!result.recordingText().getString().isBlank()) {
                source.sendSuccess(() -> Component.translatable("commands.mstudios.run_vinyl_modifiers.message", result.recordingText()), true);
            }
            return 1;
        } else {
            source.sendFailure(Component.translatable("commands.mstudios.run_vinyl_modifiers.fail", stackDisplayName.withStyle(ChatFormatting.RED)));
            return 0;
        }
    }
}
