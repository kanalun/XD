package com.xindian.mvc.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编码过滤器
 * 
 * @author cc
 * 
 */
public class EncodingFilter implements Filter
{
	private String encoding;

	private static Logger logger = LoggerFactory.getLogger(EncodingFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.encoding = filterConfig.getInitParameter("encoding");
		if (this.encoding == null)
		{
			this.encoding = "utf-8";
			logger.warn("FilterConfig 中没有对编码进行配置,系统使用默认的编码:[" + encoding + "]");
			// System.err.println("FilterConfig 中没有对编码进行配置,系统使用默认的编码:" +
			// encoding);
		}
		logger.info("编码设置为:[" + encoding + "]");
		// System.err.println("编码设置为:" + encoding);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
			ServletException
	{
		if (request.getCharacterEncoding() == null)
		{
			request.setCharacterEncoding(encoding);
			// logger.debug("编码设置为:" + encoding);
			// System.out.println("编码过滤:" + encoding);
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void destroy()
	{
		this.encoding = null;
	}
}