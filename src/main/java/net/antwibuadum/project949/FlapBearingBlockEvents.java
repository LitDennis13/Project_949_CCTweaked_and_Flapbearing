package net.antwibuadum.project949;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.valkyrienskies.clockwork.ClockworkBlocks;
import org.valkyrienskies.clockwork.content.contraptions.flap.FlapBearingBlockEntity;

import java.util.ArrayList;
import java.util.List;

// FlapBearingBlockEvents class defines functions that will run the certain events are triggered
@Mod.EventBusSubscriber(modid = Project949.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FlapBearingBlockEvents {
    public static class EntityListType {
        public FlapBearingBlockEntity entity;
        public BlockPos pos;
        public float angle;
        public boolean assembleNextTick;
        public boolean disassembleNextTick;

        public EntityListType(FlapBearingBlockEntity e, BlockPos p, float a) {
            this.entity = e;
            this.pos = p;
            this.angle = a;
            this.assembleNextTick = true;
            this.disassembleNextTick = false;
        }
    }

    // entity list that contains all correctly oriented flap bearings connected to computers
    public static List<EntityListType> entities = new ArrayList<>();

    // runs everytime a block is placed
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        Level level = (Level) event.getLevel();

        BlockPos pos = event.getPos();

        // if the block is a flap bearing then it gets added to the entity list
        if (level.getBlockState(pos).getBlock() == ClockworkBlocks.FLAP_BEARING.get()) {
            FlapBearingPeripheralFunctions.Initialize(level, pos);
        }
    }

    // function that runs everytime a block breaks
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();

        String computer_block = "Block{computercraft:computer_advanced}";

        // if the block is a flap bearing the block's entity gets removed from the list
        if (level.getBlockState(pos).getBlock() == ClockworkBlocks.FLAP_BEARING.get()) {
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i).pos.getX() == pos.getX()
                    && entities.get(i).pos.getY() == pos.getY()
                    && entities.get(i).pos.getZ() == pos.getZ()) {
                    entities.remove(i);
                    break;
                }
            }
        }
        // if the block is a computer block all the flap bearing block entities it was connected to
        //  get removed from the list
        else if (level.getBlockState(pos).getBlock().toString().equals(computer_block)) {
            BlockPos top = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
            BlockPos bottom = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
            BlockPos front = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
            BlockPos back = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
            BlockPos left = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
            BlockPos right = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());

            BlockPos[] side_positions = {top, bottom, front, back, left, right};
            FlapBearingBlockEntity tempEntity;

            for (int i = 0; i < 6; i++) {
                if (level.getBlockState(side_positions[i]).getBlock() == ClockworkBlocks.FLAP_BEARING.get()) {
                    for (int j = 0; j < entities.size(); j++) {
                        if (entities.get(j).pos.getX() == side_positions[i].getX()
                                && entities.get(j).pos.getY() == side_positions[i].getY()
                                && entities.get(j).pos.getZ() == side_positions[i].getZ()) {
                            tempEntity = entities.get(j).entity;
                            tempEntity.remove();
                            tempEntity.setAngle(0F);
                            tempEntity.setChanged();
                            tempEntity.sendData();

                            FlapBearingPeripheralFunctions.forceBlockUpdate(level, FlapBearingBlockEvents.entities.get(j).pos);

                            entities.remove(j);
                            break;
                        }
                    }
                }
            }
        }

    }

    // function that runs every server tick and keeps angle up to date
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i).assembleNextTick) {
                    entities.get(i).entity.assemble();
                    entities.get(i).assembleNextTick = false;
                }
                else if (entities.get(i).disassembleNextTick) {
                    entities.get(i).angle = 0;
                    entities.get(i).entity.remove();
                    entities.get(i).disassembleNextTick = false;
                }
                entities.get(i).entity.setAngle(entities.get(i).angle);
                entities.get(i).entity.setChanged();
                entities.get(i).entity.sendData();
            }
        }
    }
}