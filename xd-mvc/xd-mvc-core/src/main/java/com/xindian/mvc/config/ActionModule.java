package com.xindian.mvc.config;

import java.io.Serializable;

import com.xindian.mvc.Interceptor;

/**
 * Action Group,类似于Guice的Module,用来对Action进行"打包管理"的,在模块上创建的
 * 
 * @author Elva
 * @date 2011-5-23
 * @version 1.0
 */
public class ActionModule implements Serializable
{
	private static final long serialVersionUID = 1L;

	class ActionWarp
	{
		public ActionWarp(Class<?> actionType)
		{
		}
	}

	public ActionModule addInterceptor(Interceptor interceptor)
	{
		return this;
	}
}
