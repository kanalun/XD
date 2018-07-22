package com.xindian.mvc.i18n;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.ServletContext;

/**
 * 
 * @author Elva
 * @date 2011-2-6
 * @version 1.0
 */
public class ApplicationLocaleProvider implements LocaleProvider
{
	public static final String APPLICATION_LOCALE_KEY = ".application.locale.key";

	private ServletContext servletContext;

	public ApplicationLocaleProvider(ServletContext servletContext)
	{

	}

	@Override
	public void addLocale(int index, Locale locale)
	{
		servletContext.setAttribute(APPLICATION_LOCALE_KEY, locale);
	}

	@Override
	public Enumeration<Locale> getLocales()
	{
		return null;
	}
}
