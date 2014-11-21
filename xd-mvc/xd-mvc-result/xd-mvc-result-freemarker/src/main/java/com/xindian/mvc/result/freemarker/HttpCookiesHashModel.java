package com.xindian.mvc.result.freemarker;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;

/**
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
@SuppressWarnings("serial")
public class HttpCookiesHashModel extends SimpleHash
{
	public HttpCookiesHashModel(ObjectWrapper objectWrapper, HttpServletRequest request)
	{
		super(objectWrapper);
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (Cookie ck : cookies)
			{
				put(ck.getName(), ck);
			}
		}
	}
}
