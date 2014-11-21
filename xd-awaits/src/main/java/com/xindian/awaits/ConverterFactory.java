package com.xindian.awaits;

import java.math.BigInteger;
import java.util.Date;

import com.xindian.awaits.converter.BigIntegerToDateConverter;
import com.xindian.awaits.converter.BigIntegerToLong;

public class ConverterFactory
{
	// FOR TEST
	public static <T, O> Converter getConverter(Class<T> scrType, Class<O> b)
	{
		if (scrType.equals(BigInteger.class) && b.equals(Long.class))
		{
			return new BigIntegerToLong();
		}
		if (scrType.equals(BigInteger.class) && b.equals(Date.class))
		{
			return new BigIntegerToDateConverter();
		}
		return null;
	}
}
