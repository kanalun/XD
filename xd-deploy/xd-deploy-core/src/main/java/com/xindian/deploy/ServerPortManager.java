package com.xindian.deploy;

import java.util.Collection;

/**
 * 
 * private ArrayList<ServerPort> ports;
 * 
 * @author Elva
 * @date 2013-4-18
 * @version 1.0.0
 */
public interface ServerPortManager
{
	/**
	 * 返回所有的客户端
	 * 
	 * @return
	 */
	public Collection<ServerPort> getPorts();

	/**
	 * 注册端口号
	 * 
	 * @param serverPort
	 */
	public void registerPort(ServerPort serverPort);

	/**
	 * 取消注册端口号
	 * 
	 * @param serverPort
	 */
	public void unregisterPort(ServerPort serverPort);
}
