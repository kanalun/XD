package com.xindian.ioc;

import com.xindian.ioc.annotation.ScopeType;

/**
 * @author Elva
 * @date 2011-3-5
 * @version 1.0
 */
public abstract class AbstractModule implements Module
{
	@Override
	public Object getBean(String name)
	{
		return null;
	}

	public <T> BeanMeta<T> bind(Class<T> type, Class<? extends T> impl, ScopeType scopeType)
	{
		return null;
	}

	public <T> BeanMeta<T> bind(Class<T> type, ScopeType scopeType)
	{
		return null;
	}

	public <T> BeanMeta<T> bind(Class<T> type)
	{
		return null;
	}
}
