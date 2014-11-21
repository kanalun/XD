package com.xindian.mvc.result;

public class RedirectProtocolParser implements ResultProtocolParser
{
	@Override
	public String getProtocolName()
	{
		return "redirect";
	}

	@Override
	public Resultable parse(String content)
	{
		return new Redirect(content);
	}

}
