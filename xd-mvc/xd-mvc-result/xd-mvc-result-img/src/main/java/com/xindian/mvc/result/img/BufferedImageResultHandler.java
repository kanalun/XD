package com.xindian.mvc.result.img;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.exception.PowerlessException;
import com.xindian.mvc.result.ResultHandler;

/**
 * 
 * @author Elva
 * @date 2011-1-19
 * @version 1.0
 */
public class BufferedImageResultHandler implements ResultHandler
{
	@Override
	public void doResult(ActionContext actionContext, Object result) throws PowerlessException, IOException, ServletException
	{
		String encode = "jpg";

		if (result instanceof BufferedImageResult<?>)
		{
			if (encode == null)
			{
				encode = "jpg";// FIXME 我在干嘛?
			}
			result = ((BufferedImageResult<?>) result).getBufferedImage();
		}
		if (result instanceof BufferedImage)
		{
			HttpServletResponse response = ActionContext.getResponse();
			ImageIO.write((BufferedImage) result, encode, response.getOutputStream());
		}
	}
}
