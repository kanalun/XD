package com.xindian.awaits.id;

import com.xindian.awaits.exception.AwaitsException;

public class IdentifierGenerationException extends AwaitsException
{
	private static final long serialVersionUID = 1L;

	public IdentifierGenerationException(String msg)
	{
		super(msg);
	}

	public IdentifierGenerationException(String msg, Throwable t)
	{
		super(msg, t);
	}
}
