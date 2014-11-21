package com.xindian.mvc.result;

/*
 import java.io.UnsupportedEncodingException;
 import java.lang.reflect.InvocationTargetException;
 import java.net.URLEncoder;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;*/

/**
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class Redirect extends AbstractURLResult<Redirect> implements Resultable
{
	public Redirect(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	public static Redirect redirect(String baseUrl)
	{
		return new Redirect(baseUrl);
	}

	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return ServletRedirectResultHandler.class;
	}

	// private String page;

	// private Map<String, String> map;

	// private List<Object> objects;

	/**
	 * <code>	
	 * 
	 * 	public Redirect addParameter(String name, String value)
	{
		if (map == null)
		{
			map = new HashMap<String, String>();
		}
		map.put(name, value);
		return this;
	}

	public Redirect removeParameter(String name)
	{
		if (map != null)
		{
			map.remove(name);
		}
		return this;
	}

	public Redirect addParameter(Object value)
	{
		try
		{
			Map<String, String> m = BeanUtils.describe(value);
			m.remove("class");
			if (map == null)
			{
				map = new HashMap<String, String>();
			}
			map.putAll(m);
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		return this;
	}

	public String getURL()
	{
		String p = null;
		try
		{
			String q = getQueryString(map, encodeChaset);
			if (q == null)
			{
				p = page;
			} else
			{
				p = page + q;
			}

		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return p;
	}

	private String encodeChaset;

	public Redirect setEncode(String chaset)
	{
		encodeChaset = chaset;
		return this;
	}

	public Redirect removeEncode()
	{
		encodeChaset = null;
		return this;
	}
	 * 
	 * @Override public Class<? extends ResultHandler> getResultHandler() {
	 *           return ServletRedirectResultHandler.class; }
	 * @Override public Object getResultValue() { return getURL(); }
	 * 
	 *           private static final String getQueryString(Map<String, String>
	 *           params, String encodeChaset) throws
	 *           UnsupportedEncodingException { if (params == null) { return
	 *           null; }
	 * 
	 *           StringBuffer queryString = new StringBuffer();
	 * 
	 *           for (Iterator<String> keys = params.keySet().iterator();
	 *           keys.hasNext();) { String key = (String) keys.next(); String
	 *           value = (String) params.get(key);
	 * 
	 *           if (key != null && value != null) { if (queryString.length() ==
	 *           0) { queryString.append("?"); } else { queryString.append("&");
	 *           } queryString.append(key); queryString.append("="); if
	 *           (encodeChaset != null && !encodeChaset.trim().equals("")) {
	 *           System.out.println(encodeChaset);
	 *           queryString.append(URLEncoder.encode(value, encodeChaset)); }
	 *           else { queryString.append(value); } } } return
	 *           queryString.toString(); } </code>
	 */
}
