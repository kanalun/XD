package com.xindian.commons.i18n;

public class ConstantsInvocationException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ConstantsInvocationException(Throwable e)
	{
		super(e);
	}

	public ConstantsInvocationException(String message, Throwable e)
	{
		super(message, e);
	}

	public ConstantsInvocationException(String string)
	{
		super(string);
	}
}
