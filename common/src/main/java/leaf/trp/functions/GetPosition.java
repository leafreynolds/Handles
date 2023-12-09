package leaf.trp.functions;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.MethodResult;

public class GetPosition implements IFunction
{
	@Override
	public String getName()
	{
		return "getPos";
	}

	@Override
	public MethodResult run(IArguments args)
	{

		return MethodResult.of("Success");
	}
}
