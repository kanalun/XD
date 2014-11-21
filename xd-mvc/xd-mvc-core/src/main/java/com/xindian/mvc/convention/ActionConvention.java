package com.xindian.mvc.convention;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.ActionMeta;
import com.xindian.mvc.MVC;
import com.xindian.mvc.MVCAction;
import com.xindian.mvc.annotation.Action;
import com.xindian.mvc.test.actions.I18NAction;
import com.xindian.mvc.test.actions.TestAop;
import com.xindian.mvc.test.actions.TestMethods;
import com.xindian.mvc.test.actions.TestResults;

/**
 * Action的约定
 * 
 * 1,实现MVCAction接口的类
 * 
 * 2,被Action注解的类
 * 
 * 3,以Action结尾的的类:XXXAction
 * 
 * @author Elva
 * @date 2011-3-6
 * @version 1.0
 */
public class ActionConvention implements Plugin
{
	private static Logger logger = LoggerFactory.getLogger(ActionConvention.class);

	private List<ActionConventionFilter> conventionFilters = new ArrayList<ActionConventionFilter>();

	public class AssignableFromMVCAction implements ActionConventionFilter
	{
		@Override
		public boolean filter(Class<?> type)
		{
			if (MVCAction.class.isAssignableFrom(type))// 实现MVCAction接口的类
			{
				logger.debug("Assignable From MVCAction");
				return true;
			}
			return false;
		}
	}

	public class EndWithAction implements ActionConventionFilter
	{
		@Override
		public boolean filter(Class<?> type)
		{
			if (type.getName().endsWith(ACTION_ENDS_WITH))
			{
				logger.debug("Type[" + type.getName() + "] end With " + ACTION_ENDS_WITH);
				return true;
			}
			return false;
		}
	}

	@Override
	public void install()
	{
		String actionPackagesStr = (String) PluginContext.getPluginContext().getParam(ACTION_PACKAGES);
		String[] actionPackages = actionPackagesStr.trim().split(",");
		for (String actionPackage : actionPackages)
		{
			// ConfigUtils.findClassesInPackageByFile(packageName, packagePath,
			// recursive, classes);
		}
	}

	@Override
	public void uninstall()
	{
		// TODO 初始化插件环境,搜索class下的插件,然后逐个将其安装
		String actionPackagesStr = (String) PluginContext.getPluginContext().getParam(ACTION_PACKAGES);
		if (actionPackagesStr != null)// TODO 将
		{
			try
			{
				Class<?> c = Class.forName("com.xindian.mvc.convention.ActionConvention");
				Object ic = c.newInstance();
				Method i = c.getMethod("init", null);
				Object cs = i.invoke(ic, "");
				MVC.getSingleton().addAction((Class) cs);//
			} catch (ClassNotFoundException e)
			{
				logger.debug("com.kan.mvc.convention.ActionConvention 不存在,请安装ActionConvention插件:将convention放到classpath中");
				e.printStackTrace();
			} catch (InstantiationException e)
			{
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			} catch (SecurityException e)
			{
				e.printStackTrace();
			} catch (NoSuchMethodException e)
			{
				e.printStackTrace();
			} catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}
	}

	void init(String[] packageNames)
	{
		Package.getPackage(packageNames[0]);
	}

	private static String ACTION_PACKAGES = "actionPackages";

	public final static String ACTION_ENDS_WITH = "Action";

	/**
	 * @param type
	 * @return
	 */
	public static boolean isAction(Class<?> type)
	{
		if (MVCAction.class.isAssignableFrom(type))// 实现MVCAction接口的类
		{
			logger.debug("Assignable From MVCAction");
			return true;
		}
		if (type.isAnnotationPresent(Action.class))// 被Action注解了的类
		{
			logger.debug("Annotation Present By Action");
			return true;
		}
		if (type.getName().endsWith(ACTION_ENDS_WITH))
		{
			logger.debug("Type[" + type.getName() + "] end With " + ACTION_ENDS_WITH);
			return true;
		}
		return false;
	}

	public static <T> ActionMeta<T> getActionMeta(Class<T> type)
	{
		boolean isActionAnnotationPresent = type.isAnnotationPresent(Action.class);
		if (isActionAnnotationPresent)
		{
			Action actionAnnotation = type.getAnnotation(Action.class);
			String actionName = actionAnnotation.name();
			String namespace = actionAnnotation.namespace();
			String defaultMethodName = actionAnnotation.defaultMethod();
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Properties props = System.getProperties();

		Iterator iter = props.keySet().iterator();
		/*
		 * while (iter.hasNext()) { String key = (String) iter.next();
		 * System.out.println(key + " = " + props.get(key)); }
		 */
		logger.debug(System.getProperty("java.class.path"));
		// 获得所有的jar
		logger.debug(isAction(I18NAction.class) + "");
		logger.debug(isAction(TestMethods.class) + "");
		logger.debug(isAction(TestResults.class) + "");
		logger.debug(isAction(TestAop.class) + "");
		//
	}
}
