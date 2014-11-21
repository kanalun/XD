package com.xindian.mvc.result;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Elva
 * @param <T>
 */
public abstract class AbstractURLResult<T extends AbstractURLResult<?>> extends AbstractResult<AbstractURLResult<T>> implements
		Resultable
{
	private String urlEncodingCharset;// 查询字符串编码

	protected String baseUrl;// 基本URL

	private Map<String, String> map;// 参数

	private List<Object> objects;// TODO 参数对象

	/**
	 * 为URL添加查询字符串
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T addParameter(String name, String value)
	{
		if (map == null)
		{
			map = new HashMap<String, String>();
		}
		map.put(name, value);
		return (T) this;
	}

	/**
	 * 删除URL中的字符串
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T removeParameter(String name)
	{
		if (map != null)
		{
			map.remove(name);
		}
		return (T) this;
	}

	/*	*//**
	 * 为URL添加参数.bean 中的包含getter/setter,的非null的值会被
	 * 
	 * 这个方法现在可能有些问题,请避免使用<br/>
	 * 
	 * @param value
	 * @return
	 */
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Deprecated public T addParameter(Object bean) { try { Map<String,
	 * String> m = BeanUtils.describe(bean); m.remove("class"); if (map == null)
	 * { map = new HashMap<String, String>(); } map.putAll(m); } catch
	 * (IllegalAccessException e) { e.printStackTrace(); } catch
	 * (InvocationTargetException e) { e.printStackTrace(); } catch
	 * (NoSuchMethodException e) { e.printStackTrace(); } return (T) this; }
	 */

	/**
	 * 得到整个URL
	 * 
	 * @return
	 */
	public String getURL()
	{
		String p = null;
		try
		{
			String q = getQueryString(map, urlEncodingCharset);
			if (q == null)
			{
				p = baseUrl;
			} else
			{
				p = baseUrl + q;
			}

		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return p;
	}

	/**
	 * 
	 * @return
	 */
	public String getBaseUrl()
	{
		return baseUrl;
	}

	@SuppressWarnings("unchecked")
	public T setBaseUrl(String baseUrl)
	{
		this.baseUrl = baseUrl;
		return (T) this;
	}

	/**
	 * 对参数编码
	 * 
	 * @param chaset
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T setURLEncode(String chaset)
	{
		urlEncodingCharset = chaset;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T removeURLEncode()
	{
		urlEncodingCharset = null;
		return (T) this;
	}

	protected static final String getQueryString(Map<String, String> params, String encodeChaset)
			throws UnsupportedEncodingException
	{
		if (params == null)
		{
			return null;
		}
		StringBuffer queryString = new StringBuffer();
		for (Iterator<String> keys = params.keySet().iterator(); keys.hasNext();)
		{
			String key = (String) keys.next();
			String value = (String) params.get(key);

			if (key != null && value != null)
			{
				if (queryString.length() == 0)
				{
					queryString.append("?");
				} else
				{
					queryString.append("&");
				}
				queryString.append(key);
				queryString.append("=");
				if (encodeChaset != null && !encodeChaset.trim().equals(""))
				{
					queryString.append(URLEncoder.encode(value, encodeChaset));
				} else
				{
					queryString.append(value);
				}
			}
		}
		return queryString.toString();
	}
}
