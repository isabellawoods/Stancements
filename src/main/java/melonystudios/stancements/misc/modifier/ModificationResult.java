package melonystudios.stancements.misc.modifier;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

/// The result after running the *music recording pipeline*.
/// @param template The recordable disc {@link ItemStackTemplate} after running all modifications.
/// @param recordingText The action bar text to show, if any.
public record ModificationResult(ItemStackTemplate template, Component recordingText) {
    /// @return The recordable disc {@link ItemStack} after running all modifications.
    public ItemStack stack() {
        return this.template().create();
    }
}
