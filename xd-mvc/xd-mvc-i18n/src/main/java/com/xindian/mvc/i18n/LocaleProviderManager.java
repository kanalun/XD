package com.xindian.mvc.i18n;

import java.util.Enumeration;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Vector;

public class LocaleProviderManager
{
	LocaleProvider[] localeProviders = new TestLocaleProvider2[] { new TestLocaleProvider2(), new TestLocaleProvider2() };

	class TestLocaleProvider2 implements LocaleProvider
	{
		Vector<Locale> ls = new Vector<Locale>();
		{
			for (int i = 0; i < 5; i++)
			{
				ls.add(new Locale("zh_" + i));
			}
		}

		@Override
		public void addLocale(int index, Locale locale)
		{
			ls.add(locale);
		}

		@Override
		public Enumeration<Locale> getLocales()
		{
			return ls.elements();
		}
	}

	public static void main(String args[])
	{
		LocaleProviderManager localeProviderManager = new LocaleProviderManager();

		Enumeration<Locale> ls = localeProviderManager.get();

		while (ls.hasMoreElements() || true)
		{
			System.out.println(ls.nextElement());
		}
	}

	public Enumeration<Locale> get()
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
				throw new NoSuchElementException("Vector Enumeration");
			}
		};
	}
}
