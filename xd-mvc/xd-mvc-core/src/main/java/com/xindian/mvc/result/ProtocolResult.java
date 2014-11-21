package com.xindian.mvc.result;

import java.io.IOException;

import javax.servlet.ServletException;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.exception.ErrorCodeException;
import com.xindian.mvc.exception.PowerlessException;

/**
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class ProtocolResult implements Resultable, ResultHandler
{
	private static ProtocolParserFactory parser = ProtocolParserFactory.getSingleton();

	private static ResultHandlerFactory resultHandlerFactory = ResultHandlerFactory.getSingleton();

	private Resultable resultable;

	public ProtocolResult(String protocol, String value)
	{
		resultable = parser.parseResultable(protocol, value);
	}

	// TODO
	public ProtocolResult(String protocol, Object... values)
	{
		for (Object o : values)
		{
			System.out.println("TYPE" + o.getClass());
		}
	}

	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return ProtocolResult.class;
	}

	@Override
	public void doResult(ActionContext actionContext, Object result) throws PowerlessException, IOException, ServletException
	{
		if (result instanceof ProtocolResult)
		{
			Resultable rs = ((ProtocolResult) result).resultable;
			if (rs != null)
			{
				resultHandlerFactory.getResultHandler(rs.getHandler()).doResult(actionContext, rs);
			} else
			{
				throw new ErrorCodeException(500, "无法解析协议:[" + result + "]");
			}
		} else
		{
			throw new PowerlessException("ProtocolResult ERROR");
		}

	}
}
