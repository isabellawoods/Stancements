package melonystudios.stancements.misc.datamap;

import melonystudios.stancements.Stancements;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class STDataMaps {
    public static final DataMapType<Item, PotPlantables> POT_PLANTABLES = DataMapType.builder(Stancements.stancements("pot_plantables"), Registries.ITEM, PotPlantables.CODEC).synced(PotPlantables.CODEC, false).build();
    public static final DataMapType<JukeboxSong, RecordedDiscStyles> RECORDED_DISC_STYLES = DataMapType.builder(Stancements.stancements("recorded_disc_styles"), Registries.JUKEBOX_SONG, RecordedDiscStyles.CODEC).synced(RecordedDiscStyles.CODEC, false).build();
}
