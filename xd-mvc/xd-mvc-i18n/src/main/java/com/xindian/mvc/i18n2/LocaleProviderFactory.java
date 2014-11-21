package com.xindian.mvc.i18n2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class LocaleProviderFactory
{
	private static final char HYPHEN = '-';

	private static final char UNDERSCORE = '_';

	public static final String PAGE_CONTEXT_LOCALE_PROVIDER_KEY = ".com.xindian.mvc.i18n.PageContextLocaleProvider";

	public static final String PARAMETER_LOCALE_PROVIDER_KEY = ".com.xindian.mvc.i18n.ParameterLocaleProvider";

	public static final String SESSION_LOCALE_PROVIDER_KEY = ".com.xindian.mvc.i18n.SessionLocaleProvider";

	public static final String BROWSER_LOCALE_PROVIDER_KEY = ".com.xindian.mvc.i18n.BrowserLocaleProvider";

	public static final String APPLICATION_LOCALE_PROVIDER_KEY = ".com.xindian.mvc.i18n.ApplicationLocaleProvider";

	public static final String REQUEST_ATTRIBUTE_LOCALE_PROVIDER_KEY = ".com.xindian.mvc.i18n.RequestAttributeLocaleProvider";

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

	}

	public static LocaleProvider getLocaleProvider(HttpServletRequest request, ServletContext servletContext,
			PageContext pageContext)
	{
		List<LocaleProvider> all = new ArrayList<LocaleProvider>();

		// PageContext
		if (pageContext != null)
		{
			PageContextLocaleProvider pageContextLocaleProvider = (PageContextLocaleProvider) pageContext
					.getAttribute(PAGE_CONTEXT_LOCALE_PROVIDER_KEY);
			if (pageContextLocaleProvider == null)
			{
				pageContextLocaleProvider = new PageContextLocaleProvider(pageContext);
				pageContext.setAttribute(PAGE_CONTEXT_LOCALE_PROVIDER_KEY, pageContextLocaleProvider);
			}
			all.add(pageContextLocaleProvider);
		}

		// Parameter
		ParameterLocaleProvider parameterLocaleProvider = (ParameterLocaleProvider) request
				.getAttribute(PARAMETER_LOCALE_PROVIDER_KEY);
		if (parameterLocaleProvider == null)
		{
			parameterLocaleProvider = new ParameterLocaleProvider(request);
			request.setAttribute(PARAMETER_LOCALE_PROVIDER_KEY, parameterLocaleProvider);
		}
		all.add(parameterLocaleProvider);

		// RequestAttribute
		RequestAttributeLocaleProvider requestAttributeLocaleProvider = (RequestAttributeLocaleProvider) request
				.getAttribute(REQUEST_ATTRIBUTE_LOCALE_PROVIDER_KEY);
		if (requestAttributeLocaleProvider == null)
		{
			requestAttributeLocaleProvider = new RequestAttributeLocaleProvider(request);
			request.setAttribute(REQUEST_ATTRIBUTE_LOCALE_PROVIDER_KEY, requestAttributeLocaleProvider);
		}
		all.add(requestAttributeLocaleProvider);

		// Session
		SessionLocaleProvider sessionLocaleProvider = (SessionLocaleProvider) request.getSession().getAttribute(
				SESSION_LOCALE_PROVIDER_KEY);
		if (sessionLocaleProvider == null)
		{
			sessionLocaleProvider = new SessionLocaleProvider(request);
			request.getSession().setAttribute(SESSION_LOCALE_PROVIDER_KEY, sessionLocaleProvider);
		}
		all.add(sessionLocaleProvider);

		// Browser
		BrowserLocaleProvider browserLocaleProvider = (BrowserLocaleProvider) request.getAttribute(BROWSER_LOCALE_PROVIDER_KEY);
		if (browserLocaleProvider == null)
		{
			browserLocaleProvider = new BrowserLocaleProvider(request);
			request.setAttribute(BROWSER_LOCALE_PROVIDER_KEY, browserLocaleProvider);
		}
		all.add(browserLocaleProvider);

		// Application
		ApplicationLocaleProvider applicationLocaleProvider = (ApplicationLocaleProvider) servletContext
				.getAttribute(APPLICATION_LOCALE_PROVIDER_KEY);
		if (applicationLocaleProvider == null)
		{
			applicationLocaleProvider = new ApplicationLocaleProvider(servletContext);
			servletContext.setAttribute(APPLICATION_LOCALE_PROVIDER_KEY, applicationLocaleProvider);
		}
		all.add(applicationLocaleProvider);

		MultipleLocaleProvider multipleLocaleProvider = new MultipleLocaleProvider(all.toArray(new LocaleProvider[0]));

		return multipleLocaleProvider;
	}

	public static Locale parseLocale(String locale, String variant)
	{
		Locale ret = null;
		String language = locale;
		String country = null;
		int index = -1;

		if (((index = locale.indexOf(HYPHEN)) > -1) || ((index = locale.indexOf(UNDERSCORE)) > -1))
		{
			language = locale.substring(0, index);
			country = locale.substring(index + 1);
		}

		if ((language == null) || (language.length() == 0))
		{
			throw new IllegalArgumentException("LOCALE_NO_LANGUAGE");
		}

		if (country == null)
		{
			if (variant != null)
				ret = new Locale(language, "", variant);
			else
				ret = new Locale(language, "");
		} else if (country.length() > 0)
		{
			if (variant != null)
				ret = new Locale(language, country, variant);
			else
				ret = new Locale(language, country);
		} else
		{
			throw new IllegalArgumentException("LOCALE_EMPTY_COUNTRY");
		}
		return ret;
	}
}
