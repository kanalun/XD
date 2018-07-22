package com.xindian.mvc.i18n;

import java.util.Enumeration;
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * 
 * @author Elva
 * @date 2011-2-6
 * @version 1.0
 */
public class MultipleLocaleProvider implements LocaleProvider
{
	private LocaleProvider[] localeProviders = new LocaleProvider[0];

	@Override
	public void addLocale(int index, Locale locale)
	{
		// DO_NOTHING
	}

	public MultipleLocaleProvider(LocaleProvider[] localeProviders)
	{
		this.localeProviders = localeProviders;
	}

	@Override
	public Enumeration<Locale> getLocales()
	{
		return new Enumeration<Locale>()
		{
			int count = 0;

			Enumeration<Locale> now;

			@Override
			public boolean hasMoreElements()
			{
				if (now != null && now.hasMoreElements())
				{
					return true;
				}
				for (; count < localeProviders.length;)
				{
					now = localeProviders[count++].getLocales();
					if (now.hasMoreElements())
					{
						return true;
					}
				}
				return false;
			}

			@Override
			public Locale nextElement()
			{
				if (now != null && now.hasMoreElements())
				{
					return now.nextElement();
				}
				for (; count < localeProviders.length;)
				{
					now = localeProviders[count++].getLocales();
					if (now.hasMoreElements())
					{
						return now.nextElement();
					}
				}
				throw new NoSuchElementException("MultipleLocaleProvider Enumeration");
			}
		};
	}
}
