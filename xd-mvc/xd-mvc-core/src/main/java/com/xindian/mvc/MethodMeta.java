package com.xindian.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javassist.NotFoundException;

import com.xindian.beanutils.Bean5;
import com.xindian.mvc.annotation.After;
import com.xindian.mvc.annotation.Before;
import com.xindian.mvc.annotation.DefaultResultHandler;
import com.xindian.mvc.annotation.Forbidden;
import com.xindian.mvc.annotation.MethodType;
import com.xindian.mvc.annotation.Result;
import com.xindian.mvc.annotation.Results;
import com.xindian.mvc.aop.Handler;
import com.xindian.mvc.exception.VoteException;
import com.xindian.mvc.result.ResultHandler;
import com.xindian.mvc.utils.MethodUtils;
import com.xindian.mvc.utils.MethodUtils.MissingLVException;

/**
 * 存储了配置方法的元数据// TODO 写成一个接口,可以有不同的实现:注解,XML配置....
 * 
 * @author Elva
 * 
 */
public class MethodMeta // implements IMethodMeta
{
	private ActionMeta<?> actionMeta;

	/** action 方法的名称,除非特别指定,否则默认为:method Name */
	private String methodName;

	/** 方法 */
	private Method method;

	/** action对应的处理类型,GET/POST/... */
	private MethodType type = MethodType.ALL;

	/** AOP */
	private Class<? extends Handler>[] before;

	private Class<? extends Handler>[] after;

	private List<ResultMeta> results;

	private ForbiddenMeta forbiddenMeta;

	private Class<? extends ResultHandler> defaultResultHandlerType;

	private ParameterMeta[] parameterMetas;

	/**
	 * 给定字符串返回该字符串对应的结果处理器,如果不存在,返回null
	 * 
	 * @param name
	 * @return
	 */
	public Class<? extends ResultHandler> getResultHandler(String name)
	{
		if (results != null)
		{
			for (ResultMeta resultMeta : results)
			{
				if (resultMeta.name.equals(name))
				{
					if (resultMeta.type != null)
						return resultMeta.type;
				}
			}
		}
		return null;
	}

	public ResultMeta getResultMeta(String name)
	{
		if (results != null)
		{
			for (ResultMeta resultMeta : results)
			{
				if (resultMeta.name.equals(name))
				{
					if (resultMeta.type != null)
						return resultMeta;
				}
			}
		}
		return null;
	}

	private void addResultMeta(ResultMeta resultMeta)
	{
		if (results == null)
		{
			synchronized (this)
			{
				results = new ArrayList<ResultMeta>();
			}
		}
		synchronized (results)
		{
			results.add(resultMeta);
		}
	}

	protected MethodMeta(ActionMeta<?> actionMeta, Method method)
	{
		boolean isBefore = method.isAnnotationPresent(Before.class);
		if (isBefore)
		{
			Before before = method.getAnnotation(Before.class);
			this.before = before.value();
		}
		boolean isAfter = method.isAnnotationPresent(After.class);
		if (isAfter)
		{
			After after = method.getAnnotation(After.class);
			this.after = after.value();
		}
		boolean isAnnotationPresent = method.isAnnotationPresent(com.xindian.mvc.annotation.Method.class);
		if (isAnnotationPresent)
		{
			com.xindian.mvc.annotation.Method methodAnnotation = method.getAnnotation(com.xindian.mvc.annotation.Method.class);
			methodName = methodAnnotation.name();
			type = methodAnnotation.type();
		}
		if (methodName == null || methodName.equals(""))
		{
			methodName = method.getName();
		}
		Annotation[] annotations = method.getDeclaredAnnotations();
		for (Annotation annotation : annotations)
		{
			if (annotation instanceof Result)
			{
				addResultMeta(new ResultMeta(this, (Result) annotation));
			}
			if (annotation instanceof Results)
			{
				Result[] rs = ((Results) annotation).value();
				for (Result r : rs)
				{
					addResultMeta(new ResultMeta(this, r));
				}
			}
		}
		if (method.isAnnotationPresent(DefaultResultHandler.class))
		{
			defaultResultHandlerType = method.getAnnotation(DefaultResultHandler.class).value();
		}
		if (method.isAnnotationPresent(Forbidden.class))
		{
			this.forbiddenMeta = new ForbiddenMeta(method.getAnnotation(Forbidden.class));
		}
		Class<?>[] methodParameters = method.getParameterTypes();
		try
		{
			String[] pNames = MethodUtils.getMethodParamNames(actionMeta.getActionClass(), method.getName(), methodParameters);
			parameterMetas = new ParameterMeta[pNames.length];
			for (int i = 0; i < pNames.length; i++)
			{
				parameterMetas[i] = new ParameterMeta();
				parameterMetas[i].name = pNames[i];
				parameterMetas[i].type = methodParameters[i];
				System.out.println(parameterMetas[i]);
				// TODO OTHER
			}
		} catch (NotFoundException e)
		{
			e.printStackTrace();
		} catch (MissingLVException e)
		{
			e.printStackTrace();
		}
		this.method = method;
		this.actionMeta = actionMeta;
	}

