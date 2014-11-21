package com.xindian.mvc.result.velocity;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletContext;

import org.apache.velocity.context.AbstractContext;

/**
 * 一般而言这些仅仅包装器:或者称为"被包装对象的视图",<br/>
 * 
 * 虽然提供了可写的方法但是他们应该是只读的!
 * 
 * @author Elva
 * @date 2011-1-18
 * @version 1.0
 */
public class ServletContextVelocityContext extends AbstractContext implements Cloneable
{
	private/* transient */ServletContext servletContext;

	public ServletContextVelocityContext(ServletContext servletContext)
	{
		this.servletContext = servletContext;
	}

	@Override
	public boolean internalContainsKey(Object key)
	{
		return servletContext.getAttribute((String) key) != null;
	}

	@Override
	public Object internalGet(String key)
	{
		return servletContext.getAttribute((String) key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] internalGetKeys()
	{
		ArrayList<Object> a = new ArrayList<Object>();
		Enumeration ea = servletContext.getAttributeNames();
		while (ea.hasMoreElements())
		{
			a.add(ea.nextElement());
		}
		return a.toArray();
	}

	@Override
	public Object internalPut(String key, Object value)
	{
		servletContext.setAttribute(key, value);
		return value;
	}

	@Override
	public Object internalRemove(Object key)
	{
		Object o = servletContext.getAttribute((String) key);
		servletContext.removeAttribute((String) key);
		return o;
	}

}
