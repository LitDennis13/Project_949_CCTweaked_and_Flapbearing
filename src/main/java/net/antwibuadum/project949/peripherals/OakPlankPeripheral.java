package net.antwibuadum.project949.peripherals;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;
import java.util.Objects;

public class OakPlankPeripheral implements IPeripheral {

    private final Level level;
    private final BlockPos pos;

    public OakPlankPeripheral(Level level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    @Nonnull
    @Override
    public String getType() {
        return "oak_plank";
    }

    @Override
    public boolean equals(IPeripheral other) {
        return this == other || (other instanceof OakPlankPeripheral && Objects.equals(pos, ((OakPlankPeripheral) other).pos));
    }

    // Custom method implemented in Java
    @LuaFunction
    public int getPlankCount() {
        // Check if the block is an oak plank
        if (level.getBlockState(pos).getBlock() == Blocks.OAK_PLANKS) {
            return 1; // Return 1 if the block is an oak plank
        }
        return 0; // Return 0 otherwise
    }

    // Another custom method implemented in Java
    @LuaFunction
    public String sayHello() {
        return "Hello from the oak plank!";
    }

    @LuaFunction
    public String happyness() {
        return "THIS ACTUALLY WORKED";
    }
}
