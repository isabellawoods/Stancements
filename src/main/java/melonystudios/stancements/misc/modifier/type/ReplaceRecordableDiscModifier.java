package melonystudios.stancements.misc.modifier.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.misc.modifier.ModifierComponentType;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.VinylModifier;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;

public record ReplaceRecordableDiscModifier(ItemStack replacement) implements ModifierComponentType {
    public static final Codec<ReplaceRecordableDiscModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("replacement").forGetter(ReplaceRecordableDiscModifier::replacement)
    ).apply(instance, ReplaceRecordableDiscModifier::new));

    @Override
    public void onApplyModifiers(ModificationContext context, Holder<VinylModifier> modifier) {
        ItemStack stack = this.replacement().copy();
        stack.applyComponents(context.transientStack().getComponentsPatch());
        context.withTransientStack(stack);
    }
}
