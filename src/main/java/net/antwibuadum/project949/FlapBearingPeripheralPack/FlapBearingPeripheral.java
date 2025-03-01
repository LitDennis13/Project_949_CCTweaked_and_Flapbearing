package net.antwibuadum.project949.FlapBearingPeripheralPack;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.antwibuadum.project949.FlapBearingBlockEvents;
import net.antwibuadum.project949.RotationalPowerProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FlapBearingPeripheral implements IPeripheral {
    private final Level level;
    private final BlockPos pos;

    public FlapBearingPeripheral(Level level, BlockPos pos) {
        this.level = level;
        this.pos = pos;

        boolean exist = false;

        // checks if the flap bearing is correctly oriented
        if (RotationalPowerProvider.correctOrientation(level.getBlockState(this.pos), RotationalPowerProvider.GetComputerSide(level, this.pos))) {
            // checks if the current connected flap bearing is in the entity list
            for (int i = 0; i < FlapBearingBlockEvents.entities.size(); i++) {
                if (FlapBearingBlockEvents.entities.get(i).pos.getX() == this.pos.getX()
                    && FlapBearingBlockEvents.entities.get(i).pos.getY() == this.pos.getY()
                    && FlapBearingBlockEvents.entities.get(i).pos.getZ() == this.pos.getZ()) {
                    exist = true;
                }
            }
            // if not then initialize the current flap bearing and add to the entity list
            if (!exist) {
                   RotationalPowerProvider.Initialize(level, this.pos);
            }
        }
    }

    @Nonnull
    @Override
    public String getType() {
        return "flap_bearing";
    }

    @Override
    public boolean equals(IPeripheral other) {
        return this == other || (other instanceof FlapBearingPeripheral && Objects.equals(pos, ((FlapBearingPeripheral) other).pos));
    }

    // function for the computer that allows it to set the angle of the flap bearing
    @LuaFunction
    public void setFlapAngle(double inputtedAngle) {
        float angle = (float)inputtedAngle;

        for (int i = 0; i < FlapBearingBlockEvents.entities.size(); i++) {
            if (FlapBearingBlockEvents.entities.get(i).pos.getX() == this.pos.getX()
                    && FlapBearingBlockEvents.entities.get(i).pos.getY() == this.pos.getY()
                    && FlapBearingBlockEvents.entities.get(i).pos.getZ() == this.pos.getZ()) {
                FlapBearingBlockEvents.entities.get(i).angle = angle;
            }
        }
    }

    // function for the computer that allows it to activate the flap bearing
    @LuaFunction
    public void activate() {
        BlockState block_state;
        for (int i = 0; i < FlapBearingBlockEvents.entities.size(); i++) {
            if (FlapBearingBlockEvents.entities.get(i).pos.getX() == this.pos.getX()
                    && FlapBearingBlockEvents.entities.get(i).pos.getY() == this.pos.getY()
                    && FlapBearingBlockEvents.entities.get(i).pos.getZ() == this.pos.getZ()) {
                FlapBearingBlockEvents.entities.get(i).entity.setAssembleNextTick(true);
                FlapBearingBlockEvents.entities.get(i).entity.setChanged();
                FlapBearingBlockEvents.entities.get(i).entity.sendData();

                RotationalPowerProvider.forceBlockUpdate(level, FlapBearingBlockEvents.entities.get(i).pos);
            }
        }
    }

    // function for the computer that allows it to deactivate the flap bearing
    @LuaFunction
    public void deactivate() {
        for (int i = 0; i < FlapBearingBlockEvents.entities.size(); i++) {
            if (FlapBearingBlockEvents.entities.get(i).pos.getX() == this.pos.getX()
                    && FlapBearingBlockEvents.entities.get(i).pos.getY() == this.pos.getY()
                    && FlapBearingBlockEvents.entities.get(i).pos.getZ() == this.pos.getZ()) {
                FlapBearingBlockEvents.entities.get(i).angle = 0F;
                FlapBearingBlockEvents.entities.get(i).entity.remove();
                FlapBearingBlockEvents.entities.get(i).entity.setChanged();
                FlapBearingBlockEvents.entities.get(i).entity.sendData();

                RotationalPowerProvider.forceBlockUpdate(level, FlapBearingBlockEvents.entities.get(i).pos);
            }
        }
    }



}
