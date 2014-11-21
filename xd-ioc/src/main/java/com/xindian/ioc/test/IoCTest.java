package com.xindian.ioc.test;

import java.lang.reflect.Method;

import com.xindian.ioc.BeanFactory;

public class IoCTest
{

	static BeanFactory beanFactory = BeanFactory.getSingleton();

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		long start = System.currentTimeMillis();

		for (Method m : UserActionImpl.class.getMethods())
		{
			// System.out.println("RUN: " + m + " TIME:" +
			// (System.currentTimeMillis() - start) + "(ms)");
		}
		for (int i = 0; i < 2; i++)
		{
			IoCUserAction userService1 = beanFactory.getBean(IoCUserAction.class);
			IoCUserAction userService2 = beanFactory.getBean(IoCUserAction.class);
			IoCUserAction userService3 = beanFactory.getBean(IoCUserAction.class);

			userService1.login();
			userService2.login();
			userService3.login();

			System.out.println("RUN: " + i + " TIME:" + (System.currentTimeMillis() - start) + "(ms)");
		}
		System.out.println("TIME:" + (System.currentTimeMillis() - start) + "(ms)");
	}
}
