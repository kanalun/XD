package com.xindian.mvc.exception;

@SuppressWarnings("serial")
public class ForbiddenException extends ErrorCodeException
{
	public ForbiddenException(Integer errorCode, String message)
	{
		super(errorCode, message);
	}

	public ForbiddenException(Integer errorCode)
	{
		super(errorCode);
	}

	public ForbiddenException()
	{
		super(403);
	}

}
