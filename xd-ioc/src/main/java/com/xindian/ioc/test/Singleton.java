package com.xindian.ioc.test;

public class Singleton
{
	private static final ThreadLocal perThreadInstance = new ThreadLocal();

	private static Singleton singleton;

	private Singleton()
	{
	}

	public static Singleton getInstance()
	{
		if (perThreadInstance.get() == null)
		{
			// 每个线程第一次都会调用
			createInstance();
		}
		return singleton;
	}

	private static final void createInstance()
	{
		synchronized (Singleton.class)
		{
			if (singleton == null)
			{
				singleton = new Singleton();
			}
		}
		perThreadInstance.set(perThreadInstance);
	}
}