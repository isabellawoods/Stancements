package melonystudios.stancements.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.client.option.STClientOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mod(value = Stancements.MOD_ID, dist = Dist.CLIENT)
public class STClient {
    /// A **queue** of all music discs blocking the {@link net.minecraft.client.sounds.MusicManager MusicManager} from playing.
    public static final Queue<SoundInstance> DISCS_BLOCKING_MUSIC = new ConcurrentLinkedQueue<>();
    public static final int TRANSPARENT_TEXT_BACKDROP = 0x7F000000;
    public static final Logger LOGGER = LogUtils.getLogger();

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

    /// Gets the accent color for a given mod, falling back to a default if one is not defined. Defaults to **#FFFFA0** in most of this method's usages.
    ///
    /// The accent color is stored in the `rendersliceapi:accent_color` property under the `[modproperties.<mod ID>]` block of the `neoforge.mods.toml` file.
    /// @param modID The mod ID used to find the color.
    /// @param defaultColor A fallback color in case the mod doesn't have a color defined, or if an error occurs when parsing it.
    public static Integer accentColorOrDefault(String modID, int defaultColor) {
        Optional<? extends ModContainer> container = ModList.get().getModContainerById(modID);
        if (container.isPresent()) {
            Map<String, Object> properties = container.get().getModInfo().getModProperties();
            Object object = properties.getOrDefault("rendersliceapi:accent_color", -1);

            JsonElement element;
            if (object instanceof Number number) element = new JsonPrimitive(number);
            else if (object instanceof String string) element = new JsonPrimitive(string);
            else return defaultColor;

            return ExtraCodecs.STRING_RGB_COLOR.decode(JsonOps.INSTANCE, element)
                    .resultOrPartial(error -> LOGGER.error("Failed to decode \"rendersliceapi:accent_color\" property from mod '{}'\n{}", modID, error))
                    .map(Pair::getFirst)
                    .orElse(defaultColor);
        }
        return defaultColor;
    }
}
