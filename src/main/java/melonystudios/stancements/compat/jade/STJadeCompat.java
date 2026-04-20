package melonystudios.stancements.compat.jade;

import melonystudios.stancements.block.custom.MusicRecorderBlock;
import melonystudios.stancements.blockentity.custom.MusicRecorderBlockEntity;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class STJadeCompat implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(MusicRecorderProvider.INSTANCE, MusicRecorderBlockEntity.class);
        registration.registerEntityDataProvider(MinecartTagsProvider.INSTANCE, AbstractMinecart.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(MusicRecorderProvider.Client.INSTANCE, MusicRecorderBlock.class);
        registration.registerEntityComponent(MinecartTagsProvider.Client.INSTANCE, AbstractMinecart.class);
    }
}
