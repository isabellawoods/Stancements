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
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public record RecordableTransform(Holder<Item> onRecord, Holder<Item> onRemix, Holder<Item> onWrite) {
    public static final Codec<RecordableTransform> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.ITEM.holderByNameCodec().optionalFieldOf(Transforms.ON_RECORD.getSerializedName(), STItems.RECORDED_DISC).forGetter(RecordableTransform::onRecord),
            BuiltInRegistries.ITEM.holderByNameCodec().optionalFieldOf(Transforms.ON_REMIX.getSerializedName(), STItems.RECORDED_DISC).forGetter(RecordableTransform::onRecord),
            BuiltInRegistries.ITEM.holderByNameCodec().optionalFieldOf(Transforms.ON_WRITE.getSerializedName(), STItems.RECORDED_DISC).forGetter(RecordableTransform::onRecord)
    ).apply(instance, RecordableTransform::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, RecordableTransform> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(Registries.ITEM),
            RecordableTransform::onRecord,
            ByteBufCodecs.holderRegistry(Registries.ITEM),
            RecordableTransform::onRemix,
            ByteBufCodecs.holderRegistry(Registries.ITEM),
            RecordableTransform::onWrite,
            RecordableTransform::new
    );

    public static RecordableTransform vinylDisc() {
        return forAll(STItems.RECORDED_DISC);
    }

    public static RecordableTransform sculkInfestedVinylDisc() {
        return forAll(STItems.SCULK_INFESTED_RECORDED_DISC);
    }

    public static RecordableTransform forAll(Holder<Item> recorded) {
        return new RecordableTransform(recorded, recorded, recorded);
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("RecordableTransform[onRecord=%s, onRemix=%s, onWrite=%s]",
                this.onRecord().getRegisteredName(),
                this.onRemix().getRegisteredName(),
                this.onWrite().getRegisteredName()
        );
    }

    public enum Transforms implements StringRepresentable {
        ON_RECORD("on_record"), // music recorder -> recorded disc
        ON_REMIX("on_remix"), // music remixer -> remixed disc
        ON_WRITE("on_write"); // cassette manager block -> recorded disc

        public static final Transforms[] VALUES = Transforms.values();
        public static final Codec<Transforms> CODEC = StringRepresentable.fromEnum(() -> VALUES);
        private final String name;

        Transforms(String name) {
            this.name = name;
        }

        @Override
        @NotNull
        public String getSerializedName() {
            return this.name;
        }

        public Item itemFor(RecordableTransform transform) {
            return switch (this) {
                case ON_RECORD -> transform.onRecord().value();
                case ON_REMIX -> transform.onRemix().value();
                case ON_WRITE -> transform.onWrite().value();
            };
        }
    }
}
