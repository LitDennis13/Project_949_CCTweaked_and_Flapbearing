package net.antwibuadum.project949.FlapBearingPeripheralPack;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import org.valkyrienskies.clockwork.ClockworkBlocks;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FlapBearingPeripheralProvider implements IPeripheralProvider {
    @Nullable
    @Override
    public LazyOptional<IPeripheral> getPeripheral(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Direction side) {
        if (level.getBlockState(pos).getBlock() == ClockworkBlocks.FLAP_BEARING.get()) {
            return LazyOptional.of(() -> new FlapBearingPeripheral(level, pos));
        }
        return LazyOptional.empty();
    }


}