package com.xindian.mvc.result.rss;

import com.xindian.mvc.result.ResultHandler;
import com.xindian.mvc.result.ResultProtocolParser;
import com.xindian.mvc.result.Resultable;

public class RSSResult implements Resultable, ResultProtocolParser
{
	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return null;
	}

	public RSSResult()
	{

	}

	public void addChannel()
	{

	}

	@Override
	public String getProtocolName()
	{
		// "RSS://a.xml"
		return null;
	}

	@Override
	public Resultable parse(String content)
	{
		return null;
	}
}
