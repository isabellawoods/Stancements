package melonystudios.stancements.misc.attachment;

import melonystudios.stancements.Stancements;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;

public class STAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Stancements.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<MinecartTags>> MINECART_TAGS = ATTACHMENTS.register("minecart_tags",
            () -> AttachmentType.serializable(holder -> new MinecartTags(holder, new ArrayList<>(16))).sync(MinecartTags.TagsSyncHandler.INSTANCE).build());
}
