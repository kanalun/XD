package com.xindian.mvc.result.velocity;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.context.AbstractContext;

/**
 * HttpRequest的Velocity"视图",
 * 
 * @author Elva
 * @date 2011-1-18
 * @version 1.0
 */
public class HttpRequestVelocityContext extends AbstractContext implements Cloneable
{
	private transient HttpServletRequest request;

	public HttpRequestVelocityContext(HttpServletRequest request)
	{
		this.request = request;
	}

	@Override
	public boolean internalContainsKey(Object key)
	{
		return request.getAttribute((String) key) != null;
	}

	@Override
	public Object internalGet(String key)
	{
		return request.getAttribute((String) key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] internalGetKeys()
	{
		ArrayList<Object> a = new ArrayList<Object>();
		Enumeration ea = request.getAttributeNames();
		while (ea.hasMoreElements())
		{
			a.add(ea.nextElement());
		}
		return a.toArray();
	}

	@Override
	public Object internalPut(String key, Object value)
	{
		request.setAttribute(key, value);
		return value;
	}

	@Override
	public Object internalRemove(Object key)
	{
		Object o = request.getAttribute((String) key);
		request.removeAttribute((String) key);
		return o;
	}
}
