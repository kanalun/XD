package com.xindian.commons.conversion;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.conversion.Conversion.DefaultDateFormats;
import com.xindian.commons.conversion.Conversion.DefaultDelimiter;
import com.xindian.commons.conversion.Conversion.DefaultLocale;
import com.xindian.commons.conversion.Conversion.DefaultTimeZone;
import com.xindian.commons.conversion.Conversion.OnlyFirstToString;
import com.xindian.commons.conversion.Conversion.Trim;

/**
 * @author Elva
 * @date 2011-2-15
 * @version 1.0
 */
public class ConversionUtils
{
	private static Map<Member, Map<String, Object>> contextCache = new HashMap<Member, Map<String, Object>>();

	private static Logger logger = LoggerFactory.getLogger(ConversionUtils.class);

	/**
	 * 
	 * @param member
	 * @return
	 */
	public static Map<String, Object> getContext(Member member)
	{
		return getContext(member, null);
	}

	/**
	 * 从member的注解中获得类型转换上下文,FIXME
	 * 
	 * @param member
	 * @return
	 */
	public static Map<String, Object> getContext(Member member, Map<String, Object> coustom)
	{
		logger.debug("static getContext member[" + member + "]");
		Map<String, Object> fixedContext = getContextFromCache(member);
		if (fixedContext == null)
		{
			fixedContext = new HashMap<String, Object>();
			if (member instanceof Field)
			{
				logger.debug("  member is a Field");
				Field fld = ((Field) member);
				if (fld.isAnnotationPresent(Trim.class))
				{
					logger.debug("  member Trim[" + fld.getAnnotation(Trim.class).value() + "]");
					fixedContext.put(Converter.CONTEXT_STRING_TRIM_KEY,
							fld.getAnnotation(Trim.class).value());
				}
				if (fld.isAnnotationPresent(DefaultDelimiter.class))
				{
					logger.debug("  member DefaultDelimiter["
							+ fld.getAnnotation(DefaultDelimiter.class).value() + "]");
					fixedContext.put(Converter.CONTEXT_DELIMITER_KEY,
							fld.getAnnotation(DefaultDelimiter.class).value());
				}
				if (fld.isAnnotationPresent(DefaultDateFormats.class))
				{
					logger.debug("  member DefaultDateFormats["
							+ fld.getAnnotation(DefaultDateFormats.class).value() + "]");
					fixedContext.put(Converter.CONTEXT_DATE_TIME_PATTERN_ARRAY_KEY, fld
							.getAnnotation(DefaultDateFormats.class).value());
				}
				if (fld.isAnnotationPresent(OnlyFirstToString.class))
				{
					logger.debug("  member OnlyFirstToString["
							+ fld.getAnnotation(OnlyFirstToString.class).value() + "]");
					fixedContext.put(Converter.CONTEXT_ONLY_FIRST_TO_STRING_FLAG_KEY, fld
							.getAnnotation(OnlyFirstToString.class).value());
				}
				if (fld.isAnnotationPresent(DefaultLocale.class))
				{
					Locale locale = new Locale(fld.getAnnotation(DefaultLocale.class).value());
					logger.debug("  member DefaultLocale[" + locale + "]");
					fixedContext.put(Converter.CONTEXT_LOCALE_KEY, locale);
				}
				if (fld.isAnnotationPresent(DefaultTimeZone.class))
				{
					TimeZone timeZone = TimeZone.getTimeZone(fld.getAnnotation(
							DefaultTimeZone.class).value());
					logger.debug("  member DefaultTimeZone[" + timeZone + "]");
					fixedContext.put(Converter.CONTEXT_TIME_ZONE_KEY, timeZone);
				}
				putContextToCache(member, fixedContext);// put context to cache
			} else
			{
				throw new ConversionException("ddddddddd");
			}
		}
		if (coustom == null)
		{
			return fixedContext;// 直接返回
		} else
		{
			Map<String, Object> context = new HashMap<String, Object>();// 用自定义的上下文覆盖注解上下文
			context.putAll(fixedContext);
			context.putAll(coustom);
			/**
			 * <code>
			for (Iterator<String> it = fixedContext.keySet().iterator(); it.hasNext();)
			{
				String key = it.next();
				context.put(key, fixedContext.get(key));
			}
			for (Iterator<String> it = coustom.keySet().iterator(); it.hasNext();)
			{
				String key = it.next();
				context.put(key, coustom.get(key));
			}</code>
			 */
			return fixedContext;
		}

	}

	protected static Map<String, Object> getContextFromCache(Member member)
	{
		return contextCache.get(member);
	}

	protected static void putContextToCache(Member member, Map<String, Object> context)
	{
		contextCache.put(member, context);// put context to cache
	}

}
