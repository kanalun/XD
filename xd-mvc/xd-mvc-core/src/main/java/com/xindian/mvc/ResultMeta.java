package com.xindian.mvc;

import com.xindian.mvc.annotation.Result;
import com.xindian.mvc.result.ResultHandler;

/**
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
public class ResultMeta
{
	String name;// 逻辑字符串

	String protocol;

	String value;//

	String[] parameters;

	Class<? extends ResultHandler> type;

	MethodMeta methodMeta;

	public ResultMeta(MethodMeta methodMeta, Result result)
	{
		this.methodMeta = methodMeta;
		name = result.name();
		value = result.value();
		parameters = result.parameters();
		type = result.type();
		protocol = result.protocol();
	}
}
