package melonystudios.stancements.option;

import net.neoforged.neoforge.common.ModConfigSpec;

public class STCommonOptions {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Crop pots
    public static final ModConfigSpec.IntValue CROP_POT_GROWTH_CHANCE = BUILDER.comment("The chance of a planted crop pot advancing its growth stage.", "Vanilla defaults to 1 in 25, and Stancements to 1 in 15.").translation("option.stancements.crop_pot_growth_chance").defineInRange("block.cropPotGrowthChance", 15, 1, 100);

    // Gilded rails
    public static final ModConfigSpec.DoubleValue GILDED_RAIL_SPEED_MULTIPLIER = BUILDER.comment("The base speed multiplier applied to minecarts in gilded rails.").translation("option.stancements.gilded_rail_speed_multiplier").defineInRange("block.gildedRail.speedMultiplier", 1.2D, 0, 5);
    public static final ModConfigSpec.IntValue GILDED_RAIL_ACCELERATION_TIME = BUILDER.comment("How many ticks the minecart takes to reach maximum speed on this rail.").translation("option.stancements.gilded_rail_acceleration_time").defineInRange("block.gildedRail.maxAcceleration", 100, 1, 1200);

    // Music recorder
    public static final ModConfigSpec.IntValue DEFAULT_RECORDING_DURATION = BUILDER.comment("How long ambient song recordings should be. Defaults to 600 ticks (30 seconds).").translation("option.stancements.default_recording_duration").defineInRange("block.defaultRecordingDuration", 600, 1, 72000);
    public static final ModConfigSpec.BooleanValue RECORDER_FREE_WILL = BUILDER.comment("Whether the music recorder has the right to eject discs out by itself after a certain amount of time.").translation("option.stancements.recorder_free_will").define("block.recorderFreeWill", true);
    public static final ModConfigSpec.BooleanValue RECORDED_DISC_AUTO_CONVERSION = BUILDER.comment("Whether recorded discs should try to automatically re-record themselves if they don't have an existing song.").translation("option.stancements.recorded_disc_auto_conversion").define("item.recordedDiscAutoConversion", true);

    // Miscellaneous
    public static final ModConfigSpec.BooleanValue POPULATE_DYED_WATER_BUCKETS = BUILDER.comment("Whether to populate all dyed water buckets in Stancements' creative tab.").worldRestart().translation("option.stancements.populate_dyed_water_buckets").define("item.populateDyedWaterBuckets", true);
    public static final ModConfigSpec.BooleanValue ADD_ITEMS_TO_VANILLA_TABS = BUILDER.comment("Whether to add Stancements' items to vanilla creative tabs instead of its own.").worldRestart().translation("option.stancements.add_items_to_vanilla_tabs").define("item.addItemsToVanillaTabs", false);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
