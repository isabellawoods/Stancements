package melonystudios.stancements.misc.modifier.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.ModifierComponentType;
import melonystudios.stancements.misc.modifier.VinylModifier;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

public record ReplaceRecordableDiscModifier(ItemStackTemplate replacement, boolean keepComponents) implements ModifierComponentType {
    public static final Codec<ReplaceRecordableDiscModifier> CODEC = RecordCodecBuilder.<ReplaceRecordableDiscModifier>create(instance -> instance.group(
            ItemStackTemplate.CODEC.fieldOf("replacement").forGetter(ReplaceRecordableDiscModifier::replacement),
            Codec.BOOL.optionalFieldOf("keep_components", true).forGetter(ReplaceRecordableDiscModifier::keepComponents)
    ).apply(instance, ReplaceRecordableDiscModifier::new)).validate(modifier -> modifier.replacement().count() > 1 ?
            DataResult.error(() -> "Replacement item stack cannot have a stack size above 1") :
            DataResult.success(modifier)
    );

    public ReplaceRecordableDiscModifier(ItemStackTemplate replacement) {
        this(replacement, true);
    }

    @Override
    public void onApplyModifiers(ModificationContext context, Holder<VinylModifier> modifier) {
        ItemStack stack = this.replacement().create().copy();
        if (this.keepComponents()) stack.applyComponents(context.transientStack().getComponentsPatch());
        context.withTransientStack(stack);
    }
}
