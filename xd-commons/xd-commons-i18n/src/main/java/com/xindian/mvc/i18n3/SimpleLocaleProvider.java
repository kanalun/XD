package com.xindian.mvc.i18n3;

import java.util.Locale;

/**
 * 如果你无需国际化,你可以使用这个本地信息Provider,他提供默认的本地信息
 * 
 * @author Elva
 * @date 2011-2-4
 * @version 1.0
 */
public class SimpleLocaleProvider implements LocaleProvider
{
	private static final long serialVersionUID = 1L;

	private Locale locale = Locale.getDefault();

	@Override
	public Locale getLocale()
	{
		return locale;
	}

	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}
}
