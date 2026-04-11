package melonystudios.stancements.item.custom;

import com.google.common.collect.Lists;
import melonystudios.reutilities.api.ReAPI;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.component.custom.MusicData;
import melonystudios.stancements.misc.datamap.STDataMaps;
import melonystudios.stancements.misc.STJukeboxSongs;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;

import java.util.List;

// todo: make discs with the "music_id" component automatically resolve to the jukebox song when available ~isa 03-08-25
public class RecordedDiscItem extends Item {
    public static final int DEFAULT_DISC_COLOR = 0xFFF9FFFE;
    public static final int DISC_LABEL_MIN = 1;
    public static final int DISC_LABEL_MAX = 13;

    public RecordedDiscItem(Properties properties) {
        super(properties.component(STDataComponents.LABEL, (float) DISC_LABEL_MIN).component(DataComponents.DYED_COLOR, new DyedItemColor(DEFAULT_DISC_COLOR, false)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        MusicData data = stack.get(STDataComponents.MUSIC_DATA);
        if (data != null && data.copied() && ReAPI.shouldDisplay(stack, Stancements.stancements("recorded_disc/copied"))) {
            data.addToTooltip(context, tooltip::add, flag);
        }

        if (!stack.has(DataComponents.JUKEBOX_PLAYABLE) && ReAPI.shouldDisplay(stack, Stancements.stancements("recorded_disc/blank"))) {
            tooltip.add(Component.translatable("tooltip.stancements.recorded_disc.blank").withStyle(ChatFormatting.GRAY));
        }
    }

    /// Returns the location of a {@linkplain STJukeboxSongs jukebox song} based on the recorded `music_id`.
    /// @param musicID A resource location of the song's location within the game's files.
    public static ResourceLocation getJukeboxSongLocation(ResourceLocation musicID) {
        String namespace = musicID.getNamespace().equals("minecraft") ? "stancements" : musicID.getNamespace();
        return ResourceLocation.tryBuild(namespace, musicID.getPath()
                .replace("sounds/", "")
                .replace("music/", "")
                .replace(".ogg", ""));
    }

    /// Sanitizes the id the music currently being recorded for usage in advancements.
    /// @param musicID A resource location of the song's location within the game's files.
    public static ResourceLocation sanitizeMusicIDLocation(ResourceLocation musicID) {
        return ResourceLocation.parse(musicID.toString()
                .replace("sounds/", "")
                .replace("music/", "")
                .replace(".ogg", ""));
    }

    public static boolean setJukeboxSong(ItemStack stack, Level level, ResourceLocation musicID, boolean copyingSong) {
        var jukeboxSongs = level.registryAccess().registry(Registries.JUKEBOX_SONG);
        if (jukeboxSongs.isPresent()) {
            var song = jukeboxSongs.get().getHolder(copyingSong ? musicID : getJukeboxSongLocation(musicID));
            if (song.isPresent()) {
                if (copyingSong) {
                    stack.set(STDataComponents.MUSIC_DATA, stack.getOrDefault(STDataComponents.MUSIC_DATA, MusicData.copiedDisc()).markCopied(true));
                }
                stack.set(DataComponents.JUKEBOX_PLAYABLE, new JukeboxPlayable(new EitherHolder<>(song.get()), true));
                return true;
            } else {
                stack.set(STDataComponents.MUSIC_DATA, MusicData.unknownSong(musicID, copyingSong));
                return false;
            }
        }
        return false;
    }

    public static ItemStack getRecordedDisc(Level level, ResourceLocation musicID, boolean copyingSong, ItemStack originalStack) {
        if (originalStack.isEmpty() || !originalStack.has(STDataComponents.RECORDING_TURNS_INTO)) return ItemStack.EMPTY;
        ItemStack discStack = new ItemStack(originalStack.get(STDataComponents.RECORDING_TURNS_INTO).whenRecorded().value());

        if (copyingSong) {
            ItemStack stack = setAppearanceFromDataMap(level, discStack, musicID);
            return stack == null ? randomizeAppearance(level, discStack, musicID, true) : stack;
        }
        return randomizeAppearance(level, discStack, musicID, false);
    }

    public static ItemStack randomizeAppearance(Level level, ItemStack stack, ResourceLocation musicID, boolean copyingSong) {
        setJukeboxSong(stack, level, musicID, copyingSong);
        stack.set(STDataComponents.LABEL, (float) (level.getRandom().nextInt(DISC_LABEL_MAX) + 1));
        return getRandomLabelColor(stack, level.getRandom());
    }

    public static ItemStack setAppearanceFromDataMap(Level level, ItemStack stack, ResourceLocation musicID) {
        var jukeboxSongs = level.registryAccess().registry(Registries.JUKEBOX_SONG);
        if (jukeboxSongs.isEmpty()) return null;
        var copyStyle = jukeboxSongs.get().getDataMap(STDataMaps.RECORDED_DISC_STYLES);

        for (ResourceKey<JukeboxSong> song : copyStyle.keySet()) {
            if (song.location().equals(musicID)) {
                setJukeboxSong(stack, level, musicID, true);
                stack.set(STDataComponents.LABEL, copyStyle.get(song).label());
                stack.set(STDataComponents.MUSIC_DATA, MusicData.copiedDisc());
                stack.set(DataComponents.DYED_COLOR, new DyedItemColor(copyStyle.get(song).color(), false));
                return stack;
            }
        }
        return null;
    }

    public static ItemStack getRandomLabelColor(ItemStack stack, RandomSource rand) {
        List<DyeItem> dyes = Lists.newArrayList();
        dyes.add(getRandomDye(rand));
        if (rand.nextFloat() > 0.7F) dyes.add(getRandomDye(rand));
        if (rand.nextFloat() > 0.8F) dyes.add(getRandomDye(rand));
        ItemStack dyedStack = DyedItemColor.applyDyes(stack.copy(), dyes);
        dyedStack.set(DataComponents.DYED_COLOR, dyedStack.getOrDefault(DataComponents.DYED_COLOR, new DyedItemColor(DEFAULT_DISC_COLOR, false)).withTooltip(false));
        return dyedStack.isEmpty() ? stack : dyedStack;
    }

    /// Picks a random {@link DyeColor} from the existing 16 colors.
    /// @param rand The random source of randomize the colors.
    private static DyeItem getRandomDye(RandomSource rand) {
        return DyeItem.byColor(DyeColor.byId(rand.nextInt(16)));
    }
}
