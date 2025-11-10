package melonystudios.stancements.misc.advancement;

import melonystudios.stancements.Stancements;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STCriteriaTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, Stancements.MOD_ID);

    public static final RecordSongTrigger RECORD_SONG = register("record_song", new RecordSongTrigger());

    public static <T extends CriterionTrigger<?>> T register(String name, T trigger) {
        TRIGGERS.register(name, () -> trigger);
        return trigger;
    }
}
