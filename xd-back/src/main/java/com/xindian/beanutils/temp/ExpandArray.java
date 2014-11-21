package com.xindian.beanutils.temp;

import java.lang.reflect.Array;

public class ExpandArray
{
	private static float loadFactor = 0.75f;
	private static int DEFAULT_INITIAL_CAPACITY = 16;

	public static void main(String[] args)
	{
		Object[] values = new Object[DEFAULT_INITIAL_CAPACITY];
		Object[] expandObject = (Object[]) expand(values, (int) (values.length * loadFactor));
	}

	public static Object expand(Object array, int newSize)
	{
		if (array == null)
		{
			return null;
		}
		Class c = array.getClass();// 返回此object运行时类
		if (c.isArray())
		{
			// Array提供了动态创建和访问数组的
			int len = Array.getLength(array);// 返回指定数组的长度
			if (len >= newSize)
			{
				return array;
			} else
			{
				Class classTypeClass = c.getComponentType();// 返回表示数组组件类型的class
				// 创建一个指定组件类型和长度的新数组
				Object newArray = Array.newInstance(classTypeClass, newSize);
				/**
				 * arraycopy(Object src,原数组 int srcPos,原数组中的起始位置 Object
				 * dest,目标数组
				 * 
				 * int destPos,目标数组的起始位置 int length)要复制的数组元素的数量
				 */
				System.arraycopy(array, 0, newArray, 0, len);
				return newArray;
			}
		} else
		{
			throw new ClassCastException("传入的参必须是数组!");
		}
	}
}