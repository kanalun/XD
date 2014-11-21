package com.xindian.mvc.conversion.converters;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.conversion.ConversionException;
import com.xindian.mvc.conversion.ConverterFactory;

/**
 * 将特定类型按照上下文转化为的:String
 * 
 * 受到支持的类型有,基本类型(及包装类型),日期(SQL)
 * 
 * @author Elva
 * @date 2011-2-10
 * @version 1.0
 */
public class StringConverter extends AbstractConverter
{
	private static Logger logger = LoggerFactory.getLogger(StringConverter.class);

	// public static String CONTEXT_DATE_TIME_PATTERN_KEY = "format";

	// public static String CONTEXT_LOCALE_KEY = "locale";

	public static String NULL_VALUE = "null";

	@Override
	public Object convert(Map<String, Object> context, Class targetType, Object value) throws ConversionException
	{
		if (value == null)
		{
			throw new ConversionException(context, String.class, value, "value can not be null");
		}
		if (value instanceof String)
		{
			logger.debug("value is a String");
			Boolean trim = (Boolean) context.get(CONTEXT_STRING_TRIM_KEY);
			if (trim != null && trim)
			{
				logger.debug("String trim");
				return ((String) value).trim();

			} else
			{
				logger.debug("String no trim");
				return value;
			}
			// throw new ConversionException(context, targetType, value,
			// "value is not a java.lang.String type");
		}
		if (value instanceof java.util.Date)// Date -> String
		{
			Locale locale = (Locale) context.get(CONTEXT_LOCALE_KEY);
			String pattern = (String) context.get(CONTEXT_DATE_TIME_PATTERN_KEY);
			SimpleDateFormat simpleDateFormat;
			if (locale != null && pattern != null)
			{
				simpleDateFormat = new SimpleDateFormat(pattern, locale);
			} else
			{
				simpleDateFormat = new SimpleDateFormat();
			}
			return simpleDateFormat.format(value);
		}
		if (value instanceof Calendar)// Calendar->String
		{
			TimeZone timeZone = (TimeZone) context.get(CONTEXT_TIME_ZONE_KEY);
		}
		String result = null;

		if (value.getClass().isArray())// Array->String
		{
			try
			{
				return arrayToString(context, value);
			} catch (Throwable e)
			{
				throw new ConversionException(context, targetType, value, "dddd", e);
			}
		}
		if (value instanceof Number)// Number->String
		{
			Locale locale = (Locale) context.get(CONTEXT_LOCALE_KEY);
			NumberFormat format;
			if (locale != null)
			{
				format = NumberFormat.getInstance(locale);
			} else
			{
				format = NumberFormat.getInstance();
			}

			format.setGroupingUsed(false);

			result = format.format(value);
			return result;
		} else
		{
			result = value.toString();
			if (logger.isDebugEnabled())
			{
				logger.debug("    Converted  to String using toString() '" + result + "'");
			}
		}
		return value.toString();
	}

	private String arrayToString(Map<String, Object> context, Object value) throws Throwable
	{
		int size = 0;
		Iterator iterator = null;
		Class type = value.getClass();
		// if (type.isArray())
		{
			size = Array.getLength(value);
		}
		// else
		{
			// throw new ConversionException("not a array");
			// Collection collection = convertToCollection(type, value);
			// size = collection.size();
			// iterator = collection.iterator();
		}
		if (size == 0)
		{
			return "";// (String) getDefault(String.class);
		}
		Boolean onlyFirstToString = (Boolean) context.get(CONTEXT_ONLY_FIRST_TO_STRING_FLAG_KEY);
		if (onlyFirstToString != null && onlyFirstToString)
		{
			size = 1;
		}

		// Create a StringBuffer containing a delimited list of the values
		StringBuffer buffer = new StringBuffer();
		String delimiter = (String) context.get(CONTEXT_DELIMITER_KEY);
		if (delimiter == null)
		{
			delimiter = DEFAULT_DELIMITER;
		}
		for (int i = 0; i < size; i++)
		{
			if (i > 0)
			{
				buffer.append(delimiter);
			}
			Object element = iterator == null ? Array.get(value, i) : iterator.next();
			element = ConverterFactory.convert(context, String.class, element);
			if (element != null)
			{
				buffer.append(element);
			}
		}
		return buffer.toString();
	}

	@SuppressWarnings("deprecation")
	public static void main(String args[])
	{
		StringConverter stringConverter = new StringConverter();

		Map<String, Object> context = new HashMap<String, Object>();

		// context.put(CONTEXT_DATE_TIME_PATTERN_KEY,
		// "yyyy年MM月dd日,E,hh:mm aaa");

		// context.put(CONTEXT_LOCALE_KEY, Locale.CHINA);

		/*
		 * logger.debug("==========" + stringConverter.convert(context,
		 * java.sql.Date.class, new java.sql.Date(new Date().getTime())));
		 */

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date());
		logger.debug("==========" + calendar.get(Calendar.DATE));
		logger.debug("==========" + new Date().toGMTString());
		logger.debug("==========" + new Date().parse(""));
	}
}
