package com.xindian.mvc.i18n3;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 国际化工具类
 * 
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
public class I18N
{
	private static final Logger logger = LoggerFactory.getLogger(I18N.class);

	private static ResourceProvider resourceProvider;

	private static final Object[] EMPTY_ARGS = new Object[0];

	private static final Map cache = new ConcurrentHashMap();

	/**
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Constants> T create(Class<T> type) // throws
	{
		T constants = (T) cache.get(type);
		if (constants == null)
		{
			constants = ConstantsInvocationHandler.newInstance(type);
			cache.put(type, constants);
		}
		return constants;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Constants> T create(Class<T> type, ResourceProvider resourceProvider, LocaleProvider localeProvider) // throws
	{
		T constants = (T) cache.get(type);
		if (constants == null)
		{
			constants = ConstantsInvocationHandler.newInstance(type);
			cache.put(type, constants);
		}
		return constants;
	}

	/**
	 * 
	 * @param key
	 * @param defaultText
	 * @param resourceProvider
	 * @param localeProvider
	 * @param args
	 * @return
	 */
	public static String getText(String key, String defaultText, ResourceProvider resourceProvider,
			LocaleProvider localeProvider, Object... args)
	{
		Locale locale = localeProvider.getLocale();

		logger.debug("=============" + locale);

		String text = resourceProvider.findText(key, locale);
		if (text == null)
		{
			text = defaultText;
		}
		if (text == null)
		{
			return null;
		}
		MessageFormat textFormater = new MessageFormat(text);
		textFormater.setLocale(locale);
		textFormater.applyPattern(text);
		if (args == null)
		{
			return textFormater.format(EMPTY_ARGS);
		}
		return textFormater.format(args);

	}
}
