package melonystudios.stancements.misc.modifier;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

/// The result after running the *music recording pipeline*.
/// @param stack The recordable disc {@link ItemStack} after running all modifications.
/// @param recordingText The action bar text to show, if any.
public record ModificationResult(ItemStack stack, Component recordingText) {
}
