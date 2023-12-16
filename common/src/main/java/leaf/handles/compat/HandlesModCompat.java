package leaf.handles.compat;

import dev.architectury.platform.Platform;
import leaf.handles.peripherals.RefinedPeripheral;

public class HandlesModCompat
{
	public static void init()
	{
		if (Platform.isModLoaded("tardis_refined"))
		{
			TardisRefinedCompat.init();
		}
	}
}
