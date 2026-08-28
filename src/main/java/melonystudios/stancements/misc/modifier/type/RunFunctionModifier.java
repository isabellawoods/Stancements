package melonystudios.stancements.misc.modifier.type;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.stancements.misc.modifier.ModificationContext;
import melonystudios.stancements.misc.modifier.ModifierComponentType;
import melonystudios.stancements.misc.modifier.VinylModifier;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.functions.CommandFunction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.MarkerFactory;

import java.util.Optional;

/// Copy of {@link net.minecraft.world.item.enchantment.effects.RunFunction RunFunction} that works with vinyl modifiers.
public record RunFunctionModifier(ResourceLocation function) implements ModifierComponentType {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Codec<RunFunctionModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("function").forGetter(RunFunctionModifier::function)
    ).apply(instance, RunFunctionModifier::new));

    @Override
    public void onApplyModifiers(ModificationContext context, Holder<VinylModifier> modifier) {
        MinecraftServer server = context.level().getServer();
        ServerFunctionManager functionManager = server.getFunctions();
        Optional<CommandFunction<CommandSourceStack>> function = functionManager.get(this.function);

        if (function.isPresent()) {
            CommandSourceStack sourceStack = server.createCommandSourceStack()
                    .withPermission(Commands.LEVEL_GAMEMASTERS)
                    .withSuppressedOutput()
                    .withLevel(context.level())
                    .withPosition(context.blockPosition().getCenter());

            Player player = context.playerOrNull();
            if (player != null) sourceStack.withEntity(player).withRotation(player.getRotationVector());

            functionManager.execute(function.get(), sourceStack);
        } else {
            LOGGER.error(MarkerFactory.getMarker(modifier.getRegisteredName()), "Vinyl modifier 'run_function' component failed due to a non-existent function: {}", this.function);
        }
    }
}
