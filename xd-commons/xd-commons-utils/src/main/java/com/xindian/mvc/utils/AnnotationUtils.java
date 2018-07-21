package com.xindian.mvc.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationUtils
{
	private static Logger logger = LoggerFactory.getLogger(AnnotationUtils.class);

	/**
	 * 使用从注解中获取数据<br/>
	 * 
	 * <strong>注意:你无法直接将注解类型转换,从而试图从中获得数据,
	 * 
	 * 因为运行时注解使用了Proxy,直接进行类型转换会抛出ClassCastException </strong>
	 * 
	 * @param <T>
	 * @param name
	 * @param valueType
	 * @param annotation
	 * @return 如果注解中有名称为name的方法,返回他的值,否则返回NULL,TODO,是否应该使用异常来处理?
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(String name, Class<T> valueType, Annotation annotation)
	{
		try
		{
			logger.debug("get [" + name + "]from Annotation[" + annotation + "]...");
			return (T) annotation.getClass().getMethod(name).invoke(annotation, new Object[0]);
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (SecurityException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
