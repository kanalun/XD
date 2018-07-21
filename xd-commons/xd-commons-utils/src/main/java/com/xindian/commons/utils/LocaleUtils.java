package com.xindian.commons.utils;

import java.util.Locale;

/**
 * 本地化工具
 * 
 * @author Elva
 * @date 2011-2-21
 * @version 1.0
 */
public abstract class LocaleUtils
{
	/**
	 * @param localeStr
	 * @param defaultLocale
	 * @return 如果localeStr中包含locale信息,返回构造的locale;否则返回defaultLocale(可能是null)
	 */
	public static Locale localeFromString(String localeStr, Locale defaultLocale)
	{
		if ((localeStr == null) || (localeStr.trim().length() == 0) || (localeStr.equals("_")))
		{
			return defaultLocale;
		}
		int index = localeStr.indexOf('_');
		if (index < 0)
		{
			return new Locale(localeStr);
		}
		String language = localeStr.substring(0, index);
		if (index == localeStr.length())
		{
			return new Locale(language);
		}
		localeStr = localeStr.substring(index + 1);
		index = localeStr.indexOf('_');
		if (index < 0)
		{
			return new Locale(language, localeStr);
		}
		String country = localeStr.substring(0, index);
		if (index == localeStr.length())
		{
			return new Locale(language, country);
		}
		localeStr = localeStr.substring(index + 1);
		return new Locale(language, country, localeStr);
	}

	/**
	 * 
	 * @param localeStr
	 * @param defaultLocale
	 * @return
	 */
	private static Locale localeFromString2(String localeStr, Locale defaultLocale)
	{
		if ((localeStr == null) || (localeStr.trim().length() == 0) || (localeStr.equals("_")))
		{
			if (defaultLocale != null)
			{
				return defaultLocale;
			}
			return Locale.getDefault();// TODO Remove it,use Exception instead
		}
		int index = localeStr.indexOf('_');
		if (index < 0)
		{
			return new Locale(localeStr);
		}
		String language = localeStr.substring(0, index);
		if (index == localeStr.length())
		{
			return new Locale(language);
		}
		localeStr = localeStr.substring(index + 1);
		index = localeStr.indexOf('_');
		if (index < 0)
		{
			return new Locale(language, localeStr);
		}
		String country = localeStr.substring(0, index);
		if (index == localeStr.length())
		{
			return new Locale(language, country);
		}
		localeStr = localeStr.substring(index + 1);
		return new Locale(language, country, localeStr);
	}
}
