package com.xindian.ioc.exception;

public class IOCException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public IOCException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public IOCException(Throwable cause)
	{
		super(cause);
	}

}
