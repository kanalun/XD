package com.xindian.deploy;

/**
 * @author Elva
 * @date 2014-5-23
 * @version 1.0
 */
public interface Container
{
	// 想容器注册端口
	// 从容器中获得上下文信息和特定的执行环境,比如ClassPath信息等
	// 向容器发布
	// 告知容器模块所感兴趣的信息

	// 比如:配置中心中的配置发生变化->通过网络通知容器->容器向特定的模块发送该事件
	// ->具体模块做出响应,结束之后返回一个状态(或者一个特定的消息)
	// ->容器得到消息,将消息发送给配置中心告诉处理情况
	int addListener();// 这是一个同步方法,模块同步告诉容器,容器和其他系统通信使用同步或者异步由容器决定
}
