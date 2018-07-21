package com.xindian.commons.beanutils;

public class UnnameException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public UnnameException(Throwable e)
	{
		super(e);
	}

	public UnnameException(String message, Throwable e)
	{
		super(message, e);
	}

	public UnnameException(String string)
	{
		super(string);
	}
}
