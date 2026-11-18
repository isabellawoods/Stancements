package melonystudios.stancements.data.model;

import melonystudios.stancements.Stancements;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;

import java.util.Optional;

public class STModelTemplates {
    public static final ModelTemplate SHELF = new ModelTemplate(
            Optional.of(Stancements.stancements("block/template_shelf")),
            Optional.empty(),
            STTextureSlots.SHELF, STTextureSlots.SHELF_SUPPORT
    );
    public static final ModelTemplate CRAFTING_TABLE_CLOTH = new ModelTemplate(
            Optional.of(Stancements.stancements("block/template_crafting_table_cloth")),
            Optional.empty(),
            TextureSlot.TOP, TextureSlot.SIDE
    );
    public static final ModelTemplate CROP_POT_EMPTY = new ModelTemplate(
            Optional.of(Stancements.stancements("block/template_crop_pot")),
            Optional.empty(),
            STTextureSlots.POT
    );
    public static final ModelTemplate CROP_POT_FULL = new ModelTemplate(
            Optional.of(Stancements.stancements("block/template_full_crop_pot")),
            Optional.empty(),
            STTextureSlots.POT, TextureSlot.CROP
    );
    public static final ModelTemplate MUSIC_REMIXER_OPEN = new ModelTemplate(
            Optional.of(Stancements.stancements("block/template_music_remixer_open")),
            Optional.empty()
    );
    public static final ModelTemplate MUSIC_REMIXER_CLOSED = new ModelTemplate(
            Optional.of(Stancements.stancements("block/template_music_remixer_closed")),
            Optional.empty(),
            STTextureSlots.CASING_SIDE, STTextureSlots.CASING_END
    );
    public static final ModelTemplate CASING_UNSUPPORTED = new ModelTemplate(
            Optional.of(Stancements.stancements("block/template_casing")),
            Optional.empty(),
            TextureSlot.SIDE, TextureSlot.END
    );
    public static final ModelTemplate CASING_SUPPORTED = new ModelTemplate(
            Optional.of(Stancements.stancements("block/template_supported_casing")),
            Optional.empty(),
            TextureSlot.SIDE, TextureSlot.END
    );
}
