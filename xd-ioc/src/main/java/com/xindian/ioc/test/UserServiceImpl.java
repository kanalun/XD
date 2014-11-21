package com.xindian.ioc.test;

public class UserServiceImpl implements UserService
{
	int i;

	@Override
	public String doSomething()
	{
		System.out.println("UserServiceImpl Works " + i + " time(s)");
		return "UserServiceImpl Works " + i++ + " time(s)";
	}
}
