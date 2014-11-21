package com.xindian.mvc;

import com.xindian.mvc.exception.BuildActionException;

/**
 * 
 * @author Elva
 * @date 2011-1-27
 * @version 1.0
 */
public interface ActionFactory
{
	public <T> T buildAction(ActionMeta<T> actionMeta) throws BuildActionException;
}
