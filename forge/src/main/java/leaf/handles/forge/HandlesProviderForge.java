package leaf.handles.forge;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import leaf.handles.peripherals.HandlesProviderCommon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class HandlesProviderForge implements IPeripheralProvider
{
	@Override
	public LazyOptional<IPeripheral> getPeripheral(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Direction side)
	{
		IPeripheral ip = HandlesProviderCommon.getPeripheral(world, pos, side);
		return ip != null ? LazyOptional.of(() -> ip) : LazyOptional.empty();
	}
}