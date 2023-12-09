package leaf.trp.functions;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;

import java.util.HashMap;
import java.util.Optional;

public class FunctionsHandler
{
	private static HashMap<String, IFunction> functions = new HashMap<>();

	public static void init()
	{
		registerAll(
				new RunTest()
		);
	}

	public static void register(IFunction function)
	{
		functions.put(function.getName(), function);
	}

	public static void registerAll(IFunction... functions)
	{
		for (IFunction function : functions)
		{
			register(function);
		}
	}

	public static String[] getFunctionsNames()
	{
		return functions.keySet().toArray(new String[0]);
	}

	public static MethodResult run(String functionName, IArguments args) throws LuaException
	{
		Optional<IFunction> function = Optional.ofNullable(functions.get(functionName));
		if (function.isPresent())
		{
			return function.get().run(args);
		}
		return MethodResult.of();
	}


}
