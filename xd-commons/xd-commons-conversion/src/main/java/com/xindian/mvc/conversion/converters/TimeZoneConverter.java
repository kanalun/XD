package com.xindian.mvc.conversion.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.conversion.ConversionException;
import com.xindian.mvc.utils.Validator;

/**
 * 将字符串(TimeZone ID)或者时区偏移转换成特定的时区
 * 
 * @author Elva
 * @date 2011-3-4
 * @version 1.0
 */
public class TimeZoneConverter extends AbstractConverter
{
	private static Logger logger = LoggerFactory.getLogger(TimeZoneConverter.class);

	@Override
	public Object convert(Map<String, Object> context, Class targetType, Object sourceValue) throws ConversionException
	{
		if (sourceValue == null)// 空值返回null TODO 使用context的默认值来处理?
		{
			return null;
		}
		if (sourceValue instanceof Number)// 数字,使用时区
		{
			TimeZone timeZone = getTimeZone(((Number) sourceValue).intValue());
			return timeZone;
		} else
		{
			TimeZone timeZone = getTimeZone(sourceValue.toString());// 否则解析
			return timeZone;
		}
		// return super.convert(context, targetType, sourceValue);
	}

	/**
	 * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
	 * 
	 * @param timeZoneOffset
	 * @return
	 */
	public static TimeZone getTimeZone(int timeZoneOffset) throws ConversionException
	{
		if (timeZoneOffset > 13 || timeZoneOffset < -12)
		{
			throw new IllegalArgumentException("timeZoneOffset must <= 13 && >= -12");
		}
		String[] ids = TimeZone.getAvailableIDs(timeZoneOffset * 60 * 60 * 1000);
		if (ids.length == 0)
		{
			// if no ids were returned, something is wrong. use default TimeZone
			return null;
		} else
		{
			TimeZone timeZone = new SimpleTimeZone(timeZoneOffset * 60 * 60 * 1000, ids[0]);
			return timeZone;
		}
	}

	/**
	 * 解析字符串获得TimeZone
	 * 
	 * @param timeZoneId
	 * @return 如果给定字符串可以作为timeZone的ID;如果timeZoneId
	 *         为空(""/null)返回null,如果给定的字符串无法解析为Timezone抛出异常
	 */
	public static TimeZone getTimeZone(String timeZoneId) throws ConversionException
	{
		logger.debug("parse TimeZone from timeZoneId[" + timeZoneId + "]");
		if (timeZoneId == null || timeZoneId.length() <= 0)
		{
			return null;
			// throw new
			// IllegalArgumentException("timeZoneId can not be empty");
		} else
		{
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
			if (timeZone.getID().equals("GMT") && !timeZoneId.equals("GMT"))
			{
				throw new ConversionException("timeZoneId [" + timeZoneId + "] can not parse to an available TimeZone");
			} else
			{
				return timeZone;
			}
		}
	}

	// TEST
	private static String getFormatedDateString(String timeZoneStr)
	{
		TimeZone timeZone = null;
		if (Validator.isBlank(timeZoneStr))
		{
			timeZone = TimeZone.getTimeZone("error");
		} else
		{
			timeZone = TimeZone.getTimeZone(timeZoneStr);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");

		sdf.setTimeZone(timeZone);

		return sdf.format(new Date());
	}

	public static void main2(String args[])
	{
		TimeZoneConverter timeZoneConverter = new TimeZoneConverter();

		Object r = timeZoneConverter.convert(Collections.EMPTY_MAP, TimeZone.class, 8);

		logger.debug(r + "");
	}

	// TEST
	public static void main(String args[])
	{
		System.out.println("默认:	" + getFormatedDateString(""));
		System.out.println("GMT:	" + getFormatedDateString("GMT"));
		System.out.println("上海:	" + getFormatedDateString("Asia/Shanghai"));
		System.out.println("东八区:	" + getFormatedDateString("GMT+8:00"));
		System.out.println("日本:	" + getFormatedDateString("Japan"));
		System.out.println("马德里:	" + getFormatedDateString("Europe/Madrid"));

		// 默认时区为本地时区()本地运行的失去

		// 默认为GMT时间

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		sdf.setTimeZone(getTimeZone(7));
		logger.debug(" TIMEZONE:" + sdf.getTimeZone().getID());
		try
		{
			Date d = sdf.parse("2011-03-04  08:53:33");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
			logger.debug("TIME:" + sdf2.format(d) + " TIMEZONE:" + sdf2.getTimeZone().getID());

		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		/*
		 * for (String s : TimeZone.getAvailableIDs()) {
		 * logger.debug("TIMEZONE:" + s); }
		 */
		// logger.debug("TIMEZONE:" + TimeZone.getTimeZone("44").getID());
		// printSysProperties();
	}
}
