package leaf.trp.fabric;

import dan200.computercraft.api.ComputerCraftAPI;
import leaf.trp.TRPMod;
import net.fabricmc.api.ModInitializer;

public class TRPModFabric implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		TRPMod.init();
		ComputerCraftAPI.registerPeripheralProvider(new TRPProviderFabric());
	}
}
