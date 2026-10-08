package melonystudios.stancements.misc.loot;

import melonystudios.stancements.misc.modifier.ModificationContext;

/// Interface that provides the [modification context][ModificationContext] for any loot function or loot condition that requires it.
public interface ModificationContextAware {
    /// Sets the context of the loot function or loot condition.
    /// @param context The context.
    void withContext(ModificationContext context);
}
