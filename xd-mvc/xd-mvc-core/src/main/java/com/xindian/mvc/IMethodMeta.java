package com.xindian.mvc;

import java.lang.reflect.Method;

import com.xindian.mvc.annotation.MethodType;
import com.xindian.mvc.aop.Handler;
import com.xindian.mvc.exception.VoteException;
import com.xindian.mvc.result.ResultHandler;

public interface IMethodMeta
{
	public Class<? extends ResultHandler> getResultHandler(String resultName);

	public ResultMeta getResultMeta(String name);

	public void addResultMeta(ResultMeta resultMeta);

	public ForbiddenMeta getForbiddenMeta();

	public String getMethodName();

	public Class<? extends Handler>[] getBefore();

	public Class<? extends Handler>[] getAfter();

	public Object invoke(Object bean);

	public void invokeBefores() throws VoteException, InstantiationException, IllegalAccessException;

	public void invokeAfters() throws VoteException, InstantiationException, IllegalAccessException;

	public MethodType getType();

	public boolean isForbidden();

	public boolean canDoGet();

	public boolean canDoPost();

	public boolean canDoMethodType(String method);

	public Class<? extends ResultHandler> getDefaultResultHandlerType();

	public Method getMethod();

	public ActionMeta<?> getActionMeta();
}
