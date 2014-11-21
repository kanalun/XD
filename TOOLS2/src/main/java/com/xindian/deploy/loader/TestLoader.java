package com.xindian.deploy.loader;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.apache.catalina.LifecycleException;

public class TestLoader
{
	private static String APP_BASE_DIR = "file:/D:/APP";

	private static String DEFAULT_CLASSES_PATH = APP_BASE_DIR + "/classes/";

	private static String DEFAULT_JAR_PATH = APP_BASE_DIR + "/lib/TestClass.jar";

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws LifecycleException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) throws ClassNotFoundException, LifecycleException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException
	{
		ApplicationClassLoader loader = new ApplicationClassLoader(null);
		loader.addRepository(DEFAULT_CLASSES_PATH);
		System.out.println(DEFAULT_CLASSES_PATH);
		loader.addRepository(DEFAULT_JAR_PATH);
		loader.setJarPath(DEFAULT_JAR_PATH);
		loader.setDelegate(true);
		loader.setSearchExternalFirst(true);
		loader.start();
		Class<?> calzz = loader.loadClass("TestClass");
		Object obj = calzz.newInstance();
		loader.loadClass("com.xindian.deploy.TestClass2");

		// loader.getURLs();

		URL[] urls = loader.getURLs();
		for (URL url : urls)
		{
			System.out.println("url:" + url);
		}
		System.out.println("------:" + System.getProperty("java.class.path"));
		// WebappClassLoader loader2 = new WebappClassLoader(null);
		// loader2.addRepository("file:/D:/");
		// loader2.setDelegate(false);
		// loader2.start();
		// Class<?> calzz2 = loader2.loadClass("TestClass");

		// calzz2.cast(obj);
		// obj.getClass().getClassLoader();
		calzz.getMethod("start").invoke(obj);
		System.out.println("LOADER:" + calzz.getClassLoader());
		// System.out.println("LOADER:" + calzz2.getClassLoader());
	}
}
