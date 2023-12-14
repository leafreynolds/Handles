package leaf.handles.fabric;

import leaf.handles.HandlesExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class HandlesExpectPlatformImpl
{
	/**
	 * This is our actual method to {@link HandlesExpectPlatform#getConfigDirectory()}.
	 */
	public static Path getConfigDirectory()
	{
		return FabricLoader.getInstance().getConfigDir();
	}
}
