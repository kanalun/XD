package com.xindian.mvc.cache;

/**
 * Something went wrong in the cache
 * 
 * @author liudong
 */
@SuppressWarnings("serial")
public class CacheException extends RuntimeException
{
	public CacheException(String s)
	{
		super(s);
	}

	public CacheException(String s, Throwable e)
	{
		super(s, e);
	}

	public CacheException(Throwable e)
	{
		super(e);
	}
}
