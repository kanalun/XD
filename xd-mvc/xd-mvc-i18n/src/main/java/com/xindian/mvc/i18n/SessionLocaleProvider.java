package com.xindian.mvc.i18n;

import java.util.Enumeration;
import java.util.Locale;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionLocaleProvider implements LocaleProvider
{
	public static final String SESSION_LOCALE_KEY = ".SESSION_LOCALE_KEY";

	final HttpServletRequest request;

	public SessionLocaleProvider(HttpServletRequest request)
	{
		this.request = request;
	}

	@Override
	public void addLocale(int index, Locale locale)
	{
		HttpSession session = request.getSession();
		synchronized (session)// 防止多个线程对同一个session设置,貌似这个没太大意义
		{
			session.setAttribute(SESSION_LOCALE_KEY, locale);
		}
	}

	@Override
	public Enumeration<Locale> getLocales()
	{
		return new Enumeration<Locale>()
		{
			HttpSession session;

			int count = 0;

			@Override
			public boolean hasMoreElements()
			{
				session = request.getSession(false);
				if (session == null)
				{
					return false;
				} else if (count > 0)
				{
					return false;
				} else
				{
					return session.getAttribute("") != null;
				}
			}

			@Override
			public Locale nextElement()
			{
				session = request.getSession(false);
				if (session == null)
				{
					throw new NoSuchElementException("SessionLocaleProvider");
				} else if (count > 0)
				{
					throw new NoSuchElementException("SessionLocaleProvider");

				} else if ((Locale) session.getAttribute("") == null)
				{
					throw new NoSuchElementException("SessionLocaleProvider");
				} else
				{
					count++;
					return (Locale) session.getAttribute("");
				}
			}
		};
	}
}
