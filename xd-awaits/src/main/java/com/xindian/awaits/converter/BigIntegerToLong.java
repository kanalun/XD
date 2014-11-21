package com.xindian.awaits.converter;

import java.math.BigInteger;

import com.xindian.awaits.Converter;

public class BigIntegerToLong implements Converter
{

	public Object convert(Class calzz, Object value)
	{
		BigInteger bigInter = (BigInteger) value;
		return bigInter.longValue();
	}
}
