package com.xindian.mvc.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;

import com.xindian.mvc.ActionContext;

/**
 * 给与{@link Resultable}"公用"的方法,这些方法往往跟返回结果有关 <br/>
 * 比如开/关浏览器缓存,设置编码,返回文档头信息和Cookie等
 * <p>
 * 我们也能在Response中(也可以利用{@link ActionContext}中的方法得到)来完成这些工作,<br/>
 * 于是就存优先级的问题问题了:谁会被谁覆盖?如果我在程序中预设(不一定使用:事实上,程序只能有一个返回结果)<br/>
 * 
 * 为了避免冲突和造成混乱,你需要记住两点:<br>
 * <li><strong>直接在Response中的设置,设置会立马会生效;</strong><br/>
 * 而Resultable中的需要等到这个结果被处理的时候才会有效 <br/>
 * (比如:你可能会希望返回不同的{@link Resultable} 在这些{@link Resultable}中设置不同的返回信息)<br/>
 * 这些方法设置之后不会立即添加到respones中去,而是存放在他的实例中,<br/>
 * 等到真正处理了这个返回结果的时候这些信息才会被添加到Response中去<br/>
 * <li><strong>Resultable中的设置优先级高于直接在Response设置</strong><br/>
 * 即:如果Resultable和Response设置了同一个"参数"的不同值时,
 * 
 * 系统会选择Resultable,而忽略Response<br/>
 * 
 * 
 * @author Elva
 * 
 * @param <T>
 *            子类类型,以便可以使用串联方法
 */
public abstract class AbstractResult<T extends AbstractResult<T>> implements Resultable
{
	private String contentType;

	// private String encoding;

	private String characterEncoding;

	private Map<String, Object> headers;

	private Integer contentLength;

	private Integer bufferSize;

	private Locale locale;

	private Integer status;

	private boolean browserCache = true;//

	private List<Cookie> cookies;

	public boolean containsHeader(String name)
	{
		if (headers == null)
		{
			return false;
		} else
		{
			return headers.containsKey(name);
		}
	}

	/*
	 * public String getEncoding() { return encoding; }
	 */

	@SuppressWarnings("unchecked")
	public T addCookie(Cookie cookie)
	{
		if (cookies == null)
		{
			cookies = new ArrayList<Cookie>();
		}
		cookies.add(cookie);
		return (T) this;
	}

	/*
	 * @SuppressWarnings("unchecked") public T setEncoding(String encoding) {
	 * this.encoding = encoding; return (T) this; }
	 */

	public String getContentType()
	{
		return contentType;
	}

	@SuppressWarnings("unchecked")
	public T setContentType(String contentType)
	{
		this.contentType = contentType;
		return (T) this;
	}

	public String getCharacterEncoding()
	{
		return characterEncoding;
	}

	@SuppressWarnings("unchecked")
	public T setCharacterEncoding(String characterEncoding)
	{
		this.characterEncoding = characterEncoding;
		return (T) this;
	}

	public Map<String, Object> getHeaders()
	{
		return headers;
	}

	public List<Cookie> getCookies()
	{
		return cookies;
	}

	public void setCookies(List<Cookie> cookies)
	{
		this.cookies = cookies;
	}

	/***************** Header *******************/

	@SuppressWarnings("unchecked")
	public T addHeader(String name, String value)
	{
		checkHeader();
		headers.put(name, value);
		return (T) this;
	}

	public T setHeader(String name, String value)
	{
		checkHeader();
		return addHeader(name, value);
	}

	@SuppressWarnings("unchecked")
	public T removeHeader(String name)
	{
		if (headers != null)
		{
			headers.remove(name);
		}
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T addDateHeader(String name, long value)
	{
		checkHeader();
		headers.put(name, value);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T setDateHeader(String name, long value)
	{
		checkHeader();
		headers.put(name, value);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T setIntHeader(String name, int value)
	{
		checkHeader();
		headers.put(name, value);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T addIntHeader(String name, int value)
	{
		checkHeader();
		headers.put(name, value);
		return (T) this;

	}

	// prevent null
	private void checkHeader()
	{
		if (headers == null)
		{
			headers = new HashMap<String, Object>();
		}
	}

	/***************** End of Header *******************/

	@SuppressWarnings("unchecked")
	public T setStatus(int sc)
	{
		status = sc;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T setHeaders(Map<String, Object> headers)
	{
		this.headers = headers;
		return (T) this;
	}

	public Integer getContentLength()
	{
		return contentLength;
	}

	@SuppressWarnings("unchecked")
	public T setContentLength(Integer contentLength)
	{
		this.contentLength = contentLength;
		return (T) this;
	}

	public Integer getBufferSize()
	{
		return bufferSize;
	}

	@SuppressWarnings("unchecked")
	public T setBufferSize(Integer bufferSize)
	{
		this.bufferSize = bufferSize;
		return (T) this;
	}

	public Locale getLocale()
	{
		return locale;
	}

	@SuppressWarnings("unchecked")
	public T setLocale(Locale locale)
	{
		this.locale = locale;
		return (T) this;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	/**
	 * 设置浏览器缓存是否开启
	 * 
	 * @param browserCache
	 *            true为开启缓存,false为关闭浏览器缓存
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T setBrowserCache(boolean browserCache)
	{
		this.browserCache = browserCache;
		return (T) this;
	}

	/**
	 * 浏览器缓存是否开启
	 * 
	 * @return true为开启缓存,false为关闭浏览器缓存
	 * 
	 */
	public boolean isBrowserCache()
	{
		return browserCache;
	}

	/**
	 * 关闭浏览器缓存相当于 @see setBrowserCache(false)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T closeBrowserCache()
	{
		this.browserCache = false;
		return (T) this;
	}

}
