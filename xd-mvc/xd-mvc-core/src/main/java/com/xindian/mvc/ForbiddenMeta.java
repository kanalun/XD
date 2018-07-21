package com.xindian.mvc;

import com.xindian.mvc.annotation.Forbidden;
import com.xindian.mvc.exception.ErrorCodeException;

/**
 * @author Elva
 * @date 2011-3-9
 * @version 1.0
 */
public class ForbiddenMeta
{
	int code;

	Class<? extends ErrorCodeException> exception;

	String message;

	public ForbiddenMeta(Forbidden forbidden)
	{
		code = forbidden.value();
		exception = forbidden.exception();
		message = forbidden.message();
	}
}
