package com.xindian.mvc.i18n3;

//TODO 临时使用的一个异常
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
