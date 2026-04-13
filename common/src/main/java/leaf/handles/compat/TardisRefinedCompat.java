package leaf.handles.compat;

import leaf.handles.blockEntities.TardisPeripheralTile;
import whocraft.tardis_refined.api.event.TardisCommonEvents;

public class TardisRefinedCompat
{

	public static void init()
	{
		TardisCommonEvents.TAKE_OFF.register(TardisPeripheralTile::onTakeOff);
		TardisCommonEvents.LAND.register(TardisPeripheralTile::onLand);
		TardisCommonEvents.TARDIS_ENTRY_EVENT.register(TardisPeripheralTile::onTardisEntered);
		TardisCommonEvents.DOOR_CLOSED_EVENT.register(TardisPeripheralTile::onDoorClosed);
		TardisCommonEvents.DOOR_OPENED_EVENT.register(TardisPeripheralTile::onDoorOpened);
		TardisCommonEvents.SHELL_CHANGE_EVENT.register(TardisPeripheralTile::onShellChanged);
		TardisCommonEvents.TARDIS_CRASH_EVENT.register(TardisPeripheralTile::onCrashed);
	}
}
