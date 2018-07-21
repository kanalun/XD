package com.xindian.mvc.test.actions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xindian.beanutils.annotation.Element;
import com.xindian.beanutils.annotation.Type;
import com.xindian.beanutils.annotation.UseConverter;
import com.xindian.mvc.ActionContext;
import com.xindian.mvc.annotation.Action;
import com.xindian.mvc.annotation.Constant;
import com.xindian.mvc.annotation.Forbidden;
import com.xindian.mvc.annotation.Hook;
import com.xindian.mvc.annotation.Method;
import com.xindian.mvc.annotation.MethodType;
import com.xindian.mvc.annotation.Origin;
import com.xindian.mvc.annotation.Parameter;
import com.xindian.mvc.annotation.Result;
import com.xindian.mvc.annotation.Results;
import com.xindian.mvc.annotation.ReturnedValue;
import com.xindian.mvc.conversion.converters.EnumConverter.Gender;
import com.xindian.mvc.i18n.ConstantUtils2;
import com.xindian.mvc.result.Forward;
import com.xindian.mvc.result.Redirect;
import com.xindian.mvc.result.ServletRedirectResultHandler;
import com.xindian.mvc.result.Stream;
import com.xindian.mvc.result.StringResult;
import com.xindian.mvc.test.User;
import com.xindian.mvc.utils.FileItem;

/**
 * 
 * @author Elva
 * @date 2011-1-19
 * @version 1.0
 */
// @Forbidden
@Action(namespace = "a", name = "test", defaultMethod = "defaultMethod")
public class TestAction
{
	@Parameter(name = "username", origin = Origin.SESSION)
	private String userName;

	private User u;// = new User();

	private String password;

	private FileItem file;

	@Parameter(name = "birth", format = "yyyy-MM-dd", defaultValue = "null")
	private Date birth;// String to date

	private int age;

	// String to enum
	Gender gender2;

	@UseConverter(Date.class)
	private boolean gender;

	// 数组赋值
	private String[] ss;

	private int[] ii;

	@Type(ArrayList.class)
	@Element(String.class)
	// 在不使用泛型的情况下
	private List<String> list;

	private Map<String, Object> map;

	private Set<String> set;
	// 常量注入?
	@Constant
	private String constant;

	@ReturnedValue
	String hello;// 要被返回 数据

