package melonystudios.stancements.block;

import com.mojang.serialization.MapCodec;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.custom.CraftingTableClothBlock;
import melonystudios.stancements.block.custom.TaggingRailBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class STBlockTypes {
    public static final DeferredRegister<MapCodec<? extends Block>> TYPES = DeferredRegister.create(Registries.BLOCK_TYPE, Stancements.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<? extends CraftingTableClothBlock>> CRAFTING_TABLE_CLOTH = TYPES.register("crafting_table_cloth", () -> CraftingTableClothBlock.CODEC);
    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<? extends TaggingRailBlock>> TAGGING_RAIL = TYPES.register("tagging_rail", () -> TaggingRailBlock.CODEC);
}
