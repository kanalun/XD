package com.xindian.mvc.test;

import java.util.Collection;

public class PrintUtils
{
	public static void pln(Object o)
	{
		if (o instanceof String)
		{
			System.out.println(o);
		} else if (o != null)
		{
			System.out.println(JsonUtils.getJson(o));
		} else
		{
			System.out.println("ERROR @PrintUtils... THIS Object IS NULL!!!");
		}
	}

	public static void pln(Object[] os)
	{
		for (Object o : os)
		{
			if (o instanceof String)
			{
				System.out.println(o);
			} else if (o != null)
			{
				System.out.println(JsonUtils.getJson(o));
			} else
			{
				System.out.println("ERROR @PrintUtils... THIS Object IS NULL!!!");
			}
		}
	}

	public static void p(Object o)
	{
		if (o instanceof String)
		{
			System.out.print(o);
		} else if (o != null)
		{
			System.out.print(JsonUtils.getJson(o));
		} else
		{
			System.out.print("ERROR @PrintUtils... THIS Object IS NULL!!!");
		}
	}

	public static <T> void pln(Collection<T> collection)
	{
		for (T a : collection)
		{
			if (a instanceof String)
			{
				System.out.println(a);
			}
			{
				System.out.println(JsonUtils.getJson(a));
			}
		}
	}

	public static <T> void plnStr(Collection<T> list)
	{
		for (T a : list)
		{
			System.out.println(a.toString());
		}

	}

	public static <T> void plnJson(Collection<T> list)
	{
		for (T a : list)
		{
			System.out.println(JsonUtils.getJson(a));
		}
	}
}
