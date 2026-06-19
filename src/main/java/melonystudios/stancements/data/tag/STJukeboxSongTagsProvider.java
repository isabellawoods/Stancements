package melonystudios.stancements.data.tag;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.tag.STJukeboxSongTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

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
    }
}
