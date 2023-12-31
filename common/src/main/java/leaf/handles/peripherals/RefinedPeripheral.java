package leaf.handles.peripherals;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisFlightEventManager;
import whocraft.tardis_refined.common.tardis.manager.TardisControlManager;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.DimensionUtil;
import whocraft.tardis_refined.patterns.ShellPatterns;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//ignore unused, as computercraft collects functions at run time
@SuppressWarnings("unused")
public class RefinedPeripheral implements IHandlesPeripheral
{
	private final BlockEntity blockEntity;

	public List<IComputerAccess> computers = new ArrayList<>();

	public RefinedPeripheral(BlockEntity blockEntity)
	{
		this.blockEntity = blockEntity;
	}

	@Nonnull
	@Override
	public String getType()
	{
		return "fez";
	}

	@Override
	public boolean equals(IPeripheral other)
	{
		return this == other;
	}

	@Override
	public void attach(@NotNull IComputerAccess computer)
	{
		computers.add(computer);
	}

	@Override
	public void detach(@NotNull IComputerAccess computer)
	{
		computers.remove(computer);
	}

	//called from tick?
	public void queueEvent(@Nonnull String event, @Nullable Object... arguments)
	{
		for (IComputerAccess computer : computers)
		{
			computer.queueEvent(event, computer.getAttachmentName(), arguments);
		}
	}

	//region Flight -
	// canBeginFlight/beginFlight/isInFlight/getFlightEventActive/getFlightEventControl/getRequiredFlightEvents/getRespondedFlightEvents/
	// isInDangerZone/areControlEventsComplete/areDangerZoneEventsComplete/getFlightPercent/canEndFlight/endFlight/getIsLanding


	@LuaFunction
	public final boolean canBeginFlight() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			return controlManager.canBeginFlight();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	//Auto lands the tardis if stabilized flight is true.
	//Stabilized flight has no flight events.
	@LuaFunction
	public final MethodResult beginFlight(boolean stabilizedFlight) throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			//auto lands the tardis if stabilized flight is true.

			blockEntity.getLevel().getServer().tell(new TickTask(1,
					() -> controlManager.beginFlight(stabilizedFlight)
			));
			return MethodResult.of();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
/* DEV ENVIRONMENT ONLY - because I just can't help myself

	@LuaFunction
	public final MethodResult crash() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			//auto lands the tardis if stabilized flight is true.

			blockEntity.getLevel().getServer().tell(new TickTask(1,
					controlManager::crash
			));
			return MethodResult.of();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult endCrash() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			//auto lands the tardis if stabilized flight is true.

			blockEntity.getLevel().getServer().tell(new TickTask(1,
					controlManager::onCrashEnd
			));
			return MethodResult.of();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult endCooldown() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			//auto lands the tardis if stabilized flight is true.

			blockEntity.getLevel().getServer().tell(new TickTask(1,
					controlManager::endCoolDown
			));
			return MethodResult.of();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
*/

	@LuaFunction
	public final boolean isInFlight() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			return controlManager.isInFlight();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult getFlightEventActive() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			TardisFlightEventManager flightEventManager = tardisLevelOperator.getTardisFlightEventManager();
			return MethodResult.of(flightEventManager.isWaitingForControlResponse());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	//if the tardis has an active flight event control to press,
	//will return the id name of that control
	//else returns null (nil for lua?)
	@LuaFunction
	public final MethodResult getFlightEventControl() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			TardisFlightEventManager flightEventManager = tardisLevelOperator.getTardisFlightEventManager();
			return MethodResult.of(
					flightEventManager.isWaitingForControlResponse()
					? flightEventManager.getWaitingControlPrompt().getSerializedName()
					: null
			);
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult getRequiredFlightEvents() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			TardisFlightEventManager flightEventManager = tardisLevelOperator.getTardisFlightEventManager();
			return MethodResult.of(flightEventManager.getRequiredControlRequests());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult getRespondedFlightEvents() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			TardisFlightEventManager flightEventManager = tardisLevelOperator.getTardisFlightEventManager();
			return MethodResult.of(flightEventManager.getControlResponses());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult isInDangerZone() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			TardisFlightEventManager flightEventManager = tardisLevelOperator.getTardisFlightEventManager();
			return MethodResult.of(flightEventManager.isInDangerZone());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult areControlEventsComplete() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			TardisFlightEventManager flightEventManager = tardisLevelOperator.getTardisFlightEventManager();
			return MethodResult.of(flightEventManager.areControlEventsComplete());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult areDangerZoneEventsComplete() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			TardisFlightEventManager flightEventManager = tardisLevelOperator.getTardisFlightEventManager();
			return MethodResult.of(flightEventManager.areDangerZoneEventsComplete());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
	/* todo enable function when accessor is available
	@LuaFunction
	public final MethodResult isEventInComboTime() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			TardisFlightEventManager flightEventManager = tardisLevelOperator.getTardisFlightEventManager();
			return MethodResult.of(flightEventManager.isEventInComboTime());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	//todo enable function when accessor is available
	@LuaFunction
	public final MethodResult getControlRequestCooldown() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			TardisFlightEventManager flightEventManager = tardisLevelOperator.getTardisFlightEventManager();
			return MethodResult.of(flightEventManager.getControlRequestCooldown());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
*/

