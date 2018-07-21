package com.xindian.commons.i18n;

import java.util.Locale;

import com.xindian.commons.promise.ThreadSafe;

/**
 * 为系统提供原始资源
 * 
 * @author Elva
 * @date 2011-2-4
 * @version 1.0
 */
public interface ResourceProvider extends ThreadSafe
{
	/**
	 * @param key
	 *            资源对应的key
	 * @param locale
	 *            本地信息
	 * @return
	 */
	public String findText(String key, Locale locale);

	/**
	 * 在特定的bundle中获得key的文本
	 * 
	 * @param bundleName
	 * @param key
	 * @param locale
	 * @return
	 */
	public String findText(String bundleName, String key, Locale locale);

	/**
	 * 重新载入现有资源,清除缓存等..
	 */
	public void reloadResources();

	/**
	 * 添加资源
	 * 
	 * @param name
	 *            资源名称
	 */
	public void addResource(String name);

	/**
	 * 删除资源
	 * 
	 * @param name
	 *            资源名称
	 */
	public boolean removeResource(String name);

	/**
	 * 删除所有资源
	 */
	public void clearResources();
}
