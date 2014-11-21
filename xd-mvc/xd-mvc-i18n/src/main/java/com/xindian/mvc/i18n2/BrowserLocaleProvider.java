package com.xindian.mvc.i18n2;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * 每一个请求一个BrowserLocaleProvider,用以从用户浏览器的设置中获取本地信息
 * 
 * @author Elva
 * @date 2011-2-6
 * @version 1.0
 */
public class BrowserLocaleProvider implements LocaleProvider
{
	private HttpServletRequest request;

	public BrowserLocaleProvider(HttpServletRequest request)
	{
		this.request = request;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Enumeration<Locale> getLocales()
	{
		return request.getLocales();
	}

	@Override
	@Deprecated
	public void addLocale(int index, Locale locale)
	{
		// DO_NOTHING
	}

}
