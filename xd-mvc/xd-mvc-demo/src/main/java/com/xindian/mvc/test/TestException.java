package com.xindian.mvc.test;

/**
 * 直接返回异常,需要在web.xml下捕获这个异常,指定发生这个异常之后的页面或动作
 * 
 * @author Elva
 * 
 */
public class TestException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9035099778983086783L;

	public TestException(String message)
	{
		super(message);
	}

}
