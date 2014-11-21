package com.xindian.mvc.exception;

/**
 * 服务器状态码错误
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ErrorCodeException extends RuntimeException
{
	private int errorCode;

	private String message;

	public ErrorCodeException(int errorCode, String message)
	{
		super("ERROR CODE [" + errorCode + "] MESSAGE [ " + message + "]!");
		this.errorCode = errorCode;
		this.message = message;
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

	public String getMessage()
	{
		return message;
	}
}
