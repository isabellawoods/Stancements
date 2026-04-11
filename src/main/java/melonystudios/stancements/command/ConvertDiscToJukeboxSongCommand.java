package melonystudios.stancements.command;

import com.mojang.brigadier.CommandDispatcher;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.MusicData;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class ConvertDiscToJukeboxSongCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("melonystudios")
                .then(Commands.literal(Stancements.stancements("gameplay/convert_disc_to_jukebox_song").toString())
                        .executes(context -> convertDiscToJukeboxSong(context.getSource()))));
    }

    private static int convertDiscToJukeboxSong(CommandSourceStack source) {
        Entity entity = source.getEntity();
        if (entity instanceof LivingEntity livEntity) {
            ItemStack handStack = livEntity.getItemBySlot(EquipmentSlot.MAINHAND);
            MusicData data = handStack.get(STDataComponents.MUSIC_DATA);
            MutableComponent stackDisplay = handStack.getDisplayName().copy();
            if (handStack.isEmpty() || data == null || data.id().isEmpty()) {
                source.sendFailure(Component.translatable("commands.stancements.convert_to_js.fail", stackDisplay.withStyle(ChatFormatting.RED)));
                return 0;
            }

            boolean converted = RecordedDiscItem.setJukeboxSong(handStack, source.getLevel(), data.id().get(), false);
            if (converted) {
                if (data.copied()) {
                    handStack.set(STDataComponents.MUSIC_DATA, new MusicData(Optional.empty(), true));
                } else {
                    handStack.remove(STDataComponents.MUSIC_DATA);
                }
                source.sendSuccess(() -> Component.translatable("commands.stancements.convert_to_js.success", stackDisplay), true);
                return 1;
            } else {
                source.sendFailure(Component.translatable("commands.stancements.convert_to_js.fail", stackDisplay.withStyle(ChatFormatting.RED)));
                return 0;
            }
        }

        return 0;
    }
}
