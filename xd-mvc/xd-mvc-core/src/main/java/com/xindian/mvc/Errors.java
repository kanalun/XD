package com.xindian.mvc;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放错误,每一个请求包含一个errors对象,存储该请求产生的错误
 * 
 * @author Elva
 * @date 2011-2-10
 * @version 1.0
 */
public class Errors
{
	public Map<String, Map<String, String>> errors = new HashMap<String, Map<String, String>>();

	public final static String ERROR_TYPE_DEFAULT = "default";

	public final static String ERROR_TYPE_CONVERTION = "convertion";

	public String getError(String key)
	{
		for (Map<String, String> typedErrorMap : errors.values())
		{
			String error = typedErrorMap.get(key);
			if (error != null)
			{
				return error;
			}
		}
		return null;
	}

	public String getError(String errorType, String key)
	{
		Map<String, String> typedErrorMap = errors.get(errorType);
		if (typedErrorMap == null)
		{
			return null;
		}
		return typedErrorMap.get(key);
	}

	public void putError(String key, String error)
	{
		putError(ERROR_TYPE_DEFAULT, key, error);
	}

	public void putError(String errorType, String key, String error)
	{
		if (errorType == null)
		{
			errorType = ERROR_TYPE_DEFAULT;
		}
		Map<String, String> typedErrorMap = errors.get(errorType);
		if (typedErrorMap == null)
		{
			typedErrorMap = new HashMap<String, String>();
			errors.put(errorType, typedErrorMap);
		}
		typedErrorMap.put(key, error);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
	}

}
