package com.xindian.mvc.conversion;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.conversion.Conversion.DefaultDateFormats;
import com.xindian.mvc.conversion.Conversion.Trim;
import com.xindian.mvc.conversion.converters.ArrayConverter;
import com.xindian.mvc.conversion.converters.BooleanConverter;
import com.xindian.mvc.conversion.converters.CharacterConverter;
import com.xindian.mvc.conversion.converters.CollectionConverter;
import com.xindian.mvc.conversion.converters.DateTimeConverter;
import com.xindian.mvc.conversion.converters.EnumConverter;
import com.xindian.mvc.conversion.converters.LocaleConverter;
import com.xindian.mvc.conversion.converters.NumberConverter;
import com.xindian.mvc.conversion.converters.SafeHtmlConverter;
import com.xindian.mvc.conversion.converters.StringConverter;
import com.xindian.mvc.conversion.converters.TimeZoneConverter;
import com.xindian.mvc.conversion.converters.URLConverter;
import com.xindian.mvc.i18n3.SafeHtml;
//import com.xindian.mvc.test.User;
import com.xindian.mvc.utils.TypeUtils;

/**
 * @author Elva
 * @date 2011-2-10
 * @version 1.0
 */
public class ConverterFactory
{
	private static Logger logger = LoggerFactory.getLogger(ConverterFactory.class);

	private static Map<Class, Converter> converters = new HashMap<Class, Converter>();

	/**
	 * @param context
	 * @param targetType
	 * @param value
	 * @return
	 * @throws ConversionException
	 */
	public static Object convert(Map<String, Object> context, Class<?> targetType, Object sourceValue) throws ConversionException
	{
		if (targetType.isInstance(sourceValue))
		{
			return sourceValue;
		}
		Class<?> t = TypeUtils.primitive(targetType);

		if (targetType.isArray() || sourceValue.getClass().isArray())
		{
			t = Array.class;
		}
		if (t.isEnum())
		{
			t = Enum.class;
		}
		if (Collection.class.isAssignableFrom(t))
		{
			t = Collection.class;
		}
		Converter converter = converters.get(t);
		if (converter != null)
		{
			logger.debug("Use Converter:[" + converter.getClass() + "]");
			return converter.convert(context, targetType, sourceValue);
		} else
		{
			throw new ConversionException(context, targetType, sourceValue, "Can not find a appropriate converter to convert["
					+ sourceValue.getClass() + "] to targetType[" + targetType + "]");
		}
	}

	@SuppressWarnings("unchecked")
	public static void registerConverter(Class type, Converter converter)
	{
		synchronized (converters)
		{
			converters.put(type, converter);
		}
	}

	static
	{
		registerConverter(Boolean.class, new BooleanConverter());

		registerConverter(Character.class, new CharacterConverter());

		/**** 数字(使用相同的类型转换器) ****/
		registerConverter(Byte.class, new NumberConverter());

		registerConverter(Short.class, new NumberConverter());

		registerConverter(Integer.class, new NumberConverter());

		registerConverter(Long.class, new NumberConverter());

		registerConverter(Float.class, new NumberConverter());

		registerConverter(Double.class, new NumberConverter());

		registerConverter(BigInteger.class, new NumberConverter());

		registerConverter(BigDecimal.class, new NumberConverter());

		/**** 字串 ****/
		registerConverter(String.class, new StringConverter());

		registerConverter(SafeHtml.class, new SafeHtmlConverter());

		registerConverter(URL.class, new URLConverter());

		/**** 时间 ****/
		registerConverter(Date.class, new DateTimeConverter());

		registerConverter(Calendar.class, new DateTimeConverter());

		registerConverter(java.sql.Date.class, new DateTimeConverter());

		registerConverter(Timestamp.class, new DateTimeConverter());

		registerConverter(Time.class, new DateTimeConverter());

		registerConverter(TimeZone.class, new TimeZoneConverter());

		registerConverter(Locale.class, new LocaleConverter());

		/**** 枚举 ***/
		registerConverter(Enum.class, new EnumConverter());

		/**** 数组 ***/
		registerConverter(Array.class, new ArrayConverter());

		/**** 集合 ***/
		registerConverter(Collection.class, new CollectionConverter());
	}

	public static void main0(String[] args)
	{
		Map<String, Object> context = new HashMap<String, Object>();

		// context.put(CONTEXT_DATE_TIME_PATTERN_KEY,
		// "yyyy年MM月dd日,E,hh:mm aaa");

		context.put(Converter.CONTEXT_DATE_TIME_PATTERN_ARRAY_KEY, new String[] { "yyyy-MM-dd HH:mm:ss" });

		context.put(Converter.CONTEXT_ONLY_FIRST_TO_STRING_FLAG_KEY, false);

		logger.debug(Collection.class.isAssignableFrom(Vector.class) + "");

		logger.debug("==========" + convert(context, Vector.class, new int[] { 55, 2, 3 }));

		logger.debug("==========" + convert(context, Integer.TYPE, "12.4"));
		// context.put(CONTEXT_LOCALE_KEY, Locale.CHINA);
		long i2 = new Date().getTime();

		String x = "2010-01-10 12:12:12";
		List<Long> a = new ArrayList<Long>();

		for (int i = 0; i < 10; i++)
		{
			a.add(new Long(i + 1));
		}

		logger.debug("==========" + ((BigDecimal[]) convert(context, BigDecimal[].class, a.toArray())).length);

		// logger.debug("==========" + new Date());
	}

	static class A
	{
		@DefaultDateFormats({ "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日 HH:mm:ss" })
		public Date date;

		@Trim(true)
		public SafeHtml[] str;
	}

	/**
	 * @param args
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static void main(String[] args) throws SecurityException, NoSuchFieldException
	{
		Field field = A.class.getField("date");

		Map<String, Object> context = ConversionUtils.getContext(field);

		Field field2 = A.class.getField("str");

		Map<String, Object> context2 = ConversionUtils.getContext(field2);

	//	User[] u = new User[2];

		String x = "2010年01月10,日 12:12:12  <br/> ";

		// logger.debug("==========" + ((SafeHtml[]) convert(context2,
		// field2.getType(), x))[1]);

		//logger.debug("==========" + ((User[]) convert(context2, User[].class, u)).length);

	}
}
