package com.xindian.mvc.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Request handling utility class.
 */
public class RequestUtils
{
	/**
	 * Retrieves the current request servlet path. Deals with differences
	 * between servlet specs (2.2 vs 2.3+)
	 * 
	 * @param request
	 *            the request
	 * @return the servlet path
	 */
	public static String getServletPath(HttpServletRequest request)
	{
		String servletPath = request.getServletPath();

		String requestUri = request.getRequestURI();
		// Detecting other characters that the servlet container cut off (like
		// anything after ';')
		if (requestUri != null && servletPath != null && !requestUri.endsWith(servletPath))
		{
			int pos = requestUri.indexOf(servletPath);
			if (pos > -1)
			{
				servletPath = requestUri.substring(requestUri.indexOf(servletPath));
			}
		}
		if (null != servletPath && !"".equals(servletPath))
		{
			return servletPath;
		}

		int startIndex = request.getContextPath().equals("") ? 0 : request.getContextPath().length();
		int endIndex = request.getPathInfo() == null ? requestUri.length() : requestUri.lastIndexOf(request.getPathInfo());

		if (startIndex > endIndex)
		{ // this should not happen
			endIndex = startIndex;
		}

		return requestUri.substring(startIndex, endIndex);
	}

	/**
	 * isMultipart
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isMultipart(HttpServletRequest request)
	{
		return ((request.getContentType() != null) && (request.getContentType().toLowerCase().startsWith("multipart")));
	}
}
