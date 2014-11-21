package com.xindian.mvc.result.img;

import java.awt.image.BufferedImage;

import com.xindian.mvc.result.AbstractResult;
import com.xindian.mvc.result.ResultHandler;
import com.xindian.mvc.result.Resultable;

public class BufferedImageResult<T> extends AbstractResult<BufferedImageResult<T>> implements Resultable
{
	BufferedImage bufferedImage;

	String encode;

	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return BufferedImageResultHandler.class;
	}

	public BufferedImageResult(BufferedImage bufferedImage, String encode)
	{
		this.bufferedImage = bufferedImage;
		this.encode = encode;
	}

	public BufferedImage getBufferedImage()
	{
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage)
	{
		this.bufferedImage = bufferedImage;
	}

	public String getEncode()
	{
		return encode;
	}

	public void setEncode(String encode)
	{
		this.encode = encode;
	}
}
