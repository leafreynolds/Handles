package leaf.handles.forge;

import dan200.computercraft.api.ForgeComputerCraftAPI;
import dev.architectury.platform.forge.EventBuses;
import leaf.handles.HandlesMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HandlesMod.MOD_ID)
public class HandlesModForge
{
	public HandlesModForge()
	{
		// Submit our event bus to let architectury register our content on the right time
		EventBuses.registerModEventBus(HandlesMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
		HandlesMod.init();
		ForgeComputerCraftAPI.registerPeripheralProvider(new HandlesProviderForge());
	}
}
