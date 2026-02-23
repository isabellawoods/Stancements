package melonystudios.stancements.option;

import net.neoforged.neoforge.common.ModConfigSpec;

public class STOptions {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue CROP_POT_GROWTH_CHANCE = BUILDER.comment("The chance of a planted crop pot advancing its growth stage.", "Vanilla defaults to 1 in 25, and Stancements to 1 in 15.").translation("option.stancements.crop_pot_growth_chance").defineInRange("block.cropPotGrowthChance", 15, 1, 100);
    public static final ModConfigSpec.DoubleValue GILDED_RAIL_SPEED_MULTIPLIER = BUILDER.comment("The base speed multiplier applied to minecarts in gilded rails.").translation("option.stancements.gilded_rail_speed_multiplier").defineInRange("block.gildedRail.speedMultiplier", 1.2F, 0, 5);
    public static final ModConfigSpec.IntValue GILDED_RAIL_ACCELERATION_TIME = BUILDER.comment("How many ticks the minecart takes to reach maximum speed on this rail.").translation("option.stancements.gilded_rail_acceleration_time").defineInRange("block.gildedRail.maxAcceleration", 100, 1, 1200);
    public static final ModConfigSpec.BooleanValue POPULATE_DYED_WATER_BUCKETS = BUILDER.comment("Whether to populate all dyed water buckets in Stancements' creative tab.").worldRestart().translation("option.stancements.populate_dyed_water_buckets").define("item.populateDyedWaterBuckets", true);
    public static final ModConfigSpec.BooleanValue ADD_ITEMS_TO_VANILLA_TABS = BUILDER.comment("Whether to add Stancements' items to vanilla creative tabs instead of its own.").worldRestart().translation("option.stancements.add_items_to_vanilla_tabs").define("item.addItemsToVanillaTabs", false);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
