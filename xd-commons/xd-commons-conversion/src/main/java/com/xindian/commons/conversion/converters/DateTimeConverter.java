package com.xindian.commons.conversion.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.conversion.ConversionException;

/**
 * 将sourceValue转化为Date:
 * 
 * 1,Number->Date
 * 
 * 2,Date->Date
 * 
 * 3,String ->Date
 * 
 * 字符串/Long/
 * 
 * @author Elva
 * @date 2011-2-10
 * @version 1.0
 */

@SuppressWarnings("rawtypes")
public class DateTimeConverter extends AbstractConverter
{
	private static Logger logger = LoggerFactory.getLogger(DateTimeConverter.class);

	/**
	 * 将字符串解析为Date
	 * 
	 * @param dateStr
	 *            时间字符串
	 * @param formatPatten
	 *            格式
	 * @param parseTimeZone
	 *            解析时区,如果为null,则使用DateFormat的默认时区解析
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date parseDateTime(String dateStr, String formatPatten,
			TimeZone parseTimeZone) throws ParseException
	{
		logger.debug("Parse String to DateTime...");
		logger.debug(" |use dateStr:[" + dateStr + "]");
		logger.debug(" |use format Patten:[" + formatPatten + "]");
		DateFormat dateFormat = new SimpleDateFormat(formatPatten);
		if (parseTimeZone != null)
		{
			dateFormat.setTimeZone(parseTimeZone);// 使用指定的TimeZone
		}
		logger.debug(" |use TimeZone ID:[" + dateFormat.getTimeZone().getID() + "]");
		// 否则使用默认的时区(本地时区)
		Date dateTime = dateFormat.parse(dateStr);
		logger.debug(" |parse to GMT DateTime:[" + dateTime.toGMTString() + "]");

		dateFormat.getCalendar();
		return dateTime;
	}

	@Override
	public Object convert(Map<String, Object> context, Class targetType,
			Object sourceValue) throws ConversionException
	{
		if (sourceValue instanceof Number)// Number -> Date,数字到时间不使用任何转换,数组表示
		{
			return long2Date(context, targetType, ((Number) sourceValue).longValue());
		} else if (sourceValue instanceof CharSequence)// String -> Date
		{
			String value = ((CharSequence) sourceValue).toString();
			String[] dateTimePatterns = (String[]) context
					.get(CONTEXT_DATE_TIME_PATTERN_ARRAY_KEY);
			TimeZone timeZone = (TimeZone) context.get(CONTEXT_TIME_ZONE_KEY);
			if (dateTimePatterns != null)
			{
				for (String dateTimePattern : dateTimePatterns)// 指定格式
				{
					try
					{
						Date date = parseDateTime(value, dateTimePattern, timeZone);
						return date2date(context, targetType, date);
					} catch (ParseException e)
					{
						continue;
					}
				}
				throw new ConversionException(context, targetType, sourceValue,
						"指定格式都无法为类型转换..");
			} else
			{
				try
				{
					Date date = DateFormat.getDateInstance().parse(value);// 默认格式
					return date2date(context, targetType, date);
				} catch (ParseException e1)
				{
					try
					{
						return long2Date(context, targetType, Long.parseLong(value));// String->Long->Date
					} catch (NumberFormatException e)
					{
						//
						throw new ConversionException(" String->Long->Date error");
					}
				}

			}
		} else
		// Date->Date
		{
			return date2date(context, targetType, sourceValue);
		}
		// return super.convert(context, targetType, sourceValue);
	}

	/**
	 * 
	 * @param context
	 * @param targetType
	 * @param value
	 * @return
	 */
	protected Object long2Date(Map<String, Object> context, Class targetType, long value)
	{
		// long -> java.util.Date
		if (targetType.equals(Date.class))
		{
			return new Date(value);
		}

		// long -> java.sql.Date
		if (targetType.equals(java.sql.Date.class))
		{
			return new java.sql.Date(value);
		}

		// long -> java.sql.Time
		if (targetType.equals(java.sql.Time.class))
		{
			return new java.sql.Time(value);
		}

		// long -> java.sql.Timestamp
		if (targetType.equals(java.sql.Timestamp.class))
		{
			return new java.sql.Timestamp(value);
		}

		// long -> java.util.Calendar
		if (targetType.equals(Calendar.class))
		{
			Locale locale = (Locale) context.get(CONTEXT_LOCALE_KEY);
			TimeZone timeZone = (TimeZone) context.get(CONTEXT_TIME_ZONE_KEY);
			Calendar calendar = null;
			if (locale == null && timeZone == null)
			{
				calendar = Calendar.getInstance();
			} else if (locale == null)
			{
				calendar = Calendar.getInstance(timeZone);
			} else if (timeZone == null)
			{
				calendar = Calendar.getInstance(locale);
			} else
			{
				calendar = Calendar.getInstance(timeZone, locale);
			}
			calendar.setTime(new Date(value));
			calendar.setLenient(false);
			return calendar;
		}
		String msg = toString(getClass()) + " cannot handle conversion to '"
				+ toString(targetType) + "'";
		if (logger.isWarnEnabled())
		{
			logger.warn("    " + msg);
		}
		throw new ConversionException(msg);
	}

