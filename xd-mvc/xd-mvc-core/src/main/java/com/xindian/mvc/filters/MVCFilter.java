package com.xindian.mvc.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class MVCFilter
 */
public class MVCFilter implements Filter
{

	/**
	 * Default constructor.
	 */
	public MVCFilter()
	{
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy()
	{
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{
		// HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		// System.out.println("nameNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNnn");
		// httpServletRequest.getSession().setAttribute("name",
		// "NNNNNNNNNNNNNn");
		/*
		 * COSMultipartRequest multipartRequest = new
		 * COSMultipartRequest(httpServletRequest, "D:\\temp", 1024 * 1024,
		 * "UTF-8");
		 * 
		 * String name = multipartRequest.getParameter("fileName");
		 * 
		 * Enumeration<String> fileNames = multipartRequest.getFileNames();
		 * 
		 * while (fileNames.hasMoreElements()) { String fileName =
		 * fileNames.nextElement(); System.out.println("	fileName = " +
		 * fileName); System.out.println("	OriginalFileName = " +
		 * multipartRequest.getOriginalFileName(fileName));
		 * System.out.println("	ContentType = " +
		 * multipartRequest.getContentType(fileName));
		 * System.out.println("	FilesystemName = " +
		 * multipartRequest.getFilesystemName(fileName));
		 * System.out.println("	NOWIN = " +
		 * multipartRequest.getFile(fileName).getAbsolutePath());
		 * System.out.println(); } Map<String, Object> map =
		 * multipartRequest.getParameterMap();
		 * 
		 * Iterator<String> ks = map.keySet().iterator(); while (ks.hasNext()) {
		 * String s = ks.next(); System.out.println("	KEY = " + s);
		 * System.out.println("	VALUE = " + map.get(s)); }
		 */

		// File file = multipartRequest.getFile("file");
		// if (file != null && name != null)
		{
			// file.renameTo(new File("D:\\temp\\" + name + ".jpg"));
		}
		// System.out.println("FILENAME = " + name);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
		// TODO Auto-generated method stub
	}

}
