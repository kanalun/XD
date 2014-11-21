package com.xindian.mvc.exception;

/**
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class MVCException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public MVCException(Throwable e)
	{
		super(e);
	}

	public MVCException(String message, Throwable e)
	{
		super(message, e);
	}

	public MVCException(String string)
	{
		super(string);
	}
}
