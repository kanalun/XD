package com.xindian.ioc.test;

import com.xindian.commons.promise.Singleton;
import com.xindian.commons.promise.ThreadSafe;
import com.xindian.ioc.annotation.Implementation;
import com.xindian.ioc.annotation.Scope;
import com.xindian.ioc.annotation.ScopeType;

/**
 * @author Elva
 * @date 2011-2-10
 * @version 1.0
 */
@Scope(ScopeType.SINGLETON)
@Implementation(UserActionImpl.class)
public interface IoCUserAction extends Singleton, ThreadSafe
{
	public Object login();

	public Object logOut();

	public Object register();

}
