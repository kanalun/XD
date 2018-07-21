package com.xindian.ioc.test;

import com.xindian.commons.promise.Singleton;
import com.xindian.ioc.annotation.Implementation;
import com.xindian.ioc.annotation.Scope;
import com.xindian.ioc.annotation.ScopeType;

@Implementation(UserServiceImpl.class)
@Scope(ScopeType.SINGLETON)
public interface UserService extends Singleton
{
	public String doSomething();
}
