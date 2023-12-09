package leaf.trp.peripherals;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.Direction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IPeripheralTileTRP
{
	@Nullable
	IPeripheral getPeripheral(@Nonnull Direction side);
}
