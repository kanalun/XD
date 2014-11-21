package com.xindian.ioc.winte;

import com.xindian.ioc.BeanFactory;
import com.xindian.ioc.exception.IOCException;
import com.xindian.mvc.ActionFactory;
import com.xindian.mvc.ActionMeta;
import com.xindian.mvc.exception.BuildActionException;

/**
 * 让Action支持依赖注入
 * 
 * @author Elva
 * @date 2011-1-26
 * @version 1.0
 */
public class IoCActionFactory implements ActionFactory
{
	@Override
	public <T> T buildAction(ActionMeta<T> actionMeta) throws BuildActionException
	{
		try
		{
			return BeanFactory.getSingleton().getBean(actionMeta.getActionClass());
		} catch (IOCException e)
		{
			throw new BuildActionException("无法从IOC容器中得到Action", e);
		}

	}
}