	protected Object date2date(Map<String, Object> context, Class targetType, Object value)
			throws ConversionException
	{
		// Handle java.sql.Timestamp
		if (value instanceof java.sql.Timestamp)
		{
			// ---------------------- JDK 1.3 Fix ----------------------
			// N.B. Prior to JDK 1.4 the Timestamp's getTime() method
			// didn't include the milliseconds. The following code
			// ensures it works consistently accross JDK versions
			java.sql.Timestamp timestamp = (java.sql.Timestamp) value;
			long timeInMillis = ((timestamp.getTime() / 1000) * 1000);
			timeInMillis += timestamp.getNanos() / 1000000;
			// ---------------------- JDK 1.3 Fix ----------------------
			return long2Date(context, targetType, timeInMillis);
		}

		// Handle Date (includes java.sql.Date & java.sql.Time)
		if (value instanceof Date)
		{
			Date date = (Date) value;
			return long2Date(context, targetType, date.getTime());
		}

		// Handle Calendar
		if (value instanceof Calendar)
		{
			Calendar calendar = (Calendar) value;
			return long2Date(context, targetType, calendar.getTime().getTime());
		}
		throw new ConversionException("date to date error!");
	}

	protected Calendar parse(Map<String, Object> context, Class sourceType,
			Class targetType, String value, DateFormat format) throws ConversionException
	{
		format.setLenient(false);
		ParsePosition pos = new ParsePosition(0);
		Date parsedDate = format.parse(value, pos); // ignore the result (use
													// the Calendar)
		if (pos.getErrorIndex() >= 0 || pos.getIndex() != value.length()
				|| parsedDate == null)
		{
			String msg = "Error converting '" + toString(sourceType) + "' to '"
					+ toString(targetType) + "'";
			if (format instanceof SimpleDateFormat)
			{
				msg += " using pattern '" + ((SimpleDateFormat) format).toPattern() + "'";
			}
			throw new ConversionException(msg);
		}
		Calendar calendar = format.getCalendar();
		return calendar;
	}

	public static void main2(String args[])
	{
		DateTimeConverter dateTimeConverter = new DateTimeConverter();

		Map<String, Object> context = new HashMap<String, Object>();

		// context.put(CONTEXT_DATE_TIME_PATTERN_KEY,
		// "yyyy年MM月dd日,E,hh:mm aaa");

		context.put(CONTEXT_DATE_TIME_PATTERN_ARRAY_KEY,
				new String[] { "yyyy-MM-dd HH:mm:ss" });

		// context.put(CONTEXT_LOCALE_KEY, Locale.CHINA);
		long i = new Date().getTime();
		String x = "2010-01-10 12:12:12";//
		logger.debug("==========" + dateTimeConverter.convert(context, null, x));
		logger.debug("==========" + i
				+ Currency.getInstance(Locale.getDefault()).getSymbol());
	}

	public static void main(String args[])
	{
		DateTimeConverter dateTimeConverter = new DateTimeConverter();

		Map<String, Object> context = new HashMap<String, Object>();

		// context.put(CONTEXT_DATE_TIME_PATTERN_KEY,
		// "yyyy年MM月dd日,E,hh:mm aaa");

		context.put(CONTEXT_DATE_TIME_PATTERN_ARRAY_KEY,
				new String[] { "yyyy-MM-dd HH:mm:ss" });

		context.put(CONTEXT_TIME_ZONE_KEY, TimeZone.getTimeZone("Japan"));

		// context.put(CONTEXT_LOCALE_KEY, Locale.CHINA);
		// long i = new Date().getTime();
		String x = "2010-01-10 12:12:12";// 比如 一个日本鬼子正在使用,这个是日本鬼子时间(+9时间)

		// 将时间转换为标准时间
		// 然后将
		Object d = dateTimeConverter.convert(context, Date.class, x);// 将其转换为"绝对时间",时间的偏移量
		// Calendar c = Calendar.getInstance();
		// c.setTimeZone(TimeZone.getTimeZone("Japan"));
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		sdf2.setTimeZone(TimeZoneConverter.getTimeZone(0));// 转换成(东8区时间)
		logger.debug("time:" + sdf2.format(d) + " timezone:" + sdf2.getTimeZone().getID());
		// logger.debug("==========" + c.getTime());
		// logger.debug("==========" +
		// Currency.getInstance(Locale.getDefault()).getSymbol());
	}
}
