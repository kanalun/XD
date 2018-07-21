package com.xindian.commons.conversion.converters;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.conversion.ConversionException;
import com.xindian.commons.utils.TypeUtils;

/**
 * 提供其他类型到对数字类型转换的支持:String->Number:Number->Number
 * 
 * 1,精度损失:异常,包容
 * 
 * 2,数字格式化:默认格式化,配置格式化..
 * 
 * @author Elva
 * @date 2011-2-10
 * @version 1.0
 */
public class NumberConverter extends AbstractConverter
{
	private static Logger logger = LoggerFactory.getLogger(NumberConverter.class);

	@Override
	@SuppressWarnings("unchecked")
	public Object convert(Map<String, Object> context, final Class targetType, final Object sourceValue)
			throws ConversionException
	{
		Object value = sourceValue;

		Class theTargetType = TypeUtils.primitive(targetType);// 包装基本类型

		if (sourceValue instanceof String)// String -> Number
		{
			value = toNumber(theTargetType, (String) sourceValue);
			logger.debug("convert String[\"" + value + "\"] to Number[" + value + "]" + value.getClass());
			return value;
		}
		if (sourceValue instanceof Number)// Number -> Number
		{
			value = toNumber(theTargetType, (Number) sourceValue);
			return value;
		}
		return super.convert(context, targetType, sourceValue);//
	}

	protected Number toNumber2(Map<String, Object> context, Class targetType, String value) throws NumberFormatException,
			ParseException
	{
		NumberFormat numberFormat = null;

		Locale locale = (Locale) context.get(CONTEXT_LOCALE_KEY);
		if (locale != null)
		{
			numberFormat = NumberFormat.getInstance(locale);
		} else
		{
			numberFormat = NumberFormat.getInstance();
		}
		return numberFormat.parse(value);
	}

