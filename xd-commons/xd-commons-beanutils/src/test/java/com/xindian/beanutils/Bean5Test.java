package com.xindian.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.xindian.beanutils.Address;
import com.xindian.beanutils.Bean5;
import com.xindian.beanutils.BeanUtilsTest;
import com.xindian.beanutils.User;

public class Bean5Test
{
	private static User[][][][][][] us;//

	public static void main3(String args[]) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException, InstantiationException
	{
		User[] users = new User[4];

		users[3] = new User();

		int[] a = { 1, 2, 3, 4 };

		int i = 0;
		users = setObjectToArray(users, 10, new User());
		for (User u : users)
		{
			System.out.println(i++ + "	" + u);
		}
	}

	public static void main7(String args[]) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException, InstantiationException
	{

		// User[][] us = null;// = new User[5][2];

		// us.getClass().getComponentType().isArray();

		//
		Class<?> t = BeanUtilsTest.getField(Bean5.class, "us").getType();
		int d = 0;
		while (t.isArray())
		{
			d++;
			t = t.getComponentType();
		}

		// Array.get(us, 1).getClass();

		System.out.println(d);

		User user = new User();

		String name = "address.city";

		String ss = "ss";

		String ssValue = "s0";

		String value = "HUZHOU";

		Address address = new Address();

		address.setCity("ddddddddddd");

		// setValue(user, "name[1].map(a)", address);
		// setValue(user, "list[10].list[0]", address);
		// setValue(user, "list[10]", address);
		// setValue(user, "map(huzhou).city", "HUZHOU");
		// Class<?> entityClass = (Class<?>) ((ParameterizedType)

		// System.out.println("	l " +
		// user.getList().get(10).getList().get(0).getClass());

		// System.out.println("	USER:map(huzhou).city: " +
		// user.getMap().get("huzhou").getCity());
		// System.out.println("	USER:map(hangzhou).city: " +
		// user.getMap().get("hangzhou").getMap().get("huzhou"));

		// System.out.println("	USER:map.size: " +
		// user.getMap().values().size());
		// System.out.println("MAP:" + resolver.getProperty("qcc(a)"));
	}

	public static void main(String args[]) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException, InstantiationException
	{
		User user = new User();

		String name1 = "address.city";
		String name2 = "address.city";
		String name3 = "address.city";
		String name4 = "address.city";
		String name5 = "address.city";
		String name6 = "address.city";
		String name7 = "address.city";
		String name8 = "address.city";

		String ss = "ss";

		String ssValue = "s0";

		String value = "HUZHOU";

		Address address = new Address();

		address.setCity("ZHEJIANG");

		// setValue(user, "name[1].map(a)", address);

		setValue(user, "list[10]", address);
		// setValue(user, "list[10].city", "HUZHOU");
		// setValue(user, "map(huzhou).city", "HUZHOU");
		// Class<?> entityClass = (Class<?>) ((ParameterizedType)

		System.out.println("	USER:map(huzhou).city: " + user.getList().get(10).getCity());
		System.out.println(List.class.isAssignableFrom(ArrayList.class));
		// System.out.println("	USER:map(huzhou).city: " +
		// user.getMap().get("huzhou").getCity());
		// System.out.println("	USER:map(hangzhou).city: " +
		// user.getMap().get("hangzhou").getMap().get("huzhou"));

		// System.out.println("	USER:map.size: " +
		// user.getMap().values().size());
		// System.out.println("MAP:" + resolver.getProperty("qcc(a)"));
	}

}
