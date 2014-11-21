package com.xindian.mvc.test.actions;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.annotation.Action;
import com.xindian.mvc.annotation.Before;
import com.xindian.mvc.annotation.Method;
import com.xindian.mvc.annotation.MethodType;
import com.xindian.mvc.aop.Handler;
import com.xindian.mvc.exception.VoteException;
import com.xindian.mvc.result.Forward;
import com.xindian.mvc.result.Redirect;
import com.xindian.mvc.test.User;
import com.xindian.mvc.utils.FileItem;
import com.xindian.mvc.utils.Validator;
import com.xindian.mvc.validation.Validation.Required;

/**
 * 本例演示用户注册/登录/退出..等常规操作
 * 
 * 该示例用了一组简单的逻辑
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
@Action
public class UserAction
{
	public final static String USER_ONLINE_KEY = "user";

	public final static String ERROR_MESSAGE_KEY = "error_msg";

	@Required
	private User user; // = new User();

	private String rePassword;

	private String errorMessage;

	private FileItem photo;//

	@Method(type = MethodType.POST)
	public Object login(String username, String password)
	{
		// Validate password
		if (Validator.allNotBlank(username, username) && user.getPassword().equals("123"))
		{
			ActionContext.getSession().setAttribute(USER_ONLINE_KEY, user);
			return "redirect://index.jsp";
		} else
		{
			errorMessage = "用户名或密码错误!请使用123作为密码登陆";
			return "login.jsp";
		}
	}

	@Method(type = MethodType.POST)
	public Object login()
	{
		// Validate password
		if (Validator.notNull(user) && Validator.allNotBlank(user.getName(), user.getPassword())
				&& user.getPassword().equals("123"))
		{
			ActionContext.getSession().setAttribute(USER_ONLINE_KEY, user);
			return "redirect://index.jsp";
		} else
		{
			errorMessage = "用户名或密码错误!请使用123作为密码登陆";
			return "login.jsp";
		}
	}

	@Method(type = MethodType.GET)
	public Object logout()
	{
		ActionContext.getSession().removeAttribute(USER_ONLINE_KEY);
		return new Redirect("index.jsp");
	}

	public Object register()
	{
		if (Validator.allNotNull(rePassword, user.getName(), user.getPassword()) && rePassword.equals(user.getPassword()))
		{
			// DO SAVE THIS USER
			return new Redirect("login.jsp");
		} else
		{
			ActionContext.getRequest().setAttribute(ERROR_MESSAGE_KEY, "两次密码不相同!");
			return new Forward("register.jsp").addParameter("ERROR_MESSAGE_KEY", "两次密码不相同!");
		}
	}

	@Before(CheckAdmin.class)
	public Object adminLogin()
	{
		return login();
	}

	static class CheckAdmin implements Handler
	{
		@Override
		public void execute() throws VoteException
		{

		}
	}

	// --------------Getter/Setter-------------

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

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

}
