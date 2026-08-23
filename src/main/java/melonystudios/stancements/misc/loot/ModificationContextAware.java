package melonystudios.stancements.misc.loot;

import melonystudios.stancements.misc.modifier.ModificationContext;

/// Interface that provides the {@linkplain ModificationContext modification context} for any loot function or loot condition that requires it.
public interface ModificationContextAware {
    /// Sets the context of the loot function or loot condition.
    /// @param context The context.
    void withContext(ModificationContext context);
}
