package com.xindian.mvc.i18n;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.jsp.PageContext;

public class PageContextLocaleProvider implements LocaleProvider
{
	public static final String PAGE_LOCALE_KEY = ".PAGE_LOCALE_KEY";

	private PageContext pageContext;

	public PageContextLocaleProvider(PageContext pageContext)
	{
		this.pageContext = pageContext;
	}

	@Override
	public void addLocale(int index, Locale locale)
	{
		pageContext.setAttribute(PAGE_LOCALE_KEY, locale);
	}

	@Override
	public Enumeration<Locale> getLocales()
	{
		return null;
	}

}
