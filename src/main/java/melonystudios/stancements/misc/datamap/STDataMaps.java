package melonystudios.stancements.misc.datamap;

import melonystudios.stancements.Stancements;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class STDataMaps {
    public static final DataMapType<Item, PotPlantable> POT_PLANTABLE = DataMapType.builder(Stancements.stancements("pot_plantable"), Registries.ITEM, PotPlantable.CODEC).synced(PotPlantable.CODEC, false).build();
}
