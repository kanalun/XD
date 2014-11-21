package com.xindian.ioc.test;

import com.xindian.ioc.annotation.Autowired;
import com.xindian.ioc.annotation.Qualifier;

public class UserActionImpl implements IoCUserAction
{
	static class User
	{

	}

	@Autowired
	@Qualifier("userService")
	public UserService userService;

	private User user;

	private String rePassword;

	@Override
	public Object login()
	{
		return "string://" + userService.doSomething();
	}

	@Override
	public Object logOut()
	{
		return null;
	}

	@Override
	public Object register()
	{
		return null;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

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

}
