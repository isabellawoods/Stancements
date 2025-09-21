package melonystudios.stancements;

import net.neoforged.neoforge.common.ModConfigSpec;

public class STConfigs {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue POPULATE_DYED_WATER_BUCKETS = BUILDER.comment("Whether to populate all dyed water buckets in Stancements' creative tab.").worldRestart().translation("config.stancements.populate_dyed_water_buckets").define("item.populateDyedWaterBuckets", true);
    public static final ModConfigSpec.IntValue CROP_POT_GROWTH_CHANCE = BUILDER.comment("The chance of a planted crop pot advancing its growth stage.", "Vanilla defaults to 1 in 25, and Stancements to 1 in 15.").translation("config.stancements.crop_pot_growth_chance").defineInRange("block.cropPotGrowthChance", 15, 1, 100);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
