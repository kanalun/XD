package com.xindian.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xindian.mvc.result.Resultable;

/**
 * Action 的上下文,
 * 
 * 用户可以通过这个类的方法接触到原始的Session,Request,Response等
 * 
 * @author Elva
 * 
 */
public final class ActionContext
{
	private List<ActionContextDestoryListener> listeners;// 只对该ActionContext有效

	/** application */
	ServletContext context;

	/** session */
	HttpSession session;

	HttpServletRequest request;

	HttpServletResponse response;

	Map<String, Cookie> cookies;

	/** 映射信息 */
	Mapping mapping;

	// TODO NO_USE
	Map<String, Object> returned;

	/** 这个encoding来自全局配置,最后可能会设置到Response中去,处理过程中,用户可以修改 */
	private String encoding = "UTF-8";

	private Object action;

	private Resultable result;

	ActionContext()
	{

	}

	private void fireBeforeThisActionContextDestory(ActionContext actionContext)
	{
		if (listeners != null)
		{
			for (ActionContextDestoryListener actionContextDestoryListener : listeners)
			{
				actionContextDestoryListener.onActionContextDestoryed(actionContext);
			}
		}
	}

	/**
	 * 为ActionContext设置一个监听器<br/>
	 * 在ActionContext"被回收"的时候会触发这个监听器的动作<br/>
	 * 注意,这个监听器只对该ActionContext有效
	 * 
	 * @param actionContextListener
	 * @return
	 */
	public boolean addDestoryListener(ActionContextDestoryListener actionContextListener)
	{
		if (listeners == null)
		{
			listeners = new ArrayList<ActionContextDestoryListener>(1);
		}
		synchronized (listeners)
		{
			listeners.add(actionContextListener);
		}
		return true;
	}

	/**
	 * 删除ActionContext设置一个监听器<br/>
	 * 
	 * @param actionContextListener
	 * @return
	 */
	public boolean removeDestoryListener(ActionContextListener actionContextListener)
	{
		if (listeners != null)
		{
			synchronized (listeners)
			{
				listeners.remove(actionContextListener);
			}
		}
		return true;
	}

	/**
	 * 删除ActionContext中所有监听器<br/>
	 * 
	 * @return
	 */
	public boolean clearActionContextListener()
	{
		if (listeners != null)
		{
			synchronized (listeners)
			{
				listeners.clear();
			}
		}
		return true;
	}

	/**
	 * 
	 */
	protected void onDestory()
	{
		fireBeforeThisActionContextDestory(this);
	}

	public static ActionContext getContext()
	{
		return ActionContextManager.getSingleton().getActionContext();
	}

	public static ServletContext getServletContext()
	{
		return getContext().getServleteContext0();
	}

	private ServletContext getServleteContext0()
	{
		return context;
	}

	/**
	 * 得到一个Session,如果Session不存在,创建一个,语意相当于:request.getSession(true)
	 * 
	 * @return
	 */
	public static HttpSession getSession()
	{
		return getContext()._getSession(true);
	}

	/**
	 * 
	 * @param create
	 *            是否创建
	 * @return
	 */
	public static HttpSession getSession(boolean create)
	{
		return getContext()._getSession(create);
	}

	private HttpSession _getSession(boolean create)
	{
		return (session == null && create) ? (session = request.getSession()) : session;
	}

	public static HttpServletRequest getRequest()
	{
		return getContext().getRequest0();
	}

	private HttpServletRequest getRequest0()
	{
		return request;
	}

	public static HttpServletResponse getResponse()
	{
		return getContext()._getResponse();
	}

	private HttpServletResponse _getResponse()
	{
		return response;
	}

	public static Locale getLocale()
	{
		return getContext()._getLocale();
	}

	private Locale _getLocale()
	{
		Locale locale = request.getLocale();
		return locale;
	}

	public Object getAction()
	{
		return action;
	}

	void setAction(Object action)
	{
		this.action = action;
	}

	public String getEncoding()
	{
		return encoding;
	}

	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}

	public void setResult(Resultable result)
	{
		this.result = result;
	}

	public Resultable getResult()
	{
		return result;
	}

	public Object getSessionAttr(String attrName)
	{
		HttpSession ssn = getSession();
		return (ssn != null) ? ssn.getAttribute(attrName) : null;
	}

	public Cookie getCookie(String name)
	{
		return cookies.get(name);
	}

	public void setCookie(String name, String value, int expiry)
	{
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}

	public void setCookie(Cookie cookie)
	{
		response.addCookie(cookie);
	}

	// FIXME
	public void deleteCookie(String name)
	{
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	public String getHeader(String name)
	{
		return request.getHeader(name);
	}

	public void setHeader(String name, String value)
	{
		response.setHeader(name, value);
	}

	public void setIntHeader(String name, int value)
	{
		response.setIntHeader(name, value);
	}

	public void setDateHeader(String name, long value)
	{
		response.setDateHeader(name, value);
	}

	/**
	 * 关闭浏览器缓存
	 */
	public void closeBrowserCache()
	{
		// HTTP/1.1 + IE extensions
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, " + "post-check=0, pre-check=0");
		// HTTP/1.0
		response.setHeader("Pragma", "no-cache");
		// Last resort for those that ignore all of the above
		response.setDateHeader("Expires", 0L);
	}

	/**
	 * 关闭浏览器缓存
	 */
	private void closeBrowserCache0()
	{
		setHeader("Pragma", "No-cache");
		setHeader("Cache-Control", "no-cache");
		setDateHeader("Expires", 0L);
	}
}
