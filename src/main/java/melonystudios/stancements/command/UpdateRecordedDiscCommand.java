package melonystudios.stancements.command;

import com.google.common.collect.ImmutableMap;
import com.mojang.brigadier.CommandDispatcher;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.misc.recording.Track;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class UpdateRecordedDiscCommand {
    public static final Map<String, String> UPDATED_SONG_NAMES = new ImmutableMap.Builder<String, String>()
            .put("hal1", "subwoofer_lullaby").put("hal2", "living_mice").put("hal3", "haggstrom").put("hal4", "danny")
            .put("calm1", "minecraft").put("calm2", "clark").put("calm3", "sweden")
            .put("piano1", "dry_hands").put("piano2", "wet_hands").put("piano3", "mice_on_venus")
            .put("nuance1", "key").put("nuance2", "oxygene")
            .put("creative1", "biome_fest").put("creative2", "blind_spots").put("creative3", "haunt_muskie").put("creative4", "aria_math").put("creative5", "dreiton").put("creative6", "taswell")
            .put("nether1", "concrete_halls").put("nether2", "dead_voxel").put("nether3", "warmth").put("nether4", "ballad_of_the_cats")
            .put("end/end", "end/the_end")
            // The Mato music pack songs from 1.16
            .put("themato", "minecraft") // correct mod ID
            .put("caves_and_cliffs/", "")
            .put("wild_update/", "swamp/")
            .put("trails_and_tales/", "")
            .put("tricky_trials/", "")
            .put("chase_the_skies/", "")
            .put("drop_2_2025/", "") // existed at some point, don't know if any discs have/had this though
            .put("chaos_cubed/", "")
            .build();
    public static final String IDENTIFIER = "gameplay/update_recorded_disc";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mstudios")
                .then(Commands.literal(IDENTIFIER)
                        .executes(context -> updateRecordedDisc(
                                context.getSource(),
                                context.getSource().getPlayer(),
                                98 // weapon.mainhand
                        ))
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> updateRecordedDisc(
                                        context.getSource(),
                                        EntityArgument.getPlayer(context, "player"),
                                        98 // weapon.mainhand
                                ))
                                .then(Commands.argument("slot", SlotArgument.slot())
                                        .executes(context -> updateRecordedDisc(
                                                context.getSource(),
                                                EntityArgument.getPlayer(context, "player"),
                                                SlotArgument.getSlot(context, "slot")
                                        ))))));
    }

    private static int updateRecordedDisc(CommandSourceStack source, @Nullable ServerPlayer player, int slotID) {
        if (player == null) {
            source.sendFailure(Component.translatable("commands.mstudios.update_recorded_disc.no_player"));
            return 0;
        }

        ItemStack slotStack = player.getSlot(slotID).get();
        CustomData data = slotStack.get(DataComponents.CUSTOM_DATA);
        MutableComponent stackDisplay = slotStack.getDisplayName().copy();

        if (slotStack.isEmpty() || data == null) {
            source.sendFailure(Component.translatable("commands.mstudios.update_recorded_disc.fail", stackDisplay.withStyle(ChatFormatting.RED)));
            return 0;
        }

        boolean updatedID = false;
        boolean updatedLabel = false;
        CompoundTag tag = data.copyTag();
        if (tag.contains("music_id", Tag.TAG_STRING)) {
            String musicID = tag.getString("music_id");
            for (String oldName : UPDATED_SONG_NAMES.keySet()) {
                String newName = UPDATED_SONG_NAMES.get(oldName);
                if (musicID.contains(oldName)) {
                    musicID = musicID.replace(oldName, newName);
                }
            }
            tag.remove("music_id");
            slotStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            updatedID = RecordedDiscItem.setJukeboxSong(source.getLevel(), slotStack, new Track(ResourceLocation.parse(musicID), false), false, false);
        }

        if (tag.contains("label", Tag.TAG_ANY_NUMERIC)) {
            slotStack.set(STDataComponents.LABEL, Math.clamp(tag.getFloat("label"), RecordedDiscItem.DISC_LABEL_MIN, RecordedDiscItem.DISC_LABEL_MAX));
            tag.remove("label");
            slotStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            updatedLabel = true;
        }

        if (slotStack.has(DataComponents.DYED_COLOR)) {
            slotStack.set(DataComponents.DYED_COLOR, slotStack.get(DataComponents.DYED_COLOR).withTooltip(false));
            updatedLabel = true;
        }

        if (slotStack.get(DataComponents.CUSTOM_DATA).isEmpty()) slotStack.remove(DataComponents.CUSTOM_DATA);

        if (updatedID || updatedLabel) {
            String translation = updatedID && updatedLabel ? "both" : (updatedID ? "music_id" : "label");
            source.sendSuccess(() -> Component.translatable("commands.mstudios.update_recorded_disc." + translation, stackDisplay), true);
            return updatedID && updatedLabel ? 2 : 1;
        } else {
            source.sendFailure(Component.translatable("commands.mstudios.update_recorded_disc.fail", stackDisplay.withStyle(ChatFormatting.RED)));
            return 0;
        }
    }
}
