package com.xindian.mvc.result;

import java.io.IOException;

import javax.servlet.ServletException;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.exception.ErrorCodeException;
import com.xindian.mvc.exception.PowerlessException;

/**
 * deal with ERROR
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class ErrorResultHandler implements ResultHandler
{
	@Override
	public void doResult(ActionContext actionContext, Object result) throws IOException, ServletException
	{
		if (result instanceof ErrorResult)
		{
			result = ((ErrorResult) result).getErrorCodeException();
		}
		if (result instanceof ErrorCodeException)
		{
			if (((ErrorCodeException) result).getMessage() != null)
			{
				ActionContext.getResponse().sendError(((ErrorCodeException) result).getErrorCode(),
						((ErrorCodeException) result).getMessage());
			} else
			{
				ActionContext.getResponse().sendError(((ErrorCodeException) result).getErrorCode());
			}
			return;
		} else if (result instanceof RuntimeException)
		{
			// TODO DO LOG
			((RuntimeException) result).printStackTrace();
			throw (RuntimeException) result;
		} else
		{
			throw new PowerlessException("ErrorResultHandler Can't handle error:[" + result + "]");
		}
	}
}
