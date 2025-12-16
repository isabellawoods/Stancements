package melonystudios.stancements.misc.attachment;

import melonystudios.stancements.Stancements;
import net.neoforged.neoforge.capabilities.EntityCapability;

public class STCapabilities {
    public static final EntityCapability<MinecartTags, Void> MINECART_TAGS = EntityCapability.createVoid(Stancements.stancements("minecart_tags"), MinecartTags.class);
}
