package melonystudios.stancements.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class STBlockStateProperties {
    public static final BooleanProperty RECORDING = BooleanProperty.create("recording");
    public static final BooleanProperty HOPPING = BooleanProperty.create("hopping");
    public static final EnumProperty<Direction> ATTACHMENT = EnumProperty.create("attachment", Direction.class, direction -> direction != Direction.UP);
}
