package com.xindian.mvc.i18n2;

import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.utils.LocaleUtils;
import com.xindian.commons.utils.Validator;

/**
 * 对定制的本地信息读取和设定提供支持
 * 
 * @author Elva
 * @date 2011-2-6
 * @version 1.0
 */
public class HttpLocaleProviderSupport
{
	private static Logger logger = LoggerFactory.getLogger(HttpLocaleProviderSupport.class);

	// scope
	public static final String PAGE = "page";

	public static final String REQUEST = "request";

	public static final String SESSION = "session";

	public static final String APPLICATION = "application";

	public static final String COOKIE = "cookie";

	// key
	public static String PARAMETER_LOCALE_KEY = "locale";// 这个来自于配置

	public static final String PAGE_LOCALE_KEY = ".page.locale.key";

	public static final String REQUEST_LOCALE_KEY = ".request.locale.key";

	public static final String SESSION_LOCALE_KEY = ".session.locale.key";

	public static final String COOKIE_LOCALE_KEY = ".cookie.locale.key";

	public static final String APPLICATION_LOCALE_KEY = ".application.locale.key";

	// context
	public static final String CONTEXT_MAXAGE_KEY = "maxAge";

	public static final String CONTEXT_DOMAIN_KEY = "domain";

	public static final String CONTEXT_PATH_KEY = "path";

	public static final String CONTEXT_SECURE_KEY = "secure";

	private static boolean parameterEnable = true;// 是否允许参数设定

	/**
	 * 
	 * @param locale可用的本地信息
	 * @param scope
	 *            受支持scope是: request,session,
	 * @param request
	 * @param response
	 * @param servletContext
	 * @param pageContext
	 * @param context
	 *            参数,比如存放cookie的时间,域名的..
	 */
	@SuppressWarnings("unchecked")
	public static void setLocale(Locale locale, String scope, HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, PageContext pageContext, Map context)
	{
		if (REQUEST.equalsIgnoreCase(scope))
		{
			request.setAttribute(REQUEST_LOCALE_KEY, locale);
			logger.debug("setLocale[" + locale + "]to request");

		} else if (SESSION.equalsIgnoreCase(scope))
		{
			request.getSession().setAttribute(SESSION_LOCALE_KEY, locale);
			logger.debug("setLocale[" + locale + "]to session");

		} else if (APPLICATION.equalsIgnoreCase(scope))
		{
			servletContext.setAttribute(APPLICATION_LOCALE_KEY, locale);
			logger.debug("setLocale[" + locale + "]to application");
		} else if (COOKIE.equalsIgnoreCase(scope))
		{
			Cookie cookie = new Cookie(COOKIE_LOCALE_KEY, locale.toString());
			if (context != null)
			{
				Integer maxAge = (Integer) context.get(CONTEXT_MAXAGE_KEY);
				if (maxAge != null)
				{
					cookie.setMaxAge(maxAge);
				} else
				{
					cookie.setMaxAge(Integer.MAX_VALUE);
				}
				String dommain = (String) context.get(CONTEXT_DOMAIN_KEY);
				if (dommain != null)
				{
					cookie.setDomain(dommain);
				}
				String path = (String) context.get(CONTEXT_PATH_KEY);
				if (path != null)
				{
					cookie.setPath(path);
				}
				Boolean secure = (Boolean) context.get(CONTEXT_SECURE_KEY);
				if (secure != null)
				{
					cookie.setSecure(secure);
				}

			} else
			{
				cookie.setMaxAge(Integer.MAX_VALUE);
			}

			response.addCookie(cookie);

		} else if (PAGE.equalsIgnoreCase(scope) && pageContext != null)
		{
			pageContext.setAttribute(PAGE_LOCALE_KEY, locale);
			logger.debug("setLocale[" + locale + "]to page");
		} else
		{
			// shall we throw a Exception ?
			logger.warn("Unsupported Scope[" + scope + "]");
		}
	}

	/**
	 * 
	 * @param request
	 * @param servletContext
	 * @param pageContext
	 * @param defaultLocale
	 * @return
	 */
	public static Locale getLocale(HttpServletRequest request, ServletContext servletContext, PageContext pageContext,
			Locale defaultLocale)
	{
		Locale locale = null;
		// PageContext
		if (pageContext != null)
		{
			locale = (Locale) pageContext.getAttribute(PAGE_LOCALE_KEY);
			if (locale != null)
			{
				return locale;
			}
		}

		// Parameter
		if (parameterEnable)
		{
			String locStr = request.getParameter(PARAMETER_LOCALE_KEY);
			if (Validator.notBlank(locStr))
			{
				locale = LocaleUtils.localeFromString(locStr, null);
				if (locale != null)
				{
					return locale;
				}
			}
		}

		// Request Attribute
		locale = (Locale) request.getAttribute(REQUEST_LOCALE_KEY);
		if (locale != null)
		{
			return locale;
		}

		// Session
		HttpSession session = request.getSession(false);
		if (session != null)
		{
			locale = (Locale) session.getAttribute(SESSION_LOCALE_KEY);
			if (locale != null)
			{
				return locale;
			}
		}
		// Cookie

		// request.getCookies();

		// Request.getLocales
		locale = request.getLocale();
		if (locale != null)
		{
			return locale;
		}
		// defaultLocale
		if (defaultLocale != null)
		{
			return defaultLocale;
		}
		// application
		locale = (Locale) servletContext.getAttribute(APPLICATION_LOCALE_KEY);
		if (locale != null)
		{
			return locale;
		}
		// Config

		// System
		locale = Locale.getDefault();// 最后只能使用本地的
		return locale;
	}

	public static boolean isParameterEnable()
	{
		return parameterEnable;
	}

	public static void setParameterEnable(boolean parameterEnable)
	{
		HttpLocaleProviderSupport.parameterEnable = parameterEnable;
	}
}
