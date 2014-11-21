package com.xindian.mvc.result.json;

import com.xindian.mvc.result.ReadableResult;
import com.xindian.mvc.result.ResultHandler;
import com.xindian.mvc.result.Resultable;

/**
 * Wrap an object for a Json
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class JSONResult extends ReadableResult<JSONResult> implements Resultable
{
	private Object object;

	public JSONResult(Object object)
	{
		this.object = object;
		setContentType("application/json");
	}

	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return JSONResultHandler.class;
	}

	public Object getObject()
	{
		return object;
	}

	public void setObject(Object object)
	{
		this.object = object;
	}

	/**
	 * filter("u.passowrd",false);
	 * 
	 * @param fieldName
	 * @param filter
	 */
	protected void filter(String fieldName, boolean filter)
	{
		// TODO
	}

	protected void append(String fieldName, Object value)
	{
		// TODO
	}

}
