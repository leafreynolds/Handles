package leaf.handles;

import leaf.handles.registries.BlockEntityRegistry;
import leaf.handles.registries.BlockRegistry;
import leaf.handles.registries.ItemRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HandlesMod
{
	public static final String MOD_ID = "handles";
	public static final Logger LOGGER = LogManager.getLogger();

	public static void init()
	{
		//blocks have to go first
		BlockRegistry.BLOCKS.register();
		//then items
		ItemRegistry.ITEMS.register();
		BlockEntityRegistry.BLOCK_ENTITIES.register();

		System.out.println(HandlesExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
	}
}
