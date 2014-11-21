package com.xindian.mvc.test.actions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.annotation.Action;
import com.xindian.mvc.annotation.DefaultResultHandler;
import com.xindian.mvc.annotation.Result;
import com.xindian.mvc.annotation.Results;
import com.xindian.mvc.result.ErrorResult;
import com.xindian.mvc.result.Forward;
import com.xindian.mvc.result.ProtocolResult;
import com.xindian.mvc.result.Redirect;
import com.xindian.mvc.result.ServletRedirectResultHandler;
import com.xindian.mvc.result.SimpleHTMLResult;
import com.xindian.mvc.result.Stream;
import com.xindian.mvc.result.StringResult;
import com.xindian.mvc.result.freemarker.Freemarker;
import com.xindian.mvc.result.json.JSONResult;
import com.xindian.mvc.result.velocity.Velocity;
import com.xindian.mvc.test.Address;
import com.xindian.mvc.test.TestException;
import com.xindian.mvc.test.User;

import freemarker.template.Template;

/**
 * 本例演示返回结果,原则上XX的Action可以返回任何类型(包括不返回)
 * 
 * ,而系统也会"智能的"对这些返回进行处理,按照合理的、可配置的方式呈现给客户端
 * 
 * @author elva
 * @date 2010-1-18
 * @version 1.0
 */
@Action(name = "rs")
public class TestResults
{
	private User user;

	private String rePassword;

	private String a = "";

	private String name = "Name IN Action";

	/**
	 * 什么也不返回,你会在浏览器中看到白花花的"页面"
	 */
	public void noResult0()
	{
		// RETURN NULL
	}

	/**
	 * 也什么也不返回
	 * 
	 * @return
	 */
	public Object noResult1()
	{
		return null;
	}

	/**
	 * 什么也不返回.用户会傻傻愣在哪儿期待着发生什么,
	 * 
	 * 而你却什么也不返回?这样是"不礼貌"的,使用常规的方式向客户端输出点什么?
	 */
	public void noResult2() throws IOException
	{
		HttpServletResponse response = ActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		Writer out = response.getWriter();
		// response.setContentType("text/html");
		out.append("<center>HI,QCC!请问?中文会乱码么?!</center>" + response.getBufferSize() / 1024)//
		// .close()// 事实上你可以不关闭的!
		;
	}

	/*********** Servlet/JSP Forward *********/
	public Object forward0()
	{
		return "index.jsp";// 注意:后缀为.jsp
	}

	public Object forward1()
	{
		return new Forward("index.jsp");
	}

	// index.jsp?CN="你好中文"
	@SuppressWarnings("deprecation")
	public Object forward2()
	{
		user = new User();
		user.setName("username");
		return new Forward("index.jsp")//
				// .addParameter(user)//
				.addParameter("CN", "你好中文")//
				.setURLEncode("UTF-8");//
	}

	public Object forward3()
	{
		return "forward://index.jsp";// 注意前缀为:"forward://",这个表示一个协议,
	}

	/*********** Servlet/JSP Redirect *********/
	public Object redirect0()
	{
		user = new User();
		return new Redirect("index.jsp");
	}

	public Object redirect4()
	{
		return Redirect.redirect("index.jsp");
	}

	public Object redirect1()
	{
		return "redirect://index.jsp";// 注意前缀为:"redirect://",这个表示一个协议,
	}

	/**
	 * 如果返回字符串,默认的处理方式是Forward,当然,可以用@ResultHandler改变这一默认规则
	 * 
	 * @return
	 */
	@DefaultResultHandler(ServletRedirectResultHandler.class)
	public Object redirect2()
	{
		return "index.jsp";
	}

	@SuppressWarnings("deprecation")
	public Object redirect3()
	{
		user = new User();
		user.setName("username");
		return new Redirect("index.jsp")//
				// .addParameter(user)//
				.addParameter("CN", "你好中文")//
				.setURLEncode("UTF-8");//
	}

	/*********** 返回 Stream *********/
	/**
	 * 如果返回一个输入流,程序会"智能"的选择用流的方式来处理
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public Object stream0() throws FileNotFoundException
	{
		return new FileInputStream("D:\\photo.jpg");
	}

	/**
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public Object stream1() throws FileNotFoundException
	{
		return new Stream(new FileInputStream("D:\\d2.jpg"))//
				.setContentType("application/x-download")//
				.setFileName("qcc.jpg")//
		// .forceToDownload("ccc")
		;
	}

	public Object stream2() throws FileNotFoundException
	{
		return new File("D:\\elva.jpg");
	}

	/**************************************/

	@Result(name = "SUCCESS", value = "index.jsp", type = ServletRedirectResultHandler.class, protocol = "string")
	// 这里的 typeName = "string"会被忽略掉,处程序会优先选择type,只有当type无法处理的时候才会选择协议
	public Object config()
	{
		return "SUCCESS";
	}

	@Results({ @Result(name = "a", value = "a.jsp", protocol = "forward"),//
			@Result(name = "FAIL", value = "fail.jsp", type = ServletRedirectResultHandler.class),//
			@Result(name = "OK", value = "test.jsp", protocol = "forward") })
	@Result(name = "a", value = "success.jsp", type = ServletRedirectResultHandler.class)
	public Object config0()
	{
		return a;
	}

	/*********** 返回 JSON *************/
	public Object json0()
	{
		user = new User();
		user.setName("qcc");
		user.setBirth(new Date());
		user.setPassword("somepass");
		user.setAge(13);
		Address address = new Address();
		address.setAddress("some place!");
		user.setAddress(address);
		return new JSONResult(user);
	}

