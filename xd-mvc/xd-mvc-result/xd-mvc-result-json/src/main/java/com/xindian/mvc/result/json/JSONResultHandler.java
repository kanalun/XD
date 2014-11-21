package com.xindian.mvc.result.json;

import java.io.IOException;

import javax.servlet.ServletException;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.result.ResultHandler;

public class JSONResultHandler implements ResultHandler
{
	@SuppressWarnings("static-access")
	@Override
	public void doResult(ActionContext actionContext, Object result) throws IOException, ServletException
	{
		if (result instanceof JSONResult)
		{
			String s = JsonUtil.object2json(((JSONResult) result).getObject());
			actionContext.getResponse().setContentType("application/json");
			actionContext.getResponse().getWriter().append(s).flush();
		}
	}
}
