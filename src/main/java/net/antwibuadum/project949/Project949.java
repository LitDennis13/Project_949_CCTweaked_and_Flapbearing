package net.antwibuadum.project949;

import com.mojang.logging.LogUtils;
import net.antwibuadum.project949.FlapBearingPeripheralPack.FlapBearingPeripheralProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import dan200.computercraft.api.ForgeComputerCraftAPI;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Project949.MOD_ID)
public class Project949
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "project949";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Project949(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


        ForgeComputerCraftAPI.registerPeripheralProvider(new FlapBearingPeripheralProvider());

    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }
}
