package com.xindian.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xindian.mvc.utils.COSMultipartRequest;
import com.xindian.mvc.utils.RequestUtils;

/**
 * ActionContext 管理器,负责创建,回收ActionContext;
 * 
 * 添加全局监听等功能@ 负责扩展改变ActionContext,比如添加文件上传功能 程序性单例
 * 
 * @author Elva
 */
public class ActionContextManager
{
	private static ActionContextManager singleton;

	private Map<String, String> config;// TODO 配置信息

	private ActionContextManager()
	{

	}

	public static ActionContextManager getSingleton()
	{
		if (singleton == null)
		{
			singleton = new ActionContextManager();
		}
		return singleton;
	}

	private final ThreadLocal<ActionContext> contexts = new ThreadLocal<ActionContext>();

	// 监听所有的ActionContex
	private final List<ActionContextListener> listeners = new ArrayList<ActionContextListener>(0);

	private void fireAfterActionContextCreated(ActionContext actionContext)
	{
		for (ActionContextListener actionContextListener : listeners)
		{
			actionContextListener.onActionContextCreated(actionContext);
		}
	}

	private void fireBeforeActionContextDestoryed(ActionContext actionContext)
	{
		for (ActionContextListener actionContextListener : listeners)
		{
			actionContextListener.onActionContextDestoryed(actionContext);
		}
	}

	public boolean addGlobalActionContextListener(ActionContextListener actionContextListener)
	{
		synchronized (listeners)
		{
			listeners.add(actionContextListener);
		}
		return true;
	}

	public boolean removeGlobalActionContextListener(ActionContextListener actionContextListener)
	{
		synchronized (listeners)
		{
			listeners.remove(actionContextListener);
		}
		return true;
	}

	// TODO
	// 注册 ActionContextListener类型
	private boolean registerGlobalActionContextListener(
			Class<? extends ActionContextListener> actionContextListenerType)
	{
		return false;
	}

	// TODO
	private boolean unregisterGlobalActionContextListener(
			Class<? extends ActionContextListener> actionContextListenerType)
	{
		return false;
	}

	/**
	 * Remove All Global ActionContextListeners
	 */
	public void clearGlobalActionContextListeners()
	{
		synchronized (listeners)
		{
			listeners.clear();
		}
	}

	private String tempDir;// fileupload tempdir

	private String encoding = "UTF-8";// default encoding

	private int fileUploadMaxSize = 1024 * 1024;// TODO

	public ActionContext createActionContext(ServletContext servletContext, ServletRequest request,
			ServletResponse response) throws IOException
	{
		ActionContext actionContext;
		if (RequestUtils.isMultipart((HttpServletRequest) request))
		{
			COSMultipartRequest multipartRequest = new COSMultipartRequest(
					(HttpServletRequest) request, tempDir, fileUploadMaxSize, encoding);
			actionContext = _createActionContext(servletContext, multipartRequest,
					(HttpServletResponse) response);
		} else
		{
			actionContext = _createActionContext(servletContext, (HttpServletRequest) request,
					(HttpServletResponse) response);
		}
		fireAfterActionContextCreated(actionContext);
		return actionContext;
	}

	/**
	 * 
	 * @param servletContext
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionContext _createActionContext(ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
	{
		ActionContext actionContext = new ActionContext();
		actionContext.context = servletContext;
		actionContext.request = request;
		actionContext.response = response;
		actionContext.session = request.getSession(false);
		actionContext.cookies = new HashMap<String, Cookie>();
		actionContext.setEncoding(encoding);
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (Cookie ck : cookies)
			{
				actionContext.cookies.put(ck.getName(), ck);
			}
		}
		contexts.set(actionContext);
		return actionContext;
	}

	/**
	 * 销毁回收ActionContext,触发响应事件
	 * 
	 * @param actionContext
	 */
	public void destoryActionContext(ActionContext actionContext)
	{
		actionContext.onDestory();
		fireBeforeActionContextDestoryed(actionContext);
		actionContext.context = null;
		actionContext.request = null;
		actionContext.response = null;
		actionContext.session = null;
		actionContext.cookies = null;
		contexts.remove();
	}

	/**
	 * 返回本线程的 ActionContext,用户一般使用
	 * 
	 * @return
	 */
	protected ActionContext getActionContext()
	{
		return contexts.get();
	}

	/**
	 * 得到系统默认的上传临时文件夹
	 * 
	 * @return
	 */
	public String getTempDir()
	{
		return tempDir;
	}

	/**
	 * 设置系统默认的上传临时文件夹
	 * 
	 * @param tempDir
	 */
	public void setTempDir(String tempDir)
	{
		this.tempDir = tempDir;
	}

	/**
	 * 得到默认的编码
	 * 
	 * @return
	 */
	public String getEncoding()
	{
		return encoding;
	}

	/**
	 * 设置默认的编码
	 * 
	 * @param encoding
	 */
	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}
}
