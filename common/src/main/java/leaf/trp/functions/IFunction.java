package leaf.trp.functions;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;

public interface IFunction
{
	public String getName();

	public MethodResult run(IArguments args) throws LuaException;
}
