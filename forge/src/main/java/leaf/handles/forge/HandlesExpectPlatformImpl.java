package leaf.handles.forge;

import leaf.handles.HandlesExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class HandlesExpectPlatformImpl
{
	/**
	 * This is our actual method to {@link HandlesExpectPlatform#getConfigDirectory()}.
	 */
	public static Path getConfigDirectory()
	{
		return FMLPaths.CONFIGDIR.get();
	}
}
