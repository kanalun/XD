package com.xindian.mvc.exception;

/**
 * 当无法创建/实例化Action的时候抛出该异常
 * 
 * @author Elva
 * @date 2011-1-27
 * @version 1.0
 */
public class BuildActionException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public BuildActionException(String string)
	{
		super(string);
	}

	public BuildActionException(String string, Throwable e)
	{
		super(string, e);
	}

}
