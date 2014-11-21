package com.xindian.mvc.convention;

/**
 * @author Elva
 * @date 2011-3-6
 * @version 1.0
 */
public interface Plugin
{
	/**
	 * 安装插件,安装方法只在安装的时候运行一次
	 */
	void install();

	/**
	 * 卸载插件
	 */
	void uninstall();

}