	@LuaFunction
	public final float getFlightPercent() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			TardisFlightEventManager flightEventManager = tardisLevelOperator.getTardisFlightEventManager();
			return flightEventManager.getPercentComplete();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult canEndFlight() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			return MethodResult.of(controlManager.canEndFlight());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult endFlight() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());

		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();

			blockEntity.getLevel().getServer().tell(new TickTask(1,
					controlManager::endFlight
			));
			return MethodResult.of();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult getIsLanding() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			return MethodResult.of(controlManager.isLanding());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
	//endregion

	private static ServerLevel getServerLevel(TardisLevelOperator tardisLevelOperator, String dim)
	{
		final MinecraftServer server = tardisLevelOperator.getLevel().getServer();

		if (server == null)
		{
			return null;
		}

		for (ServerLevel level : server.getAllLevels())
		{
			final ResourceKey<Level> dimension = level.dimension();
			final ResourceLocation location = dimension.location();
			if (DimensionUtil.isAllowedDimension(dimension) && (location.toString().equals(dim) || location.getPath().equals(dim)))
			{
				return level;
			}
		}

		return null;
	}

	//region Target Location - Get/Set
	@LuaFunction
	public final MethodResult getTargetLocation() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();

			final TardisNavLocation targetLocation = tardisLevelOperator.getControlManager().getTargetLocation();
			return MethodResult.of(
					targetLocation.getPosition().getX(),
					targetLocation.getPosition().getY(),
					targetLocation.getPosition().getZ(),
					targetLocation.getDirection().toString(),
					targetLocation.getDimensionKey().location().toString()
			);
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult setTargetLocation(IArguments args) throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();

			int x = args.getInt(0);
			int y = args.getInt(1);
			int z = args.getInt(2);
			Direction direction = Direction.byName(args.getString(3));
			final ServerLevel targetDimension = getServerLevel(tardisLevelOperator, args.getString(4));

			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			TardisNavLocation targetLocation =
					new TardisNavLocation(
							new BlockPos(x, y, z),
							direction,
							targetDimension != null
							? targetDimension
							: controlManager.getTargetLocation().getLevel()
					);

			blockEntity.getLevel().getServer().tell(new TickTask(1,
					() -> controlManager.setTargetLocation(targetLocation)
			));

			return MethodResult.of();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
	//endregion

	//region Target Position - Get/Set
	@LuaFunction
	public final MethodResult getTargetPosition() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();

			final TardisNavLocation targetLocation = tardisLevelOperator.getControlManager().getTargetLocation();
			return MethodResult.of(
					targetLocation.getPosition().getX(),
					targetLocation.getPosition().getY(),
					targetLocation.getPosition().getZ()
			);
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult setTargetPosition(IArguments args) throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();

			int x = args.getInt(0);
			int y = args.getInt(1);
			int z = args.getInt(2);

			blockEntity.getLevel().getServer().tell(new TickTask(1,
					() -> tardisLevelOperator.getControlManager().setTargetPosition(new BlockPos(x, y, z))
			));

			return MethodResult.of();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
	//endregion

	//region Target Direction - Get/Set
	//  "north"
	//  "south"
	//  "west"
	//  "east"
	@LuaFunction
	public final MethodResult getTargetDirection() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisNavLocation lastKnownLocation = tardisLevelOperator.getExteriorManager().getLastKnownLocation();
			return MethodResult.of(lastKnownLocation.getDirection().toString());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	//  "north"
	//  "south"
	//  "west"
	//  "east"
	@LuaFunction
	public final MethodResult setTargetDirection(String dir) throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisNavLocation lastKnownLocation = tardisLevelOperator.getExteriorManager().getLastKnownLocation();

			Direction direction = Direction.byName(dir);
			if (direction != null)//todo - check only for valid directions, no up or down
			{
				if (direction == Direction.UP || direction == Direction.DOWN)
				{
					throw new LuaException("Invalid Tardis Facing Direction");
				}

				blockEntity.getLevel().getServer().tell(new TickTask(1,
						() -> tardisLevelOperator.getControlManager().getTargetLocation().setDirection(direction)
				));

				return MethodResult.of(dir);
			}
			else
			{
				return MethodResult.of();
			}
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
	//endregion

	//region Target Dimension - Get/Set
	@LuaFunction
	public final MethodResult getTargetDimension() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			return MethodResult.of(tardisLevelOperator.getControlManager().getTargetLocation().getDimensionKey().location().toString());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult setTargetDimension(String dimensionName) throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final ServerLevel targetDimension = getServerLevel(tardisLevelOperator, dimensionName);

			if (targetDimension == null)
			{
				throw new LuaException("Dimension not found");
			}

			blockEntity.getLevel().getServer().tell(new TickTask(1,
					() -> tardisLevelOperator.getControlManager().getTargetLocation().setLevel(targetDimension)
			));

			return MethodResult.of();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
	//endregion

	//region LastKnownLocation - (x,y,z,direction,dimension) / Dimension / Direction
	@LuaFunction
	public final MethodResult getLastKnownLocation() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();

			final TardisNavLocation lastKnownLocation = tardisLevelOperator.getExteriorManager().getLastKnownLocation();
			return MethodResult.of(
					lastKnownLocation.getPosition().getX(),
					lastKnownLocation.getPosition().getY(),
					lastKnownLocation.getPosition().getZ(),
					lastKnownLocation.getDirection().toString(),
					lastKnownLocation.getDimensionKey().location().toString()
			);
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult getLastKnownDimension() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisNavLocation lastKnownLocation = tardisLevelOperator.getExteriorManager().getLastKnownLocation();
			return MethodResult.of(
					lastKnownLocation.getDimensionKey().location().toString()
			);
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult getLastKnownDirection() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisNavLocation lastKnownLocation = tardisLevelOperator.getExteriorManager().getLastKnownLocation();
			return MethodResult.of(
					lastKnownLocation.getDirection().toString()
			);
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
	//endregion

	//region FastReturnLocation - Get
	/* todo uncomment when available
	@LuaFunction
	public final MethodResult getFastReturnLocation() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();

			final TardisNavLocation fastReturnLocation = controlManager.getFastReturnLocation();
			if (fastReturnLocation != null)
			{
				return MethodResult.of(
						fastReturnLocation.getPosition().getX(),
						fastReturnLocation.getPosition().getY(),
						fastReturnLocation.getPosition().getZ()
				);
			}
			else
			{
				return MethodResult.of();
			}
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}*/
	//endregion

	//region Dimensions - gets list of dimensions that the tardis is allowed to travel to
	@LuaFunction
	public final MethodResult getDimensions() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final MinecraftServer server = tardisLevelOperator.getLevel().getServer();
			if (server == null)
			{
				//Shouldn't ever actually happen, just adding a comment
				throw new LuaException("Server Null Exception");
			}

			final Set<String> filteredDimensions = StreamSupport.stream(server.getAllLevels().spliterator(), false)//convert server levels to streamable
					.map(Level::dimension)// map to dimension associated with level
					.filter(DimensionUtil::isAllowedDimension)//filter out the non-allowed dimensions, according to tardis refined
					.map(dimension -> dimension.location().toString())//then map those to a string that the user will use
					.collect(Collectors.toSet());//finally, collect it into a table for the user to iterate over

			return MethodResult.of(filteredDimensions);
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
	//endregion

	//region Doors - getInternalDoorOpen, setDoorClosed, getDoorLocked, setDoorLocked
	@LuaFunction
	public final MethodResult getInternalDoorOpen() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			return MethodResult.of(
					tardisLevelOperator.getInternalDoor().isOpen()
			);
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult setDoorClosed(boolean closed) throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();

			blockEntity.getLevel().getServer().tell(new TickTask(1,
					() -> tardisLevelOperator.setDoorClosed(closed)
			));

			return MethodResult.of();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult getDoorLocked() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			return MethodResult.of(tardisLevelOperator.getInternalDoor().locked(), tardisLevelOperator.getExteriorManager().locked());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult setDoorLocked(boolean locked) throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();

			blockEntity.getLevel().getServer().tell(new TickTask(1,
					() ->
					{
						if (tardisLevelOperator.getInternalDoor() != null)
						{
							tardisLevelOperator.getInternalDoor().setLocked(locked);
						}
						if (tardisLevelOperator.getExteriorManager() != null)
						{
							tardisLevelOperator.getExteriorManager().setLocked(locked);
						}
					}
			));

			tardisLevelOperator.setDoorClosed(true);

			return MethodResult.of();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
	//endregion

	//region Cooldown - getIsOnCooldown, getIsCrashing, getCooldownTicks, getCooldownDuration
	@LuaFunction
	public final MethodResult getIsOnCooldown() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			return MethodResult.of(controlManager.isOnCooldown());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult getIsCrashing() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			return MethodResult.of(controlManager.isCrashing());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

