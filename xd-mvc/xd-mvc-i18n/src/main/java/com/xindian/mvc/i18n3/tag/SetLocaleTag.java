package com.xindian.mvc.i18n3.tag;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.utils.LocaleUtils;
import com.xindian.mvc.i18n2.HttpLocaleProviderSupport;

/**
 * SetLocale TODO 加入scope page
 * 
 * @author Elva
 * @date 2011-2-6
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SetLocaleTag extends TagSupport
{
	private static Logger logger = LoggerFactory.getLogger(SetLocaleTag.class);

	private Object value;

	private String variant;

	private String scope;

	@Override
	public int doStartTag() throws JspException
	{
		logger.debug("--------------------SetLocaleTag" + value);
		Locale locale = LocaleUtils.localeFromString((String) value, null);
		HttpLocaleProviderSupport.setLocale(locale, scope, (HttpServletRequest) pageContext.getRequest(),
				(HttpServletResponse) pageContext.getResponse(), pageContext.getServletContext(), pageContext, null);
		return super.doStartTag();
	}

	public void setValue(Object value) throws JspTagException
	{
		this.value = value;
	}

	public void setVariant(String variant) throws JspTagException
	{
		this.variant = variant;
	}

	public void setScope(String scope)
	{
		this.scope = scope;
	}
}
