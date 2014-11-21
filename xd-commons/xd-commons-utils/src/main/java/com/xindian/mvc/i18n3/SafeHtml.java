package com.xindian.mvc.i18n3;

import com.xindian.mvc.utils.TextUtils;

/**
 * 将HTML标记转码,保证文本在HTML中显示为原来,
 * 
 * TODO 对于空格我们应该怎么处理?
 * 
 * Escape HTML
 * 
 * @author Elva
 * @date 2011-2-6
 * @version 1.0
 */
public class SafeHtml implements CharSequence
{
	private CharSequence charSequence;

	private String escapedXml;

	public String getSafeHtml()
	{
		if (escapedXml == null)
		{
			escapedXml = TextUtils.escapeXml(charSequence.toString());
		}
		return escapedXml;
	}

	/**
	 * 
	 * @return
	 */
	public String getNonSafeHtml()
	{
		return charSequence.toString();
	}

	public SafeHtml(CharSequence charSequence)
	{
		this.charSequence = charSequence;
	}

	@Override
	public String toString()
	{
		return getSafeHtml();
	}

	@Override
	public char charAt(int index)
	{
		return getSafeHtml().charAt(index);
	}

	@Override
	public int length()
	{
		return getSafeHtml().length();
	}

	@Override
	public CharSequence subSequence(int start, int end)
	{
		return getSafeHtml().subSequence(start, end);
	}
}
