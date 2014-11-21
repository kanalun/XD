package com.xindian.mvc;

/**
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
public interface ActionContextListener extends ActionContextDestoryListener, ActionContextInitializedListener
{
	// Initialized
	public void onActionContextCreated(ActionContext context);

	// Destroyed
	public void onActionContextDestoryed(ActionContext context);
}
