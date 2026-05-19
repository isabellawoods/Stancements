package melonystudios.stancements.misc.discstyle;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.STRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;

public class STRecordedDiscStyles {
    // Minecraft
    public static final ResourceKey<RecordedDiscStyle> THIRTEEN = vanilla("13");
    public static final ResourceKey<RecordedDiscStyle> CAT = vanilla("cat");
    public static final ResourceKey<RecordedDiscStyle> BLOCKS = vanilla("blocks");
    public static final ResourceKey<RecordedDiscStyle> CHIRP = vanilla("chirp");
    public static final ResourceKey<RecordedDiscStyle> FAR = vanilla("far");
    public static final ResourceKey<RecordedDiscStyle> MALL = vanilla("mall");
    public static final ResourceKey<RecordedDiscStyle> MELLOHI = vanilla("mellohi");
    public static final ResourceKey<RecordedDiscStyle> STAL = vanilla("stal");
    public static final ResourceKey<RecordedDiscStyle> STRAD = vanilla("strad");
    public static final ResourceKey<RecordedDiscStyle> WARD = vanilla("ward");
    public static final ResourceKey<RecordedDiscStyle> ELEVEN = vanilla("11");
    public static final ResourceKey<RecordedDiscStyle> WAIT = vanilla("wait");
    public static final ResourceKey<RecordedDiscStyle> PIGSTEP = vanilla("pigstep");
    public static final ResourceKey<RecordedDiscStyle> OTHERSIDE = vanilla("otherside");
    public static final ResourceKey<RecordedDiscStyle> FIVE = vanilla("5");
    public static final ResourceKey<RecordedDiscStyle> RELIC = vanilla("relic");
    public static final ResourceKey<RecordedDiscStyle> PRECIPICE = vanilla("precipice");
    public static final ResourceKey<RecordedDiscStyle> CREATOR = vanilla("creator");
    public static final ResourceKey<RecordedDiscStyle> CREATOR_MUSIC_BOX = vanilla("creator_music_box");

    // Stancements
    public static final ResourceKey<RecordedDiscStyle> ALPHA = stancements("game/end/alpha");

    public static void bootstrap(BootstrapContext<RecordedDiscStyle> context) {
        // Minecraft
        context.register(THIRTEEN, new RecordedDiscStyle(0xFFD800, 9));
        context.register(CAT, new RecordedDiscStyle(0x4CFF00, 2));
        context.register(BLOCKS, new RecordedDiscStyle(0xE2543B, 1));
        context.register(CHIRP, new RecordedDiscStyle(0xFF0004, 3));
        context.register(FAR, new RecordedDiscStyle(0xB6FF00, 4));
        context.register(MALL, new RecordedDiscStyle(0x9A75FF, 2));
        context.register(MELLOHI, new RecordedDiscStyle(0xB200FF, 5));
        context.register(STAL, new RecordedDiscStyle(0x000000, 1));
        context.register(STRAD, new RecordedDiscStyle(0xFFFFFF, 1));
        context.register(WARD, new RecordedDiscStyle(0x8EC600, 9));
        context.register(ELEVEN, new RecordedDiscStyle(0x000000, 1));
        context.register(WAIT, new RecordedDiscStyle(0x81A9E2, 12));
        context.register(PIGSTEP, new RecordedDiscStyle(0xFDF55F, 6));
        context.register(OTHERSIDE, new RecordedDiscStyle(0x1E8B8C, 2));
        context.register(FIVE, new RecordedDiscStyle(0x29DFEB, 11));
        context.register(RELIC, new RecordedDiscStyle(0x88E6FF, 6));
        context.register(PRECIPICE, new RecordedDiscStyle(0x7AB799, 7));
        context.register(CREATOR, new RecordedDiscStyle(0xFFDD99, 10));
        context.register(CREATOR_MUSIC_BOX, new RecordedDiscStyle(0xFFDD99, 10));

        // Stancements
        context.register(ALPHA, new RecordedDiscStyle(0x9AC9BF, 13, Rarity.EPIC));
    }

    public static ResourceKey<RecordedDiscStyle> vanilla(String name) {
        return ResourceKey.create(STRegistries.RECORDED_DISC_STYLE, ResourceLocation.withDefaultNamespace(name));
    }

    public static ResourceKey<RecordedDiscStyle> stancements(String name) {
        return ResourceKey.create(STRegistries.RECORDED_DISC_STYLE, Stancements.stancements(name));
    }
}
