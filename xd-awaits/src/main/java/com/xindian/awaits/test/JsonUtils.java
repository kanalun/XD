package com.xindian.awaits.test;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.xindian.awaits.validate.Validator;

public class JsonUtils
{
	public static String getJson(Object object)
	{
		return getJson(describe(object));
	}

	public static Map<String, Object> describe(Object object)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> t = object.getClass();
		Field[] fs = t.getDeclaredFields();

		try
		{
			for (Field field : fs)
			{
				Object value = field.get(object);
				map.put(field.getName(), value);
			}
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return map;

	}

	public static String getJson(Map<String, Object> javaObjectAttributes)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		Set<String> names = javaObjectAttributes.keySet();
		Iterator<String> nameIt = names.iterator();
		int i = 0;
		while (nameIt.hasNext())
		{
			String name = nameIt.next();
			Object value = javaObjectAttributes.get(name);
			sb.append(getJsonAttr(name, value));
			if (i < names.size() - 1)
			{
				sb.append(",");
			}
			i++;
		}
		sb.append("}");
		return sb.toString();
	}

	// FIXME
	private static String getJsonAttr(String name, Object value)
	{
		String temp = null;
		if (Validator.isNumeric(value))
		{
			temp = "\"{?}\" : {?}";
		} else
		{
			temp = "\"{?}\" : \"{?}\"";
		}
		return StringUtils.fillString(temp, name, value);
	}

	public static void main(String args[])
	{
		System.out.println(getJson(new Person()));
		System.out.println(getJsonAttr("id", 1));
		System.out.println(getJsonAttr("id", new Integer(1)));
		System.out.println(getJsonAttr("id", 1.1F));
		System.out.println(getJsonAttr("id", 1.2D));// double
		System.out.println(getJsonAttr("id", 1L));// LONG
		System.out.println(getJsonAttr("id", new Date()));
		System.out.println(getJsonAttr("id", "1"));
	}
}
