package melonystudios.stancements.event;

import melonystudios.stancements.Stancements;
import melonystudios.stancements.command.UpdateRecordedDiscCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = Stancements.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class STNeoForgeEvents {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        UpdateRecordedDiscCommand.register(event.getDispatcher());
    }
}
