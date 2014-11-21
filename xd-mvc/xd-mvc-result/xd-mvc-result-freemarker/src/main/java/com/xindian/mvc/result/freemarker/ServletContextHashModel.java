package com.xindian.mvc.result.freemarker;

import javax.servlet.ServletContext;

import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public final class ServletContextHashModel implements TemplateHashModel
{
	private transient final ServletContext servletctx;
	private final ObjectWrapper wrapper;

	public ServletContextHashModel(ServletContext servletctx, ObjectWrapper wrapper)
	{
		this.servletctx = servletctx;
		this.wrapper = wrapper;
	}

	public TemplateModel get(String key) throws TemplateModelException
	{
		return wrapper.wrap(servletctx.getAttribute(key));
	}

	public boolean isEmpty()
	{
		return !servletctx.getAttributeNames().hasMoreElements();
	}
}
