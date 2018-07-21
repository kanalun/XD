package com.xindian.mvc.exception;

/**
 * 服务器状态码错误
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class ErrorCodeException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	private int errorCode;

	public ErrorCodeException(int errorCode, String message)
	{
		super("ERROR CODE [" + errorCode + "] MESSAGE [ " + message + "]!");
		this.errorCode = errorCode;
	}

	public ErrorCodeException(int errorCode)
	{
		super("ERROR CODE [" + errorCode + "]!");
		this.errorCode = errorCode;
	}

	public int getErrorCode()
	{
		return errorCode;
	}
}
