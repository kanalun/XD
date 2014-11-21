package com.xindian.mvc.result.img;

import java.awt.image.BufferedImage;

/**
 * @author Elva
 * @date 2011-1-22
 * @version 1.0
 */
public class PNG extends BufferedImageResult<PNG>
{
	public PNG(BufferedImage bufferedImage)
	{
		super(bufferedImage, "png");
		setContentType("image/png");
	}
}
