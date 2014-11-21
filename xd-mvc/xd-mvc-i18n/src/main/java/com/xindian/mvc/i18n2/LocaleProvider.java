package com.xindian.mvc.i18n2;

import java.util.Enumeration;
import java.util.Locale;

public interface LocaleProvider
{
	public static final String LOCALE_KEY = ".SESSION_LOCALE_KEY";

	void addLocale(int index, Locale locale);

	Enumeration<Locale> getLocales();
}
