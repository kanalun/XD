package com.xindian.mvc;

/**
 * 翻译后的映射,系统会根据这个映射中的信息找到合适的Action
 * 
 * @author Elva
 * 
 */
public class Mapping
{
	String namespace;

	String actionName;

	String methodName;

	public Mapping(String namespace, String actionName, String methodName)
	{
		this.namespace = namespace;
		this.actionName = actionName;
		this.methodName = methodName;
	}

	public Mapping()
	{
	}

	public String getNamespace()
	{
		return namespace;
	}

	void setNamespace(String namespace)
	{
		this.namespace = namespace;
	}

	String getActionName()
	{
		return actionName;
	}

	void setActionName(String actionName)
	{
		this.actionName = actionName;
	}

	String getMethodName()
	{
		return methodName;
	}

	void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}

	@Override
	public String toString()
	{
		String s = "Namespace =[" + getNamespace() + "] ActionName=[" + getActionName() + "] MethodName =[" + getMethodName()
				+ "]";
		return s;
	}
}
