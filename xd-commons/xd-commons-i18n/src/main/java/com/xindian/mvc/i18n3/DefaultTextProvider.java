package com.xindian.mvc.i18n3;

import java.util.List;

/**
 * @author Elva
 * @date 2011-2-4
 * @version 1.0
 */
public class DefaultTextProvider implements TextProvider
{
	private static final Object[] EMPTY_ARGS = new Object[0];

	public static final DefaultTextProvider INSTANCE = new DefaultTextProvider();

	private LocaleProvider localeProvider;

	private ResourceProvider resourceProvider;

	public LocaleProvider getLocaleProvider()
	{
		return localeProvider;
	}

	public void setLocaleProvider(LocaleProvider localeProvider)
	{
		this.localeProvider = localeProvider;
	}

	public ResourceProvider getResourceProvider()
	{
		return resourceProvider;
	}

	public void setResourceProvider(ResourceProvider resourceProvider)
	{
		this.resourceProvider = resourceProvider;
	}

	private DefaultTextProvider()
	{
		setLocaleProvider(new SimpleLocaleProvider());
		setResourceProvider(DefaultResourceProvider.INSTANCE);
	}

	@Override
	public String getText(String key)
	{
		return getText(key, (String) null);
	}

	@Override
	public String getText(String key, String defaultValue)
	{
		return resourceProvider.findText(key, getLocaleProvider().getLocale());
	}

	@Override
	public String getText(String key, List args)
	{
		return getText(key, null, args);
	}

	@Override
	public String getText(String key, Object... args)
	{
		return I18N.getText(key, null, getResourceProvider(), getLocaleProvider(), args);
	}

	@Override
	public String getText(String key, String defaultValue, List args)
	{
		Object[] params;
		if (args != null)
		{
			params = args.toArray();
		} else
		{
			params = EMPTY_ARGS;
		}
		return I18N.getText(key, defaultValue, getResourceProvider(), getLocaleProvider(), params);
	}

	@Override
	public String getText(String key, String defaultValue, Object... args)
	{
		return I18N.getText(key, defaultValue, getResourceProvider(), getLocaleProvider(), args);
	}
}
