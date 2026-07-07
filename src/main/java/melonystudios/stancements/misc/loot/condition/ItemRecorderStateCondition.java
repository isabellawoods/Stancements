package melonystudios.stancements.misc.loot.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import melonystudios.stancements.misc.loot.ModificationContextAware;
import melonystudios.stancements.misc.loot.STLootConditions;
import melonystudios.stancements.misc.modifier.ModificationContext;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/// Checks if the transient stack of the current {@linkplain ModificationContext modification context} matches the provided list.
public class ItemRecorderStateCondition implements LootItemCondition, ModificationContextAware {
    public static final MapCodec<ItemRecorderStateCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("targets").forGetter(ItemRecorderStateCondition::targets)
    ).apply(instance, ItemRecorderStateCondition::new));
    private final HolderSet<Item> targets;
    private @Nullable ModificationContext context;

    /// Checks if the transient stack of the current {@linkplain ModificationContext modification context} matches the provided list.
    /// @param targets The list of items to check against.
    public ItemRecorderStateCondition(HolderSet<Item> targets) {
        this.targets = targets;
    }

    /// @return The list of items to check against.
    public HolderSet<Item> targets() {
        return this.targets;
    }

    @Override
    public boolean test(LootContext context) {
        if (this.context != null) {
            return this.context.transientStack().is(this.targets());
        } else if (context.getParamOrNull(LootContextParams.BLOCK_ENTITY) instanceof MusicRecorderBlockEntity recorder) {
            return recorder.getTheItem().is(this.targets());
        }
        return false;
    }

    @Override
    public void withContext(ModificationContext context) {
        this.context = context;
    }

    @Override
    @NotNull
    public LootItemConditionType getType() {
        return STLootConditions.ITEM_RECORDER_STATE.get();
    }
}
