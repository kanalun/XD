package com.xindian.mvc.result;

/**
 * forward://page.jsp
 * 
 * @author Elva
 * @date 2011-1-18
 * @version 1.0
 */
public class ForwardProtocolParser implements ResultProtocolParser
{
	@Override
	public String getProtocolName()
	{
		return "forward";
	}

	@Override
	public Resultable parse(String content)
	{
		return new Forward(content);
	}

}
