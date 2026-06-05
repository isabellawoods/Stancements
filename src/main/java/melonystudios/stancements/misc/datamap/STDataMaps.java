package melonystudios.stancements.misc.datamap;

import melonystudios.stancements.Stancements;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class STDataMaps {
    public static final DataMapType<Item, PotPlantables> POT_PLANTABLES = DataMapType.builder(Stancements.stancements("pot_plantables"), Registries.ITEM, PotPlantables.CODEC).synced(PotPlantables.CODEC, false).build();
}
