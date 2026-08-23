package melonystudios.stancements.client;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.client.option.STClientOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mod(value = Stancements.MOD_ID, dist = Dist.CLIENT)
public class STClient {
    /// A **queue** of all music discs blocking the {@link net.minecraft.client.sounds.MusicManager MusicManager} from playing.
    public static final Queue<SoundInstance> DISCS_BLOCKING_MUSIC = new ConcurrentLinkedQueue<>();

    public STClient(IEventBus eventBus, ModContainer container) {
        eventBus.addListener(this::clientSetup);

        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        container.registerConfig(ModConfig.Type.CLIENT, STClientOptions.SPEC, "melonystudios/stancements-client.toml");
    }

    private void clientSetup(final FMLClientSetupEvent event) {}

    /// @return Whether a music disc in the {@link melonystudios.stancements.tag.STJukeboxSongTags#CANCELS_AMBIENT_MUSIC #stancements:cancels_ambient_music}
    /// jukebox song tag is currently playing.
    public static boolean isMusicDiscPlaying() {
        for (SoundInstance sound : DISCS_BLOCKING_MUSIC) {
            if (Minecraft.getInstance().getSoundManager().isActive(sound)) return true;
            DISCS_BLOCKING_MUSIC.remove(sound);
        }

        return !DISCS_BLOCKING_MUSIC.isEmpty();
    }
}
