package com.xindian.mvc.test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.xindian.mvc.utils.Validator;

/**
 * 简单Json工具类,不支持嵌套等..用于测试打印
 * 
 * @author Elva
 * 
 */
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
				if (!Modifier.isStatic(field.getModifiers()))
				{
					Object value = field.get(object);
					map.put(field.getName(), value);
				}
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

	}
}