	/**
	 * 提供字符转到数字时间转换的桥梁
	 * 
	 * @param targetType
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	@SuppressWarnings("unchecked")
	protected Number toNumber(Class targetType, String value) throws NumberFormatException
	{
		// Byte
		if (targetType.equals(Byte.class))
		{
			return new Byte(value);
		}
		// Short
		if (targetType.equals(Short.class))
		{
			return new Short(value);
		}
		// Integer
		if (targetType.equals(Integer.class))
		{
			return new Integer(value);
		}
		// Long
		if (targetType.equals(Long.class))
		{
			return new Long(value);
		}
		// Float
		if (targetType.equals(Float.class))
		{
			return new Float(value);
		}
		// Double
		if (targetType.equals(Double.class))
		{
			return new Double(value);
		}
		// BigDecimal
		if (targetType.equals(BigDecimal.class))
		{
			return new BigDecimal(value);
		}
		// BigInteger
		if (targetType.equals(BigInteger.class))
		{
			return new BigInteger(value);
		}
		throw new ConversionException();
	}

	/**
	 * 数字之间转换桥梁
	 * 
	 * @param targetType
	 * @param value
	 * @return
	 */
	private Number toNumber(Class targetType, Number value)
	{
		// Correct Number type already
		if (targetType.equals(value.getClass()))
		{
			logger.debug("target Type[" + targetType + "]equals source type[" + value.getClass() + "], just return it!");
			return value;
		}

		// Byte
		if (targetType.equals(Byte.class))
		{
			long longValue = value.longValue();
			if (longValue > Byte.MAX_VALUE)
			{
				throw new ConversionException(toString(value.getClass()) + " value '" + value + "' is too large for "
						+ toString(targetType));
			}
			if (longValue < Byte.MIN_VALUE)
			{
				throw new ConversionException(toString(value.getClass()) + " value '" + value + "' is too small "
						+ toString(targetType));
			}
			logger.debug("target Type[" + targetType + "] source type[" + value.getClass() + "]");
			return new Byte(value.byteValue());
		}

		// Short
		if (targetType.equals(Short.class))
		{
			long longValue = value.longValue();
			if (longValue > Short.MAX_VALUE)
			{
				throw new ConversionException(toString(value.getClass()) + " value '" + value + "' is too large for "
						+ toString(targetType));
			}
			if (longValue < Short.MIN_VALUE)
			{
				throw new ConversionException(toString(value.getClass()) + " value '" + value + "' is too small "
						+ toString(targetType));
			}
			logger.debug("target Type[" + targetType + "] source type[" + value.getClass() + "]");
			return new Short(value.shortValue());
		}

		// Integer
		if (targetType.equals(Integer.class))
		{
			long longValue = value.longValue();
			if (longValue > Integer.MAX_VALUE)
			{
				throw new ConversionException(toString(value.getClass()) + " value '" + value + "' is too large for "
						+ toString(targetType));
			}
			if (longValue < Integer.MIN_VALUE)
			{
				throw new ConversionException(toString(value.getClass()) + " value '" + value + "' is too small "
						+ toString(targetType));
			}
			logger.debug("target Type[" + targetType + "] source type[" + value.getClass() + "]");
			return new Integer(value.intValue());
		}

		// Long
		if (targetType.equals(Long.class))
		{
			logger.debug("target Type[" + targetType + "] source type[" + value.getClass() + "]");
			return new Long(value.longValue());
		}

		// Float
		if (targetType.equals(Float.class))
		{
			if (value.doubleValue() > Float.MAX_VALUE)
			{
				throw new ConversionException(toString(value.getClass()) + " value '" + value + "' is too large for "
						+ toString(targetType));
			}
			logger.debug("target Type[" + targetType + "] source type[" + value.getClass() + "]");
			return new Float(value.floatValue());
		}

		// Double
		if (targetType.equals(Double.class))
		{
			logger.debug("target Type[" + targetType + "] source type[" + value.getClass() + "]");
			return new Double(value.doubleValue());
		}

		// BigDecimal
		if (targetType.equals(BigDecimal.class))
		{
			logger.debug("target Type[" + targetType + "] source type[" + value.getClass() + "]");
			if (value instanceof Float || value instanceof Double)
			{
				return new BigDecimal(value.toString());
			} else if (value instanceof BigInteger)
			{
				return new BigDecimal((BigInteger) value);
			} else
			{
				return BigDecimal.valueOf(value.longValue());
			}
		}

		// BigInteger
		if (targetType.equals(BigInteger.class))
		{
			logger.debug("target Type[" + targetType + "] source type[" + value.getClass() + "]");
			if (value instanceof BigDecimal)
			{
				return ((BigDecimal) value).toBigInteger();
			} else
			{
				return BigInteger.valueOf(value.longValue());
			}
		}

		String msg = toString(getClass()) + " cannot handle conversion to '" + toString(targetType) + "'";
		if (logger.isWarnEnabled())
		{
			logger.warn("    " + msg);
		}
		throw new ConversionException(msg);
	}

	public static void main(String args[]) throws ParseException
	{
		// logger.debug(NumberFormat.getInstance().parse("1.4f").getClass());
		//
		// logger.debug(new NumberConverter().convert(null, Integer.TYPE, new
		// BigDecimal("12.2")));
		// logger.debug(new NumberConverter().convert(null, Double.TYPE, new
		// BigDecimal("12.2")));
		// logger.debug(new NumberConverter().convert(null, Float.TYPE, new
		// BigDecimal("12.2")));
		// logger.debug(new NumberConverter().convert(null, Double.class, new
		// BigDecimal("12.2")));
		// logger.debug(new NumberConverter().convert(null, Float.class, new
		// BigDecimal("12.2")));
		// logger.debug(new NumberConverter().convert(null, BigInteger.class,
		// new BigDecimal("12.2")));
		// logger.debug(new NumberConverter().convert(null, BigInteger.class,
		// "1232"));
	}
}
