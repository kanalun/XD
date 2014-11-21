package com.xindian.beanutils.temp;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;

/**
 * 测试将内省关联到OurButton
 * 
 * @author Elva
 * @date 2011-1-26
 * @version 1.0
 */
public class OurButtonBeanInfo implements BeanInfo
{
	@Override
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		System.out.println("OHY");// 正常情况下这行代码只会被运行一次
		return new PropertyDescriptor[] {};
	}

	@Override
	public MethodDescriptor[] getMethodDescriptors()
	{
		return new MethodDescriptor[] {};
	}

	@Override
	public Image getIcon(int iconKind)
	{
		return null;
	}

	@Override
	public BeanInfo[] getAdditionalBeanInfo()
	{
		return new BeanInfo[] {};
	}

	@Override
	public BeanDescriptor getBeanDescriptor()
	{
		return new BeanDescriptor(OurButton.class);
	}

	@Override
	public int getDefaultEventIndex()
	{
		return 0;
	}

	@Override
	public int getDefaultPropertyIndex()
	{
		return 0;
	}

	@Override
	public EventSetDescriptor[] getEventSetDescriptors()
	{
		return new EventSetDescriptor[] {};
	}
}
