package com.xindian.mvc.result;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.servlet.ServletException;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.exception.PowerlessException;

/**
 * 
 * @author Elva
 * @date 2011-1-18
 * @version 1.0
 */
public class SimpleHTMLResult extends ReadableResult<SimpleHTMLResult> implements Resultable, ResultHandler
{
	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return SimpleHTMLResult.class;
	}

	String html;

	public SimpleHTMLResult()
	{
	}

	public SimpleHTMLResult(String html)
	{
		this.html = html;
		setContentType("text/html");
	}

	public SimpleHTMLResult(File htmlFile)
	{
	}

	public SimpleHTMLResult(Reader htmlReader)
	{
	}

	public SimpleHTMLResult(InputStream htmlReader)
	{
	}

	@Override
	public void doResult(ActionContext actionContext, Object result) throws PowerlessException, IOException, ServletException
	{

	}

}
