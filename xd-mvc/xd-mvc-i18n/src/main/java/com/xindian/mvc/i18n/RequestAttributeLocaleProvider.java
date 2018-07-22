package com.xindian.mvc.i18n;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public class RequestAttributeLocaleProvider implements LocaleProvider
{
	public static final String REQUEST_LOCALE_KEY = ".REQUEST_LOCALE_KEY";

	@Override
	public void addLocale(int index, Locale locale)
	{

	}

	@Override
	public Enumeration<Locale> getLocales()
	{
		return null;
	}

	public RequestAttributeLocaleProvider(HttpServletRequest request)
	{
	}
}
