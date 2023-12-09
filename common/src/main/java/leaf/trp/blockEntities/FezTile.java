package leaf.trp.blockEntities;

import dan200.computercraft.api.peripheral.IPeripheral;
import leaf.trp.peripherals.IPeripheralTileTRP;
import leaf.trp.peripherals.RefinedPeripheral;
import leaf.trp.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FezTile extends BlockEntity implements IPeripheralTileTRP
{
	private RefinedPeripheral refinedPeripheral;

	public FezTile(BlockEntityType<FezTile> type, BlockPos pos, BlockState state)
	{
		super(type, pos, state);
		refinedPeripheral = new RefinedPeripheral(this);
	}

	public FezTile(BlockPos pos, BlockState state)
	{
		this(BlockEntityRegistry.FEZ.get(), pos, state);
	}

	@Nullable
	@Override
	public IPeripheral getPeripheral(@NotNull Direction side)
	{
		if (refinedPeripheral == null)
		{
			refinedPeripheral = new RefinedPeripheral(this);
		}
		return refinedPeripheral;
	}

}
