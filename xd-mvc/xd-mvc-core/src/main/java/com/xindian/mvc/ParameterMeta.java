package com.xindian.mvc;

/**
 * 方法参数元数据
 * 
 * @author Elva
 * @date 2011-5-22
 * @version 1.0
 */
public class ParameterMeta
{
	String name;

	Class<?> type;

	Boolean require;

	@Override
	public String toString()
	{
		return "ParameterMeta: name[" + name + "] type [" + type + "]";
	}
}
