package melonystudios.stancements.block;

import com.mojang.serialization.MapCodec;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.custom.*;
import melonystudios.stancements.block.custom.croppot.BeetrootCropPotBlock;
import melonystudios.stancements.block.custom.croppot.EmptyCropPotBlock;
import melonystudios.stancements.block.custom.croppot.FilledCropPotBlock;
import melonystudios.stancements.block.custom.croppot.NetherWartCropPotBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STBlockTypes {
    public static final DeferredRegister<MapCodec<? extends Block>> TYPES = DeferredRegister.create(Registries.BLOCK_TYPE, Stancements.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<? extends MusicRecorderBlock>> MUSIC_RECORDER = TYPES.register("music_recorder", () -> MusicRecorderBlock.CODEC);
    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<? extends ShelfBlock>> SHELF = TYPES.register("shelf", () -> ShelfBlock.CODEC);
    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<? extends CraftingTableClothBlock>> CRAFTING_TABLE_CLOTH = TYPES.register("crafting_table_cloth", () -> CraftingTableClothBlock.CODEC);
    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<? extends EmptyCropPotBlock>> EMPTY_CROP_POT = TYPES.register("crop_pot/empty", () -> EmptyCropPotBlock.CODEC);
    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<? extends FilledCropPotBlock>> FILLED_CROP_POT = TYPES.register("crop_pot/filled", () -> FilledCropPotBlock.CODEC);
    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<? extends BeetrootCropPotBlock>> BEETROOT_CROP_POT = TYPES.register("crop_pot/beetroots", () -> BeetrootCropPotBlock.CODEC);
    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<? extends NetherWartCropPotBlock>> NETHER_WART_CROP_POT = TYPES.register("crop_pot/nether_wart", () -> NetherWartCropPotBlock.CODEC);
    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<? extends TaggingRailBlock>> TAGGING_RAIL = TYPES.register("tagging_rail", () -> TaggingRailBlock.CODEC);
    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<? extends AlbumBlock>> ALBUM = TYPES.register("album", () -> AlbumBlock.CODEC);
    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<? extends CasingBlock>> CASING = TYPES.register("casing", () -> CasingBlock.CODEC);
    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<? extends MusicRemixerBlock>> MUSIC_REMIXER = TYPES.register("music_remixer", () -> MusicRemixerBlock.CODEC);
}
