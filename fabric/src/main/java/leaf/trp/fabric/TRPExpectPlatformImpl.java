package leaf.trp.fabric;

import leaf.trp.TRPExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class TRPExpectPlatformImpl
{
	/**
	 * This is our actual method to {@link TRPExpectPlatform#getConfigDirectory()}.
	 */
	public static Path getConfigDirectory()
	{
		return FabricLoader.getInstance().getConfigDir();
	}
}
