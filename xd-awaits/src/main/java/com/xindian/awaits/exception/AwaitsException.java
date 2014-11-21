package com.xindian.awaits.exception;

public class AwaitsException extends RuntimeException
{
	private static final long serialVersionUID = 6102398530412193596L;

	public AwaitsException(String message)
	{
		super(message);
	}

	public AwaitsException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
}
