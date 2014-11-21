package com.xindian.mvc.test;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.aop.Handler;
import com.xindian.mvc.exception.VoteException;
import com.xindian.mvc.result.StringResult;

public class AfterActionHandler implements Handler
{
	@Override
	public void execute() throws VoteException
	{
		((StringResult) (ActionContext.getContext().getResult())).append("AfterActionHandler execute \n");
		System.out.println("AfterActionHandler execute");
	}
}