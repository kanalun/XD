package com.xindian.mvc.result;

import java.util.HashMap;
import java.util.Map;

/**
 * forward, FWD
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class Forward extends AbstractURLResult<Forward> implements Resultable
{

	private Map<String, Object> attributes;

	public Forward(String baseUrl)
	{
		setBaseUrl(baseUrl);
	}

	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return ServletForwardResultHandler.class;
	}

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public Forward addAttribute(String name, Object value)
	{
		checkAttributes();
		attributes.put(name, value);
		return this;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public Forward removeAttribute(String name)
	{
		if (attributes != null)
		{
			attributes.remove(name);
		}
		return this;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name)
	{
		if (attributes != null)
		{
			return attributes.get(name);
		}
		return null;
	}

	//
	private void checkAttributes()
	{
		if (attributes == null)
		{
			attributes = new HashMap<String, Object>();
		}
	}

	public Map<String, Object> getAttributes()
	{
		return attributes;
	}

	void setAttributes(Map<String, Object> attributes)
	{
		this.attributes = attributes;
	}
}
