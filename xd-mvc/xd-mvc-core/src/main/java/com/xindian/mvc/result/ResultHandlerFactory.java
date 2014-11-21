package com.xindian.mvc.result;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.exception.MVCException;

/**
 * 该类用来,管理器ResultHandler,控制器会从这里取出合适的"结果处理器"
 * 
 * @author Elva
 * 
 */
public class ResultHandlerFactory
{
	private static Logger logger = LoggerFactory.getLogger(ResultHandlerFactory.class);

	public static ResultHandler getResultHandler(String s)
	{
		return new ServletForwardResultHandler();
	}

	/** 支持自动注册处理结果 */
	private/* volatile */Boolean autoRegister = true;

	private ResultHandlerFactory()
	{
		logger.debug("A ResultHandlerFactory init_ed");
	}

	private Map<Class<? extends ResultHandler>, ResultHandler> resultHandlers = new HashMap<Class<? extends ResultHandler>, ResultHandler>();

	private Map<Class<?>, Class<? extends ResultHandler>> typeHandlers = new HashMap<Class<?>, Class<? extends ResultHandler>>();

	private static ResultHandlerFactory resultHandlerFactory = new ResultHandlerFactory();

	public static ResultHandlerFactory getSingleton()
	{
		return resultHandlerFactory;
	}

	public ResultHandler getResultHandler(Class<? extends ResultHandler> resultHandlerType) throws MVCException
	{
		ResultHandler resultHandler = resultHandlers.get(resultHandlerType);
		if (resultHandler == null && isAutoRegister())// 自动注册
		{
			logger.debug("Auto Register ResultHandler...");
			if (registerResultHandler(resultHandlerType))// 注册
			{
				resultHandler = resultHandlers.get(resultHandlerType);// 再次获取
			}
		}
		return resultHandler;
	}

	public boolean registerResultHandler(Class<? extends ResultHandler> resultHandlerType) throws MVCException
	{
		try
		{
			synchronized (resultHandlers)
			{
				resultHandlers.put(resultHandlerType, resultHandlerType.newInstance());
			}
			logger.debug("Registered A ResultHandler Type:" + resultHandlerType);
			return true;
		} catch (InstantiationException e)
		{
			logger.warn(e.getMessage());
			throw new MVCException(resultHandlerType + " Can not be Instantiated", e);
		} catch (IllegalAccessException e)
		{
			logger.warn(e.getMessage());
			throw new MVCException(resultHandlerType + " Can not be Instantiated", e);
		}
	}

	public boolean registerTypeHandler(Class<?> type, Class<? extends ResultHandler> resultHandler) throws MVCException
	{
		synchronized (typeHandlers)
		{
			typeHandlers.put(type, resultHandler);
		}
		logger.debug("Registered A ResultHandler Type:" + resultHandler);
		return true;

	}

	public boolean handleType(Class<?> type) throws MVCException
	{
		return true;
	}

	public boolean clearResultHandler()
	{
		synchronized (resultHandlers)
		{
			resultHandlers.clear();
		}
		logger.debug("All ResultHandlers Cleard!");
		return true;
	}

	public boolean unRgisterResultHandler(Class<? extends ResultHandler> resultHandlerType)
	{
		synchronized (resultHandlers)
		{
			resultHandlers.remove(resultHandlerType);
		}
		logger.debug("Unregister A ResultHandler Type:" + resultHandlerType);
		return true;

	}

	public void setAutoRegister(boolean autoRegister)
	{
		logger.debug("Set AutoRegiste :" + autoRegister);
		synchronized (this.autoRegister)
		{
			this.autoRegister = autoRegister;
		}
	}

	public boolean isAutoRegister()
	{
		return autoRegister;
	}
}
