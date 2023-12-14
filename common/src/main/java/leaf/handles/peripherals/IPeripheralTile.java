package leaf.handles.peripherals;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.Direction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IPeripheralTile
{
	@Nullable
	IPeripheral getPeripheral(@Nonnull Direction side);
}
