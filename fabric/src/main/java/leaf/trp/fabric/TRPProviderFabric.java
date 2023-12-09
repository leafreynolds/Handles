package leaf.trp.fabric;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import leaf.trp.peripherals.TRPProviderCommon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TRPProviderFabric implements IPeripheralProvider
{
	@Nullable
	@Override
	public IPeripheral getPeripheral(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Direction side)
	{
		return TRPProviderCommon.getPeripheral(world, pos, side);
	}

}
