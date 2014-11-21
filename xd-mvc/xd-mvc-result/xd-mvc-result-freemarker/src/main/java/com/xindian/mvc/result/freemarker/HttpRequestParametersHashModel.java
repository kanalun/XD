package com.xindian.mvc.result.freemarker;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import freemarker.template.SimpleCollection;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;

/**
 * @date 2011-1-16
 * @version 1.0
 */
public class HttpRequestParametersHashModel implements TemplateHashModelEx
{
	private transient final HttpServletRequest request;

	@SuppressWarnings("unchecked")
	private List keys;

	public HttpRequestParametersHashModel(HttpServletRequest request)
	{
		this.request = request;
	}

	public TemplateModel get(String key)
	{
		String value = request.getParameter(key);
		return value == null ? null : new SimpleScalar(value);
	}

	public boolean isEmpty()
	{
		return !request.getParameterNames().hasMoreElements();
	}

	public int size()
	{
		return getKeys().size();
	}

	public TemplateCollectionModel keys()
	{
		return new SimpleCollection(getKeys().iterator());
	}

	@SuppressWarnings("unchecked")
	public TemplateCollectionModel values()
	{
		final Iterator iter = getKeys().iterator();
		return new SimpleCollection(new Iterator()
		{
			public boolean hasNext()
			{
				return iter.hasNext();
			}

			public Object next()
			{
				return request.getParameter((String) iter.next());
			}

			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		});
	}

	@SuppressWarnings("unchecked")
	private synchronized List getKeys()
	{
		if (keys == null)
		{
			keys = new ArrayList();
			for (Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();)
			{
				keys.add(enumeration.nextElement());
			}
		}
		return keys;
	}
}
