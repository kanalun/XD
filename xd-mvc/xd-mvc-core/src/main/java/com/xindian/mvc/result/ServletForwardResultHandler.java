package com.xindian.mvc.result;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.exception.PowerlessException;

/**
 * Use Servlet Forward to
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class ServletForwardResultHandler implements ResultHandler
{
	private static Logger logger = LoggerFactory.getLogger(ServletForwardResultHandler.class);

	@Override
	public void doResult(ActionContext actionContext, Object result) throws IOException, ServletException
	{
		Map<String, Object> attributes = null;

		logger.debug("ServletForwardResultHandler ...");
		if (result instanceof Forward)
		{
			attributes = ((Forward) result).getAttributes();
			result = ((Forward) result).getURL();
		}
		if (result instanceof String)
		{
			Object action = actionContext.getAction();
			Field[] fs = action.getClass().getDeclaredFields();
			if (fs != null)
			{
				logger.debug("ServletForwardResultHandler fs" + fs.length);
				for (Field f : fs)
				{
					try
					{
						// TODO 这样显然是不行的
						f.setAccessible(true);
						ActionContext.getRequest().setAttribute(f.getName(), f.get(action));
						System.out.println("KEY = " + f.getName() + "    VALUE = " + f.get(action));
						if (f.get(action) != null)
						{
							System.out.println(" TYPE =" + f.get(action).getClass());
						} else
						{
							System.out.println();
						}
					} catch (Exception e)
					{
						e.printStackTrace();
						continue;
					}
				}
			}
			// SET ATTRIBUTE
			if (attributes != null)
			{
				for (String name : attributes.keySet())
				{
					ActionContext.getRequest().setAttribute(name, attributes.get(name));
				}
			}
			ActionContext.getRequest().getRequestDispatcher((String) result)
					.forward(ActionContext.getRequest(), ActionContext.getResponse());
			return;
		} else
		{
			throw new PowerlessException("Can't Forward to:[" + result + "]");
		}
	}
}
