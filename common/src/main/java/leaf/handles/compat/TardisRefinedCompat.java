package leaf.handles.compat;

import leaf.handles.blockEntities.TardisPeripheralTile;
import whocraft.tardis_refined.api.event.TardisEvents;

public class TardisRefinedCompat
{

	public static void init()
	{
		TardisEvents.TAKE_OFF.register(TardisPeripheralTile::onTakeOff);
		TardisEvents.LAND.register(TardisPeripheralTile::onLand);
		TardisEvents.TARDIS_ENTRY_EVENT.register(TardisPeripheralTile::onTardisEntered);
		TardisEvents.DOOR_CLOSED_EVENT.register(TardisPeripheralTile::onDoorClosed);
		TardisEvents.DOOR_OPENED_EVENT.register(TardisPeripheralTile::onDoorOpened);
		TardisEvents.SHELL_CHANGE_EVENT.register(TardisPeripheralTile::onShellChanged);
		TardisEvents.TARDIS_CRASH_EVENT.register(TardisPeripheralTile::onCrashed);
	}
}
