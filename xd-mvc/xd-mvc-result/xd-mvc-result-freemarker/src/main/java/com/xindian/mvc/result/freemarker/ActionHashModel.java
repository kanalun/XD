package com.xindian.mvc.result.freemarker;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;

/**
 * BeanModel wrap for the Action object
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class ActionHashModel extends BeanModel
{
	public ActionHashModel(Object action, BeansWrapper beansWrapper)
	{
		super(action, beansWrapper);
	}

	public ActionHashModel(Object action)
	{
		super(action, new BeansWrapper());
	}
}
