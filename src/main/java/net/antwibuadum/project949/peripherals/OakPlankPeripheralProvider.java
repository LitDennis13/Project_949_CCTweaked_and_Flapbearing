package net.antwibuadum.project949.peripherals;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OakPlankPeripheralProvider implements IPeripheralProvider {

    @Nullable
    @Override
    public LazyOptional<IPeripheral> getPeripheral(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Direction side) {
        // Check if the block is an oak plank
        if (level.getBlockState(pos).getBlock() == Blocks.OAK_PLANKS) {
            return LazyOptional.of(() -> new OakPlankPeripheral(level, pos));
        }
        return LazyOptional.empty();
    }
}