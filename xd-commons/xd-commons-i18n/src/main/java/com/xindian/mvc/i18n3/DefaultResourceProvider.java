package com.xindian.mvc.i18n3;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用ResourceBundle为系统提供原始资源!
 * 
 * @author Elva
 * @date 2011-2-4
 * @version 1.0
 */
public class DefaultResourceProvider implements ResourceProvider
{
	private static final Logger logger = LoggerFactory.getLogger(DefaultResourceProvider.class);

	public static DefaultResourceProvider INSTANCE = new DefaultResourceProvider();

	private List<String> defaultResourceBundleNames = null;

	private final Map<String, String> misses = new ConcurrentHashMap<String, String>();// 找不到的资源

	public DefaultResourceProvider()
	{
		clearDefaultResourceBundles();
	}

	@Override
	public String findText(String textKey, Locale locale)
	{
		logger.debug("findText:[" + textKey + "]	Locale:[" + locale + "]");
		List<String> bundleNames = defaultResourceBundleNames; // it isn't
																// sync'd, but
																// this is so
																// rare, let's
																// do it anyway
		for (Iterator<String> iterator = bundleNames.iterator(); iterator.hasNext();)
		{
			String bundleName = iterator.next();
			logger.debug("	findText:[" + textKey + "]	Locale:[" + locale + "] try:" + bundleName);
			ResourceBundle bundle = findResourceBundle(bundleName, locale);
			if (bundle != null)
			{
				try
				{
					return bundle.getString(textKey);
				} catch (MissingResourceException e)
				{
					// ignore and try others
				}
			}
		}
		return null;
	}

	/**
	 * Find text from ResourceBundle whose name is bundleName
	 */
	public String findText(String bundleName, String textKey, Locale locale)
	{
		ResourceBundle bundle = findResourceBundle(bundleName, locale);
		if (bundle != null)
		{
			try
			{
				return bundle.getString(textKey);
			} catch (MissingResourceException e)
			{
				// ignore and try others
			}
		}
		return null;
	}

	@Override
	public void reloadResources()
	{
		reloadBundles();
	}

	@Override
	public void addResource(String name)
	{
		addDefaultResourceBundle(name);
	}

	@Override
	public boolean removeResource(String name)
	{
		return defaultResourceBundleNames.remove(name);
	}

	@Override
	public void clearResources()
	{
		clearDefaultResourceBundles();
	}

	/* --------------------------------------------------------------- */

	protected void clearDefaultResourceBundles()
	{
		if (defaultResourceBundleNames != null)
		{
			defaultResourceBundleNames.clear();
		}
		defaultResourceBundleNames = Collections.synchronizedList(new ArrayList<String>());
	}

	protected void addDefaultResourceBundle(String resourceBundleName)
	{
		// make sure this doesn't get added more than once
		defaultResourceBundleNames.remove(resourceBundleName);
		defaultResourceBundleNames.add(0, resourceBundleName);
		if (logger.isDebugEnabled())
		{
			logger.debug("Added default resource bundle '" + resourceBundleName + "' to default resource bundles = "
					+ defaultResourceBundleNames);
		}
	}

	/**
	 * 
	 * @param bundleName
	 * @param locale
	 * @return
	 */
	protected ResourceBundle findResourceBundle(String bundleName, Locale locale)
	{
		String key = createMissesKey(bundleName, locale);
		try
		{
			if (!misses.containsKey(key))
			{
				return ResourceBundle.getBundle(bundleName, locale, Thread.currentThread().getContextClassLoader());
			}
		} catch (MissingResourceException ex)
		{
			misses.put(key, bundleName);
		}
		return null;
	}

	private static String createMissesKey(String bundleName, Locale locale)
	{
		return bundleName + "_" + locale.toString();
	}

	private static void reloadBundles()
	{
		try
		{
			clearMap(ResourceBundle.class, null, "cacheList");// clear
																// cacheList:Map
																// In
																// ResourceBundle
			// now, for the true and utter hack, if we're running in tomcat,
			// clear
			// it's class loader resource cache as well.
			clearTomcatCache();
		} catch (Exception e)
		{
			logger.error("Could not reload resource bundles", e);
		}
	}

	@SuppressWarnings("unchecked")
	private static void clearMap(Class cl, Object obj, String name) throws NoSuchFieldException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException
	{
		Field field = cl.getDeclaredField(name);
		field.setAccessible(true);

		Object cache = field.get(obj);

		synchronized (cache)
		{
			Class ccl = cache.getClass();
			Method clearMethod = ccl.getMethod("clear");
			clearMethod.invoke(cache);
		}

	}

	@SuppressWarnings("unchecked")
	private static void clearTomcatCache()
	{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		// no need for compilation here.
		Class cl = loader.getClass();
		try
		{
			if ("org.apache.catalina.loader.WebappClassLoader".equals(cl.getName()))
			{
				clearMap(cl, loader, "resourceEntries");
			} else
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("class loader " + cl.getName() + " is not tomcat loader.");
				}
			}
		} catch (Exception e)
		{
			logger.warn("couldn't clear tomcat cache", e);
		}
	}

}
