package com.xindian.commons.i18n;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Random
{
	private static Map cache = new ConcurrentHashMap();

	public static <T extends Randoms> T create(Class<T> type) // throws
	{
		T constants = (T) cache.get(type);
		if (constants == null)
		{
			constants = RandomsInvocationHandler.newInstance(type);
			cache.put(type, constants);
		}
		return constants;
	}

}
