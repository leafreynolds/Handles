package leaf.handles.blockEntities;

import dan200.computercraft.api.peripheral.IPeripheral;
import dev.architectury.platform.Platform;
import leaf.handles.peripherals.IHandlesPeripheral;
import leaf.handles.peripherals.IPeripheralTile;
import leaf.handles.peripherals.RefinedPeripheral;
import leaf.handles.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.api.event.EventResult;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.ExteriorShell;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TardisPeripheralTile extends BlockEntity implements IPeripheralTile
{
	private IHandlesPeripheral peripheral;

	private static final Set<TardisPeripheralTile> tiles = new HashSet<>();

	public TardisPeripheralTile(BlockEntityType<TardisPeripheralTile> type, BlockPos pos, BlockState state)
	{
		super(type, pos, state);
		getModDependentPeripheral();
		if (this.level instanceof ServerLevel)
		{
			tiles.add(this);
		}
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

	private IHandlesPeripheral getModDependentPeripheral()
	{
		if (peripheral == null)
		{
			if (Platform.isModLoaded("tardis_refined"))
			{
				peripheral = new RefinedPeripheral(this);
			}

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

	@Override
	public void setRemoved()
	{
		super.setRemoved();
		if (this.level instanceof ServerLevel)
		{
			tiles.remove(this);
		}
	}

	@Override
	public void clearRemoved()
	{
		super.clearRemoved();
		if (this.level instanceof ServerLevel)
		{
			tiles.add(this);
		}
	}

	//region Tardis: Refined event hookups
	//todo - move these into a tardis refined specific tile class when that comes relevant
	public static EventResult onTakeOff(TardisLevelOperator tardisLevelOperator, LevelAccessor levelAccessor, BlockPos blockPos)
	{
		for (var tile : tiles)
		{
			final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) tile.getLevel());
			if (optional.isPresent() && optional.get() == tardisLevelOperator)
			{
				tile.getModDependentPeripheral().queueEvent("onTakeOff");
			}
		}
		return EventResult.pass();
	}

	public static void onTardisEntered(TardisLevelOperator tardisLevelOperator, ExteriorShell exteriorShell, Player player, BlockPos blockPos, Level level, Direction direction)
	{
		for (var tile : tiles)
		{
			final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) tile.getLevel());
			if (optional.isPresent() && optional.get() == tardisLevelOperator)
			{
				tile.getModDependentPeripheral().queueEvent("onTardisEntered", player.getName().getString());
			}
		}
	}

	public static void onDoorClosed(TardisLevelOperator tardisLevelOperator)
	{
		for (var tile : tiles)
		{
			final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) tile.getLevel());
			if (optional.isPresent() && optional.get() == tardisLevelOperator)
			{
				tile.getModDependentPeripheral().queueEvent("onDoorClosed");
			}
		}
	}

	public static void onDoorOpened(TardisLevelOperator tardisLevelOperator)
	{
		for (var tile : tiles)
		{
			final Level tileLevel = tile.getLevel();
			final ServerLevel serverLevel = (ServerLevel) tileLevel;
			final Optional<TardisLevelOperator> optional = TardisLevelOperator.get(serverLevel);
			if (optional.isPresent() && optional.get() == tardisLevelOperator)
			{
				tile.getModDependentPeripheral().queueEvent("onDoorOpened");
			}
		}
	}

	public static void onShellChanged(TardisLevelOperator tardisLevelOperator, ShellTheme shellTheme)
	{
		for (var tile : tiles)
		{
			final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) tile.getLevel());
			if (optional.isPresent() && optional.get() == tardisLevelOperator)
			{
				tile.getModDependentPeripheral().queueEvent("onShellChanged", shellTheme.getSerializedName());
			}
		}
	}

	public static void onCrashed(TardisLevelOperator tardisLevelOperator, TardisNavLocation tardisNavLocation)
	{
		for (var tile : tiles)
		{
			final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) tile.getLevel());
			if (optional.isPresent() && optional.get() == tardisLevelOperator)
			{
				final BlockPos position = tardisNavLocation.getPosition();
				tile.getModDependentPeripheral().queueEvent(
						"onCrashed",
						position.getX(),
						position.getY(),
						position.getZ(),
						tardisNavLocation.getDirection().toString(),
						tardisNavLocation.getDimensionKey().location().toString()
				);
			}
		}
	}
	//endregion

}
