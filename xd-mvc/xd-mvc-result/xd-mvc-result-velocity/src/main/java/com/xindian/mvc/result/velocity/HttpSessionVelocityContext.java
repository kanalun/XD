package com.xindian.mvc.result.velocity;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.velocity.context.AbstractContext;

/**
 * @author Elva
 * @date 2011-1-18
 * @version 1.0
 */
public class HttpSessionVelocityContext extends AbstractContext implements Cloneable
{
	private transient HttpSession session;

	// FOR LAZY INIT
	private transient HttpServletRequest request;

	public HttpSessionVelocityContext(HttpSession session, HttpServletRequest request)
	{
		this.request = request;
		this.session = session;
	}

	@Override
	public boolean internalContainsKey(Object key)
	{
		checkSession();
		if (session == null)
		{
			return false;
		}
		return session.getAttribute((String) key) != null;
	}

	private HttpSession checkSession()
	{
		if (this.session == null)
		{
			this.session = request.getSession();
		}
		return this.session;
	}

	@Override
	public Object internalGet(String key)
	{
		checkSession();
		if (session == null)
		{
			return null;
		}
		return session.getAttribute((String) key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] internalGetKeys()
	{
		checkSession();
		if (session == null)
		{
			return null;
		}
		ArrayList<Object> a = new ArrayList<Object>();
		Enumeration ea = session.getAttributeNames();
		while (ea.hasMoreElements())
		{
			a.add(ea.nextElement());
		}
		return a.toArray();
	}

	@Override
	public Object internalPut(String key, Object value)
	{
		// checkSession();
		// if (session == null)
		// {
		session = request.getSession(true);
		// }
		session.setAttribute(key, value);
		return value;
	}

	@Override
	public Object internalRemove(Object key)
	{
		checkSession();
		if (session == null)
		{
			return null;
		}
		Object o = session.getAttribute((String) key);
		session.removeAttribute((String) key);
		return o;
	}

}
