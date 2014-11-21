package com.xindian.mvc.annotation;

import com.xindian.mvc.result.ResultProtocolParser;

public @interface ResultProtocol
{
	String[] value();

	Class<? extends ResultProtocolParser> parser();
}
