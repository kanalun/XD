package com.xindian.mvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.beanutils.Bean5;
import com.xindian.ioc.winte.IoCActionFactory;
import com.xindian.mvc.exception.ErrorCodeException;
import com.xindian.mvc.exception.ForbiddenException;
import com.xindian.mvc.exception.MVCException;
import com.xindian.mvc.exception.PowerlessException;
import com.xindian.mvc.result.ErrorResultHandler;
import com.xindian.mvc.result.ProtocolParserFactory;
import com.xindian.mvc.result.ResultHandlerFactory;
import com.xindian.mvc.result.Resultable;
import com.xindian.mvc.result.ServletForwardResultHandler;
import com.xindian.mvc.result.StreamResultHandler;
import com.xindian.mvc.utils.ConfigUtils;

/**
 * 控制器:系统的核心<br/>
 * 
 * 程序解析URL->调用Action->处理返回结果的所在<br>
 * 
 * TODO 需要重构,特别是Action搜索的方式和数据结构有很大的问题!
 * 
 * 1,"树"模型,对URL进行匹配搜索?
 * 
 * 2,对搜索结果缓存在一个map中!
 * 
 * TODO 为映射信息增加个Cache什么的?
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
public class MVC
{
	private static Logger logger = LoggerFactory.getLogger(MVC.class);

	private static MVC mvc = new MVC();

	private MVC()
	{
		// DO NOTHING
	}

	public static MVC getSingleton()
	{
		return mvc;
	}

	// TODO 通过配置文件获得这个ActionFactory
	private ActionFactory actionFactory = new IoCActionFactory();

	//
	private final Map<String, ActionMeta<?>> actionMetas = new ConcurrentHashMap<String, ActionMeta<?>>();

	// PathTranslators
	private List<PathTranslator> pathTranslators = new ArrayList<PathTranslator>();

	// ProtocolParserFactory:
	private ProtocolParserFactory protocolParser = ProtocolParserFactory.getSingleton();

	// ResultHandlerFactory
	private ResultHandlerFactory resultHandlerFactory = ResultHandlerFactory.getSingleton();

	public boolean addPathTranslator(PathTranslator pathTranslator)
	{
		synchronized (pathTranslators)
		{
			return pathTranslators.add(pathTranslator);
		}
	}

	/**
	 * 翻译路径
	 * 
	 * @param path
	 * @return
	 * @throws ErrorCodeException
	 */
	private Mapping translatePath(String path) throws ErrorCodeException
	{
		Mapping mapping = null;
		for (PathTranslator pathTranslator : pathTranslators)
		{
			try
			{
				mapping = pathTranslator.translate(path);
				if (mapping != null)
				{
					return mapping;
				}
			} catch (PowerlessException e)
			{
				e.printStackTrace();
				continue;
			} catch (ErrorCodeException e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return mapping;
	}

	// 映射过滤
	private List<MappingFilter> mappingFilters = new ArrayList<MappingFilter>();

	public boolean addMappingFilter(MappingFilter mappingFilter)
	{
		synchronized (mappingFilters)
		{
			return mappingFilters.add(mappingFilter);
		}
	}

	/**
	 * 对mapping进行过滤,返回true表示通过过滤
	 * 
	 * @param mapping
	 * @return
	 * @throws ErrorCodeException
	 */
	private boolean filterMapping(Mapping mapping) throws ErrorCodeException
	{
		boolean flteFlag = true;// 默认通过过滤
		for (MappingFilter mappingFilter : mappingFilters)
		{
			flteFlag = mappingFilter.filter(mapping);
			if (!flteFlag)
			{
				return false;
			}
		}
		return flteFlag;
	}

	// Uuhappy to make it public
	// 去他妈的,就这样发布吧
	@SuppressWarnings("static-access")
	public boolean doAction(ActionContext actionContext) throws IOException, ServletException
	{
		try
		{
			String requestURI = actionContext.getRequest().getRequestURI();

			Mapping mapping = translatePath(requestURI);

			if (mapping == null)// 无法得到映射
			{
				return false;
			}
			if (!filterMapping(mapping))// 过滤映射;不通过,当然,这里应该处理异常
			{
				return false;
			}
			if (mapping == null)// 过滤之后映射任然可能为空:过滤器中可能将他设置为空
			{
				return false;
			}
			if (mapping.actionName == null)
			{
				logger.debug("No Action to deal with this Request!!");
				return false;
			}
			// TODO 处理包
			ActionMeta<?> actionMeta = actionMetas.get(mapping.getNamespace() + "_" + mapping.actionName);
			if (actionMeta == null)
			{
				logger.debug("No Action to deal with this Request!! MAPING:" + mapping.getNamespace() + "_" + mapping.actionName);
				return false;
			}
			if (actionMeta.isForbidden())
			{
				logger.debug(" ACTION: [" + actionMeta.getActionName() + "]Is Forbidden!");
				try
				{
					Constructor<? extends ErrorCodeException> exceptionConstructor = actionMeta.getForbiddenMeta().exception
							.getConstructor(Integer.class, String.class);
					throw exceptionConstructor.newInstance(actionMeta.getForbiddenMeta().code,
							actionMeta.getForbiddenMeta().message);
				} catch (NoSuchMethodException e)
				{
					e.printStackTrace();
					throw new ForbiddenException();
				}
			}
			if (mapping.methodName == null || "".equals(mapping.methodName.trim()))
			{
				logger.debug("Method name is null use default method: [" + actionMeta.getDefaultMethodName() + "]!");
				mapping.setMethodName(actionMeta.getDefaultMethodName());
			}
			MethodMeta methodMeta = getActionMethodMeta(actionMeta, mapping.methodName);
			if (methodMeta == null)
			{
				logger.debug("No Method to deal With this Request!!");
				return false;
			}
			if (methodMeta.isForbidden())
			{
				logger.debug(" CAN NOT RUN Method: [" + actionMeta.getActionName() + "." + methodMeta.getMethodName()
						+ "()] Because The Method Is Forbidden");
				try
				{
					Constructor<? extends ErrorCodeException> exceptionConstructor = methodMeta.getForbiddenMeta().exception
							.getConstructor(Integer.class, String.class);
					throw exceptionConstructor.newInstance(methodMeta.getForbiddenMeta().code,
							methodMeta.getForbiddenMeta().message);
				} catch (NoSuchMethodException e)
				{
					e.printStackTrace();
					throw new ForbiddenException();
				}
			}
			if (!methodMeta.canDoMethodType(actionContext.getRequest().getMethod()))
			{
				logger.debug(" can not run Method: [" + actionMeta.getActionName() + "." + methodMeta.getMethodName()
						+ "()] cause The MethodType Of This Request Is '" + actionContext.getRequest().getMethod()
						+ "' and this Method can only deal with 'POST'");// FIXME
				return false;
			}

			logger.debug("DO With:Action[ " + actionMeta.getActionName() + "] Method[" + methodMeta.getMethodName() + "] Type[ "
					+ actionContext.getRequest().getMethod() + "]");

			// -----------Action方法的调用,重构:使用代理类进行包装-----------
			// 1
			actionMeta.invokeBefores();

			// 2
			methodMeta.invokeBefores();

			// 3
			Object action = getAction(actionContext, actionMeta);

			// Object result = methodMeta.invoke(action);

			// TODO 这里有一些问题
			Object result = methodMeta.invoke(action, actionContext.getRequest().getParameterMap());

			// 4
			methodMeta.invokeAfters();

			// 5
			actionMeta.invokeAfters();

			// -----------处理返回结果 FIXME-----------
			Resultable resultable;
			ResultMeta resultMeta;

			if (result == null && ActionContext.getContext().getResult() != null)
			{
				result = ActionContext.getContext().getResult();
			}

			if (result == null)// 返回null
			{
				// TODO 检查是否有默认返回类型,如果有,返回,否则,什么也不做
				logger.debug("ACTION RETURN:Null ");
				logger.debug("[[[Action Returned A Null Type!!How shoud I deal with it, that is a problem]]]");

			} else if (result instanceof String && (resultable = protocolParser.parseResultable((String) result)) != null)// Protocol
			{
				logger.debug("ACTION RETURN:Protocol " + result);
				resultHandlerFactory.getResultHandler(resultable.getHandler()).doResult(actionContext, resultable);

			} else if ((result instanceof String) && (resultMeta = methodMeta.getResultMeta((String) result)) != null)// ResultMeta
			{
				logger.debug("ACTION RETURN:KEY IN ResultMeta " + result);
				try
				{
					resultHandlerFactory.getResultHandler(resultMeta.type).doResult(actionContext, resultMeta.value);// 尝试使用返回配置中的
				} catch (MVCException e)// 无法实例化
				{
					resultable = protocolParser.parseResultable(resultMeta.protocol, resultMeta.value);
					if (resultable != null)
					{
						resultHandlerFactory.getResultHandler(resultable.getHandler()).doResult(actionContext, resultable);
					} else
					{
						throw new MVCException("[" + methodMeta.getMethod() + "]方法Results或者Result配置错误,无法对返回类型为@Result["
								+ resultMeta.name + "]进行解析");
					}
				}

			} else if (result instanceof Resultable)// 返回Resultable
			{
				logger.debug("ACTION RETURN:Resultable " + result);
				resultHandlerFactory.getResultHandler(((Resultable) result).getHandler()).doResult(actionContext, result);

			} else if (methodMeta.getDefaultResultHandlerType() != null)// 采用默认返回方式
			{
				logger.debug("ACTION RETURN: DefaultResultHandler " + methodMeta.getDefaultResultHandlerType());
				resultHandlerFactory.getResultHandler(methodMeta.getDefaultResultHandlerType()).doResult(actionContext, result);
			}
			// 以下是程序臆断返回类型
			// TODO 这些可以放在一个工厂方法中,
			// 由配置决定如何安排这些默认的返回处理:
			// 可以自由安排是否以及处理他们的顺序
			// 同时可以设定结果的"处理链"

			else if (result instanceof File)// 返回文件,
			{
				// TODO 检查文件是否为文本文件,然后在选择文件输出方式
				logger.debug("ACTION RETURN:A File " + result);
				// 在一个map中找到文件对应的mimeType,然后根据文件选择输出方式,比如一个html...
				resultHandlerFactory.getResultHandler(StreamResultHandler.class).doResult(actionContext,
						new FileInputStream((File) result));
			} else if (result instanceof InputStream)// 返回流
			{
				logger.debug("ACTION RETURN:An InputStream " + result);
				resultHandlerFactory.getResultHandler(StreamResultHandler.class).doResult(actionContext, result);
			} else if (result instanceof RuntimeException)// 异常
			{
				logger.debug("ACTION RETURN:A RuntimeException " + result);
				resultHandlerFactory.getResultHandler(ErrorResultHandler.class).doResult(actionContext, result);
			} else
			{
				// DEFAULT
				logger.debug("ACTION RETURN DEFAULT: ServletForwardResultHandler");
				resultHandlerFactory.getResultHandler(ServletForwardResultHandler.class).doResult(actionContext, result); // 默认返回ServletForwardResultHandler
			}
			return true;
		} catch (IllegalArgumentException e)
		{
			resultHandlerFactory.getResultHandler(ErrorResultHandler.class).doResult(actionContext, e);
			return true;

		} catch (RuntimeException e)// ErrorCodeException
		{
			// TODO -------------以下部分需要找到更好的处理了方案--------------
			// 可能需要增加一个异常处理系统,可以扩展:比如出现异常发送消息?EMAIL等:
			// ErrorListener?
			// 这里暂时简单的记录日志然后返回/抛出异常
			logger.debug("ACTION THROWS: A RuntimeException:" + e);
			resultHandlerFactory.getResultHandler(ErrorResultHandler.class).doResult(actionContext, e);
			return true;
		} catch (InstantiationException e)
		{
			// TODO
			logger.debug("DO ACTION THROWS: A InstantiationException:" + e);
			resultHandlerFactory.getResultHandler(ErrorResultHandler.class).doResult(actionContext, e);
			return true;
		} catch (InvocationTargetException e)
		{
			// TODO
			logger.debug("DO ACTION THROWS: A InvocationTargetException:" + e);
			resultHandlerFactory.getResultHandler(ErrorResultHandler.class).doResult(actionContext, e);
			return true;
		} catch (IllegalAccessException e)
		{
			// TODO
			// 对未知的其他异常,打包抛出500错误
			// e.printStackTrace();
			logger.debug("DO ACTION THROWS: A IllegalAccessException:" + e);
			resultHandlerFactory.getResultHandler(ErrorResultHandler.class).doResult(actionContext,
					new ErrorCodeException(500, e.getMessage()));
			return false;
		}
	}

	// 根据方法参数将ActionContext 中的参数创建参数数组
	private Object[] getP(ActionContext actionContext, MethodMeta methodMeta)
	{
		ParameterMeta[] parameterMetas = methodMeta.getParameterMetas();

		Map<String, Object> ps = new HashMap<String, Object>();
		for (ParameterMeta parameterMeta : parameterMetas)
		{
			// parameterMeta.name;
		}
		Bean5.populate(ps, actionContext.getRequest().getParameterMap());

		return ps.values().toArray();
	}

	// fuck it. let’s release.
	private Object getAction(ActionContext actionContext, ActionMeta<?> actionMeta) throws InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		// 得到action实例
		Object action = actionFactory.buildAction(actionMeta);// actionMeta.getActionClass().newInstance();
		//
		// TODO 可以设置代理类?运行时代码注入?采用lazy Get,有在get数据的时候才检查和转换数据?貌似也不是很好!

		// 将请求中的Request中的数据注入Action
		// BeanUtils.populate(action,
		// actionContext.getRequest().getParameterMap());

		// BeanUtilsTest.populate(action,
		// actionContext.getRequest().getParameterMap());
		Bean5.populate(action, actionContext.getRequest().getParameterMap());
		// Map<String, String[]> map = new HashMap<String, String[]>();
		// Enumeration<String> names =
		// actionContext.getHttpServletRequest().getParameterNames();
		// while (names.hasMoreElements())
		// {
		// String name = (String) names.nextElement();
		// map.put(name,
		// actionContext.getHttpServletRequest().getParameterValues(name));
		// logger.debug(name);
		// }
		// BeanUtils.populate(action, map);
		actionContext.setAction(action);
		return action;
	}

	/**
	 * 获取名为{method}的方法
	 * 
	 * @param action
	 * @param method
	 * @return
	 */
	private MethodMeta getActionMethodMeta(ActionMeta<?> action, String methodname)
	{
		return action.getMethodMeta(methodname);
	}

	// register action
	public MVC addAction(Class<?> actionClass) throws MVCException
	{
		ActionMeta<?> actionMeta = ActionMeta.get(actionClass);
		actionMetas.put(actionMeta.getNamespace() + "_" + actionMeta.getActionName(), actionMeta);
		logger.debug("Add Action " + actionClass + "	MAPING:" + actionMeta.getNamespace() + "_" + actionMeta.getActionName());
		return this;
	}

	// register action
	public MVC addActions(String packageName) throws MVCException
	{
		Set<Class<?>> clazzes = ConfigUtils.getClasses(packageName);
		for (Class<?> clazz : clazzes)
		{
			addAction(clazz);
		}
		return this;
	}

	public static void main(String args[])
	{
		// FOR SIMPLE TEST
	}
}
