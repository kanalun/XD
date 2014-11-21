package com.xindian.beanutils.temp;

import java.lang.reflect.Array;
import java.util.Random;

public class ArrayCreator
{
	public static void main(String args[])
	{
		Object array = Array.newInstance(int.class, 3);
		printType(array);
		fillArray(array);
		displayArray(array);
	}

	private static void printType(Object object)
	{
		Class type = object.getClass();
		if (type.isArray())
		{
			Class elementType = type.getComponentType();
			System.out.println("Array of: " + elementType);
			System.out.println("Array size: " + Array.getLength(object));
		}
	}

	private static void fillArray(Object array)
	{
		int length = Array.getLength(array);
		Random generator = new Random(System.currentTimeMillis());
		for (int i = 0; i < length; i++)
		{
			int random = generator.nextInt();
			Array.setInt(array, i, random);
		}
	}

	private static void displayArray(Object array)
	{
		int length = Array.getLength(array);
		for (int i = 0; i < length; i++)
		{
			int value = Array.getInt(array, i);
			System.out.println("Position: " + i + ", value: " + value);
		}
	}
}