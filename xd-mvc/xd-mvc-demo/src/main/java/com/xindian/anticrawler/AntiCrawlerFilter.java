package com.xindian.anticrawler;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AnticrawlerFilter
 */
public class AntiCrawlerFilter implements Filter
{

	/**
	 * Default constructor.
	 */
	public AntiCrawlerFilter()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy()
	{
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		Enumeration headerNames = httpServletRequest.getHeaderNames();
		/*
		 * while (headerNames.hasMoreElements()) { String name = (String)
		 * headerNames.nextElement(); System.out.println(name + " : " +
		 * httpServletRequest.getHeader(name));
		 * 
		 * } String userAgent = httpServletRequest.getHeader("user-Agent");
		 * System.out.println("AuthType : " + httpServletRequest.getAuthType());
		 * System.out.println("CharacterEncoding : " +
		 * httpServletRequest.getCharacterEncoding());
		 * System.out.println("ContentLength : " +
		 * httpServletRequest.getContentLength());
		 * System.out.println("Locale : " + httpServletRequest.getLocale());
		 * System.out.println("Method : " + httpServletRequest.getMethod());
		 * System.out.println("RemoteHost : " +
		 * httpServletRequest.getRemoteHost()); System.out.println("Protocol : "
		 * + httpServletRequest.getProtocol());
		 * System.out.println("RemotePort : " +
		 * httpServletRequest.getRemotePort());
		 * System.out.println("RequestedSessionId : " +
		 * httpServletRequest.getRequestedSessionId());
		 * System.out.println("ContentType : " +
		 * httpServletRequest.getContentType());
		 * System.out.println("RemoteUser : " +
		 * httpServletRequest.getRemoteUser());
		 */
		String ip = httpServletRequest.getRemoteHost();

		IpBlacklist ipBlacklist = (IpBlacklist) servletContext.getAttribute("balckList");
		if (ipBlacklist == null)
		{
			ipBlacklist = new IpBlacklist();
			servletContext.setAttribute("balckList", ipBlacklist);
		}
		if (ipBlacklist.isInIpBackList(ip))
		{
			((HttpServletResponse) response).sendError(403);
		} else
		{
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	ServletContext servletContext;

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
		servletContext = fConfig.getServletContext();
	}

}
