package com.xindian.mvc.result.freemarker;

import com.xindian.mvc.result.ResultProtocolParser;
import com.xindian.mvc.result.Resultable;

/**
 * Parser "ftl://content.ftl" to @see Freemarker(content)
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class FreemarkerResultProtocolParser implements ResultProtocolParser
{
	@Override
	public String getProtocolName()
	{
		return "ftl";
	}

	@Override
	public Resultable parse(String content)
	{
		return new Freemarker(content);
	}

}
