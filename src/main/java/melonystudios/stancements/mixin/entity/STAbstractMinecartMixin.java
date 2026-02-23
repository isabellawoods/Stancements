package melonystudios.stancements.mixin.entity;

import com.llamalad7.mixinextras.sugar.Local;
import melonystudios.stancements.block.custom.GildedRailBlock;
import melonystudios.stancements.component.STDataComponents;
import melonystudios.stancements.misc.attachment.MinecartTags;
import melonystudios.stancements.misc.attachment.STCapabilities;
import melonystudios.stancements.option.STOptions;
import melonystudios.stancements.sound.STSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
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

@Mixin(AbstractMinecart.class)
public abstract class STAbstractMinecartMixin extends VehicleEntity implements IAbstractMinecartExtension {
    @Unique
    private int gildedSpeedBuildup = 0;

    public STAbstractMinecartMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    @NotNull
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        MinecartTags tags = this.getCapability(STCapabilities.MINECART_TAGS);
        if (tags == null) return super.interact(player, hand);
        Level level = this.level();

        if (handStack.has(STDataComponents.MINECART_TAG_COLOR) && tags.addTag(handStack.get(STDataComponents.MINECART_TAG_COLOR).color())) {
            level.playSound(null, this.blockPosition(), STSounds.TAG_MINECART.get(), SoundSource.NEUTRAL, 1, 1);
            player.awardStat(Stats.ITEM_USED.get(handStack.getItem()));
            handStack.shrink(1);
            return InteractionResult.SUCCESS;
        } else if (handStack.is(Tags.Items.TOOLS_SHEAR)) {
            boolean shearedTag = false;
            for (DyeColor color : tags.tagColors()) {
                this.spawnAtLocation(MinecartTags.getTagForColor(color));
                this.minecart().gameEvent(GameEvent.SHEAR, player);
                player.awardStat(Stats.ITEM_USED.get(handStack.getItem()));
                level.playSound(null, this.minecart(), STSounds.SHEAR_MINECART.get(), SoundSource.NEUTRAL, 1, 1);
                shearedTag = true;
            }
            tags.clearTags();
            if (shearedTag) return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return super.interact(player, hand);
    }

    @Override
    protected void destroy(DamageSource source) {
        MinecartTags tags = this.getCapability(STCapabilities.MINECART_TAGS);
        if (tags != null) {
            for (DyeColor color : tags.tagColors()) this.spawnAtLocation(MinecartTags.getTagForColor(color));
            tags.clearTags();
        }
        super.destroy(source);
    }

    @Inject(method = "moveMinecartOnRail", at = @At("HEAD"), remap = false)
    public void updateMaxSpeedOnRail(BlockPos pos, CallbackInfo callback) {
        BlockState state = this.level().getBlockState(pos);
        float speedMultiplier = STOptions.GILDED_RAIL_SPEED_MULTIPLIER.get().floatValue();
        int accelerationTime = STOptions.GILDED_RAIL_ACCELERATION_TIME.get();

        if (state.getBlock() instanceof GildedRailBlock) this.gildedSpeedBuildup = Mth.clamp(this.gildedSpeedBuildup + 5, 0, accelerationTime);
        else this.gildedSpeedBuildup = Mth.clamp(this.gildedSpeedBuildup - 5, 0, accelerationTime);

        if (this.gildedSpeedBuildup > 0) this.setCurrentCartSpeedCapOnRail(this.getMaxCartSpeedOnRail() * Math.max(speedMultiplier, speedMultiplier * ((float) this.gildedSpeedBuildup / accelerationTime)));
        else this.setCurrentCartSpeedCapOnRail(this.getMaxCartSpeedOnRail());
    }

    @ModifyArg(method = "getMaxSpeedWithRail", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(FF)F", ordinal = 0), index = 0)
    public float maintainSpeedAfterLeavingGilded(float railMaxSpeed, @Local BlockState state, @Local BlockPos pos) {
        BaseRailBlock rail = (BaseRailBlock) state.getBlock();
        return this.gildedSpeedBuildup > 0 ? rail.getRailMaxSpeed(state, this.level(), pos, this.minecart()) * (rail.getRailDirection(state, this.level(), pos, this.minecart()).isAscending() ? 1 : STOptions.GILDED_RAIL_SPEED_MULTIPLIER.get().floatValue()) : railMaxSpeed;
    }

    @Override
    public float getMaxCartSpeedOnRail() {
        return this.gildedSpeedBuildup > 0 ? STOptions.GILDED_RAIL_SPEED_MULTIPLIER.get().floatValue() : IAbstractMinecartExtension.super.getMaxCartSpeedOnRail();
    }

    @Unique
    private AbstractMinecart minecart() {
        Object object = this;
        return (AbstractMinecart) object;
    }
}
