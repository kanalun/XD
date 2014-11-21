package com.xindian.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.exception.BuildActionException;

/**
 * 自带的Action构造器,
 * 
 * 没有依赖注入,
 * 
 * 没有对象缓存,
 * 
 * 不会根据注解或者约定快速生成对象
 * 
 * 什么都没有
 * 
 * @author Elva
 * @date 2011-1-26
 * @version 1.0
 */
public class DefaultActionFactory implements ActionFactory
{
	private static Logger logger = LoggerFactory.getLogger(DefaultActionFactory.class);

	@Override
	public <T> T buildAction(ActionMeta<T> actionMeta) throws BuildActionException
	{
		Class<T> type = actionMeta.getActionClass();
		T object = null;
		try
		{
			object = type.newInstance();
		} catch (InstantiationException e)
		{
			throw new BuildActionException("无法实例化Action[" + type + "]", e);
		} catch (IllegalAccessException e)
		{
			throw new BuildActionException("无法创建基本类型[" + type + "]", e);
		}
		logger.debug("创建Action[" + type + "]");
		return object;
	}
}
