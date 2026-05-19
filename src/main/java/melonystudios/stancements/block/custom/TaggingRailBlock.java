package melonystudios.stancements.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import melonystudios.reutilities.api.ReAPI;
import melonystudios.stancements.Stancements;
import melonystudios.stancements.block.TagMatcherType;
import melonystudios.stancements.misc.attachment.MinecartTags;
import melonystudios.stancements.misc.attachment.STCapabilities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class TaggingRailBlock extends BaseRailBlock {
    public static final MapCodec<? extends TaggingRailBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            DyeColor.CODEC.listOf().fieldOf("colors_to_detect").forGetter(TaggingRailBlock::detectsColors),
            TagMatcherType.CODEC.optionalFieldOf("matcher_type", TagMatcherType.ANY_OF).forGetter(TaggingRailBlock::matcherType),
            propertiesCodec()
    ).apply(instance, TaggingRailBlock::new));
    public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final int PRESSED_CHECK_PERIOD = 20;
    private final List<DyeColor> colorsToDetect;
    private final TagMatcherType matcherType;

    @Override
    @NotNull
    protected MapCodec<? extends TaggingRailBlock> codec() {
        return CODEC;
    }

    public TaggingRailBlock(DyeColor colorToDetect, Properties properties) {
        this(colorToDetect, TagMatcherType.ANY_OF, properties);
    }

    public TaggingRailBlock(DyeColor colorToDetect, TagMatcherType matcherType, Properties properties) {
        this(List.of(colorToDetect), matcherType, properties);
    }

    public TaggingRailBlock(List<DyeColor> colorsToDetect, TagMatcherType matcherType, Properties properties) {
        super(true, properties);
        this.colorsToDetect = colorsToDetect;
        this.matcherType = matcherType;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POWERED, false)
                .setValue(SHAPE, RailShape.NORTH_SOUTH)
                .setValue(WATERLOGGED, false)
        );
    }

    public List<DyeColor> detectsColors() {
        return this.colorsToDetect;
    }

    public TagMatcherType matcherType() {
        return this.matcherType;
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide() && !state.getValue(POWERED)) this.checkPressed(level, pos, state);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED)) this.checkPressed(level, pos, state);
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction side) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction side) {
        if (!state.getValue(POWERED)) {
            return 0;
        } else {
            return side == Direction.UP ? 15 : 0;
        }
    }

    private void checkPressed(Level level, BlockPos pos, BlockState state) {
        if (!this.canSurvive(state, level, pos)) return;
        boolean powered = state.getValue(POWERED);
        boolean hasTaggedCartOnTop = false;

        List<AbstractMinecart> minecarts = this.getInteractingMinecartOfType(level, pos, AbstractMinecart.class, minecart -> {
            MinecartTags tags = minecart.getCapability(STCapabilities.MINECART_TAGS);
            if (tags == null || tags.tagColors().isEmpty()) return true; // cart doesn't have tags, detect it anyway

            return this.matcherType().matcher().test(tags, this.detectsColors());
        });

        if (!minecarts.isEmpty()) hasTaggedCartOnTop = true;

        if (hasTaggedCartOnTop && !powered) {
            BlockState onState = state.setValue(POWERED, true);
            level.setBlock(pos, onState, 3);
            this.updatePowerToConnected(level, pos, onState, true);
            level.updateNeighborsAt(pos, this);
            level.updateNeighborsAt(pos.below(), this);
            level.setBlocksDirty(pos, state, onState);
        }

        if (!hasTaggedCartOnTop && powered) {
            BlockState offState = state.setValue(POWERED, false);
            level.setBlock(pos, offState, 3);
            this.updatePowerToConnected(level, pos, offState, false);
            level.updateNeighborsAt(pos, this);
            level.updateNeighborsAt(pos.below(), this);
            level.setBlocksDirty(pos, state, offState);
        }

        if (hasTaggedCartOnTop) level.scheduleTick(pos, this, PRESSED_CHECK_PERIOD);
        level.updateNeighbourForOutputSignal(pos, this);
    }

    protected void updatePowerToConnected(Level level, BlockPos pos, BlockState state, boolean powered) {
        RailState railState = new RailState(level, pos, state);

        for (BlockPos connection : railState.getConnections()) {
            BlockState connectedState = level.getBlockState(connection);
            level.neighborChanged(connectedState, connection, connectedState.getBlock(), pos, false);
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock())) {
            BlockState updatedState = this.updateState(state, level, pos, isMoving);
            this.checkPressed(level, pos, updatedState);
        }
    }

    @Override
    @NotNull
    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    public <T extends AbstractMinecart> List<T> getInteractingMinecartOfType(Level level, BlockPos pos, Class<T> minecartType, Predicate<T> filter) {
        return level.getEntitiesOfClass(minecartType, this.getSearchBounds(pos), filter);
    }

    private AABB getSearchBounds(BlockPos pos) {
        double inflateBy = 0.2;
        return new AABB(
                (double) pos.getX() + inflateBy,
                pos.getY(),
                (double) pos.getZ() + inflateBy,
                (double) (pos.getX() + 1) - inflateBy,
                (double) (pos.getY() + 1) - inflateBy,
                (double) (pos.getZ() + 1) - inflateBy
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (ReAPI.shouldDisplay(stack, Stancements.stancements("tagging_rail/tooltip")) && !this.detectsColors().isEmpty()) {
            MutableComponent colors = prettyPrintTagColors(this.matcherType(), this.detectsColors(), true);
            tooltip.add(Component.translatable("tooltip.stancements.tagging_rail." + (this.detectsColors().size() == 1 ? "single" : "multiple"), colors)
                    .withStyle(ChatFormatting.GRAY));

            tooltip.add(Component.translatable("tooltip.stancements.tagging_rail.tagless").withStyle(ChatFormatting.GRAY));
        }
    }

    /// Takes a list of {@link DyeColor DyeColors} and puts them into a single text component.
    /// @param matcher A {@linkplain TagMatcherType tag matcher} used for the list. Can be `all` for "and" and `any_of` for "or".
    /// @param colors The list of dye colors to print.
    /// @param italicize Whether the "and" or "or" at the end should be italicized.
    public static MutableComponent prettyPrintTagColors(TagMatcherType matcher, List<DyeColor> colors, boolean italicize) {
        MutableComponent component = Component.empty();
        for (int i = 0; i < colors.size(); ++i) {
            DyeColor color = colors.get(i);
            component.append(Component.translatable("color.minecraft." + color).withColor(color.getTextureDiffuseColor()));

            if (i == colors.size() - 2) {
                component.append(Component.translatable("tooltip.stancements.delimiter." + matcher.getSerializedName()).withStyle(style -> style.withItalic(italicize)));
            } else if (i != colors.size() - 1) {
                component.append(Component.translatable("tooltip.stancements.delimiter"));
            }
        }
        return component;
    }

    @Override
    @NotNull
    protected BlockState rotate(BlockState state, Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180:
                switch (state.getValue(SHAPE)) {
                    case ASCENDING_EAST: return state.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST: return state.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_NORTH: return state.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH: return state.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case SOUTH_EAST: return state.setValue(SHAPE, RailShape.NORTH_WEST);
                    case SOUTH_WEST: return state.setValue(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_WEST: return state.setValue(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_EAST: return state.setValue(SHAPE, RailShape.SOUTH_WEST);
                }
            case COUNTERCLOCKWISE_90:
                return switch (state.getValue(SHAPE)) {
                    case ASCENDING_EAST -> state.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case ASCENDING_WEST -> state.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_NORTH -> state.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_SOUTH -> state.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case SOUTH_EAST -> state.setValue(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST -> state.setValue(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST -> state.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST -> state.setValue(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_SOUTH -> state.setValue(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST -> state.setValue(SHAPE, RailShape.NORTH_SOUTH);
                };
            case CLOCKWISE_90:
                return switch (state.getValue(SHAPE)) {
                    case ASCENDING_EAST -> state.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_WEST -> state.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case ASCENDING_NORTH -> state.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_SOUTH -> state.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case SOUTH_EAST -> state.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST -> state.setValue(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST -> state.setValue(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST -> state.setValue(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_SOUTH -> state.setValue(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST -> state.setValue(SHAPE, RailShape.NORTH_SOUTH);
                };
            default: return state;
        }
    }

    @Override
    @NotNull
    protected BlockState mirror(BlockState state, Mirror mirror) {
        RailShape shape = state.getValue(SHAPE);
        return switch (mirror) {
            case LEFT_RIGHT -> switch (shape) {
                case ASCENDING_NORTH -> state.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                case ASCENDING_SOUTH -> state.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                case SOUTH_EAST -> state.setValue(SHAPE, RailShape.NORTH_EAST);
                case SOUTH_WEST -> state.setValue(SHAPE, RailShape.NORTH_WEST);
                case NORTH_WEST -> state.setValue(SHAPE, RailShape.SOUTH_WEST);
                case NORTH_EAST -> state.setValue(SHAPE, RailShape.SOUTH_EAST);
                default -> super.mirror(state, mirror);
            };
            case FRONT_BACK -> switch (shape) {
                case ASCENDING_EAST -> state.setValue(SHAPE, RailShape.ASCENDING_WEST);
                case ASCENDING_WEST -> state.setValue(SHAPE, RailShape.ASCENDING_EAST);
                case ASCENDING_NORTH, ASCENDING_SOUTH, SOUTH_EAST -> state.setValue(SHAPE, RailShape.SOUTH_WEST);
                case SOUTH_WEST -> state.setValue(SHAPE, RailShape.SOUTH_EAST);
                case NORTH_WEST -> state.setValue(SHAPE, RailShape.NORTH_EAST);
                case NORTH_EAST -> state.setValue(SHAPE, RailShape.NORTH_WEST);
                default -> super.mirror(state, mirror);
            };
            default -> super.mirror(state, mirror);
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(this.getShapeProperty(), POWERED, WATERLOGGED);
    }
}
