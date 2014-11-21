package com.xindian.mvc.utils;

/**
 * 
 * @author Elva
 * @date 2011-2-26
 * @version 1.0
 */
public abstract class TypeUtils
{
	/**
	 * 判断value是否能兼容类型type
	 * 
	 * @param value
	 * @param type
	 * @return
	 */
	public static boolean isCompatibleType(Object value, Class<?> type)
	{
		// Do object check first, then primitives
		if (value == null || type.isInstance(value))
		{
			return true;
		}
		if (type.isPrimitive())// primitives
		{
			if (type.equals(Integer.TYPE) && Integer.class.isInstance(value))
			{
				return true;

			} else if (type.equals(Long.TYPE) && Long.class.isInstance(value))
			{
				return true;

			} else if (type.equals(Double.TYPE) && Double.class.isInstance(value))
			{
				return true;

			} else if (type.equals(Float.TYPE) && Float.class.isInstance(value))
			{
				return true;

			} else if (type.equals(Short.TYPE) && Short.class.isInstance(value))
			{
				return true;

			} else if (type.equals(Byte.TYPE) && Byte.class.isInstance(value))
			{
				return true;

			} else if (type.equals(Character.TYPE) && Character.class.isInstance(value))
			{
				return true;

			} else if (type.equals(Boolean.TYPE) && Boolean.class.isInstance(value))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 如果是基本类型,返回基本类型的包装类型;否则(非基本类型/null)返回原类型
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class primitive(Class type)
	{
		if (type == null || !type.isPrimitive())
		{
			return type;
		}
		if (type == Integer.TYPE)
		{
			return Integer.class;
		} else if (type == Double.TYPE)
		{
			return Double.class;
		} else if (type == Long.TYPE)
		{
			return Long.class;
		} else if (type == Boolean.TYPE)
		{
			return Boolean.class;
		} else if (type == Float.TYPE)
		{
			return Float.class;
		} else if (type == Short.TYPE)
		{
			return Short.class;
		} else if (type == Byte.TYPE)
		{
			return Byte.class;
		} else if (type == Character.TYPE)
		{
			return Character.class;
		} else
		{
			return type;
		}
	}
}
