package com.xindian.mvc.result.velocity;

import java.lang.reflect.Field;

import org.apache.velocity.VelocityContext;

/**
 * 包含了一个MAP和一个BEAN,BEAN是只读的,<br/>
 * 
 * 而MAP可以写入数据(一般不用这个方法),优先从MAP中获得数据,
 * 
 * @author Elva
 * @date 2011-1-18
 * @version 1.0
 */
public class BeanVelocityContext extends VelocityContext
{
	private Object bean;

	public BeanVelocityContext(Object bean)
	{
		this.bean = bean;
	}

	@Override
	public Object internalGet(String key)
	{
		// first, let's check to see if have the requested value
		if (super.internalContainsKey(key))
		{
			return super.internalGet(key);
		}
		// still no luck? let's look against the action
		if (bean != null)
		{
			Object object = null;
			try
			{
				// TODO USE GETTER/SETTER To Access The Filed
				Field field = bean.getClass().getDeclaredField(key);
				field.setAccessible(true);
				object = field.get(bean);
				if (object != null)
				{
					return object;
				}
			} catch (SecurityException e)
			{
				// e.printStackTrace();
			} catch (NoSuchFieldException e)
			{
				// DO_NOTING
			} catch (IllegalAccessException e)
			{
				// e.printStackTrace();
			}
		}
		return null;
	}

	public boolean internalContainsKey(Object key)
	{
		boolean contains = super.internalContainsKey(key);
		// first let's check to see if we contain the requested key
		if (contains)
		{
			return true;
		}
		//
		if (bean != null && key instanceof String)
		{
			Object object = null;
			try
			{
				// TODO USE GETTER/SETTER To Access The Filed
				Field field = bean.getClass().getDeclaredField((String) key);
				field.setAccessible(true);
				object = field.get(bean);
				if (object != null)
				{
					return true;
				}
			} catch (SecurityException e)
			{
				// e.printStackTrace();
			} catch (NoSuchFieldException e)
			{
				// DO_NOTING
			} catch (IllegalAccessException e)
			{
				// e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public Object[] internalGetKeys()
	{
		Field[] fds = bean.getClass().getFields();
		String[] names = new String[fds.length];
		for (int i = 0; i < fds.length; i++)
		{
			names[i] = fds[i].getName();
		}
		return names;
	}

}
