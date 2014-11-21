package com.xindian.mvc.result;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.exception.PowerlessException;

/**
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class StringResultHandler extends AbstractResultHandler implements ResultHandler
{
	@Override
	public void doResult(ActionContext actionContext, Object result) throws PowerlessException, IOException, ServletException
	{
		//
		super.doResult(actionContext, result);

		if (result instanceof StringResult)
		{
			// StringResult rs = (StringResult) result;

			HttpServletResponse response = ActionContext.getResponse();

			// 优先选择结果中的
			/**
			 * <code>			
 			if (Validator.notEmpty(rs.getCharacterEncoding()) && Charset.isSupported(rs.getCharacterEncoding()))
			{
				response.setCharacterEncoding(rs.getCharacterEncoding());
			} else
			{
				String charSet = actionContext.getEncoding();
				if (Validator.notEmpty(charSet) && Charset.isSupported(charSet))
				{
					response.setCharacterEncoding(charSet);
				}
			}
			if (rs.getContentType() != null)
			{
				response.setContentType(rs.getContentType());
			} else
			{
				response.setContentType("text/plain");
			}
			</code>
			 */
			response.getWriter().append(((StringResult) result).getValue()).flush();
		}
	}
}
