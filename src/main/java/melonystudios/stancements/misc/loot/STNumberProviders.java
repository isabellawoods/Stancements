package melonystudios.stancements.misc.loot;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.misc.loot.number.ClampedNumber;
import melonystudios.stancements.misc.loot.number.Sum;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STNumberProviders {
    public static final DeferredRegister<LootNumberProviderType> PROVIDERS = DeferredRegister.create(Registries.LOOT_NUMBER_PROVIDER_TYPE, Stancements.MOD_ID);

    public static final DeferredHolder<LootNumberProviderType, LootNumberProviderType> SUM = PROVIDERS.register("sum", () -> new LootNumberProviderType(Sum.CODEC));
    public static final DeferredHolder<LootNumberProviderType, LootNumberProviderType> CLAMPED_NUMBER = PROVIDERS.register("clamped_number", () -> new LootNumberProviderType(ClampedNumber.CODEC));
}
