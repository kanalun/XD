package com.xindian.mvc.web;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.i18n.DefaultTextProvider;
import com.xindian.commons.i18n.LocaleProvider;
import com.xindian.commons.i18n.TextProvider;
import com.xindian.mvc.Errors;
import com.xindian.mvc.MVCAction;
import com.xindian.mvc.annotation.Forbidden;

/**
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ActionSupport implements TextProvider, LocaleProvider, Serializable, MVCAction
{
	private static Logger logger = LoggerFactory.getLogger(ActionSupport.class);

	private Errors errors;

	private static final transient TextProvider textProvider = DefaultTextProvider.INSTANCE;

	@Forbidden
	@Override
	public String getText(String aTextName)
	{
		return textProvider.getText(aTextName);
	}

	@Forbidden
	@Override
	public String getText(String aTextName, String defaultValue)
	{
		return textProvider.getText(aTextName, defaultValue);
	}

	@Forbidden
	@Override
	public String getText(String aTextName, List args)
	{
		return textProvider.getText(aTextName, args);
	}

	@Forbidden
	@Override
	public String getText(String key, Object... args)
	{
		return textProvider.getText(key, args);
	}

	@Forbidden
	@Override
	public String getText(String key, String defaultValue, List args)
	{
		return textProvider.getText(key, defaultValue, args);
	}

	@Forbidden
	@Override
	public String getText(String key, String defaultValue, Object... args)
	{
		return textProvider.getText(key, defaultValue, args);
	}

	@Override
	public Enumeration<Locale> getLocales()
	{
		return null;
		// return HttpLocaleProviderSupport.getLocale(request, servletContext,
		// pageContext, defaultLocale);
	}

	@Override
	public void addLocale(int index, Locale locale)
	{

	}
}
