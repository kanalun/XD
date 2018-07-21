package com.xindian.mvc;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.annotation.Action;
import com.xindian.mvc.annotation.After;
import com.xindian.mvc.annotation.Before;
import com.xindian.mvc.annotation.DefaultResultHandler;
import com.xindian.mvc.annotation.Forbidden;
import com.xindian.mvc.aop.Handler;
import com.xindian.mvc.exception.VoteException;
import com.xindian.mvc.result.ResultHandler;

/**
 * 用注解实现配置
 * 
 * @author Elva
 * @param <T>
 */
public class ActionMeta<T> implements IActionMeta<T>
{
	private static Logger logger = LoggerFactory.getLogger(ActionMeta.class);

	private static final String DEFAULT_METHOD_NAME = "execute";

	private static final String DEFAULT_NAMESPACE = "/";

	private List<ResultMeta> results;

	private String namespace;

	private String actionName;

	private String defaultMethodName;

	private Class<T> actionClass;

	private Class<? extends Handler>[] before;

	private Class<? extends Handler>[] after;

	private ForbiddenMeta forbiddenMeta;

	private Class<? extends ResultHandler> defaultResultHandlerType;

	/** Action Methods */
	private Map<String, MethodMeta> methodMetas = new HashMap<String, MethodMeta>();

	public static <T> ActionMeta<T> get(Class<T> clazz)
	{
		return new ActionMeta<T>(clazz);
	}

	private ActionMeta(Class<T> actionClass)
	{
		this.actionClass = actionClass;
		boolean isActionAnnotationPresent = actionClass.isAnnotationPresent(Action.class);
		if (isActionAnnotationPresent)
		{
			Action actionAnnotation = actionClass.getAnnotation(Action.class);
			actionName = actionAnnotation.name();
			namespace = actionAnnotation.namespace();
			defaultMethodName = actionAnnotation.defaultMethod();
		}
		if (actionName == null || "".equals(actionName.trim()))
		{
			actionName = actionClass.getSimpleName();
		}

		if (namespace == null || "".equals(namespace.trim()))
		{
			namespace = DEFAULT_NAMESPACE;
		}
		if (!namespace.startsWith("/"))
		{
			namespace = "/" + namespace;
		}
		if (defaultMethodName == null || "".equals(defaultMethodName.trim()))
		{
			defaultMethodName = DEFAULT_METHOD_NAME;
		}
		for (Method m1 : actionClass.getDeclaredMethods())
		{
			if (Modifier.isPublic(m1.getModifiers()))
			{
				logger.debug("Method:" + m1);
				MethodMeta meta = MethodMeta.get(this, m1);
				methodMetas.put(meta.getMethodName(), meta);
			}
		}
		if (actionClass.isAnnotationPresent(DefaultResultHandler.class))
		{
			defaultResultHandlerType = actionClass.getAnnotation(DefaultResultHandler.class)
					.value();
		}
		// Forbidden
		if (actionClass.isAnnotationPresent(Forbidden.class))
		{
			this.forbiddenMeta = new ForbiddenMeta(actionClass.getAnnotation(Forbidden.class));
		}
		// AOP
		boolean isBefore = actionClass.isAnnotationPresent(Before.class);
		if (isBefore)
		{
			Before before = actionClass.getAnnotation(Before.class);
			this.before = before.value();
		}
		boolean isAfter = actionClass.isAnnotationPresent(After.class);
		if (isAfter)
		{
			After after = actionClass.getAnnotation(After.class);
			this.after = after.value();
		}
	}

	@Override
	public boolean isForbidden()
	{
		return forbiddenMeta != null;
	}

	@Override
	public ForbiddenMeta getForbiddenMeta()
	{
		return forbiddenMeta;
	}

	@Override
	public Class<T> getActionClass()
	{
		return actionClass;
	}

	private void setActionClass(Class<T> actionClass)
	{
		this.actionClass = actionClass;
	}

	@Override
	public String getActionName()
	{
		return actionName;
	}

	private void setActionName(String actionName)
	{
		this.actionName = actionName;
	}

	@Override
	public String getNamespace()
	{
		return namespace;
	}

	public void setNamespace(String namespace)
	{
		this.namespace = namespace;
	}

	@Override
	public String getDefaultMethodName()
	{
		return defaultMethodName;
	}

	@Override
	public void setDefaultMethodName(String defaultMethodName)
	{
		this.defaultMethodName = defaultMethodName;
	}

	@Override
	public MethodMeta getMethodMeta(String methodname)
	{
		return methodMetas.get(methodname);
	}

	public MethodMeta getDefaultMethodMeta()
	{
		return methodMetas.get(getDefaultMethodName());
	}

	@Override
	public void invokeBefores() throws VoteException, InstantiationException,
			IllegalAccessException
	{
		if (before != null)
		{
			for (Class<? extends Handler> beforeClass : before)
			{
				beforeClass.newInstance().execute();
			}
		}
	}

	@Override
	public void invokeAfters() throws VoteException, InstantiationException, IllegalAccessException
	{
		if (after != null)
		{
			for (Class<? extends Handler> afterClass : after)
			{
				afterClass.newInstance().execute();
			}
		}
	}

	public Class<? extends ResultHandler> getDefaultResultHandlerType()
	{
		return defaultResultHandlerType;
	}

	public void setDefaultResultHandlerType(Class<? extends ResultHandler> defaultResultHandlerType)
	{
		this.defaultResultHandlerType = defaultResultHandlerType;
	}
}
