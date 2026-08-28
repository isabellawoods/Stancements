package melonystudios.stancements.misc.modifier.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.misc.modifier.ModifierComponentType;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.VinylModifier;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;

public record ReplaceRecordableDiscModifier(ItemStack replacement, boolean keepComponents) implements ModifierComponentType {
    public static final Codec<ReplaceRecordableDiscModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("replacement").forGetter(ReplaceRecordableDiscModifier::replacement),
            Codec.BOOL.optionalFieldOf("keep_components", true).forGetter(ReplaceRecordableDiscModifier::keepComponents)
    ).apply(instance, ReplaceRecordableDiscModifier::new));

    public ReplaceRecordableDiscModifier(ItemStack replacement) {
        this(replacement, true);
    }

    @Override
    public void onApplyModifiers(ModificationContext context, Holder<VinylModifier> modifier) {
        ItemStack stack = this.replacement().copy();
        if (this.keepComponents()) stack.applyComponents(context.transientStack().getComponentsPatch());
        context.withTransientStack(stack);
    }
}