	/**
	 * 访问这个/test.ftl会先调用这个方法,然后返回到/test.ftl
	 * 
	 * @return
	 */
	@Hook("test.ftl")
	public String excute()
	{//
		String string = "ERROR";
		try
		{
			string = new String(ConstantUtils2.getText("hello", "").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Enumeration e = ActionContext.getRequest().getLocales();
		string = "";
		while (e.hasMoreElements())
		{
			string = string + "\n" + e.nextElement();
		}
		return "string://" + string;
		// return "string://" + ActionContext.getRequest().getLocale();
	}

	public Object list()
	{
		StringResult rs = new StringResult();
		rs.closeBrowserCache();
		if (list != null)
		{
			for (String s : list)
			{
				rs.append(s + "\n");
			}
		}
		return rs;
		// return "string://" + ss[1];
	}

	public String nameExecute(String username, String password)
	{
		return null;
	}

	public Object u()
	{
		StringResult rs = new StringResult();
		rs.closeBrowserCache();
		if (u != null)
		{
			rs.append(u.getAddresses()[0].getCity() + "fffffffff");
		}
		return rs;
		// return "string://" + ss[1];
	}

	public String x()
	{
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(ActionContext.getRequest().getMethod())
				.append(ActionContext.getRequest().getRequestURI())
				.append(ActionContext.getRequest().getQueryString());
		return "string://" + "URI: " + ActionContext.getRequest().getRequestURI() + "\nURL: "
				+ ActionContext.getRequest().getRequestURL() + "\n" + stringBuffer
				+ "\nContextPath: " + ActionContext.getRequest().getContextPath()
				+ "\nServletPath: " + ActionContext.getRequest().getServletPath();
	}

	public String defaultMethod()
	{
		return "string://defaultMethod()";
	}

	@Method(name = "say")
	@Result(name = "SUCCESS", value = "success.jsp")
	@Results(//
	{ @Result(name = "SUCCESS", value = "success.jsp", protocol = "forward"),//
			@Result(name = "FAIL", value = "fail.jsp", type = ServletRedirectResultHandler.class),//
			@Result(name = "OK", value = "test.jsp") //
	})
	public String sayHello()// 系统会从配置的结果中搜索合适的结果,如果结果中不存在合适的结果,则采用默认的方式
	{
		System.out.println("TestAction:sayHello");
		System.out.println("userName:" + userName);
		System.out.println("password:" + password);
		hello = "Hello! " + userName;
		return "test1.jsp";
	}

	@Method(type = MethodType.GET)
	public String onlyGet()
	{
		return "index.jsp";
	}

	@Method(type = MethodType.POST)
	public String onlyPost()
	{
		return "index.jsp";
	}

	@Forbidden(404)
	public String add()
	{
		System.out.println("TestAction:add");
		System.out.println("AGE:" + age);
		// System.out.println(ActionContext.getContext().getHttpServletRequest().getParameterValues("ss").length);
		// System.out.println("ss:" + ss.length);
		// PrintUtils.pln(ss);
		// ActionContext.getContext().getHttpServletRequest().getParameterMap();
		return "index.jsp";
	}

	// @Forbidden(404)
	public String p()
	{
		pln("userName", userName);
		pln("birth", birth);
		pln("age", age);
		for (String s : ss)
		{
			pln("ss:-----------", s);
		}
		for (int i : ii)
		{
			pln("ii:-----------", i);
		}
		pln("u", u);
		pln("u.name", u.getName());
		return "../index.jsp";
	}

	public Object remove()
	{
		System.out.println("TestAction:remove");
		return new Forward("index.jsp").addParameter("c", "WVV");
		// return "index.jsp";
	}

	public Object ac()
	{
		System.out.println("TestAction:remove");//
		return new Redirect("test1.jsp")//
				.addParameter("cn", "你好中文")//
				// .addParameter(u)//
				.setURLEncode("UTF-8")//
		;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	/**************** 测试: 向客户端输出流 *********************/
	public Object stream()
	{
		try
		{
			return new Stream(new FileInputStream("D:\\创建相册.jpg"));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return e;
		}
	}

	@Forbidden
	public String getUserName()
	{
		return userName;
	}

	@Forbidden
	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	@Forbidden
	public String getPassword()
	{
		return password;
	}

	@Forbidden
	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getHello()
	{
		return hello;
	}

	public void setHello(String hello)
	{
		this.hello = hello;
	}

	@Forbidden
	public String[] getSs()
	{
		return ss;
	}

	@Forbidden
	public void setSs(String[] ss)
	{
		this.ss = ss;
	}

	@Forbidden
	public User getU()
	{
		return u;
	}

	@Forbidden
	public void setU(User u)
	{
		this.u = u;
	}

	@Forbidden
	public FileItem getFile()
	{
		return file;
	}

	@Forbidden
	public void setFile(FileItem file)
	{
		this.file = file;
	}

	@Forbidden
	public Date getBirth()
	{
		return birth;
	}

	@Forbidden
	public void setBirth(Date birth)
	{
		this.birth = birth;
	}

	@Forbidden
	public boolean isGender()
	{
		return gender;
	}

	@Forbidden
	public void setGender(boolean gender)
	{
		this.gender = gender;
	}

	private void pln(String key, Object value)
	{
		System.out.print("FILED = " + key + "	VALUE = " + value);
		if (value != null)
		{
			System.out.println("	TYPE = " + value.getClass());
		} else
		{
			System.out.println("	TYPE = NULL");
		}
	}
}
