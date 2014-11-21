package com.xindian.mvc;

import com.xindian.mvc.exception.VoteException;

public interface IActionMeta<T>
{
	boolean isForbidden();

	ForbiddenMeta getForbiddenMeta();

	Class<T> getActionClass();

	String getActionName();

	String getNamespace();

	String getDefaultMethodName();

	void setDefaultMethodName(String defaultMethodName);

	MethodMeta getMethodMeta(String methodname);

	void invokeBefores() throws VoteException, InstantiationException, IllegalAccessException;

	void invokeAfters() throws VoteException, InstantiationException, IllegalAccessException;
}
