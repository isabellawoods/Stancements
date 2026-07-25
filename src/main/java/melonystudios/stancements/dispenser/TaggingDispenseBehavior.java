package melonystudios.stancements.dispenser;

import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.misc.attachment.MinecartTags;
import melonystudios.stancements.misc.attachment.STCapabilities;
import melonystudios.stancements.sound.STSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.NonNull;

public class TaggingDispenseBehavior extends DefaultDispenseItemBehavior {
    @Override
    @NonNull
    protected ItemStack execute(BlockSource source, ItemStack stack) {
        ServerLevel level = source.level();
        if (!level.isClientSide() && stack.has(STDataComponents.MINECART_TAG_COLOR)) {
            DyeColor color = stack.get(STDataComponents.MINECART_TAG_COLOR).color();
            BlockPos pos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));

            tryTaggingMinecart(level, pos, stack, color);
        }
        return stack;
    }

    private static void tryTaggingMinecart(ServerLevel level, BlockPos pos, ItemStack stack, DyeColor color) {
        for (AbstractMinecart minecart : level.getEntitiesOfClass(AbstractMinecart.class, new AABB(pos), minecart -> {
            var tags = minecart.getCapability(STCapabilities.MINECART_TAGS);
            return tags == null || !tags.tagColors().contains(color);
        })) {
            var tags = minecart.getCapability(STCapabilities.MINECART_TAGS);
            if (tags != null && tags.addTag(color)) {
                level.playSound(null, pos, STSounds.TAG_MINECART.get(), SoundSource.NEUTRAL, 1, 1);
                stack.shrink(1);
                return;
            }
        }
    }

    public static boolean tryShearingOffMinecartTags(ServerLevel level, BlockPos pos) {
        for (AbstractMinecart minecart : level.getEntitiesOfClass(AbstractMinecart.class, new AABB(pos), minecart -> {
            var tags = minecart.getCapability(STCapabilities.MINECART_TAGS);
            return tags != null && !tags.tagColors().isEmpty();
        })) {
            var tags = minecart.getCapability(STCapabilities.MINECART_TAGS);
            if (tags == null) return false;

            for (DyeColor color : tags.tagColors()) {
                minecart.spawnAtLocation(level, MinecartTags.getTagForColor(color));
                minecart.gameEvent(GameEvent.SHEAR, minecart);
            }
            tags.clearTags();
            return true;
        }
        return false;
    }
}
