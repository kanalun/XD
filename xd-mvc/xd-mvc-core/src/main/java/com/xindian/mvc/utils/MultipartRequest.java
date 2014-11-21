package com.xindian.mvc.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

//COMMIN UP LOADing
public class MultipartRequest extends HttpServletRequestWrapper
{
	public MultipartRequest(HttpServletRequest request)
	{
		super(request);
	}
}
