package com.xindian.mvc.test.actions;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.MVCAction;
import com.xindian.mvc.annotation.After;
import com.xindian.mvc.annotation.Before;
import com.xindian.mvc.annotation.Method;
import com.xindian.mvc.aop.Handler;
import com.xindian.mvc.exception.VoteException;
import com.xindian.mvc.result.StringResult;
import com.xindian.mvc.test.AfterActionHandler;
import com.xindian.mvc.test.BeforeActionHandler;

/**
 * 这种简单的的AOP是有代价:比如破坏程序的封装特性<br/>
 * 虽然这种方式略显笨拙和丑陋,但有时候也还是有用的
 * 
 * @author Elva
 * 
 */
@Before(BeforeActionHandler.class)
@After(AfterActionHandler.class)
public class TestAop implements MVCAction
{
	// Handler必须是可实例化的:外部,内部静态,非抽象的..
	public static class BeforeMethodHandler implements Handler
	{
		@Override
		public void execute() throws VoteException
		{
			((StringResult) (ActionContext.getContext().getResult())).append("BeforeMethodHandler1 excute \n");
			System.out.println("BeforeHandler1 excute");
		}
	}

	public static class BeforeMethodHandler2 implements Handler
	{
		@Override
		public void execute() throws VoteException
		{
			((StringResult) (ActionContext.getContext().getResult())).append("BeforeMethodHandler2 excute \n");
			System.out.println("BeforeHandler1 excute");
		}
	}

	@Method(name = "aop")
	@Before({ BeforeMethodHandler.class, BeforeMethodHandler2.class })
	@After(AfterMethodHandler.class)
	public void aop()
	{
		((StringResult) (ActionContext.getContext().getResult())).append("Method is runing \n");
		System.out.println("aop is runing");
	}

	public static class AfterMethodHandler implements Handler
	{
		@Override
		public void execute() throws VoteException
		{
			((StringResult) (ActionContext.getContext().getResult())).append("AfterMethodHandler excute \n");
			System.out.println("AfterHandler excute");
		}
	}
}
