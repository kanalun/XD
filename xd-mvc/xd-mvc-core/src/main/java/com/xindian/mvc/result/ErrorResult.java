package com.xindian.mvc.result;

import com.xindian.mvc.annotation.ResultProtocol;
import com.xindian.mvc.exception.ErrorCodeException;

/**
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
@ResultProtocol(value = { "error", "ERR" }, parser = ResultProtocolParser.class)
public class ErrorResult implements Resultable
{
	private ErrorCodeException errorCodeException;

	public ErrorResult(int code)
	{
		this.errorCodeException = new ErrorCodeException(code);
	}

	public ErrorResult(int code, String message)
	{
		this.errorCodeException = new ErrorCodeException(code, message);
	}

	public ErrorResult(ErrorCodeException errorCodeException)
	{
		this.errorCodeException = errorCodeException;
	}

	/**
	 * 
	 * @return
	 */
	public ErrorCodeException getErrorCodeException()
	{
		return errorCodeException;
	}

	/**
	 * 
	 * @return
	 */
	public int getErrorCode()
	{
		return this.errorCodeException.getErrorCode();
	}

	/**
	 * 
	 * @return
	 */
	public String getMessage()
	{
		return this.errorCodeException.getMessage();
	}

	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return ErrorResultHandler.class;
	}
}
