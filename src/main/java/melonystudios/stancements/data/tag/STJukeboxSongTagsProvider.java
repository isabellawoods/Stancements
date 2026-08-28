package melonystudios.stancements.data.tag;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.tag.STJukeboxSongTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongs;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static melonystudios.stancements.misc.STJukeboxSongs.*;

public class STJukeboxSongTagsProvider extends TagsProvider<JukeboxSong> {
    public STJukeboxSongTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.JUKEBOX_SONG, registries, Stancements.MOD_ID, existingFileHelper);
    }

    @Override
    @NotNull
    public String getName() {
        return Stancements.generatorName("Jukebox Song Tags");
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Stancements tags
        this.tag(STJukeboxSongTags.COPYING_PROHIBITED);
        this.tag(STJukeboxSongTags.CANCELS_AMBIENT_MUSIC).addTag(STJukeboxSongTags.AMBIENT_MUSIC);
        this.tag(STJukeboxSongTags.NOT_REQUIRED_FOR_COMPLETION).add(BIOME_FEST, BLIND_SPOTS, HAUNT_MUSKIE, ARIA_MATH, DREITON, TASWELL);

        this.tag(STJukeboxSongTags.AMBIENT_MUSIC)
                .add(MINECRAFT, CLARK, SWEDEN, SUBWOOFER_LULLABY, LIVING_MICE, HAGGSTROM, DANNY, KEY, OXYGENE, DRY_HANDS, WET_HANDS, MICE_ON_VENUS)
                .add(BIOME_FEST, BLIND_SPOTS, HAUNT_MUSKIE, ARIA_MATH, DREITON, TASWELL, CONCRETE_HALLS, DEAD_VOXEL, WARMTH, BALLAD_OF_THE_CATS, BOSS, THE_END, ALPHA)
                .add(SHUNIJI, DRAGON_FISH, AXOLOTL)
                .add(RUBEDO, CHRYSOPOEIA, SO_BELOW)
                .add(STAND_TALL, LEFT_TO_BLOOM, ANCESTRY, WENDING, INFINITE_AMETHYST, ONE_MORE_DAY, FLOATING_DREAM, COMFORTING_MEMORIES, AN_ORDINARY_DAY)
                .add(FIREBUGS, AERIE, LABYRINTHINE)
                .add(ECHO_IN_THE_WIND, A_FAMILIAR_ROOM, BROMELIAD, CRESCENT_DUNES)
                .add(FEATHERFALL, WATCHER, PUZZLEBOX, KOMOREBI, POKOPOKO, YAKUSOKU, DEEPER, ELD_UNKNOWN, ENDLESS);

        // Minecraft tags
        this.tag(STJukeboxSongTags.VOLUME_ALPHA)
                .add(KEY, SUBWOOFER_LULLABY, LIVING_MICE, HAGGSTROM, MINECRAFT, OXYGENE, MICE_ON_VENUS, DRY_HANDS, WET_HANDS, CLARK, JukeboxSongs.THIRTEEN, SWEDEN, JukeboxSongs.CAT, DANNY);
        this.tag(STJukeboxSongTags.VOLUME_BETA)
                .add(ALPHA, DEAD_VOXEL, BLIND_SPOTS, CONCRETE_HALLS, BIOME_FEST, HAUNT_MUSKIE, WARMTH, ARIA_MATH, BALLAD_OF_THE_CATS, TASWELL, DREITON, THE_END,
                        JukeboxSongs.CHIRP, JukeboxSongs.WAIT, JukeboxSongs.MELLOHI, JukeboxSongs.STAL, JukeboxSongs.STRAD, JukeboxSongs.ELEVEN, JukeboxSongs.WARD, JukeboxSongs.MALL,
                        JukeboxSongs.BLOCKS, JukeboxSongs.FAR);
        this.tag(STJukeboxSongTags.NETHER_UPDATE)
                .add(CHRYSOPOEIA, RUBEDO, SO_BELOW, JukeboxSongs.PIGSTEP);
        this.tag(STJukeboxSongTags.CAVES_AND_CLIFFS)
                .add(STAND_TALL, LEFT_TO_BLOOM, ANCESTRY, WENDING, INFINITE_AMETHYST, ONE_MORE_DAY, JukeboxSongs.OTHERSIDE, FLOATING_DREAM, COMFORTING_MEMORIES, AN_ORDINARY_DAY);
        this.tag(STJukeboxSongTags.THE_WILD_UPDATE)
                .add(FIREBUGS, AERIE, LABYRINTHINE, JukeboxSongs.FIVE);
        this.tag(STJukeboxSongTags.TRAILS_AND_TALES)
                .add(ECHO_IN_THE_WIND, A_FAMILIAR_ROOM, BROMELIAD, CRESCENT_DUNES, JukeboxSongs.RELIC);
        this.tag(STJukeboxSongTags.TRICKY_TRIALS)
                .add(FEATHERFALL, WATCHER, PUZZLEBOX, KOMOREBI, POKOPOKO, YAKUSOKU, DEEPER, ELD_UNKNOWN, ENDLESS, JukeboxSongs.CREATOR, JukeboxSongs.CREATOR_MUSIC_BOX, JukeboxSongs.PRECIPICE);

        this.tag(STJukeboxSongTags.UPDATE_AQUATIC).add(SHUNIJI, DRAGON_FISH, AXOLOTL);
    }
}