/*	todo Uncomment on next 1.19 update
	@LuaFunction
	public final MethodResult getCooldownTicks() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			return MethodResult.of(controlManager.getCooldownTicks());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult getCooldownDuration() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			return MethodResult.of(controlManager.getCooldownDuration());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}*/
	//endregion

	@LuaFunction
	public final MethodResult getCanUseControls() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			return MethodResult.of(controlManager.canUseControls());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	//region AutoLand
	@LuaFunction
	public final MethodResult getIsAutoLandSet() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			return MethodResult.of(controlManager.isAutoLandSet());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult setAutoLand(boolean autoLand) throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();

			blockEntity.getLevel().getServer().tell(new TickTask(1,
					() -> controlManager.setAutoLand(autoLand)
			));

			return MethodResult.of();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
	//endregion

	//region Exterior Theme - getExteriorTheme/setShellTheme/getShellThemes/getShellPatterns/setShellPattern
	@LuaFunction
	public final MethodResult getExteriorTheme() throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();
			//id is probably more useful than the translated key, but maybe we just display it separately.
			//String translated = I18n.get(controlManager.getCurrentExteriorTheme().getSerializedName());
			return MethodResult.of(controlManager.getCurrentExteriorTheme().name());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult setShellTheme(String shellTheme) throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final ShellTheme theme = ShellTheme.valueOf(shellTheme);

			blockEntity.getLevel().getServer().tell(new TickTask(1,
					() -> tardisLevelOperator.setShellTheme(theme)
			));

			return MethodResult.of(theme.name());
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}

	@LuaFunction
	public final MethodResult getShellThemes() throws LuaException
	{
		return MethodResult.of(Arrays.stream(ShellTheme.values()).map(ShellTheme::name).collect(Collectors.toSet()));
	}

	@LuaFunction
	public final MethodResult getShellThemePatterns(String themeName) throws LuaException
	{
		final ShellTheme theme = ShellTheme.valueOf(themeName);
		var patterns = ShellPatterns.getPatternsForTheme(theme);
		var ids = patterns.stream().map(shellPattern -> shellPattern.id().toString()).collect(Collectors.toSet());
		return MethodResult.of(ids);
	}

	@LuaFunction
	public final MethodResult setShellPattern(String shellTheme, String shellPattern) throws LuaException
	{
		final Optional<TardisLevelOperator> optional = TardisLevelOperator.get((ServerLevel) blockEntity.getLevel());
		if (optional.isPresent())
		{
			final TardisLevelOperator tardisLevelOperator = optional.get();
			final TardisControlManager controlManager = tardisLevelOperator.getControlManager();

			final ShellTheme theme = ShellTheme.valueOf(shellTheme);
			final var pattern = ShellPatterns.getPatternOrDefault(theme, new ResourceLocation(shellPattern));

			// needs to be passed back to main thread, so that immersive portals doesn't complain about non
			// main thread trying to add portal entities to the world.
			blockEntity.getLevel().getServer().tell(new TickTask(1,
					() ->
					{
						tardisLevelOperator.setShellTheme(theme);
						tardisLevelOperator.getExteriorManager().setShellPattern(pattern);
					}
			));

			return MethodResult.of();
		}
		else
		{
			throw new LuaException("No Tardis Found");
		}
	}
	//endregion
}
