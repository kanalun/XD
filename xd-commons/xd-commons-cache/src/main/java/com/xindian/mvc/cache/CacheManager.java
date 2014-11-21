package com.xindian.mvc.cache;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存助手
 */
public class CacheManager
{
	private static Logger log = LoggerFactory.getLogger(CacheManager.class);

	private static CacheProvider provider;

	public static void init(String cacheProviderClassName)
	{
		try
		{
			CacheManager.provider = (CacheProvider) Class.forName(cacheProviderClassName).newInstance();
			CacheManager.provider.start();
		} catch (Exception e)
		{
			log.error("Unabled to initialize cache provider:" + cacheProviderClassName + ", using ehcache default.", e);
			CacheManager.provider = new EhCacheProvider();
		}
	}

	private final static Cache getCache(String cacheName)
	{
		if (provider == null)
		{
			provider = new EhCacheProvider();
		}
		return provider.buildCache(cacheName);
	}

	/**
	 * 获取缓存中的数据
	 * 
	 * @param name
	 * @param key
	 * @return
	 */
	public final static Object get(String name, Serializable key)
	{
		if (name != null && key != null)
		{
			return getCache(name).get(key);
		}
		return null;
	}

	/**
	 * 获取缓存中的数据
	 * 
	 * @param <T>
	 * @param resultClass
	 * @param name
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final static <T> T get(Class<T> resultClass, String name, Serializable key)
	{
		if (name != null && key != null)
		{
			return (T) getCache(name).get(key);
		}
		return null;
	}

	/**
	 * 写入缓存
	 * 
	 * @param name
	 * @param key
	 * @param value
	 */
	public final static void set(String name, Serializable key, Serializable value)
	{
		if (name != null && key != null && value != null)
		{
			getCache(name).put(key, value);
		}
	}

	/**
	 * 清除缓冲中的某个数据
	 * 
	 * @param name
	 * @param key
	 */
	public final static void evict(String name, Serializable key)
	{
		if (name != null && key != null)
		{
			getCache(name).remove(key);
		}
	}

}
