package com.xindian.ioc;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.promise.Singleton;
import com.xindian.commons.promise.ThreadSafe;
import com.xindian.ioc.annotation.Autowired;
import com.xindian.ioc.annotation.Implementation;
import com.xindian.ioc.annotation.Scope;
import com.xindian.ioc.annotation.ScopeType;
import com.xindian.ioc.exception.IOCException;

/**
 * @author Elva
 * @date 2011-1-24
 * @version 1.0
 */
public class BeanFactory implements Singleton, ThreadSafe
{
	private static Logger logger = LoggerFactory.getLogger(BeanFactory.class);

	private static BeanFactory singleton = new BeanFactory();

	/**
	 * FIXME
	 * 缓存单例对象,使用弱键引用MAP可以防止内存溢出(GC会回收MAP中不再使用的单例对象,防止这些单例对象在应用的生命周期中一直的存在?)
	 */
	private Map<Class<?>, Object> singletons = Collections.synchronizedMap(new WeakHashMap<Class<?>, Object>());

	// new ConcurrentHashMap<Class<?>,Object>();

	private Map<String, BeanMeta<?>> beans = new ConcurrentHashMap<String, BeanMeta<?>>();// ID->Bean

	private ThreadLocal<Map<Class<?>, Object>> threadLocal = new ThreadLocal<Map<Class<?>, Object>>();

	// Reference<String> s = new WeakReference<String>("");

	private BeanFactory()
	{
		// DO_NOTHING
	}

	public static BeanFactory getSingleton()
	{
		return singleton;
	}

	public Object getBean(String name)
	{
		// Thread.currentThread().get
		// Runtime.getRuntime().addShutdownHook(hook);
		return null;
	}

	private Map<String, Module> modules = new HashMap<String, Module>();

	/**
	 * @param moduleName
	 * @return
	 */
	public Module getModule(String moduleName)
	{
		Module module = modules.get(moduleName);
		if (module == null)
		{
			// create a new
			module = new DefaultModule();
			modules.put(moduleName, module);
		}
		return module;
	}

	public <T> T getBean(String name, Class<T> type)
	{
		return null;
	}

	/**
	 * 注册一个bean,
	 * 
	 * @param type
	 */
	public synchronized void registerBean(Class<?> type)
	{
		// beans.put(name, bean);
	}

	public synchronized void registerBean(String name, Class<?> type, ScopeType scope)
	{
		BeanMeta bean = new BeanMeta(scope, type);
		beans.put(name, bean);
	}

	private <T> T getBean(BeanMeta<T> type) throws IOCException
	{
		return null;
	}

	/**
	 * 如果他是一个接口,查看接口是否被Implementation注解
	 * 
	 * 如果不存在,会尝试使用同一包下默认的Impl后缀实例化该接口
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	public <T> T getBean(Class<T> type) throws IOCException
	{
		Object o = singletons.get(type);
		if (o != null)
		{
			logger.debug("Return a Singleton:" + type);
			return (T) o;
		} else
		{
			try
			{
				Class<?> implType = type;
				if (type.isAnnotationPresent(Implementation.class))
				{
					Implementation implementation = type.getAnnotation(Implementation.class);
					implType = implementation.value();
				} else if (type.isInterface())// 对于接口尝试默认在后加上Impl作为实现类
				{
					implType = Class.forName(type.getName() + "Impl");
					logger.debug("use Default:" + implType);
				}
				/**
				 * <code>这部分代码有一些问题
				if (Singleton.class.isAssignableFrom(implType) || Singleton.class.isAssignableFrom(type))// 实现Singleton接口的
				{
					logger.debug("Impl..Type extends from Singleton");
					try
					{
						Method instantiatedMethod = implType.getMethod("getSingleton", new Class<?>[0]);
						o = instantiatedMethod.invoke(null, new Object[0]);// 调用静态方法来生成单例对象
						singletons.put(type, o);
					} catch (SecurityException e)
					{
						e.printStackTrace();
					} catch (NoSuchMethodException e)
					{
						e.printStackTrace();
						throw new IOCException(
								"if your type impl interface Singleton, that means you have made a promise that you should provide a static FactoryMethod which names getSingleton!",
								e);
					} catch (IllegalArgumentException e)
					{
						e.printStackTrace();
					} catch (InvocationTargetException e)
					{
						e.printStackTrace();
					}
				} else
				 */
				{
					o = implType.newInstance();
					if (type.isAnnotationPresent(Scope.class))
					{
						Scope scope = type.getAnnotation(Scope.class);
						ScopeType scopeType = scope.value();
						if (scopeType == ScopeType.SINGLETON)
						{
							singletons.put(type, o);
						}
					}
				}
				// INJECT THE Filed
				for (Field field : implType.getFields())
				{
					if (field.isAnnotationPresent(Autowired.class))
					{
						Autowired autowired = field.getAnnotation(Autowired.class);
						try
						{
							Object awo = getBean(field.getType());// 递归调用,获得依赖
							field.set(o, awo);// TODO 用内省,而非反射
						} catch (IOCException e)
						{
							if (autowired.required())
							{
								throw new IOCException("[" + type + "]中的属性[" + field.getName() + "]无法被注入", e);
							}
						}
					}
				}
				return (T) o;
			} catch (InstantiationException e)
			{
				e.printStackTrace();
				throw new IOCException("[" + type + "]无法被实例化", e);
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
				throw new IOCException("[" + type + "]无法被实例化", e);
			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
				throw new IOCException("[" + type + "]无法被实例化", e);
			}
		}
	}
}
