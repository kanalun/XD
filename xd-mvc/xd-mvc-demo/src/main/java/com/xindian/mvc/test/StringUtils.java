package com.xindian.mvc.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Elva
 * 
 */
public class StringUtils
{
	private static String regEx = "\\{\\?\\}";// {?}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

	}

	/**
	 * 用后面的参数填充s中{?}的部分
	 * 
	 * @param s
	 * @param parms
	 */
	public static String fillString(String s, Object... parms)
	{
		Pattern p = Pattern.compile(regEx);
		Matcher propsMatcher = p.matcher(s);
		StringBuffer stringBuffer = new StringBuffer();
		int i = 0;
		int needStart = 0;
		while (propsMatcher.find())
		{
			int matchingStartIndex = propsMatcher.start(); // index of start
			int matchingEndIndex = propsMatcher.end(); // index of end + 1
			stringBuffer.append(s.substring(needStart, matchingStartIndex)).append(parms[i]);
			needStart = matchingEndIndex;
			i++;
		}
		if (needStart < s.length())
		{
			stringBuffer.append(s.substring(needStart, s.length()));
		}
		return stringBuffer.toString();
	}
}
