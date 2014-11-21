package com.xindian.mvc.result;

public class StringResultProtocolParser implements ResultProtocolParser
{
	@Override
	public String getProtocolName()
	{
		return "string";
	}

	@Override
	public Resultable parse(String content)
	{
		return new StringResult(content);
	}
}
