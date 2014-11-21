package com.xindian.beanutils.temp;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xindian.mvc.cache.ActionCache;

@ActionCache(name = "qcc")
public class TestBeanIntrospector2
{
	static Log logger = LogFactory.getLog(TestBeanIntrospector2.class);

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// 实例化一个Bean
		TestBeanIntrospector2 bean = new TestBeanIntrospector2();
		// 依据Bean产生一个相关的BeanInfo类
		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
			logger.debug("beanInfo" + beanInfo.getClass());
			PropertyDescriptor[] ps = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor p : new PropertyUtilsBean().getPropertyDescriptors(bean))
			{
				if (p instanceof IndexedPropertyDescriptor)
				{
					logger.debug(((IndexedPropertyDescriptor) p).getName() + " is IndexedPropertyDescriptor");
				}
				try
				{
					Field f = bean.getClass().getDeclaredField(p.getName());
				} catch (SecurityException e)
				{
					// e.printStackTrace();
				} catch (NoSuchFieldException e)
				{
					// e.printStackTrace();
				}
				// logger.debug(p.getReadMethod().getDeclaringClass().getAnnotation(ActionCache.class).name());
				logger.debug(p.getWriteMethod().getDeclaringClass() + "--------------------");
			}
			try
			{
				logger.debug(new PropertyUtilsBean().getPropertyDescriptor(bean, "cc") + "==========");
			} catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IntrospectionException e)
		{
			e.printStackTrace();
		}
	}

	int i;

	int[] x;

	public int getI()
	{
		return i;
	}

	public void setI(int i)
	{
		this.i = i;
	}

	public int[] getX()
	{
		return x;
	}

	// List<String> ls;

	public void setX(int[] x)
	{
		this.x = x;
	}

	public void setX(int index, int x)
	{
		this.x[index] = x;
	}

	public int getX(int index)
	{
		return x[index];
	}

	public List<String> getLs()
	{
		// return ls;
		return null;
	}

	public void setLs(List<String> ls)
	{
		// this.ls = ls;
	}

	public void setLs(int index, String foo)
	{
		// this.ls.set(index, foo);
	}

	public String getLs(int index)
	{
		// return this.ls.get(index);
		return null;
	}

	public void setY(String name, String some)
	{

	}

}
