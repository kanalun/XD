package com.xindian.mvc.result.velocity;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.context.AbstractContext;

/**
 * 
 * @author Elva
 * @date 2011-1-19
 * @version 1.0
 */
public class HttpRequestParametersVelocityContext extends AbstractContext implements Cloneable
{
	HttpServletRequest request;

	public HttpRequestParametersVelocityContext(HttpServletRequest request)
	{
		this.request = request;
	}

	@Override
	public boolean internalContainsKey(Object key)
	{
		return request.getParameter(key.toString()) != null;
	}

	@Override
	public Object internalGet(String key)
	{
		return request.getParameter(key.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] internalGetKeys()
	{
		ArrayList<Object> a = new ArrayList<Object>();
		Enumeration ea = request.getParameterNames();
		while (ea.hasMoreElements())
		{
			a.add(ea.nextElement());
		}
		return a.toArray();
	}

	@Override
	public Object internalPut(String key, Object value)
	{
		return null;
	}

	@Override
	public Object internalRemove(Object key)
	{
		return null;
	}
}
