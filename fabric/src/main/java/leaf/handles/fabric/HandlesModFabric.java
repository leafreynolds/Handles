package leaf.handles.fabric;

import dan200.computercraft.api.ComputerCraftAPI;
import leaf.handles.HandlesMod;
import net.fabricmc.api.ModInitializer;

public class HandlesModFabric implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		HandlesMod.init();
		ComputerCraftAPI.registerPeripheralProvider(new HandlesProviderFabric());
	}
}
