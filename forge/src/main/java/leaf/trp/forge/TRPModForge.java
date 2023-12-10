package leaf.trp.forge;

import dan200.computercraft.api.ForgeComputerCraftAPI;
import dev.architectury.platform.forge.EventBuses;
import leaf.trp.TRPMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TRPMod.MOD_ID)
public class TRPModForge
{
	public TRPModForge()
	{
		// Submit our event bus to let architectury register our content on the right time
		EventBuses.registerModEventBus(TRPMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
		TRPMod.init();
		ForgeComputerCraftAPI.registerPeripheralProvider(new TRPProviderForge());
	}
}
