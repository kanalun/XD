package com.xindian.mvc.exception;

/**
 * 当无法创建/实例化Action的时候抛出该异常
 * 
 * @author Elva
 * @date 2011-1-27
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BuildActionException extends RuntimeException
{
	public BuildActionException(String string)
	{
		super(string);
	}

	public BuildActionException(String string, Throwable e)
	{
		super(string, e);
	}

}
