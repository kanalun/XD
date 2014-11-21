package com.xindian.mvc.result;

import java.io.InputStream;

/**
 * 结果解码器
 * 
 * @author Elva
 * @date 2011-1-18
 * @version 1.0
 */
public interface ResultDecoder<T>
{
	InputStream decode(T object);
}
