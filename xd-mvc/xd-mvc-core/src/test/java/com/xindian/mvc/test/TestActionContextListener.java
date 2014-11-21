package com.xindian.mvc.test;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.ActionContextListener;

/**
 * 测试用ActionContextListener
 * 
 * @author Elva
 * 
 */
public class TestActionContextListener implements ActionContextListener
{
	@Override
	public void onActionContextInitialized(ActionContext context)
	{

	}

	@Override
	public void onActionContextCreated(ActionContext context)
	{
		System.out.println("onActionContextCreate : " + context);
	}

	@Override
	public void onActionContextDestoryed(ActionContext context)
	{
		System.out.println("onActionContextDestory : " + context);
	}

}
