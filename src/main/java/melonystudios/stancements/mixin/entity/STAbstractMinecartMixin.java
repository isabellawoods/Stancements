package melonystudios.stancements.mixin.entity;

import com.llamalad7.mixinextras.sugar.Local;
import melonystudios.stancements.block.custom.GildedRailBlock;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.misc.attachment.MinecartTags;
import melonystudios.stancements.misc.attachment.STCapabilities;
import melonystudios.stancements.sound.STSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.extensions.IAbstractMinecartExtension;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static melonystudios.stancements.block.custom.GildedRailBlock.GILDED_RAIL_SPEED_MULTIPLIER;

@Mixin(AbstractMinecart.class)
public abstract class STAbstractMinecartMixin extends VehicleEntity implements IAbstractMinecartExtension {
    @Unique
    private int ticksInGildedRail = 0;

    public STAbstractMinecartMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Override
    @NotNull
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        MinecartTags tags = this.getCapability(STCapabilities.MINECART_TAGS);
        if (tags == null) return super.interact(player, hand);
        Level world = this.level();

        if (handStack.has(STDataComponents.MINECART_TAG_COLOR) && tags.addTag(handStack.get(STDataComponents.MINECART_TAG_COLOR).color())) {
            world.playSound(null, this.blockPosition(), STSounds.TAG_MINECART.get(), SoundSource.NEUTRAL, 1, 1);
            handStack.shrink(1);
            return InteractionResult.SUCCESS;
        } else if (handStack.is(Tags.Items.TOOLS_SHEAR)) {
            boolean shearedTag = false;
            for (DyeColor color : tags.tagColors()) {
                ItemEntity item = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), new ItemStack(MinecartTags.getTagForColor(color)));
                item.setDefaultPickUpDelay();
                if (!world.isClientSide()) {
                    world.addFreshEntity(item);
                    handStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                }
                this.minecart().gameEvent(GameEvent.SHEAR, player);
                world.playSound(null, this.minecart(), STSounds.SHEAR_MINECART.get(), SoundSource.NEUTRAL, 1, 1);
                shearedTag = true;
            }
            tags.clearTags();
            if (shearedTag) return InteractionResult.sidedSuccess(world.isClientSide());
        }
        return super.interact(player, hand);
    }

    @Inject(method = "moveMinecartOnRail", at = @At("HEAD"), remap = false)
    public void updateMaxSpeedOnRail(BlockPos pos, CallbackInfo callback) {
        BlockState state = this.level().getBlockState(pos);
        if (state.getBlock() instanceof GildedRailBlock) this.ticksInGildedRail = Mth.clamp(this.ticksInGildedRail + 5, 0, 80);
        else this.ticksInGildedRail = Mth.clamp(this.ticksInGildedRail - 5, 0, 80);

        if (this.ticksInGildedRail > 0) this.setCurrentCartSpeedCapOnRail(this.getMaxCartSpeedOnRail() * GILDED_RAIL_SPEED_MULTIPLIER);
        else this.setCurrentCartSpeedCapOnRail(this.getMaxCartSpeedOnRail());
    }

    @ModifyArg(method = "getMaxSpeedWithRail", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(FF)F", ordinal = 0), index = 0)
    public float maintainSpeedAfterLeavingGilded(float a, @Local BlockState state, @Local BlockPos pos) {
        BaseRailBlock rail = (BaseRailBlock) state.getBlock();
        return this.ticksInGildedRail > 0 ? rail.getRailMaxSpeed(state, this.level(), pos, this.minecart()) * (rail.getRailDirection(state, this.level(), pos, this.minecart()).isAscending() ? 1 : GILDED_RAIL_SPEED_MULTIPLIER) : a;
    }

    @Override
    public float getMaxCartSpeedOnRail() {
        return this.ticksInGildedRail > 0 ? GILDED_RAIL_SPEED_MULTIPLIER : IAbstractMinecartExtension.super.getMaxCartSpeedOnRail();
    }

    @Unique
    private AbstractMinecart minecart() {
        Object object = this;
        return (AbstractMinecart) object;
    }
}
