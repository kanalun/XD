package com.xindian.mvc.validation;

/**
 * 
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ValidatorException extends RuntimeException
{
	public ValidatorException(String message)
	{
		super(message);
	}
}
