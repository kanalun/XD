package com.xindian.mvc.result.img;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import com.xindian.mvc.result.ResultDecoder;

public class BufferedImageResultDecoder implements ResultDecoder<BufferedImage>
{
	@Override
	public InputStream decode(BufferedImage object)
	{
		return null;
	}
}
