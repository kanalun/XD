package com.xindian.mvc.result;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.exception.ResultException;

/**
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class StreamResultHandler extends AbstractResultHandler implements ResultHandler
{
	protected static int DEFAULT_BUFFER_SIZE = 1024;

	@Override
	public void doResult(ActionContext actionContext, Object result) throws IOException, ServletException
	{
		super.doResult(actionContext, result);

		Integer bufferSize = null;
		OutputStream out = null;
		InputStream in = null;

		if (result instanceof Stream)
		{
			Stream s = ((Stream) result);
			bufferSize = s.getBufferSize();
			//
			//
			result = s.getStream();
		}
		if (bufferSize == null || bufferSize <= 0)
		{
			bufferSize = DEFAULT_BUFFER_SIZE;
		}
		if (result instanceof InputStream)
		{
			try
			{
				out = ActionContext.getResponse().getOutputStream();
				in = ((InputStream) result);
				byte[] oBuff = new byte[bufferSize];
				int iSize;
				while (-1 != (iSize = in.read(oBuff)))
				{
					out.write(oBuff, 0, iSize);
				}
			} catch (IOException e)
			{
				e.printStackTrace();
				throw e;
			} finally
			{
				if (in != null)
				{
					in.close();
				}
				if (out != null)
				{
					out.close();
				}
			}
		} else
		{
			throw new ResultException("Acction need return an InputStream!");
		}
	}
}
