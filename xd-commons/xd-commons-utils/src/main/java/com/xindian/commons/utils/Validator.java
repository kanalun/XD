package com.xindian.commons.utils;

import java.util.Collection;
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
	/**
	 * @param object
	 * @return return true if the object is not null;return false for otherwise
	 */
	public static boolean notNull(Object object)
	{
		return object != null;
	}

	/**
	 * 
	 * @param object
	 * @return return true if the object is null;return false for otherwise
	 */
	public static boolean isNull(Object object)
	{
		return object == null;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean notBlank(String str)
	{
		return notNull(str) && !str.trim().equals("");
	}

	/**
	 * 返回字符串是否为NULL或者去除两端空格之后长度为0
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str)
	{
		return isNull(str) || "".equals(str.trim());
	}

	/**
	 * 验证所有的字符串都不为空,相当于 S1!= empty && S2! =empty...
	 * 
	 * @param strs
	 * @return 所有参数都不为空,返回true;如果有一个字符串为空,返回false,
	 */
	public static boolean allNotBlank(String... strs)
	{
		for (String str : strs)
		{
			if (isBlank(str))
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
	public static boolean allBlank(String... strs)
	{
		for (String str : strs)
		{
			if (notBlank(str))
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

	/**
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(Object obj1, Object obj2)
	{
		return obj1.equals(obj2) || obj2.equals(obj1);
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static boolean notEmpty(Collection<?> list)
	{
		return !isEmpty(list);
	}

	/**
	 * 判断列表为NULL或者没有元素
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(Collection<?> list)
	{
		return isNull(list) || list.isEmpty();
	}

	/**
	 * 
	 * @param objects
	 * @return
	 */
	public static boolean notEmpty(Object[] objects)
	{
		return notNull(objects) && objects.length > 0;
	}

	/**
	 * 测试对象数组是否为空
	 * 
	 * @param objects
	 * @return
	 */
	public static boolean isEmpty(Object[] objects)
	{
		return !notEmpty(objects);
	}

	/***************** 其他常用的验证 *******************/
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

	}

	/**
	 * 判断字符串是否是一个IP地址:这个方法是有问题的,无法严格筛选IP地址
	 * 
	 * @param addr
	 * @return
	 */
	public static boolean isIPAddress(String addr)
	{
		if (isBlank(addr))
		{
			return false;
		}
		String[] ips = addr.split("\\.");
		if (ips.length != 4)
		{
			return false;
		}
		try
		{
			int ipa = Integer.parseInt(ips[0]);
			int ipb = Integer.parseInt(ips[1]);
			int ipc = Integer.parseInt(ips[2]);
			int ipd = Integer.parseInt(ips[3]);
			return (ipa >= 0 && ipa <= 255) && (ipb >= 0 && ipb <= 255) && (ipc >= 0 && ipc <= 255)
					&& (ipd >= 0 && ipd <= 255);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	private static String mobileRegex = "^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$";

	private static String mobileRegex2 = "^((\\(\\d{2,3}\\))|(\\d{3}\\-))?((1[345]\\d{9})|(18\\d{9}))$";

	/**
	 * 
	 * @param mobile
	 * @return
	 */
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

	/**
	 * 维度经度数据是否合法,用于GOOGLE MAP
	 * 
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static boolean isLatLon(double lat, double lon)
	{
		return lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180;
	}

	// exist("user.address.city",user);
	// TODO
	public static boolean exist(String exp, Object bean)
	{
		return false;
	}

	// notEmpty("user.address.city",user);是否存在
	// TODO
	public static boolean notBlank(String exp, Object bean)
	{
		return false;
	}

	public static void main(String args[])
	{
		String ip = "";
		System.out.print(isIPAddress("152.168.21.232"));
		// return matcher.matches();
		// JUST FOR TEST
	}
}
