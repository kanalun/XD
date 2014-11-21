package com.xindian.mvc.result;

/**
 * Returns a String to the client
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
public class StringResult extends ReadableResult<StringResult> implements Resultable
{
	private StringBuffer value;

	// private String contentType;

	// private String encoding;

	public StringResult(CharSequence string)
	{
		setContentType("text/plain");
		append(string);
	}

	public StringResult()
	{
		setContentType("text/plain");
	}

	/**
	 * Append some char sequence to be sent to the client
	 * 
	 * @param string
	 * @return
	 */
	public StringResult append(CharSequence string)
	{
		if (value == null)
		{
			value = new StringBuffer(string.length());
		}
		value.append(string);
		return this;
	}

	public CharSequence getValue()
	{
		return value;
	}

	public StringResult setValue(CharSequence value)
	{
		this.value = new StringBuffer(value);
		return this;
	}

	/**
	 * 
	 <code>	
	public String getEncoding()
	{
		return encoding;
	}

	public StringResult setEncoding(String encoding)
	{
		// Charset.isSupported(encoding);
		this.encoding = encoding;
		return this;
	}

	public String getContentType()
	{
		return contentType;
	}

	public StringResult setContentType(String contentType)
	{
		this.contentType = contentType;
		return this;
	}
	</code>
	 */

	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return StringResultHandler.class;
	}
}
