package melonystudios.stancements.blockentity;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.STBlocks;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class STBlockEntities { // block en-titty
    public static final DeferredRegister<TileEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Stancements.MOD_ID);

    public static final RegistryObject<TileEntityType<MusicRecorderBlockEntity>> MUSIC_RECORDER = BLOCK_ENTITIES.register("music_recorder",
            () -> TileEntityType.Builder.of(MusicRecorderBlockEntity::new, STBlocks.MUSIC_RECORDER.get()).build(null));
}
