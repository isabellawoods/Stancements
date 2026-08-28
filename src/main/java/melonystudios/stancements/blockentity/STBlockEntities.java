package melonystudios.stancements.blockentity;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.blockentity.custom.AlbumBlockEntity;
import melonystudios.stancements.blockentity.custom.DyedWaterCauldronBlockEntity;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STBlockEntities { // block en-titties
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Stancements.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MusicRecorderBlockEntity>> MUSIC_RECORDER = BLOCK_ENTITIES.register("music_recorder",
            () -> BlockEntityType.Builder.of(MusicRecorderBlockEntity::new, STBlocks.MUSIC_RECORDER.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DyedWaterCauldronBlockEntity>> DYED_WATER_CAULDRON = BLOCK_ENTITIES.register("dyed_water_cauldron",
            () -> BlockEntityType.Builder.of(DyedWaterCauldronBlockEntity::new, STBlocks.DYED_WATER_CAULDRON.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AlbumBlockEntity>> ALBUM = BLOCK_ENTITIES.register("album",
            () -> BlockEntityType.Builder.of(AlbumBlockEntity::new, STBlocks.ALBUM.get()).build(null));
}
