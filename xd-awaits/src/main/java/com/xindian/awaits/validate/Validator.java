package com.xindian.awaits.validate;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证器
 * 
 * @author Elva
 * 
 */
public class Validator
{
	public static boolean notNull(Object object)
	{
		return object != null;
	}

	public static boolean isNull(Object object)
	{
		return object == null;
	}

	public static boolean notEmpty(String str)
	{
		return notNull(str) && !str.trim().equals("");
	}

	/**
	 * 验证所有的字符串都不为空,相当于 S1!= empty && S2! =empty...
	 * 
	 * @param strs
	 * @return 所有参数全部不为空,返回true;如果有一个字符串为空,返回false,
	 */
	public static boolean allNotEmpty(String... strs)
	{
		for (String str : strs)
		{
			if (isEmpty(str))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 验证所有的字符串都为空,相当于 S1== empty && S2= =empty...
	 * 
	 * @param strs
	 * @return 所有的字符串都是空,返回 true;否则返回false
	 */
	public static boolean allEmpty(String... strs)
	{
		for (String str : strs)
		{
			if (notEmpty(str))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 验证所有的字符串都为空,相当于 S1== empty && S2= =empty...
	 * 
	 * @param strs
	 * @return 所有的字符串都是空,返回 true;否则返回false
	 */
	public static boolean allNull(Object... objects)
	{
		for (Object str : objects)
		{
			if (notNull(str))
			{
				return false;
			}
		}
		return true;
	}

	public static boolean allNotNull(Object... objects)
	{
		for (Object str : objects)
		{
			if (isNull(str))
			{
				return false;
			}
		}
		return true;
	}

	public static boolean equals(Object obj1, Object obj2)
	{
		// /if (obj1 == null && obj2 == null)
		// return true;
		return obj1.equals(obj2);
	}

	/**
	 * 返回字符串是否为NULL或者去除两端空格之后长度为0
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str)
	{
		return isNull(str) || "".equals(str.trim());
	}

	/**
	 * 判断列表为NULL或者没有元素
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list)
	{
		return isNull(list) || list.isEmpty();
	}

	public static boolean notEmpty(List<?> list)
	{
		return !isEmpty(list);
	}

	public static boolean notEmpty(Object[] objects)
	{
		return notNull(objects) && objects.length > 0;
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email)
	{
		if (email == null)
			return false;
		email = email.trim();
		if (email.indexOf(' ') != -1)
			return false;

		int idx = email.indexOf('@');
		if (idx == -1 || idx == 0 || (idx + 1) == email.length())
			return false;
		if (email.indexOf('@', idx + 1) != -1)
			return false;
		if (email.indexOf('.') == -1)
			return false;
		return true;
		/*
		 * Pattern emailer; if(emailer==null){ String check =
		 * "^([a-z0-9A-Z]+[-|\\._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
		 * ; emailer = Pattern.compile(check); } Matcher matcher =
		 * emailer.matcher(email); return matcher.matches();
		 */
	}

	static String mobileRegex = "^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$";

	static String mobileRegex2 = "^((\\(\\d{2,3}\\))|(\\d{3}\\-))?((1[345]\\d{9})|(18\\d{9}))$";

	/**
	 * 判断字符串是否是一个IP地址
	 * 
	 * @param addr
	 * @return
	 */
	public static boolean isIPAddress(String addr)
	{
		if (isEmpty(addr))
			return false;
		String[] ips = addr.split("\\.");
		if (ips.length != 4)
			return false;
		try
		{
			int ipa = Integer.parseInt(ips[0]);
			int ipb = Integer.parseInt(ips[1]);
			int ipc = Integer.parseInt(ips[2]);
			int ipd = Integer.parseInt(ips[3]);
			return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0 && ipc <= 255 && ipd >= 0 && ipd <= 255;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isMobile(String mobile)
	{
		String check = mobileRegex2;
		Pattern emailer = Pattern.compile(check);
		Matcher matcher = emailer.matcher(mobile);
		return matcher.matches();

	}

	/**
	 * 验证是字符串是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(Object value)
	{
		if (isNull(value))
		{
			return false;
		}
		if (value instanceof Number)
		{
			return true;
		}
		Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
		return pattern.matcher(value.toString()).matches();
	}

	// 维度经度数据是否合法,用于GOOGLE MAP
	public static boolean isLatLon(double lat, double lon)
	{
		return lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180;
	}

	public static void main(String args[])
	{
		String m1 = "130138248155";
		String m2 = "13238248155";
		String m3 = "13338248155";
		String m4 = "13438248155";
		String m5 = "13538248155";
		String m6 = "13638248155";
		String m7 = "13738248155";
		String m8 = "13838248155";
		String m9 = "13938248155";
		String m0 = "14038248155";

		String m11 = "15038248155";
		String m12 = "15138248155";
		String m13 = "152338248155";
		String m14 = "15338248155";
		String m15 = "15438248155";
		String m16 = "15538248155";
		String m17 = "15638248155";
		String m18 = "15738248155";
		String m19 = "15893824815";
		String m10 = "15938248155";

		System.out.println(isMobile(m1));
		System.out.println(isMobile(m2));
		System.out.println(isMobile(m3));
		System.out.println(isMobile(m4));
		System.out.println(isMobile(m5));
		System.out.println(isMobile(m6));
		System.out.println(isMobile(m7));
		System.out.println(isMobile(m8));
		System.out.println(isMobile(m9));
		System.out.println(isMobile(m0));

		System.out.println(isMobile(m11));
		System.out.println(isMobile(m12));
		System.out.println(isMobile(m13));
		System.out.println(isMobile(m14));
		System.out.println(isMobile(m15));
		System.out.println(isMobile(m16));
		System.out.println(isMobile(m17));
		System.out.println(isMobile(m18));
		System.out.println(isMobile(m19));
		System.out.println(isMobile(m10));

		System.out.println(isNumeric("1"));
		System.out.println(isNumeric("11"));
		System.out.println(isNumeric("1.11111111111111"));
		System.out.println(isNumeric("0.1"));
		System.out.println(isNumeric("001"));
	}

	public static boolean validatePassword()
	{
		return true;
	}

	public static boolean validateUserName()
	{
		return true;
	}
}
