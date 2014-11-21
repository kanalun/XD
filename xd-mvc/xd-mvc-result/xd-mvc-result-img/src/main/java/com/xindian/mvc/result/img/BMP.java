package com.xindian.mvc.result.img;

import java.awt.image.BufferedImage;

/**
 * 
 * @author Elva
 * @date 2011-1-22
 * @version 1.0
 */
public class BMP extends BufferedImageResult<BMP>
{
	public BMP(BufferedImage bufferedImage)
	{
		super(bufferedImage, "bmp");
		setContentType("image/bmp");
	}
}
