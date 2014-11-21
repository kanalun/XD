package com.xindian.mvc;

/**
 * ActionContext 初始化后运行
 * 
 * @author Elva
 * 
 */
public interface ActionContextInitializedListener
{
	/**
	 * ActionContext 初始化后运行该方法
	 * 
	 * @param context
	 */
	public void onActionContextInitialized(ActionContext context);
}
