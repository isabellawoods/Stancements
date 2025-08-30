package melonystudios.stancements.item.custom;

import com.google.common.collect.Lists;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.STItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;

import java.util.List;

// todo: make discs with the "music_id" component automatically resolve to the jukebox song when available ~isa 3--8-25
public class RecordedDiscItem extends Item {
    public static final int DEFAULT_DISC_COLOR = 0xFFF9FFFE;

    public RecordedDiscItem(Properties properties) {
        super(properties.component(STDataComponents.LABEL, 1F).component(DataComponents.DYED_COLOR, new DyedItemColor(DEFAULT_DISC_COLOR, true)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (!stack.has(DataComponents.JUKEBOX_PLAYABLE)) tooltip.add(Component.translatable("tooltip.stancements.recorded_disc.blank").withStyle(ChatFormatting.GRAY));
    }

    public static ResourceLocation getJukeboxSongLocation(ResourceLocation musicID) {
        String namespace = musicID.getNamespace().equals("minecraft") ? "stancements" : musicID.getNamespace();
        return ResourceLocation.parse(namespace + ":" + musicID.getPath()
                .replace("sounds/", "")
                .replace("music/", "")
                .replace(".ogg", ""));
    }

    public static ItemStack getRecordedDisc(Level world, ResourceLocation musicID, ItemStack originalStack) {
        if (originalStack.isEmpty()) return ItemStack.EMPTY;
        ItemStack discStack = new ItemStack(STItems.RECORDED_DISC.get());
        world.registryAccess().registry(Registries.JUKEBOX_SONG).ifPresent(songs -> {
            var song = songs.getHolder(getJukeboxSongLocation(musicID));
            if (song.isPresent()) {
                discStack.set(DataComponents.JUKEBOX_PLAYABLE, new JukeboxPlayable(new EitherHolder<>(song.get()), true));
            } else {
                discStack.set(STDataComponents.MUSIC_ID, musicID.toString());
            }
        });
        discStack.set(STDataComponents.LABEL, (float) (world.getRandom().nextInt(10) + 1));
        return getRandomLabelColor(discStack, world.getRandom());
    }

    public static ItemStack getRandomLabelColor(ItemStack stack, RandomSource rand) {
        List<DyeItem> dyes = Lists.newArrayList();
        dyes.add(getRandomDye(rand));
        if (rand.nextFloat() > 0.7F) dyes.add(getRandomDye(rand));
        if (rand.nextFloat() > 0.8F) dyes.add(getRandomDye(rand));
        ItemStack dyedStack = DyedItemColor.applyDyes(stack.copy(), dyes);
        dyedStack.set(DataComponents.DYED_COLOR, dyedStack.get(DataComponents.DYED_COLOR).withTooltip(false));
        return dyedStack.isEmpty() ? stack : dyedStack;
    }

    /// Picks a random {@link DyeColor} from the existing 16 colors.
    /// @param rand The random source of randomize the colors.
    private static DyeItem getRandomDye(RandomSource rand) {
        return DyeItem.byColor(DyeColor.byId(rand.nextInt(16)));
    }
}
