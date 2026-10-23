package melonystudios.stancements.command.argument;

import melonystudios.stancements.Stancements;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STArgumentTypes {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> TYPES = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, Stancements.MOD_ID);

    public static final Holder<ArgumentTypeInfo<?, ?>> STRING_REPRESENTABLE_ENUM = TYPES.register("string_representable_enum", () -> ArgumentTypeInfos.registerByClass(StringRepresentableEnumArgument.class, new StringRepresentableEnumArgument.Info()));
}
