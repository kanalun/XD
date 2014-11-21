package com.xindian.mvc.result.freemarker;

import java.util.HashMap;
import java.util.Map;

import com.xindian.mvc.result.ReadableResult;
import com.xindian.mvc.result.ResultHandler;
import com.xindian.mvc.result.Resultable;

/**
 * Freemarker Result
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class Freemarker extends ReadableResult<Freemarker> implements Resultable
{
	private String ftl;

	private Map<String, Object> result;

	/**
	 * 
	 * @param ftl
	 *            template file location
	 */
	public Freemarker(String ftl)
	{
		this.ftl = ftl;
	}

	/**
	 * @param flt
	 * @param root
	 */
	public Freemarker(String flt, Map<String, Object> root)
	{
		// if (this.result == null)
		// {
		this.result = root;
		// } else
		// {
		// 一般情况这个不会被运行的的
		// this.result.putAll(root);
		// }
	}

	public Freemarker put(String key, Object value)
	{
		if (this.result == null)
		{
			this.result = new HashMap<String, Object>();
		}
		result.put(key, value);
		return this;
	}

	public Freemarker celar()
	{
		if (this.result != null)
		{
			result.clear();
		}
		return this;
	}

	public Freemarker remove(String key)
	{
		if (this.result != null)
		{
			result.remove(key);
		}
		return this;
	}

	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return FreemarkerResultHandler.class;
	}

	public String getFtl()
	{
		return ftl;
	}

	public Map<String, Object> getResult()
	{
		return result;
	}

	void setResult(Map<String, Object> result)
	{
		this.result = result;
	}
}
