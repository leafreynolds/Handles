package leaf.trp.peripherals.refined;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.core.computer.ComputerSide;
import leaf.trp.blockEntities.FezTile;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RefinedPeripheral implements IPeripheral
{
	private final FezTile fezTile;

	public List<IComputerAccess> computers = new ArrayList<IComputerAccess>();

	public RefinedPeripheral(FezTile fezTile)
	{
		this.fezTile = fezTile;
	}

	@Nonnull
	@Override
	public String getType()
	{
		return "tardis";
	}

	@Override
	public boolean equals(IPeripheral other)
	{
		return this == other;
	}

	@Override
	public void attach(IComputerAccess computer)
	{
		computers.add(computer);
	}

	@Override
	public void detach(IComputerAccess computer)
	{
		computers.remove(computer);
	}

	//called from tick?
	public void newEvent()
	{
		boolean someValue = false;
		for (IComputerAccess computer : computers)
		{
			computer.queueEvent("some_event_name", computer.getAttachmentName(), someValue);
		}
	}

	@LuaFunction
	public final void open(int channel) throws LuaException
	{
		try
		{
			//state.open(parseChannel(channel));
		}
		catch (Exception e)
		{
			throw new LuaException(e.getMessage());
		}
	}

	@LuaFunction
	public final void write(IArguments arguments) throws LuaException
	{
		//String text = StringUtil.toString(arguments.get(0));
		//Terminal page = getCurrentPage();
		//page.write(text);
		//page.setCursorPos(page.getCursorX() + text.length(), page.getCursorY());
	}

	@LuaFunction({"getAnalogOutput", "getAnalogueOutput"})
	public final int getAnalogOutput(ComputerSide side)
	{
		return 0;//environment.getOutput(side);
	}
}
