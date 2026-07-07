package melonystudios.stancements.misc.loot.function;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.item.custom.RecordedDiscItem;
import melonystudios.stancements.misc.STRegistries;
import melonystudios.stancements.misc.discstyle.RecordedDiscStyle;
import melonystudios.stancements.misc.loot.STLootFunctions;
import melonystudios.stancements.misc.loot.ModificationContextAware;
import melonystudios.stancements.misc.modifier.ModificationContext;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StyleDiscFromRegistryFunction extends LootItemConditionalFunction implements ModificationContextAware {
    public static final MapCodec<StyleDiscFromRegistryFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> commonFields(instance).and(instance.group(
            SetRandomDyesFunction.CODEC.fieldOf("fallback_dyes_setter").forGetter(function -> function.dyesSetter),
            SetRandomLabelFunction.CODEC.fieldOf("fallback_label_setter").forGetter(function -> function.labelSetter)
    )).apply(instance, StyleDiscFromRegistryFunction::new));
    private final SetRandomDyesFunction dyesSetter;
    private final SetRandomLabelFunction labelSetter;
    private @Nullable ModificationContext context;

    public StyleDiscFromRegistryFunction(List<LootItemCondition> conditions, SetRandomDyesFunction dyesSetter, SetRandomLabelFunction labelSetter) {
        super(conditions);
        this.dyesSetter = dyesSetter;
        this.labelSetter = labelSetter;
    }

    @Override
    @NotNull
    protected ItemStack run(ItemStack stack, LootContext context) {
        ItemStack copyStack = stack.copy();
        ResourceLocation musicID;
        if (this.context != null) {
            musicID = this.context.musicID();
        } else if (context.getParamOrNull(LootContextParams.BLOCK_ENTITY) instanceof MusicRecorderBlockEntity recorder) {
            musicID = recorder.musicID();
        } else {
            return copyStack;
        }

        var discStyles = context.getLevel().registryAccess().registryOrThrow(STRegistries.RECORDED_DISC_STYLE);
        RecordedDiscStyle copyStyle = discStyles.get(RecordedDiscItem.getJukeboxSongLocation(musicID));

        if (copyStyle != null) {
            copyStack.set(STDataComponents.LABEL, copyStyle.label());
            copyStack.set(DataComponents.DYED_COLOR, new DyedItemColor(copyStyle.color(), false));
            if (copyStyle.rarity() != Rarity.UNCOMMON) copyStack.set(DataComponents.RARITY, copyStyle.rarity());
        } else {
            copyStack = this.dyesSetter.run(copyStack, context);
            copyStack = this.labelSetter.run(copyStack, context);
        }
        return copyStack;
    }

    public static LootItemConditionalFunction.Builder<?> styleFromRegistry(SetRandomDyesFunction dyesSetter, SetRandomLabelFunction labelSetter) {
        return simpleBuilder(conditions -> new StyleDiscFromRegistryFunction(conditions, dyesSetter, labelSetter));
    }

    @Override
    public void withContext(ModificationContext context) {
        this.context = context;
    }

    @Override
    @NotNull
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return STLootFunctions.STYLE_DISC_FROM_REGISTRY.get();
    }
}
