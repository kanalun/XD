package com.xindian.anticrawler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端信息,这个东西存储在一个列表中,
 * 
 * 然后对他进行维护,每次有请求就更新或者添加这个这个列表或者中的元素,
 * 
 * 另外开一个线程对这个列表进行分析,把里面可以的东西中重点标注,重点监控,
 * 
 * 发现符合某些特定规则的坏分子发送过来的请求,将其动态过滤
 * 
 * 1.分析路径的特定规律
 * 
 * 2.分析请求间隔频率及规律
 * 
 * 3.分析userAgent信息
 * 
 * 4.分析IP地址
 * 
 * @author Elva
 * @date 2011-1-21
 * @version 1.0
 */
public class Client
{
	String ip;// 用户IP

	String userAgent;

	int count;// 记录访问次数

	int frequency;//

	private List<String> path;// 一段时间内这个客户端访问过的路径

	HttpServletRequest request;

	{
		// request.get
	}
}