	public ForbiddenMeta getForbiddenMeta()
	{
		return forbiddenMeta;
	}

	public static MethodMeta get(ActionMeta<?> actionMeta, Method method)
	{
		return new MethodMeta(actionMeta, method);
	}

	public String getMethodName()
	{
		return methodName;
	}

	public Class<? extends Handler>[] getBefore()
	{
		return before;
	}

	public Class<? extends Handler>[] getAfter()
	{
		return after;
	}

	// TODO
	public Object invoke(Object bean, Map<String, Object> value)
	{
		List<Object> parameters = new ArrayList<Object>();
		if (parameterMetas != null)
		{
			for (ParameterMeta parameterMeta : parameterMetas)
			{
				try
				{
					// TODO 空
					Object v = parameterMeta.type.newInstance();
					Bean5.populate(v, value);
					parameters.add(v);
				} catch (InstantiationException e)
				{
					e.printStackTrace();
				} catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}
		try
		{
			return method.invoke(bean, parameters.toArray());
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private Object invoke(Object bean)
	{
		try
		{
			return method.invoke(bean);
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void invokeBefores() throws VoteException, InstantiationException, IllegalAccessException
	{
		if (before != null)
		{
			for (Class<? extends Handler> beforeClass : before)
			{
				beforeClass.newInstance().execute();//
			}
		}
	}

	public void invokeAfters() throws VoteException, InstantiationException, IllegalAccessException
	{
		if (after != null)
		{
			for (Class<? extends Handler> afterClass : after)
			{
				afterClass.newInstance().execute();//
			}
		}
	}

	public MethodType getType()
	{
		return type;
	}

	public boolean isForbidden()
	{
		return forbiddenMeta != null;
	}

	/**
	 * @return
	 */
	public boolean canDoGet()
	{
		// !isForbidden() &&
		return (type == MethodType.ALL || type == MethodType.GET);
	}

	public boolean canDoPost()
	{
		return (type == MethodType.ALL || type == MethodType.POST);
	}

	public boolean canDoMethodType(String method)
	{
		if (method == null || method.trim().equalsIgnoreCase(""))
		{
			return false;
		}
		method = method.trim();
		return ((method.equalsIgnoreCase("GET") && canDoGet()) || (method.equalsIgnoreCase("POST") && canDoPost()));
	}

	/**
	 * 返回对结果的默认处理方法:方法,Action,Package
	 * 
	 * @return 返回默认的处理方法或者null
	 */
	public Class<? extends ResultHandler> getDefaultResultHandlerType()
	{
		if (defaultResultHandlerType != null)
		{
			return defaultResultHandlerType;
		} else
		{
			// actionMeta.getActionClass().getPackage().getAnnotation(annotationClass);
			return actionMeta.getDefaultResultHandlerType();
		}
	}

	public Method getMethod()
	{
		return method;
	}

	public ActionMeta<?> getActionMeta()
	{
		return actionMeta;
	}

	public ParameterMeta[] getParameterMetas()
	{
		return parameterMetas;
	}

}
