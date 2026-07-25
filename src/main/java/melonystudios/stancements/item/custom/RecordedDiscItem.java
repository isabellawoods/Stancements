package melonystudios.stancements.item.custom;

import com.google.common.collect.Lists;
import melonystudios.reutilities.api.ReAPI;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.MusicData;
import melonystudios.stancements.misc.STRegistries;
import melonystudios.stancements.misc.discstyle.RecordedDiscStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class RecordedDiscItem extends Item {
    public static final int DEFAULT_DISC_COLOR = 0xFFF9FFFE;
    public static final int DISC_LABEL_MIN = 1;
    public static final int DISC_LABEL_MAX = 14;

    public RecordedDiscItem(Properties properties) {
        super(properties
                .component(STDataComponents.LABEL, (float) DISC_LABEL_MIN)
                .component(DataComponents.DYED_COLOR, new DyedItemColor(DEFAULT_DISC_COLOR))
                .component(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT.withHidden(DataComponents.DYED_COLOR, true))
        );
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        MusicData data = stack.get(STDataComponents.MUSIC_DATA);
        if (data != null && data.copied() && ReAPI.shouldDisplay(stack, Stancements.stancements("recorded_disc/copied"))) {
            tooltip.accept(Component.translatable("tooltip.stancements.recorded_disc.copied").withStyle(ChatFormatting.DARK_GRAY));
        }

        if (stack.has(DataComponents.JUKEBOX_PLAYABLE)) return;

        if (data != null && data.id().isPresent() && ReAPI.shouldDisplay(stack, Stancements.stancements("recorded_disc/sound_id"))) {
            tooltip.accept(Component.translatable("tooltip.stancements.recorded_disc.sound_id", data.id().get().toString()).withStyle(ChatFormatting.GRAY));
        } else if (ReAPI.shouldDisplay(stack, Stancements.stancements("recorded_disc/blank"))) {
            tooltip.accept(Component.translatable("tooltip.stancements.recorded_disc.blank").withStyle(ChatFormatting.GRAY));
        }
    }

    /// Returns the identifier of a {@linkplain melonystudios.stancements.misc.STJukeboxSongs jukebox song} based on the recorded `music_id`.
    /// @param musicID An identifier of the song's location within the game's files.
    public static Identifier getJukeboxSongLocation(Identifier musicID) {
        return Identifier.parse(musicID.toString()
                .replace("sounds/", "")
                .replace("music/", "")
                .replace("music_disc/", "") // "music_disc" for Project Alcook's dead forest biome ~isa 19-05-26
                .replace("records/", "")
                .replace(".ogg", ""));
    }

    public static boolean setJukeboxSong(ItemStack stack, Level level, Identifier musicID, boolean copyingSong, boolean reduceDataWrites) {
        var jukeboxSongs = level.registryAccess().lookup(Registries.JUKEBOX_SONG);
        if (jukeboxSongs.isPresent()) {
            var song = jukeboxSongs.get().get(copyingSong ? musicID : getJukeboxSongLocation(musicID));
            if (song.isPresent()) {
                if (copyingSong) {
                    stack.set(STDataComponents.MUSIC_DATA, stack.getOrDefault(STDataComponents.MUSIC_DATA, MusicData.copiedDisc()).markCopied(true));
                }
                stack.set(DataComponents.JUKEBOX_PLAYABLE, new JukeboxPlayable(song.get()));
                stack.set(STDataComponents.MUSIC_DATA, stack.getOrDefault(STDataComponents.MUSIC_DATA, MusicData.data()).withID(musicID));
                return true;
            } else if (!reduceDataWrites) {
                stack.set(STDataComponents.MUSIC_DATA, MusicData.unknownSong(musicID, copyingSong));
                return false;
            }
        }
        return false;
    }

    public static ItemStack getRecordedDisc(Level level, Identifier musicID, boolean copyingSong, ItemStack originalStack) {
        if (originalStack.isEmpty() || !originalStack.has(STDataComponents.RECORDING_TURNS_INTO)) return ItemStack.EMPTY;
        ItemStack discStack = new ItemStack(originalStack.get(STDataComponents.RECORDING_TURNS_INTO).whenRecorded().value());

        if (copyingSong) {
            ItemStack stack = setAppearanceFromStyleRegistry(level, discStack, musicID);
            return stack == null ? randomizeAppearance(level, discStack, musicID, true) : stack;
        }
        return randomizeAppearance(level, discStack, musicID, false);
    }

    public static ItemStack randomizeAppearance(Level level, ItemStack stack, Identifier musicID, boolean copyingSong) {
        setJukeboxSong(stack, level, musicID, copyingSong, false);
        stack.set(STDataComponents.LABEL, (float) (level.getRandom().nextInt(DISC_LABEL_MAX) + 1));
        return getRandomLabelColor(stack, level.getRandom());
    }

    public static ItemStack setAppearanceFromStyleRegistry(Level level, ItemStack stack, Identifier musicID) {
        var discStyles = level.registryAccess().lookup(STRegistries.RECORDED_DISC_STYLE);
        if (discStyles.isEmpty()) return null;

        RecordedDiscStyle copyStyle = discStyles.get().getValue(musicID);
        if (copyStyle != null) {
            setJukeboxSong(stack, level, musicID, true, false);
            stack.set(STDataComponents.LABEL, copyStyle.label());
            stack.set(STDataComponents.MUSIC_DATA, MusicData.copiedDisc());
            stack.set(DataComponents.DYED_COLOR, new DyedItemColor(copyStyle.color()));
            TooltipDisplay display = stack.get(DataComponents.TOOLTIP_DISPLAY);
            stack.set(DataComponents.TOOLTIP_DISPLAY, (display == null ? TooltipDisplay.DEFAULT : display).withHidden(DataComponents.DYED_COLOR, true));
            if (copyStyle.rarity() != Rarity.UNCOMMON) stack.set(DataComponents.RARITY, copyStyle.rarity());
            return stack;
        }
        return null;
    }

    public static ItemStack getRandomLabelColor(ItemStack stack, RandomSource random) {
        List<DyeColor> dyes = Lists.newArrayList();
        dyes.add(getRandomDye(random));
        if (random.nextFloat() > 0.7F) dyes.add(getRandomDye(random));
        if (random.nextFloat() > 0.8F) dyes.add(getRandomDye(random));
        ItemStack dyedStack = DyedItemColor.applyDyes(stack.copy(), dyes);
        dyedStack.set(DataComponents.DYED_COLOR, dyedStack.getOrDefault(DataComponents.DYED_COLOR, new DyedItemColor(DEFAULT_DISC_COLOR)));
        TooltipDisplay display = dyedStack.get(DataComponents.TOOLTIP_DISPLAY);
        dyedStack.set(DataComponents.TOOLTIP_DISPLAY, (display == null ? TooltipDisplay.DEFAULT : display).withHidden(DataComponents.DYED_COLOR, true));
        return dyedStack.isEmpty() ? stack : dyedStack;
    }

    /// Picks a random {@link DyeColor} from the existing 16 colors.
    /// @param random The `RandomSource` of randomize the colors.
    private static DyeColor getRandomDye(RandomSource random) {
        return Util.getRandom(DyeColor.VALUES, random);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, level, owner, slot);
        if (level.isClientSide()) return;

        // remove old "music_id" component from 1.16
        Identifier id = stack.get(STDataComponents.MUSIC_ID);
        if (!stack.has(DataComponents.JUKEBOX_PLAYABLE) && id != null) {
            setJukeboxSong(stack, level, id, false, true);
            stack.remove(STDataComponents.MUSIC_ID);
        }

        // add the jukebox_playable component if it doesn't exist (if its source data pack gets disabled,
        // for example)
        MusicData data = stack.getOrDefault(STDataComponents.MUSIC_DATA, MusicData.data());
        if (!stack.has(DataComponents.JUKEBOX_PLAYABLE) && data.id().isPresent()) {
            setJukeboxSong(stack, level, data.id().get(), false, true);
        }
    }
}
