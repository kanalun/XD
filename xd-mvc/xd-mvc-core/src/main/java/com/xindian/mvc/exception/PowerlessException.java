package com.xindian.mvc.exception;

/**
 * 这个异常的语意是"无能为力",<br/>
 * 
 * 表示"鸭力很大,无法处理"时抛出一这个异常
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class PowerlessException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public PowerlessException(Throwable e)
	{
		super(e);
	}

	public PowerlessException(String message)
	{
		super(message);
	}

	public PowerlessException(String message, Throwable t)
	{
		super(message, t);
	}
}
