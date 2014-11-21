package com.xindian.mvc.convention;

/**
 * 插件上下文
 * 
 * @author Elva
 * @date 2011-3-6
 * @version 1.0
 */
public class PluginContext
{
	private static PluginContext pluginContext = new PluginContext();

	public static PluginContext getPluginContext()
	{
		return pluginContext;
	}

	public Object getParam(String name)
	{
		return null;
	}
}
