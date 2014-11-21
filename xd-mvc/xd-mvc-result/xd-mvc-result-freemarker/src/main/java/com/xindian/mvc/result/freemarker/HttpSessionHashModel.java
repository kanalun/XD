package com.xindian.mvc.result.freemarker;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * @date 2011-1-16
 * @version 1.0
 */
public final class HttpSessionHashModel implements TemplateHashModel, Serializable
{
	private static final long serialVersionUID = 1L;

	private transient HttpSession session;

	private transient final ObjectWrapper wrapper;

	private transient final HttpServletRequest request;

	/**
	 * Use this constructor when the session already exists.
	 * 
	 * @param session
	 *            the session
	 * @param wrapper
	 *            an object wrapper used to wrap session attributes
	 */
	public HttpSessionHashModel(HttpSession session, ObjectWrapper wrapper)
	{
		this.session = session;
		this.wrapper = wrapper;
		this.request = null;
	}

	/**
	 * Use this constructor when the session isn't already created. It is passed
	 * enough parameters so that the session can be properly initialized after
	 * it is detected that it was created.
	 * 
	 * @param request
	 *            the actual request
	 * @param wrapper
	 *            an object wrapper used to wrap session attributes
	 */
	public HttpSessionHashModel(HttpServletRequest request, ObjectWrapper wrapper)
	{
		this.wrapper = wrapper;
		this.request = request;
	}

	public TemplateModel get(String key) throws TemplateModelException
	{
		checkSessionExistence();
		return wrapper.wrap(session != null ? session.getAttribute(key) : null);
	}

	private void checkSessionExistence() throws TemplateModelException
	{
		if (session == null && request != null)
		{
			session = request.getSession(false);
			/**
			 * <code>
			if (session != null && servlet != null)
			{
				try
				{
					servlet.initializeSessionAndInstallModel(request, response, this, session);
				} catch (RuntimeException e)
				{
					throw e;
				} catch (Exception e)
				{
					throw new TemplateModelException(e);
				}
			}</code>
			 */
		}
	}

	boolean isOrphaned(HttpSession currentSession)
	{
		return (session != null && session != currentSession) || (session == null && request == null);
	}

	public boolean isEmpty() throws TemplateModelException
	{
		checkSessionExistence();
		return session == null || !session.getAttributeNames().hasMoreElements();
	}
}
