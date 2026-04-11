package melonystudios.stancements.component.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.item.STItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

public record RecordingTurnsInto(Holder<Item> whenRecorded) {
    public static final Codec<RecordingTurnsInto> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.ITEM.holderByNameCodec().optionalFieldOf("when_recorded", STItems.RECORDED_DISC.getDelegate()).forGetter(RecordingTurnsInto::whenRecorded)
    ).apply(instance, RecordingTurnsInto::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, RecordingTurnsInto> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(Registries.ITEM),
            RecordingTurnsInto::whenRecorded,
            RecordingTurnsInto::new
    );

    public static RecordingTurnsInto vinylDisc() {
        return new RecordingTurnsInto(STItems.RECORDED_DISC.getDelegate());
    }
}
