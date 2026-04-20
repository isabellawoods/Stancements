package melonystudios.reutilities.api;

import melonystudios.reutilities.component.ReDataComponents;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

import java.util.List;

/// ***Reutilities'*** **API** class, used by my mods to add new boats and signs, register item overrides, get light emission values, etc.
public final class ReAPI {
    /// Adds a block to the flammability map.
    ///
    /// This method should be called during the {@linkplain net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent common setup event}.
    /// @param block The block.
    /// @param encouragement The chance of fire wanting to spread to this block.
    /// @param flammability The chance that this block will actually burn out when on fire.
    public static void flammable(Block block, int encouragement, int flammability) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, encouragement, flammability);
    }

    /// Whether a tooltip can be displayed on an item, or is hidden by the {@link ReDataComponents#HIDE_COMPONENTS reutilities:hide_components} component.
    /// @param components The item stack.
    /// @param name A resource location of the tooltip name, like `reutilities:item_components`.
    public static boolean shouldDisplay(DataComponentGetter components, Identifier name) {
        List<Identifier> itemTags = components.get(ReDataComponents.HIDE_COMPONENTS);
        if (itemTags == null || itemTags.isEmpty()) return true;
        return !itemTags.contains(name);
    }
}
