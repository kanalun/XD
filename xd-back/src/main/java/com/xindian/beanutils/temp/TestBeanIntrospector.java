package com.xindian.beanutils.temp;

import java.beans.BeanInfo;
import java.beans.Beans;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;

import com.xindian.mvc.test.ExAddress;

public class TestBeanIntrospector
{
	class CL extends ClassLoader
	{

	}

	public static void a() throws IOException, ClassNotFoundException
	{
		ExAddress exAddress = (ExAddress) Beans.instantiate(null, "com.kan.mvc.test.ExAddress");

		PropertyChangeSupport bean = new PropertyChangeSupport(exAddress);

		bean.addPropertyChangeListener("address", new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				System.out.println(evt.getNewValue() + "ddd");
			}
		});
		exAddress.setOk("SETOK1");
		exAddress.setOk("SETOK2");
		// bean.firePropertyChange("address", "NU", "DDD");

		PropertyChangeListener[] listeners = bean.getPropertyChangeListeners();
		for (int i = 0; i < listeners.length; i++)
		{
			if (listeners[i] instanceof PropertyChangeListenerProxy)
			{
				PropertyChangeListenerProxy proxy = (PropertyChangeListenerProxy) listeners[i];
				if (proxy.getPropertyName().equals("foo"))
				{
					// proxy is a PropertyChangeListener which was associated
					// with the property named "foo"
				}
			}
		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, IntrospectionException
	{
		new TestBeanIntrospector();
	}

	public static void main3(String[] args) throws IOException, ClassNotFoundException, IntrospectionException
	{
		BeanInfo beanInfo = Introspector.getBeanInfo(OurButton.class);
		// OurButtonBeanInfo beanInfo =
		// (OurButtonBeanInfo)Introspector.getBeanInfo(OurButton.class);//这行代码是错误的
		beanInfo.getPropertyDescriptors();
		beanInfo.getPropertyDescriptors();
		beanInfo.getPropertyDescriptors();
	}

	public static void main2(String[] args) throws IOException, ClassNotFoundException
	{
		// ExAddress exAddress = (ExAddress) Beans.instantiate(null,
		// "com.kan.mvc.test.ExAddress");
		// System.out.println(exAddress);
		a();
		// new TestBeanIntrospector();
	}

	public TestBeanIntrospector()
	{
		try
		{
			// 实例化一个Bean
			ExAddress bean = new ExAddress();
			// 依据Bean产生一个相关的BeanInfo类
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
			// 定义一个用于显示的字符串
			String output = "";

			// 开始自省

			/**
			 * BeanInfo.getMethodDescriptors() 用于获取该Bean中的所有允许公开的成员方法，
			 * 
			 * 以MethodDescriptor数组的形式返回
			 * 
			 * MethodDescriptor类 用于记载一个成员方法的所有信息 MethodDescriptor.getName()
			 * 
			 * 获得该方法的方法名字 MethodDescriptor.getMethod()
			 * 
			 * 获得该方法的方法对象（Method类）
			 * 
			 * Method类 记载一个具体的的方法的所有信息 Method.getParameterTypes()
			 * 
			 * 获得该方法所用到的所有参数，以Class数组的形式返回
			 * 
			 * Class..getName() 获得该类型的名字
			 */
			output = "内省成员方法：\n";
			MethodDescriptor[] mDescArray = beanInfo.getMethodDescriptors();
			for (int i = 0; i < mDescArray.length; i++)
			{
				// 获得一个成员方法描述器所代表的方法的名字
				String methodName = mDescArray[i].getName();

				String methodParams = new String();
				// 获得该方法对象
				Method methodObj = mDescArray[i].getMethod();
				// 通过方法对象获得该方法的所有参数，以Class数组的形式返回
				Class[] parameters = methodObj.getParameterTypes();
				if (parameters.length > 0)
				{
					// 获得参数的类型的名字
					methodParams = parameters[0].getName();
					for (int j = 1; j < parameters.length; j++)
					{
						methodParams = methodParams + "," + parameters[j].getName();
					}
				}
				output += methodName + "(" + methodParams + ")\n";
			}
			System.out.println(output);

			/*
			 * BeanInfo.getPropertyDescriptors()
			 * 用于获取该Bean中的所有允许公开的成员属性，以PropertyDescriptor数组的形式返回
			 * 
			 * PropertyDescriptor类 用于描述一个成员属性
			 * 
			 * PropertyDescriptor.getName() 获得该属性的名字
			 * 
			 * PropertyDescriptor.getPropertyType() 获得该属性的数据类型，以Class的形式给出
			 */
			output = "内省成员属性：\n";
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (int i = 0; i < propertyDescriptors.length; i++)
			{
				// if (mPropertyArray[i] instanceof IndexedPropertyDescriptor)
				{
					// System.out.println(propertyDescriptors[i].getClass());
				}
				// String md = mPropertyArray[i].getReadMethod().getName();

				String propertyName = propertyDescriptors[i].getName();

				Class<?> propertyType = propertyDescriptors[i].getPropertyType();

				// propertyDescriptors[i].get

				if (propertyType.isArray())
				{
					// IndexedPropertyDescriptor in =
					// (IndexedPropertyDescriptor) propertyDescriptors[i];

					// System.out.println(in.getIndexedReadMethod().getName());
				}
				output += propertyName + " (" + propertyType.getName() + ")	" + " md" + "()\n";
			}
			System.out.println(output);

			/*
			 * BeanInfo.getEventSetDescriptors() 用于获取该Bean中的所有允许公开的成员事件，
			 * 
			 * 以EventSetDescriptor数组的形式返回
			 * 
			 * EventSetDescriptor类 用于描述一个成员事件
			 * 
			 * EventSetDescriptor.getName() 获得该事件的名字
			 * 
			 * EventSetDescriptor.getListenerType() 获得该事件所依赖的事件监听器，以Class的形式给出
			 */
			output = "内省绑定事件：\n";
			EventSetDescriptor[] mEventArray = beanInfo.getEventSetDescriptors();
			for (int i = 0; i < mEventArray.length; i++)
			{
				String EventName = mEventArray[i].getName();
				Class<?> listenerType = mEventArray[i].getListenerType();
				output += EventName + "(" + listenerType.getName() + ")\n";
			}
			// System.out.println(output);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}