package melonystudios.stancements.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class STConfig {
    private static final Pair<STConfig, ForgeConfigSpec> CONFIG_PAIR = new ForgeConfigSpec.Builder().configure(STConfig::new);
    public static final STConfig COMMON_CONFIGS = CONFIG_PAIR.getLeft();
    public static final ForgeConfigSpec COMMON_SPEC = CONFIG_PAIR.getRight();

    // Items
    public final ForgeConfigSpec.BooleanValue populatePaintingVariants;
    public final ForgeConfigSpec.BooleanValue populateFireworkRocketDurations;
    public final ForgeConfigSpec.BooleanValue addOminousBannerToTab;
    public final ForgeConfigSpec.BooleanValue addTermianEmpireBannerToTab;
    public final ForgeConfigSpec.BooleanValue addBeehiveTooltips;
    public final ForgeConfigSpec.BooleanValue removePotionGlint;

    public STConfig(ForgeConfigSpec.Builder builder) {
        builder.push("items");
        this.populatePaintingVariants = builder.comment("Whether to add all painting variants to the creative menu.").define("populatePaintingVariants", true);
        this.populateFireworkRocketDurations = builder.comment("Whether to add all 3 firework rocket durations to the creative menu.").define("populateFireworkRocketDurations", true);
        this.addOminousBannerToTab = builder.comment("Whether to add ominous banners to the creative menu.").define("addOminousBannerToTab", true);
        this.addTermianEmpireBannerToTab = builder.comment("Whether to add termian empire banners (from Back Math) to the creative menu.").define("addTermianEmpireBannerToTab", true);
        this.addBeehiveTooltips = builder.comment("Whether to add the \"Honey\" and \"Bees\" tooltips to bee nests and beehives.").define("addBeehiveTooltips", true);
        this.removePotionGlint = builder.comment("Whether to remove the enchantment glint from all potion types.").define("removePotionGlint", true);
        builder.pop();
    }
}
