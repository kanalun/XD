package com.xindian.mvc.test.actions;

import com.xindian.mvc.annotation.Action;
import com.xindian.mvc.annotation.Forbidden;
import com.xindian.mvc.annotation.Method;
import com.xindian.mvc.annotation.MethodType;
import com.xindian.mvc.exception.ForbiddenException;

/**
 * 测试Action方法以及方法可见性
 * 
 * @author Elva
 * 
 */
@Action(namespace = "/", name = "md", filterGetterSetter = true)
public class TestMethods
{
	private String userName;

	boolean gender = true;

	/**
	 * 什么也不声明,使用"方法名"作为Action的methodName
	 * 
	 * @return
	 */
	public Object method()
	{
		return "string://method->method()@什么也不声明,使用方法名作为Action的methodName";
	}

	// 使用m作为Action的methodName
	@Method(name = "m")
	public String method1()
	{
		return "string://m->method1()@ 使用m作为Action的methodName";
	}

	public String methodALL0()
	{
		return "string://methodALL0->methodALL0()@默认情况下所有方法都能访问";
	}

	@Method(type = MethodType.ALL)
	public String methodAll1()
	{
		return "string://methodAll1->methodAll1()@所有方法都能访问";
	}

	@Method(type = MethodType.GET)
	public String methodGet()
	{
		return "string://methodGet->methodGet()@GET方法能访问,而POST不能访问";
	}

	/**
	 * 只能用POST的方式访问
	 * 
	 * @return
	 */
	@Method(type = MethodType.POST)
	public String methodPost()
	{
		return "string://methodPost->methodPost()@POST方法能访问,而GET不能访问";
	}

	/****************** 这条线以下所有方法都是无法访问的:即正常情况下不会被执行 ***********************/
	@Forbidden
	public String forbidden0()
	{
		return "";
	}

	@Forbidden(1234)
	public String forbidden1()
	{
		return "";
	}

	@Forbidden(value = 404, message = "Sorry!这个页面不存在+_+!")
	public String forbidden2()
	{
		return "";
	}

	@Forbidden(value = 404, message = "Sorry!这个页面不存在+_+!", exception = ForbiddenException.class)
	public String forbidden3()
	{
		return "";
	}

	/** private方法、protected方法、默认访问性方法不能用作Method即:只有public方法才能用作Action的Method ****/
	@SuppressWarnings("unused")
	private String privateMeatod()
	{
		return userName;
	}

	protected String protectedMeatod()
	{
		return userName;
	}

	String defaultMeatod()
	{
		return userName;
	}

	/** Getter/Setter方法会被过滤掉,因为设置了filterGetterAndSetter = true */
	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public boolean isGender()
	{
		return gender;
	}

	public void setGender(boolean gender)
	{
		this.gender = gender;
	}

}