	// 未实现
	public Object json1()
	{
		return "json://{'a':'1'}";
	}

	public Object json2()
	{
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 10; i++)
		{
			user = new User();
			users.add(user);
		}
		return new JSONResult(users);
	}

	public Object json3()
	{
		Map<String, Object> users = new HashMap<String, Object>();
		for (int i = 0; i < 10; i++)
		{
			user = new User();
			users.put("a" + i, user);
		}
		return new JSONResult(users);
	}

	/*********** 返回错误 ERROR **************/
	/**
	 * 
	 */
	public Object error0()
	{
		return new ErrorResult(404, "页面不存在哦!");
	}

	/**
	 * 把异常作为结果! 直接返回"一个运行时异常",没有在web.xml下捕获这个异常,指定发生这个异常之后的页面或动作
	 * 
	 * @return
	 */
	public Object error1()
	{
		return new ServletException("出错啦!");
	}

	/**
	 * 直接返回特定异常,需要在web.xml下捕获这个异常,指定发生这个异常之后的页面或动作
	 * 
	 * @return
	 */
	public Object error2()
	{
		return new TestException("出错了:页面会定位到testException.jsp上去,因为为我在web.xml中设置了对这个异常进行捕获!");
	}

	/**
	 * 直接返回特定异常,需要在web.xml下捕获这个异常,指定发生这个异常之后的页面或动作
	 * 
	 * @return
	 */
	public Object error3()
	{
		return "error://500";
	}

	/**
	 * 直接返回特定异常,需要在web.xml下捕获这个异常,指定发生这个异常之后的页面或动作
	 * 
	 * @return
	 */
	public Object error4()
	{
		return "error://500,出错了,换一个试试吧?!";
	}

	/*********** 直接像浏览器输出字符串 *********/
	/**
	 * 直接像浏览器输出字符串
	 * 
	 * @return
	 */
	public Object string0()
	{
		return new StringResult("QCC 你好").append("我很好?但愿中文不会乱码...");
	}

	public Object string1()
	{
		// http://
		return "string://Hi!?中文不会乱码!";
	}

	/***************** HTML ********************/

	public Object html0()
	{
		return "html://<html><head><title>title</title></head><body>Hi!?中文不会乱码!<body></html>";
	}

	public Object html1()
	{
		return "html://file://D://test.hmtl";
	}

	public Object html2()
	{
		return new SimpleHTMLResult();
	}

	/*********** Freemarker *********/

	/**
	 * 返回模板后缀为ftl 是默认使用Freemarker来处理
	 */
	public Object ftl0()
	{
		ftl5();
		return "ftl://D://freemarker/temp.txt";
	}

	public Object ftl1()
	{
		return "ftl://file://D://freemarker/temp.ftl";
	}

	public Object ftl2()
	{
		ftl5();
		name = "Zdf的";
		return "ftl://class://com/kan/temp/temp.txt";
	}

	public Object ftl3()
	{
		ftl5();
		name = "qcc";
		return "ftl://class://temp.txt";
	}

	public Object ftl5()
	{
		user = new User();
		user.setName("QCC");
		user.setBirth(new Date());
		user.setPassword("somepass");
		user.setAge(13);
		Address address = new Address();
		address.setAddress("some place!");
		user.setAddress(address);
		// a = "THIS IS A";
		ActionContext.getSession().setAttribute("name", "sessionName");
		ActionContext.getRequest().setAttribute("name", "RequestName");
		// ActionContext.getContext().setCookie("name", "qcc", 1000);
		return new Freemarker("test.ftl").closeBrowserCache();
	}

	/*********** Use Velocity AS View *********/
	public Object vm0()
	{
		return "vm://D:\\freemarker\\test.vm.txt";
	}

	public Object vm1()
	{
		// 从Webroot中获取模板
		return "vm:///test.vm";
	}

	public Object vm()
	{
		//
		ActionContext.getRequest().setAttribute("name", "Name In Request's Attributes");
		ActionContext.getSession().setAttribute("name", "Name In Session's Attributes");
		ActionContext.getServletContext().setAttribute("name", "Name In Application's Attributes");

		ActionContext.getServletContext().setAttribute("Session", ActionContext.getSession(true));
		//
		user.setName("卧石答春绿?");
		user.setPassword("OH YEP!");

		// 让板砖跟臭鸡蛋来的更猛烈些吧
		return new Velocity("D:\\freemarker\\test.vm.txt")//
				.setTemplateDecoding("GB2312")// 试试看将这行注释掉?
		;//
	}

	protected Object tpc()
	{
		// return new ProtocolResult("xml", user);
		return new ProtocolResult("json", user);
	}

	// TODO 参数的顺序是无关紧要的,但是要保证参数的类型一定要不同
	protected Object tpc2() throws FileNotFoundException, IOException
	{
		return new ProtocolResult("ftl", new String("a"), new Integer(2), user);
	}

	protected Object tpc3() throws FileNotFoundException, IOException
	{
		return new ProtocolResult("ftl", user, new Template("a", new FileReader("")));
	}

	protected Object tpc4()
	{
		return new ProtocolResult("jpg", new BufferedImage(500, 200, BufferedImage.TYPE_INT_RGB));
	}

	protected Object tpc5()
	{
		return new ProtocolResult("png", new BufferedImage(500, 200, BufferedImage.TYPE_INT_RGB));
	}

	/*********** 最后是必要的 Getter/Setter *********/
	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public String getRePassword()
	{
		return rePassword;
	}

	public void setRePassword(String rePassword)
	{
		this.rePassword = rePassword;
	}

	public String getA()
	{
		return a;
	}

	public void setA(String a)
	{
		this.a = a;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
