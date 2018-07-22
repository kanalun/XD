package com.xindian.mvc.i18n;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public class ParameterLocaleProvider implements LocaleProvider
{
	HttpServletRequest request;

	public ParameterLocaleProvider(HttpServletRequest request)
	{
		this.request = request;
	}

	@Override
	@Deprecated
	public void addLocale(int index, Locale locale)
	{

	}

	@Override
	public Enumeration<Locale> getLocales()
	{
		return new Enumeration<Locale>()
		{
			int count = 0;

			@Override
			public boolean hasMoreElements()
			{
				return false;
			}

			@Override
			public Locale nextElement()
			{
				return null;
			}
		};
	}
}
