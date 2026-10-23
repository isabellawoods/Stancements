package melonystudios.stancements.command;

import com.mojang.brigadier.CommandDispatcher;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.misc.STRegistries;
import melonystudios.stancements.misc.discstyle.RecordedDiscStyle;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;

public class ApplyDiscStyleCommand {
    public static final String IDENTIFIER = "develop/apply_disc_style";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal("mstudios")
                .then(Commands.literal(IDENTIFIER).requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .then(Commands.argument("disc_style", ResourceArgument.resource(buildContext, STRegistries.RECORDED_DISC_STYLE))
                                .executes(context -> applyDiscStyle(
                                        context.getSource(),
                                        ResourceArgument.getResource(context, "disc_style", STRegistries.RECORDED_DISC_STYLE),
                                        context.getSource().getPlayer(),
                                        98 // weapon.mainhand
                                ))
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> applyDiscStyle(
                                                context.getSource(),
                                                ResourceArgument.getResource(context, "disc_style", STRegistries.RECORDED_DISC_STYLE),
                                                EntityArgument.getPlayer(context, "player"),
                                                98 // weapon.mainhand
                                        ))
                                        .then(Commands.argument("slot", SlotArgument.slot())
                                                .executes(context -> applyDiscStyle(
                                                        context.getSource(),
                                                        ResourceArgument.getResource(context, "disc_style", STRegistries.RECORDED_DISC_STYLE),
                                                        EntityArgument.getPlayer(context, "player"),
                                                        SlotArgument.getSlot(context, "slot")
                                                )))))));
    }

    private static int applyDiscStyle(CommandSourceStack source, Holder.Reference<RecordedDiscStyle> styleHolder, @Nullable ServerPlayer player, int slotIndex) {
        if (player == null) {
            source.sendFailure(Component.translatable("commands.mstudios.apply_disc_style.no_player"));
            return 0;
        }

        ItemStack slotStack = player.getSlot(slotIndex).get();
        RecordedDiscStyle discStyle = styleHolder.value();

        if (slotStack.isEmpty()) {
            source.sendFailure(Component.translatable("commands.mstudios.apply_disc_style.empty_slot", slotStack.getDisplayName()));
            return 0;
        }

        // color
        if (discStyle.color().isPresent()) {
            slotStack.set(DataComponents.DYED_COLOR, new DyedItemColor(discStyle.color().get()));
            TooltipDisplay display = slotStack.get(DataComponents.TOOLTIP_DISPLAY);
            slotStack.set(DataComponents.TOOLTIP_DISPLAY, (display == null ? TooltipDisplay.DEFAULT : display).withHidden(DataComponents.DYED_COLOR, true));
        }

        // label
        if (discStyle.label() >= RecordedDiscItem.DISC_LABEL_MIN) {
            slotStack.set(STDataComponents.LABEL, discStyle.label());
        }

        // rarity
        slotStack.set(DataComponents.RARITY, discStyle.rarity());

        source.sendSuccess(() -> Component.translatable(
                "commands.mstudios.apply_disc_style.success",
                Component.literal(styleHolder.key().identifier().toString()).withStyle(style -> style.withBold(true)),
                slotStack.getDisplayName()
        ), true);
        return 1;
    }
}
