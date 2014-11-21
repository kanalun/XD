package com.xindian.mvc.result;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.xindian.mvc.ActionContext;

public class PlainTextResultHandler extends AbstractResultHandler implements ResultHandler
{
	private static final String DEFAULT_CHARSET = "UTF-8";

	private static final int DEFAULT_BUFFER_SIZE = 1024;

	@Override
	public void doResult(ActionContext actionContext, Object result) throws IOException, ServletException
	{
		super.doResult(actionContext, result);

		String decoding = null;

		if (result instanceof PlainTextResult)
		{
			// 必须在result被冲掉前读取相关信息
			decoding = ((PlainTextResult) result).getDecoding();

			// 把result换传成Reader,交给下面的代码
			result = ((PlainTextResult) result).getReader();
		}
		if (result instanceof Reader)
		{
			HttpServletResponse response = ActionContext.getResponse();
			/**
			 * <code>			
			String charsetName = actionContext.getEncoding();

			if (charsetName != null && Charset.isSupported(charsetName))
			{
				response.setContentType("text/plain; charset=" + charsetName);
			} else
			{
				response.setContentType("text/plain");
			}</code>
			 */
			response.setHeader("Content-Disposition", "inline");

			Writer writer = response.getWriter();
			Reader reader = null;
			try
			{
				/**
				 * <code>	
				if (decoding != null)
				{
					reader = new InputStreamReader(ActionContext.getServletContext().getResourceAsStream(finalLocation), decoding);
				} else
				{
					reader = new InputStreamReader(ActionContext.getServletContext().getResourceAsStream(finalLocation));
				}<code>
				 */

				if (decoding != null)
				{
					// reader = new FileReader();

				} else
				{
					// reader = (Reader) result;
				}
				reader = (Reader) result;
				//
				if (reader == null)
				{
					throw new IOException("reader is null!!");
				} else
				{
					char[] buffer = new char[DEFAULT_BUFFER_SIZE];
					int charRead = 0;
					while ((charRead = reader.read(buffer)) != -1)
					{
						writer.write(buffer, 0, charRead);
					}
				}
			} finally
			{
				if (reader != null)
					reader.close();
				if (writer != null)
				{
					writer.flush();
					writer.close();
				}
			}
		}
	}
}
