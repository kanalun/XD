package com.xindian.mvc.result;

import com.xindian.mvc.exception.PowerlessException;

/**
 * 协议解析器就是,将协议转换成Resultable的玩意儿!
 * 
 * @author Elva
 * 
 */
public interface ResultProtocolParser
{
	Resultable parse(String content) throws PowerlessException;

	String getProtocolName();
}
