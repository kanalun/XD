package com.xindian.mvc.test.actions;

import java.util.Date;

import com.xindian.mvc.annotation.Constant;
import com.xindian.mvc.test.TestConstants;
import com.xindian.mvc.web.ActionSupport;

@SuppressWarnings("serial")
public class I18NAction extends ActionSupport
{
	@Constant
	String constant;

	public String a()
	{
		// testConstants.age();

		// testConstants.x("test", new Date());
		return "string://" + getText("hello");
	}

	public String text()
	{
		return "string://" + getText("message", "", 1, new Date(), "哇哈哈");
	}

	public String msg()
	{
		return "";
		// return "string://" + TestConstants.INSTANCE.message(125, new Date(),
		// "MESSAGE");
	}

	public String age()
	{
		return "string://" + TestConstants.INSTANCE.test_a_b()[0];
	}
}
