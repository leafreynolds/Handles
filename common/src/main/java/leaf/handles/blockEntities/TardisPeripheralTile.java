package leaf.handles.blockEntities;

import dan200.computercraft.api.peripheral.IPeripheral;
import dev.architectury.platform.Platform;
import leaf.handles.peripherals.IPeripheralTile;
import leaf.handles.peripherals.RefinedPeripheral;
import leaf.handles.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TardisPeripheralTile extends BlockEntity implements IPeripheralTile
{
	private IPeripheral peripheral;

	public TardisPeripheralTile(BlockEntityType<TardisPeripheralTile> type, BlockPos pos, BlockState state)
	{
		super(type, pos, state);
		getModDependentPeripheral();
	}

	public TardisPeripheralTile(BlockPos pos, BlockState state)
	{
		this(BlockEntityRegistry.TARDIS_PERIPHERAL_TILE.get(), pos, state);
	}

	@Nullable
	@Override
	public IPeripheral getPeripheral(@NotNull Direction side)
	{
		return getModDependentPeripheral();
	}

	private IPeripheral getModDependentPeripheral()
	{
		if (peripheral == null)
		{

			peripheral = new RefinedPeripheral(this);
			return peripheral;

			// Todo - The below doesn't work, because level is null.
			//  Will need a better way of getting mod dependent peripherals
			//
			////Tardis Refined compat
			//if (Platform.isModLoaded("tardis_refined") && this.level.dimension().location().getNamespace() == "tardis_refined")
			//{
			//	peripheral = new RefinedPeripheral(this);
			//}
			////"New Tardis Mod" compat
			//else if (Platform.isModLoaded("tardis") && this.level.dimension().location().getNamespace() == "tardis")
			//{
			//	//peripheral = new NTMPeripheral(this);
			//}
		}
		return peripheral;
	}

}
