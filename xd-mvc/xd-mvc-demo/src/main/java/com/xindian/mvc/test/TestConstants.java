package com.xindian.mvc.test;

import java.net.URL;
import java.util.Locale;

import com.xindian.mvc.i18n3.I18N;
import com.xindian.mvc.i18n3.LocalizableResource.DefaultLocale;
import com.xindian.mvc.i18n3.LocalizableResource.Gloabal;
import com.xindian.mvc.i18n3.LocalizableResource.Resource;
import com.xindian.mvc.i18n3.Messages;
import com.xindian.mvc.i18n3.SafeHtml;

/**
 * 测试常量
 * 
 * @author Elva
 * @date 2011-2-3
 * @version 1.0
 */
@DefaultLocale("zh_CN")
@Resource
@Gloabal
public interface TestConstants extends Messages
{
	// 直接使用INSTANCE,不需要在程序中额外创建实例了(无论如何,Constants始终是单例)
	public static TestConstants INSTANCE = I18N.create(TestConstants.class);

	@DefaultStringValue("HELLO")
	public String hello();

	@DefaultBooleanValue(true)
	public boolean isOK();

	@DefaultIntValue(23)
	public int age();

	@DefaultValue("zh_CN")
	public Locale locale();

	@DefaultValue("18.2")
	public float f2();

	@DefaultFloatValue(18.2F)
	public float f();

	@DefaultStringValue("http://www.baidu.com")
	public URL url();

	// ${1}date
	// public String message(int a, @Format("yyyy-MM-dd") Date date, String
	// str);

	@DefaultStringArrayValue({ "a", "b" })
	public String[] test_a_b();

	@DefaultStringValue("<p>HELLO</p> \n  <br/>")
	public SafeHtml safeHtml();// 对返回结果进行SafeHTML

	@DefaultStringValue("<p>HELLO QCC</p>")
	@EscapeXml
	public String safeHtmlResult();// 使用@EscapeXML对返回结果进行SafeHTML

	@DefaultStringValue("<p>HELLO{0}</p>")
	public String safeHtml2(SafeHtml safeHtml);// 只对参数部分进行SafeHtml

	@DefaultStringValue("<p>HELLO{0}</p>")
	public String safeHtmlParma(@EscapeXml String safeHtml);// 只对参数部分进行SafeHtml
}
