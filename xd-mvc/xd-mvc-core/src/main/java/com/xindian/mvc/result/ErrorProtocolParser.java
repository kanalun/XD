package com.xindian.mvc.result;

import com.xindian.mvc.exception.PowerlessException;

/**
 * 
 * error://400<br/>
 * 
 * error://400,message
 * 
 * @author Elva
 * @date 2011-1-18
 * @version 1.0
 */
public class ErrorProtocolParser implements ResultProtocolParser
{
	@Override
	public String getProtocolName()
	{
		return "error";
	}

	@Override
	public Resultable parse(String content) throws PowerlessException
	{
		try
		{
			int code;
			String message;
			int m = content.indexOf(",");
			if (m == -1)
			{
				code = Integer.parseInt(content);
				return new ErrorResult(code);
			} else
			{
				code = Integer.parseInt(content.substring(0, m));
				message = content.substring(m + 1);
				return new ErrorResult(code, message);
			}
		} catch (Exception e)
		{
			throw new PowerlessException(e);
		}
	}
}
