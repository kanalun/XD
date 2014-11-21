package com.xindian.mvc.test;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.xindian.mvc.i18n3.I18N;
import com.xindian.mvc.i18n3.Messages;

/**
 * 
 * @author Elva
 * @date 2011-2-4
 * @version 1.0
 */
public interface Constants3 extends Messages
{
	public final static Constants3 INSTANCE = I18N.create(Constants3.class);

	@DefaultBooleanValue(false)
	boolean bool();

	@DefaultBooleanArrayValue({ true, false, true })
	boolean[] bools();

	@DefaultIntValue(7)
	int it();

	@DefaultIntArrayValue({ 1, 2, 3 })
	int[] its();

	@DefaultFloatValue(1.2f)
	float flo();

	@DefaultFloatArrayValue({ 1.1f, 2.1f, 3.1f })
	float[] flos();

	@DefaultDoubleValue(1.0D)
	double dou();

	@DefaultDoubleArrayValue({ 1.0D, 1.1d })
	double[] dous();

	@DefaultBigIntegerValue("10")
	BigInteger bigInteger();

	@DefaultBigIntegerArrayValue({ "10", "20" })
	BigInteger[] bigIntegers();

	@DefaultBigDecimalValue("1.1")
	BigDecimal bigDecimal();

	@DefaultBigDecimalArrayValue({ "1.1", "1.2" })
	BigDecimal[] bigDecimals();
}
