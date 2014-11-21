package com.xindian.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.xindian.ioc.annotation.ScopeType;

/**
 * @author Elva
 * @date 2011-1-24
 * @version 1.0
 * @param <T>
 */
public class BeanMeta<T>
{
	/** name of bean */
	private String name;

	/** bean Type */
	private Class<T> type;

	private ScopeType scopeType;

	private Class<?> implType;

	private Method[] postConstructs;

	private Field[] autowireds;

	public BeanMeta(ScopeType scopeType, Class<T> type)
	{
		this.type = type;
		this.scopeType = scopeType;
	}

	public Class<T> getType()
	{
		return type;
	}

	public void setType(Class<T> type)
	{
		this.type = type;
	}

	public Class<?> getImplType()
	{
		if (implType == null)
		{
			return type;
		}
		return implType;
	}

	public void setImplType(Class<?> implType)
	{
		this.implType = implType;
	}
}
