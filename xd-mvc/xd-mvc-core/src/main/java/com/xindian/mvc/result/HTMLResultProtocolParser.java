package com.xindian.mvc.result;

import com.xindian.mvc.exception.PowerlessException;

/**
 * html://
 * 
 * @author Elva
 * @date 2011-1-18
 * @version 1.0
 */
public class HTMLResultProtocolParser implements ResultProtocolParser
{
	@Override
	public String getProtocolName()
	{
		return "html";
	}

	@Override
	public Resultable parse(String content) throws PowerlessException
	{
		return null;
	}

}
