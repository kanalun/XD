package com.xindian.mvc.result.freemarker;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleCollection;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
public final class HttpRequestHashModel implements TemplateHashModelEx
{

	private transient final HttpServletRequest request;
	// private transient final HttpServletResponse response;
	private final ObjectWrapper wrapper;

	public HttpRequestHashModel(HttpServletRequest request, ObjectWrapper wrapper)
	{
		this.request = request;
		// this(request, null, wrapper);
		this.wrapper = wrapper;
	}

	// public HttpRequestHashModel(HttpServletRequest request,
	// HttpServletResponse response, ObjectWrapper wrapper)
	// {
	// this.request = request;
	// this.response = response;
	// this.wrapper = wrapper;
	// }

	public TemplateModel get(String key) throws TemplateModelException
	{
		return wrapper.wrap(request.getAttribute(key));
	}

	public boolean isEmpty()
	{
		return !request.getAttributeNames().hasMoreElements();
	}

	@SuppressWarnings("unchecked")
	public int size()
	{
		int result = 0;
		for (Enumeration enumeration = request.getAttributeNames(); enumeration.hasMoreElements();)
		{
			enumeration.nextElement();
			++result;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public TemplateCollectionModel keys()
	{
		ArrayList keys = new ArrayList();
		for (Enumeration enumeration = request.getAttributeNames(); enumeration.hasMoreElements();)
		{
			keys.add(enumeration.nextElement());
		}
		return new SimpleCollection(keys.iterator());
	}

	@SuppressWarnings("unchecked")
	public TemplateCollectionModel values()
	{
		ArrayList values = new ArrayList();
		for (Enumeration enumeration = request.getAttributeNames(); enumeration.hasMoreElements();)
		{
			values.add(request.getAttribute((String) enumeration.nextElement()));
		}
		return new SimpleCollection(values.iterator(), wrapper);
	}

	public HttpServletRequest getRequest()
	{
		return request;
	}

	// public HttpServletResponse getResponse()
	// {
	// return response;
	// }

	public ObjectWrapper getObjectWrapper()
	{
		return wrapper;
	}
}
