package com.xindian.mvc.result.velocity;

import com.xindian.mvc.exception.PowerlessException;
import com.xindian.mvc.result.ResultProtocolParser;
import com.xindian.mvc.result.Resultable;

public class VelocityResultProtocolParser implements ResultProtocolParser
{
	@Override
	public String getProtocolName()
	{
		return "vm";
	}

	@Override
	public Resultable parse(String content) throws PowerlessException
	{
		return new Velocity(content);
	}
}
