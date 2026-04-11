package melonystudios.stancements.command;

import com.google.common.collect.ImmutableMap;
import com.mojang.brigadier.CommandDispatcher;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

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
            // The Mato music pack songs
            .put("themato", "minecraft") // correct mod id
            .put("caves_and_cliffs/", "")
            .put("wild_update/", "swamp/")
            .put("trails_and_tales/", "")
            .put("tricky_trials/", "")
            .put("chase_the_skies/", "")
            .put("drop_2_2025/", "") // existed at some point, don't know if discs could have this though
            .build();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("melonystudios")
                .then(Commands.literal(Stancements.stancements("gameplay/update_recorded_disc").toString())
                        .executes(context -> updateRecordedDisc(context.getSource()))
                )
        );
    }

    private static int updateRecordedDisc(CommandSourceStack source) {
        Entity entity = source.getEntity();
        if (entity instanceof LivingEntity livEntity) {
            ItemStack handStack = livEntity.getItemBySlot(EquipmentSlot.MAINHAND);
            CustomData data = handStack.get(DataComponents.CUSTOM_DATA);
            MutableComponent stackDisplay = handStack.getDisplayName().copy();
            if (handStack.isEmpty() || data == null) {
                source.sendFailure(Component.translatable("commands.stancements.update_recorded_disc.fail", stackDisplay.withStyle(ChatFormatting.RED)));
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
                handStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                updatedID = RecordedDiscItem.setJukeboxSong(handStack, source.getLevel(), ResourceLocation.parse(musicID), false);
            }

            if (tag.contains("label", Tag.TAG_ANY_NUMERIC)) {
                handStack.set(STDataComponents.LABEL, Math.clamp(tag.getFloat("label"), RecordedDiscItem.DISC_LABEL_MIN, RecordedDiscItem.DISC_LABEL_MAX));
                tag.remove("label");
                handStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                updatedLabel = true;
            }

            if (handStack.has(DataComponents.DYED_COLOR)) {
                handStack.set(DataComponents.DYED_COLOR, handStack.get(DataComponents.DYED_COLOR).withTooltip(false));
                updatedLabel = true;
            }

            if (handStack.get(DataComponents.CUSTOM_DATA).isEmpty()) handStack.remove(DataComponents.CUSTOM_DATA);

            if (updatedID || updatedLabel) {
                String translation = updatedID && updatedLabel ? "both" : (updatedID ? "music_id" : "label");
                source.sendSuccess(() -> Component.translatable("commands.stancements.update_recorded_disc." + translation, stackDisplay), true);
                return 1;
            } else {
                source.sendFailure(Component.translatable("commands.stancements.update_recorded_disc.fail", stackDisplay.withStyle(ChatFormatting.RED)));
                return 0;
            }
        }

        return 0;
    }
}
