package com.xindian.awaits.converter;

import java.math.BigInteger;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.awaits.Converter;

public class BigIntegerToDateConverter implements Converter
{
	static Logger logger = LoggerFactory.getLogger(BigIntegerToDateConverter.class);

	public Date date;

	public Object convert(Class calzz, Object value)
	{
		logger.debug("RUN ME");
		BigInteger bigInter = (BigInteger) value;
		return new Date(bigInter.longValue());
	}
}