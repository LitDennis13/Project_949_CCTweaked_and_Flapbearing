package net.antwibuadum.project949;

import net.antwibuadum.project949.FlapBearingPeripheralPack.FlapBearingPeripheralProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import dan200.computercraft.api.ForgeComputerCraftAPI;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Project949.MOD_ID)
public class Project949
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "project949";

    public Project949(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ForgeComputerCraftAPI.registerPeripheralProvider(new FlapBearingPeripheralProvider());

    }
}
