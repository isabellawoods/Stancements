package melonystudios.stancements.util;

/// Debugging flags used for *Stancements*' development.
public class STDebuggingFlags implements ReDebuggingFlags {
    public static final STDebuggingFlags FLAGS = new STDebuggingFlags();

    /// Enables debug logs for the *music recording pipeline*.
    /// @since 0.4.4
    /// @see melonystudios.stancements.misc.modifier.VinylModifier#checkAndRun VinylModifier.checkAndRun
    /// @see melonystudios.stancements.misc.album.Album#DIRECT_CODEC Album.DIRECT_CODEC
    public static final boolean LOGGING = FLAGS.locateBooleanFlag("logging", false);

    @Override
    public String getPrefix() {
        return "st";
    }
}
