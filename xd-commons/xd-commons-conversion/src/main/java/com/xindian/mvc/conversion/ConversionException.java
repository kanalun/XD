package com.xindian.mvc.conversion;

import java.util.Map;

/**
 * 
 * @author Elva
 * @date 2011-2-10
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ConversionException extends RuntimeException
{
	Map<String, Object> context;

	Class<?> targetType;// 目标

	Object source;

	public ConversionException()
	{
	}

	public ConversionException(Map<String, Object> context, Class<?> targetType, Object source, String message,
			Throwable throwable)
	{
		super(message, throwable);
		this.context = context;
		this.targetType = targetType;
		this.source = source;
	}

	public ConversionException(Map<String, Object> context, Class targetType, Object source, String message)
	{
		super(message);
		this.context = context;
		this.targetType = targetType;
		this.source = source;
	}

	public ConversionException(String message)
	{
		super(message);
	}

	public Map<String, Object> getContext()
	{
		return context;
	}

	public Class<?> getTargetType()
	{
		return targetType;
	}

	public Object getSource()
	{
		return source;
	}

}
