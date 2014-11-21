package com.xindian.mvc.result.img;

import java.awt.image.BufferedImage;

public class JPEG extends BufferedImageResult<JPEG>
{
	public JPEG(BufferedImage bufferedImage)
	{
		super(bufferedImage, "jpeg");
		setContentType("image/jpeg");
	}
}
