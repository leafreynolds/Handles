package leaf.trp;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.Registries;
import leaf.trp.functions.FunctionsHandler;
import leaf.trp.registries.BlockEntityRegistry;
import leaf.trp.registries.BlockRegistry;
import leaf.trp.registries.ItemRegistry;

import java.util.function.Supplier;

public class TRPMod
{
	public static final String MOD_ID = "trp";
	// We can use this if we don't want to use DeferredRegister
	public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));

	public static void init()
	{
		//blocks have to go first
		BlockRegistry.BLOCKS.register();
		//then items
		ItemRegistry.ITEMS.register();
		BlockEntityRegistry.BLOCK_ENTITIES.register();
		FunctionsHandler.init();

		System.out.println(TRPExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
	}
}
