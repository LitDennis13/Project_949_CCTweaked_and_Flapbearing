package net.antwibuadum.project949;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.valkyrienskies.clockwork.content.contraptions.flap.FlapBearingBlockEntity;


public class FlapBearingPeripheralFunctions {
    private static final float DEFAULT_ANGLE = 0F;

    // possible sides of the computer
    public static enum COMPUTER_SIDE {
        TOP, BOTTOM, FRONT, BACK, LEFT, RIGHT, NONE;
    }

    // function that adds the flap bearing's block entity to the list of entities that get updated on each tick if
    //  the flap bearing is connected to a computer and in the right orientation
    public static void Initialize(Level level, BlockPos pos) {
        BlockState block_state = level.getBlockState(pos);
        COMPUTER_SIDE computer_side = GetComputerSide(level, pos);
        boolean orientation_correct = correctOrientation(block_state, computer_side);

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof FlapBearingBlockEntity block_entity) {
            if (orientation_correct && !level.isClientSide) {

                FlapBearingBlockEvents.EntityListType listItem = new FlapBearingBlockEvents.EntityListType(block_entity, pos, DEFAULT_ANGLE );
                FlapBearingBlockEvents.entities.add(listItem);

                forceBlockUpdate(level, pos);
            }
        }
    }

    // function to return what side of the computer the flap bearing is on
    public static COMPUTER_SIDE GetComputerSide(Level level, BlockPos pos) {
        BlockPos top = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
        BlockPos bottom = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        BlockPos front = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
        BlockPos back = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
        BlockPos left = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
        BlockPos right = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());

        String top_block = level.getBlockState(top).getBlock().toString();
        String bottom_block = level.getBlockState(bottom).getBlock().toString();
        String front_block = level.getBlockState(front).getBlock().toString();
        String black_block = level.getBlockState(back).getBlock().toString();
        String left_block = level.getBlockState(left).getBlock().toString();
        String right_block = level.getBlockState(right).getBlock().toString();

        String computer_block = "Block{computercraft:computer_advanced}";

        if (top_block.equals(computer_block)) {
            return COMPUTER_SIDE.TOP;
        }
        else if (bottom_block.equals(computer_block)) {
            return COMPUTER_SIDE.BOTTOM;
        }
        else if (front_block.equals(computer_block)) {
            return COMPUTER_SIDE.FRONT;
        }
        else if (black_block.equals(computer_block)) {
            return COMPUTER_SIDE.BACK;
        }
        else if (left_block.equals(computer_block)) {
            return COMPUTER_SIDE.LEFT;
        }
        else if (right_block.equals(computer_block)) {
            return COMPUTER_SIDE.RIGHT;
        }
        else {
            return COMPUTER_SIDE.NONE;
        }
    }

    // function that checks that the flap bearing is placed correctly next to the computer
    public static boolean correctOrientation(BlockState block_state, COMPUTER_SIDE side) {
        Direction facing = block_state.getValue(BlockStateProperties.FACING);

        if (facing == Direction.UP && side == COMPUTER_SIDE.TOP) {
            return true;
        }
        else if (facing == Direction.DOWN && side == COMPUTER_SIDE.BOTTOM) {
            return true;
        }
        else if (facing == Direction.SOUTH && side == COMPUTER_SIDE.FRONT) {
            return true;
        }
        else if (facing == Direction.NORTH && side == COMPUTER_SIDE.BACK) {
            return true;
        }
        else if (facing == Direction.EAST && side == COMPUTER_SIDE.LEFT) {
            return true;
        }
        else if (facing == Direction.WEST && side == COMPUTER_SIDE.RIGHT) {
            return true;
        }
        else {
            return false;
        }
    }

    // function to force a block's state to update
    public static void forceBlockUpdate(Level level, BlockPos pos) {
        BlockState block_state = level.getBlockState(pos);
        level.sendBlockUpdated(pos, block_state, block_state, 3);
    }
}
