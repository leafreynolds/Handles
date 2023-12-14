package leaf.handles.peripherals;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

// will need to be called by classes on fabric and forge since they return @Nullable vs LazyOptional
public class HandlesProviderCommon
{
	@Nullable
	public static IPeripheral getPeripheral(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Direction side)
	{
		BlockEntity be = world.getBlockEntity(pos);
		if (be instanceof IPeripheralTile)
		{
			return ((IPeripheralTile) be).getPeripheral(side);
		}
		// todo  - update this file when trying to differentiate between tardis from NTM team and tardis from TardisRefined
		//if (Platform.isModLoaded("tardis"))
		//{
		//	IPeripheral newTardisModPeriph = NewTardisModPeripheralProvider.getPeripheral(world, pos, side);
		//	if (newTardisModPeriph != null)
		//	{
		//		return newTardisModPeriph;
		//	}
		//}
		return null;
	}
}
