package leaf.trp.forge;

import leaf.trp.TRPExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class TRPExpectPlatformImpl
{
    /**
     * This is our actual method to {@link TRPExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
