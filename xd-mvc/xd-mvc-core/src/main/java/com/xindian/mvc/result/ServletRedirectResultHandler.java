package com.xindian.mvc.result;

import java.io.IOException;

import javax.servlet.ServletException;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.exception.PowerlessException;

/**
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class ServletRedirectResultHandler implements ResultHandler
{
	@Override
	public void doResult(ActionContext actionContext, Object result) throws IOException, ServletException
	{
		if (result instanceof Redirect)
		{
			result = ((Redirect) result).getURL();
		}
		if (result instanceof String)
		{
			ActionContext.getResponse().sendRedirect(((String) result));
			return;
		} else
		{
			throw new PowerlessException("Can't REDIRECT to:[" + result + "]");
		}
	}
}
