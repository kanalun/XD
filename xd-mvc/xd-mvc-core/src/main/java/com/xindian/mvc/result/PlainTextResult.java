package com.xindian.mvc.result;

import java.io.Reader;

/**
 * "字符流"?
 * 
 * @author Elva
 * 
 */
public class PlainTextResult extends ReadableResult<PlainTextResult> implements Resultable
{
	private Reader reader;

	private String decoding = "UTF-8";// 默认文本解码,

	public PlainTextResult(Reader reader)
	{
		this.reader = reader;
		setContentType("text/plain");
	}

	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return PlainTextResultHandler.class;
	}

	public Reader getReader()
	{
		return reader;
	}

	public void setReader(Reader reader)
	{
		this.reader = reader;
	}

	public String getDecoding()
	{
		return decoding;
	}

	public PlainTextResult setDecoding(String decoding)
	{
		this.decoding = decoding;
		return this;
	}
}
