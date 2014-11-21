package com.xindian.beanutils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.xindian.beanutils.annotation.Element;

/**
 * 
 * @author Elva
 * @date 2011-1-22
 * @version 1.0
 */
public class Help
{
	/**
	 * 得到Field包含元素的类型
	 * 
	 * @param field
	 * @return
	 */
	public static Class<?> getElementType(Field field)
	{
		if (field.isAnnotationPresent(Element.class))// 首先会尝试使用注解中的element
		{
			return field.getAnnotation(Element.class).value();

		} else if (field.getType().isArray())// 数组
		{
			return field.getType().getComponentType();// 返回数组的元素类型

		} else if (field.getType().isAssignableFrom(List.class))// List,返回第一个泛型参数
		{
			return GenericsUtils.getFieldGenericType(field);

		} else if (field.getType().isAssignableFrom(Map.class))// Map,返回第二个泛型参数
		{
			return GenericsUtils.getFieldGenericType(field, 1);

		} else
		{
			return null;
		}
	}
}
