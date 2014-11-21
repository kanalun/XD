package com.xindian.mvc.i18n2;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public class CookieLocaleProvider implements LocaleProvider
{
	public static final String COOKIE_LOCALE_KEY = ".cookie.locale.key";

	private HttpServletRequest request;

	public CookieLocaleProvider(HttpServletRequest request)
	{
		this.request = request;
	}

	@Override
	public void addLocale(int index, Locale locale)
	{

	}

	@Override
	public Enumeration<Locale> getLocales()
	{
		return null;
	}
}
