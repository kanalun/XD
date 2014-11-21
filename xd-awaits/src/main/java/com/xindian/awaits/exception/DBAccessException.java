package com.xindian.awaits.exception;

/**
 * 数据库操作异常,对数据操作发生SQL,IO,等异常会被封装到该异常中
 * 
 * @author Elva
 * 
 */
public class DBAccessException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public DBAccessException(String message, Throwable exception)
	{
		super(message, exception);
	}

	public DBAccessException(Throwable exception)
	{
		super(exception);
	}

	public DBAccessException(String msg)
	{
		super(msg);
	}

	public DBAccessException()
	{
		super();
	}
}
