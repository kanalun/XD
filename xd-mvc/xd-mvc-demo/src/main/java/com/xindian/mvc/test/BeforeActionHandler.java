package com.xindian.mvc.test;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.aop.Handler;
import com.xindian.mvc.exception.VoteException;
import com.xindian.mvc.result.StringResult;

public class BeforeActionHandler implements Handler
{
	@Override
	public void execute() throws VoteException
	{
		ActionContext.getContext().setResult(new StringResult("BeforeActionHandler execute\n"));
		System.out.println("BeforeActionHandler execute");
	}
}