package com.xindian.mvc.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import com.xindian.mvc.conversion.converters.EnumConverter.Gender;
import com.xindian.mvc.i18n3.Constants;
import com.xindian.mvc.i18n3.I18N;
import com.xindian.mvc.i18n3.LocalizableResource.DefaultLocale;
import com.xindian.mvc.i18n3.LocalizableResource.Resource;
import com.xindian.mvc.i18n3.Messages.DefaultMessage;
import com.xindian.mvc.i18n3.SafeHtml;

public class ConstantsTest
{
	public static interface C extends Constants
	{
		public static C INSTANCE = I18N.create(C.class);

		@DefaultStringValue("HELLO,QCC")
		public String age();

		@DefaultStringMapValue({ "key0:value0", "key1:value1" })
		public HashMap<String, String> map();// 实际上不是使用HashMap,而是使用HashMap的扩展ReadOnlyHashMap,这个MAP是只读的,你无法使用,map的写入方法对该MAP进行写入操作

		@DefaultBigIntegerArrayValue({ "1", "2" })
		public BigInteger[] bigIntegers();

		@DefaultBigIntegerValue("100")
		public BigInteger bigInteger();

		@DefaultBigDecimalArrayValue({ "1", "2.2" })
		public BigDecimal[] bigDecimals();

		@DefaultBigDecimalValue("1.1111")
		public BigDecimal bigDecimal();

		@DefaultIntValue(7)
		public int it();

		@DefaultStringValue("zh_CN")
		public Locale lc();

		public SafeHtml safeHtml();

		/********** 枚举 **********/
		@DefaultEnumValue("MALE")
		public Gender gender();

		@DefaultEnumArrayValue({ "ALL", "MALE" })
		public Gender[] genders();
	}

	@Resource(name = "i18nTest")
	@DefaultLocale("zh")
	static interface Msg extends C
	{
		public static Msg INSTANCE = I18N.create(Msg.class);

		@DefaultMessage("你好:{0}! 现在是:{1,date,yyyy年MM月dd日,E,HH:mm:ss}!")
		String msg(String name, Date msg);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// 1,查找参数提供语言环境的本地化属性文件: Locale.FRANCE
		// 2,查找JRE运行环境的本地化属性文件: Locale.getDefault()
		// 3,查找
		ResourceBundle resourceBundle = ResourceBundle.getBundle("com.kan.mvc.test.TestConstants", Locale.FRANCE);

		System.out.println("------------" + resourceBundle.getString("safeHtml"));

		System.out.println("------------" + resourceBundle.getLocale());

		// ResourceProvider defaultResourceProvider = new
		// DefaultResourceProvider();

		// DefaultTextProvider textProvider = DefaultTextProvider.INSTANCE;

		// SimpleLocaleProvider localeProvider = new SimpleLocaleProvider();

		// localeProvider.setLocale(Locale.FRANCE);

		// textProvider.setLocaleProvider(localeProvider);

		// textProvider.setResourceProvider(defaultResourceProvider);

		System.out.println(Msg.INSTANCE.bigDecimal());

		System.out.println("------------" + TestConstants.INSTANCE.safeHtml());

		System.out.println(Msg.INSTANCE.msg("Big Boss", new Date()));

		// System.out.println(Constants3.INSTANCE.bool());

	}
}
